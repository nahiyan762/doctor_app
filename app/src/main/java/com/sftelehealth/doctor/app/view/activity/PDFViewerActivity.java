package com.sftelehealth.doctor.app.view.activity;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.barteksc.pdfviewer.PDFView;
import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.view.utils.ActivityUtils;

import java.io.File;

public class PDFViewerActivity extends BaseAppCompatActivity implements ActivityUtils.AgoraCallStatusChangeListener {

    PDFView pdfView;
    SubsamplingScaleImageView subsamplingScaleImageView;

    BroadcastReceiver agoraCallStatusListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pdfView = findViewById(R.id.pdfView);
        subsamplingScaleImageView = findViewById(R.id.imageView);

        if(getIntent().getExtras() != null) {

            ActivityUtils.setUpToolbar(this, getIntent().getStringExtra("document_title"));

            if(getIntent().getStringExtra("document_format").equalsIgnoreCase("pdf")) {
                pdfView.setVisibility(View.VISIBLE);
                subsamplingScaleImageView.setVisibility(View.GONE);
                pdfView
                        .fromFile(new File(getIntent().getStringExtra("file_path")))
                        .load();
            } else {
                pdfView.setVisibility(View.GONE);
                subsamplingScaleImageView.setVisibility(View.VISIBLE);
                subsamplingScaleImageView.setImage(ImageSource.uri(getIntent().getStringExtra("file_path")));
            }
        }

        ActivityUtils.showHideStatusBarCallStatusBar(this);
        agoraCallStatusListener = ActivityUtils.getVideoCallStatusReceiver(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ActivityUtils.showHideStatusBarCallStatusBar(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(agoraCallStatusListener, new IntentFilter(REFRESH_VIDEO_CALL_STATUS));
    }

    @Override
    protected void onPause() {
        //unregisterReceiver(agoraCallStatusListener);
        super.onPause();
    }

    private String getFileName() {
        String fileName = getIntent().getStringExtra("file_path");
        // fileName = fileName.substring(fileName.lastIndexOf('/'), fileName.lastIndexOf('.'));
        fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        return fileName;
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStatusChange(int status) {
        // If status changed then change toolbar
        ActivityUtils.showHideStatusBarCallStatusBar(this);
    }
}
