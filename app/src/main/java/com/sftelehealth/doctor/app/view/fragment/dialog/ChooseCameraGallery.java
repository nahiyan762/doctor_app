package com.sftelehealth.doctor.app.view.fragment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.view.fragment.DoctorProfileFragment;


/**
 * Created by rahul on 07/11/16.
 */
public class ChooseCameraGallery extends AppCompatDialogFragment implements View.OnClickListener{

    //TextView camera, gallery;
    TextView close;
    LinearLayout cameraContainer, galleryContainer;
    ImageOptionSelectionListener imageOptionSelectionListener;

    public ChooseCameraGallery() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dialog_camera_gallery, container, false);

        //camera = (TextView) v.findViewById(R.id.camera);
        //gallery = (TextView) v.findViewById(R.id.gallery);

        cameraContainer = (LinearLayout) v.findViewById(R.id.cameraContainer);
        galleryContainer = (LinearLayout) v.findViewById(R.id.galleryContainer);

        close = (TextView) v.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseCameraGallery.this.dismiss();
            }
        });

        cameraContainer.setOnClickListener(this);
        galleryContainer.setOnClickListener(this);

        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getActivity(), getTheme());
    }

    @Override
    public void onClick(View v) {

        if(getParentFragment() instanceof DoctorProfileFragment) {
            ((DoctorProfileFragment)getParentFragment()).onImageOptionSelected(v);
        }
       /* if(getActivity() instanceof  ProfileDetailActivity)
            ((ProfileDetailActivity)getActivity()).onImageOptionSelected(v);
        else if (getActivity() instanceof EditProfileActivity)
            ((EditProfileActivity)getActivity()).onImageOptionSelected(v);
        else if (getActivity() instanceof AddProfileActivity)
            ((AddProfileActivity)getActivity()).onImageOptionSelected(v);*/
        this.dismiss();
        /*if(imageOptionSelectionListener != null)
            imageOptionSelectionListener.onImageOptionSelected(v);*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //imageOptionSelectionListener = (ImageOptionSelectionListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //imageOptionSelectionListener = null;
    }

    static class BottomSheetDialog extends com.google.android.material.bottomsheet.BottomSheetDialog {
        public BottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
            super(context, theme);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            int width = getContext().getResources().getDimensionPixelSize(R.dimen.bottom_sheet_width);
            //noinspection ConstantConditions
            getWindow().setLayout(
                    width > 0 ? width : ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    public interface ImageOptionSelectionListener {
        public void onImageOptionSelected(View v);
    }

}