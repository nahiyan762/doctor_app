package com.sftelehealth.doctor.app.services;

import android.app.IntentService;
import android.content.Intent;

import com.sftelehealth.doctor.app.helpers.PDFUtils;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class CleanUpSpaceService extends IntentService {

    public CleanUpSpaceService() {
        super("CleanUpSpaceService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /*if (intent != null) {
            final String action = intent.getAction();
        }*/

        PDFUtils pdfUtils = new PDFUtils();
        pdfUtils.emptyDocumentsDirectory(this);

        Intent directoryCleanedUp = new Intent("directory_cleaned");
        LocalBroadcastManager.getInstance(this).sendBroadcast(directoryCleanedUp);
    }
}