package com.sftelehealth.doctor.app.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.sftelehealth.doctor.BR;
import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.helpers.FileUtils;
import com.sftelehealth.doctor.app.internal.di.components.UseCaseComponent;
import com.sftelehealth.doctor.app.listener.ProfileUpdateListener;
import com.sftelehealth.doctor.app.utils.Constant;
import com.sftelehealth.doctor.app.utils.PermissionsHelper;
import com.sftelehealth.doctor.app.utils.crop.Crop;
import com.sftelehealth.doctor.app.view.Navigator;
import com.sftelehealth.doctor.app.view.activity.MainActivity;
import com.sftelehealth.doctor.app.view.activity.ProfileDetailsActivity;
import com.sftelehealth.doctor.app.view.fragment.dialog.ChooseCameraGallery;
import com.sftelehealth.doctor.app.view.helper.SnackbarHelper;
import com.sftelehealth.doctor.app.view.viewmodel.DoctorProfileFragmentViewModel;
import com.sftelehealth.doctor.app.view.viewmodel.ViewModelFactory;
import com.sftelehealth.doctor.databinding.FragmentDoctorProfileBinding;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static android.app.Activity.RESULT_OK;
import static com.sftelehealth.doctor.app.utils.Constant.REQUEST_CAMERA;
import static com.sftelehealth.doctor.app.utils.Constant.SELECT_IMAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorProfileFragment extends Fragment implements ProfileUpdateListener, PermissionsHelper.PermissionCallback, ChooseCameraGallery.ImageOptionSelectionListener{

    UseCaseComponent useCaseComponent;

    FragmentDoctorProfileBinding binding;
    DoctorProfileFragmentViewModel viewModel;

    PermissionsHelper ph;
    String directoryCroppedImage = "";
    File f;
    File userImageFile;

    boolean showCameraGalleryDialog = false;

    boolean galleryResultReceived = false;
    Intent galleryResultData = null;

    private BroadcastReceiver uploadStatusReceiver;

    public DoctorProfileFragment() {}

    public static DoctorProfileFragment newInstance(boolean validateModel) {
        Bundle args = new Bundle();
        args.putBoolean("validate_model", validateModel);
        DoctorProfileFragment doctorProfileFragment = new DoctorProfileFragment();
        doctorProfileFragment.setArguments(args);
        return doctorProfileFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doctor_profile, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // initialize injector and get the use case component
        if(getActivity() instanceof MainActivity)
            useCaseComponent = ((MainActivity)getActivity()).getUseCaseComponent();
        else
            useCaseComponent = ((ProfileDetailsActivity)getActivity()).getUseCaseComponent();

        viewModel = obtainDoctorProfileViewModel();
        viewModel.validateModel = getArguments().getBoolean("validate_model");

        ph = new PermissionsHelper();

        setUpObservers();
        setUpData();

        uploadStatusReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
                String message = String.valueOf(intent.getIntExtra("upload_status", 0));
                Log.d("receiver", "Got message: " + message);

                binding.profileHeader.uploadStatus.setVisibility(View.VISIBLE);
                binding.profileHeader.uploadStatus.setText(message + " %");
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        if(showCameraGalleryDialog) {
            showCameraGalleryDialog = false;
            showGalleryCameraChooser();
        }

        viewModel.getDoctorDetails();

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(uploadStatusReceiver, new IntentFilter("doctor_image_upload_status_broadcast"));
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(uploadStatusReceiver);
        super.onPause();
    }

    public DoctorProfileFragmentViewModel obtainDoctorProfileViewModel() {

        ViewModelFactory factory = new ViewModelFactory(useCaseComponent);
        return ViewModelProviders.of(this, factory).get(DoctorProfileFragmentViewModel.class);   //new CallbackListFragmentViewModel();
    }

    private void setUpObservers() {

        // Once doctor details is fetched, this variable will be used to notify a successful fetch
        viewModel.doctorDetailsFetched.observe(this, aBoolean -> {
            if(aBoolean) {
                binding.setItem(viewModel.doctor.get());

                binding.profileHeader.uploadStatus.setVisibility(View.GONE);

                //Text for signature Button
                if(TextUtils.isEmpty(viewModel.doctor.get().getSignatureImage())) {
                    binding.addUpdateSignature.setText("Add Signature");
                    binding.addSignatureMessage.setVisibility(View.VISIBLE);
                } else {
                    binding.addUpdateSignature.setText("Update Signature");
                    binding.addSignatureMessage.setVisibility(View.GONE);

                    Picasso.with(getContext())
                            .load(viewModel.doctor.get().getSignatureImage())
                            .into(binding.signature);
                }

                binding.notifyPropertyChanged(BR.item);

                /*if(getActivity() instanceof MainActivity) {
                    // setup the save button based on which view this fragment is in
                    binding.save.setText("Save");
                } else if (getActivity() instanceof LoginActivity) {
                    binding.save.setText("Done");
                }*/
            }
        });

        // whenever model is validated, the validation result will be propagated via isModelValidated
        viewModel.isModelValidated.observe(this, aBoolean -> {
            if(aBoolean) {
                Navigator.navigateToMainView(getContext());
                // save the doctor profile model
                /*if(getActivity() instanceof MainActivity) {
                    // check if the parent activity is MainActivity then update and stay else go to MainActivity after a successful update

                } else if (getActivity() instanceof LoginActivity) {
                    // if not in MainActivity then update doctor details and move to the next view.

                }*/
            } else {
                SnackbarHelper.getCustomSnackBar(binding.getRoot(), viewModel.validationMessage, null, SnackbarHelper.SnackbarTypes.ERROR).show();
            }
        });
    }

    private void setUpData () {
        binding.setListener(this);
        binding.setViewmodel(viewModel);
    }

    @Override
    public void updateImage() {
        ph.requestCameraPermissions(getContext(), getActivity(), this, this);
    }

    @Override
    public void validateAndSaveDoctorModel() {
        viewModel.validateProfile();
    }

    @Override
    public void addUpdateSignature() {
        ph.requestFileSystemPermissions(getContext(), getActivity(), this, this);
    }

    @Override
    public void permissionGranted(PermissionsHelper.PermissionTypes permissionType) {
        if(permissionType == PermissionsHelper.PermissionTypes.READ_WRITE_STORAGE) {
            Navigator.navigateToSignatureScreen(getContext(), viewModel.doctor.get().getId());
        } else if (permissionType == PermissionsHelper.PermissionTypes.CAMERA_AND_GALLERY) {
            showGalleryCameraChooser();
        }
    }

    @Override
    public void permissionDenied(PermissionsHelper.PermissionTypes permissionType) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Constant.PERMISSIONS_REQUEST_CAMERA: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //cameraAccepted = true;
                    /*for (int i=0; i<grantResults.length; i++) {
                        if(grantResults[i] == PackageManager.PERMISSION_DENIED)
                            cameraAccepted = false;
                    }*/
                    //showGalleryCameraChooser();
                    showCameraGalleryDialog = true;

                } else {
                    showCameraGalleryDialog = false;
                }
            }
            break;
            /*case Constant.PERMISSIONS_REQUEST_GET_ACCOUNTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    accountsAccepted = true;
                    fillUserPrimaryEmail();
                } else {
                    // permission not granted so make the email field editable
                    email.setOnTouchListener(null);
                }
            }*/
        }
    }

    private void showGalleryCameraChooser() {
        Bundle args = new Bundle();

        ((DialogFragment) Fragment.instantiate(getContext(),
                ChooseCameraGallery.class.getName(), args))
                .show(getChildFragmentManager(), ChooseCameraGallery.class.getName());
    }

    @Override
    public void onImageOptionSelected(View v) {
        if (v.getId() == R.id.cameraContainer) {
            // start intent for camera to capture image
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            f = createImageFile();
            Uri imageURI = FileProvider.getUriForFile(getContext(),
                    getContext().getApplicationContext().getPackageName() + ".fileprovider",
                    f);
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
            startActivityForResult(intent, REQUEST_CAMERA);
        } else if (v.getId() == R.id.galleryContainer) {
            Intent intent = new Intent(
                    Intent.ACTION_PICK, //Intent.ACTION_GET_CONTENT
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(
                    Intent.createChooser(intent, "Select Image"),
                    SELECT_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                //setUserImageBitmap(BitmapFactory.decodeFile(f.getPath()));
                directoryCroppedImage = f.getParent();
                beginCrop(f, false);
                userImageFile = f;      //onCaptureImageResult(data);
            } else if (requestCode == SELECT_IMAGE) {
                // perform this in the onPostResume as activity state is not restored "onActivityResult" if once destroyed due to low memory etc.
                // {https://stackoverflow.com/questions/16265733/failure-delivering-result-onActivityForResult}
                // galleryResultReceived = true;
                // galleryResultData = data;
                f = onSelectFromGalleryResult(data);
                directoryCroppedImage = createCroppedImageFile().getParent();

                beginCrop(f, true);

            } else if (requestCode == Crop.REQUEST_CROP) {
                if(f != null) {
                    userImageFile = new File(directoryCroppedImage, "cropped");
                    // userImageFile  = new File(imageCompressionHelper.compressProfileImage(userImageFile.getPath()));
                    setUserImageBitmap(BitmapFactory.decodeFile(userImageFile.getPath()));

                    updateProfileImage(userImageFile.getPath());
                } else {
                    SnackbarHelper.getCustomSnackBar(binding.getRoot(), "Some error occurred... Please try again", null, SnackbarHelper.SnackbarTypes.ERROR);
                }
            }
        }
    }

    private File onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        File destination = null;
        if (data != null) {
            try {
                destination = FileUtils.getFile(getContext().getApplicationContext(), data.getData());
                bm = MediaStore.Images.Media.getBitmap(getContext().getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setUserImageBitmap(bm);

        return destination;
    }

    private void beginCrop(File source, boolean isFromGallery) {

        Uri sourceUri;
        Uri outputUri;

        // if the file is loaded from gallery the just get the Uri directly without calling the file provider.
        if(isFromGallery) {
            sourceUri = Uri.fromFile(source);
            outputUri = Uri.fromFile(createCroppedImageFile());  //FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".fileprovider", new File(source.getParent()));  //Uri.fromFile(new File(source.getParent(), "cropped"));
        } else {
            sourceUri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".fileprovider", source);   //Uri.fromFile(source);
            outputUri = FileProvider.getUriForFile(getContext(),  getContext().getApplicationContext().getPackageName() + ".fileprovider", new File(source.getParent(), "cropped"));  //Uri.fromFile(new File(source.getParent(), "cropped"));
        }

        new Crop(sourceUri).output(outputUri).asSquare().start(getContext(), this);
        Log.d("ProfileFragment", "source" + source.toString());
    }

    private File createImageFile(){

        String imageFileName = "_temp_doctor24x7";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    private File createCroppedImageFile() {

        String imageFileName = "cropped";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);    //getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        image = new File(storageDir, imageFileName); //File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }

    private void setUserImageBitmap(Bitmap bitmap) {
        binding.profileHeader.doctorImage.setImageBitmap(bitmap);
    }

    private void updateProfileImage(String filePath) {
        viewModel.updateDoctorImage(filePath);
    }

}
