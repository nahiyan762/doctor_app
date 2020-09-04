package com.sftelehealth.doctor.app.view.activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.internal.di.components.DaggerUseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.components.UseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.modules.UseCaseModule;
import com.sftelehealth.doctor.app.listener.OnPenMovedListener;
import com.sftelehealth.doctor.app.view.helper.signature.SignatureView;
import com.sftelehealth.doctor.app.view.utils.ActivityUtils;
import com.sftelehealth.doctor.app.view.viewmodel.SignatureActivityViewModel;
import com.sftelehealth.doctor.app.view.viewmodel.ViewModelFactory;

import static com.sftelehealth.doctor.app.utils.Constant.DOCTOR_ID;

public class SignatureActivity extends BaseAppCompatActivity implements OnPenMovedListener {

    SignatureView signatureView;
    Menu menu;

    File file;

    Boolean disabled = true;

    UseCaseComponent useCaseComponent;

    SignatureActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        ActivityUtils.setUpToolbar(this, "Signature");

        initializeInjector();

        signatureView = (SignatureView) findViewById(R.id.signature_view);
        signatureView.setPenMovedListener(this);

        viewModel = obtainViewModel(this);
        viewModel.doctorId = getIntent().getIntExtra(DOCTOR_ID, 0);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        setUpObservers();
    }

    private void setUpObservers() {
        viewModel.doctorSignatureUpdated.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;

        getMenuInflater().inflate(R.menu.menu_signature, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!disabled)
            switch (item.getItemId()) {
                case R.id.home:
                    finish();
                    return true;
                case R.id.action_clear:
                    signatureView.clearCanvas();//Clear SignatureView
                    /*Toast.makeText(getApplicationContext(),
                            "Clear canvas", Toast.LENGTH_SHORT).show();*/
                    return true;
                case R.id.action_save:
                    File directory = Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    Log.d("image folder", directory.getAbsolutePath());
                    directory.mkdirs();
                    file = new File(directory, System.currentTimeMillis() + ".png");
                    FileOutputStream out = null;
                    Bitmap bitmap = signatureView.getSignatureBitmap();
                    try {
                        out = new FileOutputStream(file);
                        if (bitmap != null) {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        } else {
                            throw new FileNotFoundException();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null) {
                                out.flush();
                                out.close();

                                if (bitmap != null) {
                                    /*Toast.makeText(getApplicationContext(),
                                            "Image saved successfully at " + file.getPath(), Toast.LENGTH_LONG).show();*/
                                    //new AsyncImageCompression(SignatureActivity.this, file.getPath(), SignatureActivity.this).execute("");
                                    viewModel.updateSignature(file.getPath());
                                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                                        new MyMediaScanner(this, file);
                                    } else {
                                        ArrayList<String> toBeScanned = new ArrayList<String>();
                                        toBeScanned.add(file.getAbsolutePath());
                                        String[] toBeScannedStr = new String[toBeScanned.size()];
                                        toBeScannedStr = toBeScanned.toArray(toBeScannedStr);
                                        MediaScannerConnection.scanFile(this, toBeScannedStr, null, null);
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void penMoved() {
        disabled = false;
        showSaveIcon();
        Log.d("Signature", "Pen moved");
    }

    private void showSaveIcon() {
        this.menu.findItem(R.id.action_clear).setIcon(R.drawable.signature_clear);
        this.menu.findItem(R.id.action_save).setIcon(R.drawable.signature_save);
    }

    public class MyMediaScanner implements
            MediaScannerConnection.MediaScannerConnectionClient {

        private MediaScannerConnection mSC;
        private File file;

        public MyMediaScanner(Context context, File file) {
            this.file = file;
            mSC = new MediaScannerConnection(context, this);
            mSC.connect();
        }

        @Override
        public void onMediaScannerConnected() {
            mSC.scanFile(file.getAbsolutePath(), null);
        }

        @Override
        public void onScanCompleted(String path, Uri uri) {
            mSC.disconnect();
        }
    }

    public SignatureActivityViewModel obtainViewModel(FragmentActivity activity) {

        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = new ViewModelFactory(useCaseComponent);
        return ViewModelProviders.of(activity, factory).get(SignatureActivityViewModel.class);
    }

    private void initializeInjector() {
        this.useCaseComponent = DaggerUseCaseComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .useCaseModule(new UseCaseModule())
                .build();
    }
}
