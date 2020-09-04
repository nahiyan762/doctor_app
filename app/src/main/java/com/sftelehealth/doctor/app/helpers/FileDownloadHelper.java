package com.sftelehealth.doctor.app.helpers;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Rahul on 26/10/17.
 */

public class FileDownloadHelper {

    long downloadReference;
    DownloadManager downloadManager;
    Context context;

    public FileDownloadHelper(Context context) {
        this.context = context;
        downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
    }

    public void downloadFile(String url, String title, String description, int documentId, String documentExtension) {

        Uri downloadUri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        //Restrict the types of networks over which this download may proceed.
        // request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(true);

        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle(title);

        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription(description);

        //Set the local destination for the downloaded file to a path within the application's external files directory
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, documentId + ".pdf");
        request.setDestinationInExternalFilesDir(context, null, "documents/" + documentId + "." + documentExtension);

        // File temporaryWritableFile = new File(getExternalFilesDirectory() + "/" + documentId + ".pdf");
        // request.setDestinationUri(Uri.fromFile(temporaryWritableFile));

        // Allow file to be indexed by media scanner
        // request.allowScanningByMediaScanner();

        // Notification must be visible when the download is complete
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //Enqueue a new download and same the referenceId
        downloadReference = downloadManager.enqueue(request);
    }

    public String getInternalDownloadsDirectory() {
        return context.getDir("documents", MODE_PRIVATE).getAbsolutePath();
    }

    public String getExternalFilesDirectory() {
        return context.getExternalFilesDir(null).getAbsolutePath();
    }

    public String getLastDownloadedDocumentPath(Intent intent) {
        String title = "";
        String localDestination = "";

        // copy the already downloaded file to an internal location and delete the original one
        Bundle extras = intent.getExtras();
        DownloadManager.Query q = new DownloadManager.Query();
        q.setFilterById(extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID));
        Cursor c = downloadManager.query(q);

        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                // process download
                title = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE));
                localDestination = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                // get other required data by changing the constant passed to getColumnIndex
            }
        }

        // get the last
        return localDestination;
    }

    public String getLastDownloadedDocumentTitle(Intent intent) {
        String title = "";
        String localDestination = "";

        // copy the already downloaded file to an internal location and delete the original one
        Bundle extras = intent.getExtras();
        DownloadManager.Query q = new DownloadManager.Query();
        q.setFilterById(extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID));
        Cursor c = downloadManager.query(q);

        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                // process download
                title = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE));
                localDestination = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                // get other required data by changing the constant passed to getColumnIndex
            }
        }

        // get the last
        return title;
    }

    public String getInternalFilePath(String filename) {

        //File internalDocumentsDirectory = new File(FileDownloadHelper.getExternalFilesDirectory(), "documents");
        //File documentFile = new File(internalDocumentsDirectory.getAbsolutePath() + "/" + filename);

        context.getExternalFilesDir(null);
        File internalDocumentsDirectory = new File(Uri.parse(filename).getPath()); // new File(context.getExternalFilesDir(null), "documents"); //context.getDir("documents", MODE_PRIVATE);
        return internalDocumentsDirectory.getAbsolutePath();
    }

    public String getInternalFilePath(String filename, boolean fromDocumentObject) {

        File internalDocumentsDirectory = new File(this.getExternalFilesDirectory(), "documents");
        File documentFile = new File(internalDocumentsDirectory.getAbsolutePath() + "/" + filename);

        //context.getExternalFilesDir(null);
        //File internalDocumentsDirectory = new File(Uri.parse(filename).getPath()); // new File(context.getExternalFilesDir(null), "documents"); //context.getDir("documents", MODE_PRIVATE);
        return documentFile.getAbsolutePath();
    }


    public boolean copyDownloadedFileToPrivateFolder(String filePath) {

        File source = new File(context.getExternalFilesDir(null), "documents" + "/" +filePath);

        String[] fileNameSplit = filePath.split("/");
        String fileName = fileNameSplit[fileNameSplit.length - 1];

        File internalDocumentsDirectory = context.getDir("documents", MODE_PRIVATE);
        File destination = new File(internalDocumentsDirectory.getAbsolutePath() + "/" + fileName);

        try {
            // Files.copy(source.toPath(), dest.toPath(), REPLACE_EXISTING);
            FileUtils.copy(source, destination);
            source.delete();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean copyUploadedFileToPrivateFolder(String filePath, int documentId) {
        //File source = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" +filePath);
        File source = new File(filePath);

        // String[] fileNameSplit = filePath.split("/");
        String fileName = documentId + ".pdf";

        File internalDocumentsDirectory = context.getDir("documents", MODE_PRIVATE);
        File destination = new File(internalDocumentsDirectory.getAbsolutePath() + "/" + fileName);

        try {
            // Files.copy(source.toPath(), dest.toPath(), REPLACE_EXISTING);
            FileUtils.copy(source, destination);
            source.delete();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getMimeType(Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }
}
