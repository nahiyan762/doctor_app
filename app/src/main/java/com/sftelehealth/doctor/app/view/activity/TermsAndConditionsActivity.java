package com.sftelehealth.doctor.app.view.activity;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.view.utils.ActivityUtils;

public class TermsAndConditionsActivity extends BaseAppCompatActivity implements ActivityUtils.AgoraCallStatusChangeListener{

    WebView termsAndConditions;

    BroadcastReceiver agoraCallStatusListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        ActivityUtils.setUpToolbar(this, "Terms and Conditions");
        ActivityUtils.showHideStatusBarCallStatusBar(this);
        agoraCallStatusListener = ActivityUtils.getVideoCallStatusReceiver(this);

        termsAndConditions = (WebView) findViewById(R.id.termsAndConditions);

        termsAndConditions.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        termsAndConditions.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });

        termsAndConditions.loadUrl(getResources().getString(R.string.terms_and_conditions_url));
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

    @Override
    public void onStatusChange(int status) {
        // If status changed then change toolbar
        ActivityUtils.showHideStatusBarCallStatusBar(this);
    }
}
