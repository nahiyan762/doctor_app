package com.sftelehealth.doctor.app.helpers;

import android.content.Context;

import java.io.File;

/**
 * Created by Rahul on 15/11/17.
 */

public class PDFUtils {

    /*private static String getPDFIconsDirectory() {
        return AppController.getAppContext().getDir("document_icons", MODE_PRIVATE).getAbsolutePath();
    }*/

    /*public static boolean generateImageFromPdf(Uri pdfUri, File internalPDFIconDirectory, int documentId, Context context) {
        int pageNumber = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(context);

        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = context.getContentResolver().openFileDescriptor(pdfUri, "r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
            saveImage(bmp, internalPDFIconDirectory, documentId);
            pdfiumCore.closeDocument(pdfDocument); // important!

            return true;
        } catch(Exception e) {
            //todo with exception
            return false;
        }
    }*/

    /*private static void saveImage(Bitmap bmp, File folder, int documentId) {

        FileOutputStream out = null;
        try {
            //File folder = new File(internalPDFIconDirectory.getAbsolutePath());
            if(!folder.exists())
                folder.mkdirs();
            File file = new File(folder, documentId + ".png");
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            //todo with exception
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                //todo with exception
            }
        }
    }*/

    /*public static String getPDFIconForDocument(Context context, int documentId) {

        File internalPDFIconDirectory = new File(context.getExternalFilesDir(null), "document_icons");  //getApplicationContext().getDir("document_icons", MODE_PRIVATE);
        return internalPDFIconDirectory.getAbsolutePath() + "/" + documentId + ".png";
    }

    public static boolean isPDFIconAvailable(int documentId, Context context) {

        File internalDocumentsDirectory = new File(context.getExternalFilesDir(null), "documents"); // getApplicationContext().getDir("documents", MODE_PRIVATE);
        File internalPDFIconDirectory = new File(context.getExternalFilesDir(null), "document_icons");   // getApplicationContext().getDir("document_icons", MODE_PRIVATE);

        File documentFile = new File(internalDocumentsDirectory.getAbsolutePath() + "/" + documentId + ".pdf");

        // check if pdf file is already downloaded in the documents folder
        if(documentFile.exists() && !documentFile.isDirectory()) {
            // check if the generated icon is available in the document_icons folder
            File documentIcon = new File(internalPDFIconDirectory.getAbsolutePath() + "/" + documentId + ".png");
            if(documentIcon.exists() && !documentIcon.isDirectory()) {
                return true;
            } else {
                // else generate icon and return result
                //return generateImageFromPdf(Uri.fromFile(documentFile), internalPDFIconDirectory, documentId, context);
                return false;
            }
        }

        // if the document is not available then return false
        return false;
    }*/

    public boolean isPDFDownloaded(int documentId, FileDownloadHelper fdh) {
        File internalDocumentsDirectory = new File(fdh.getExternalFilesDirectory(), "documents");

        File documentFile;
        documentFile = new File(internalDocumentsDirectory.getAbsolutePath() + "/" + documentId + ".pdf");
        // check if pdf file is already downloaded in the documents folder
        if(documentFile.exists() && !documentFile.isDirectory()) {
            return true;
        }

        documentFile = new File(internalDocumentsDirectory.getAbsolutePath() + "/" + documentId + ".jpg");
        // check if pdf file is already downloaded in the documents folder
        if(documentFile.exists() && !documentFile.isDirectory()) {
            return true;
        }

        documentFile = new File(internalDocumentsDirectory.getAbsolutePath() + "/" + documentId + ".jpeg");
        // check if pdf file is already downloaded in the documents folder
        if(documentFile.exists() && !documentFile.isDirectory()) {
            return true;
        }

        return false;
    }

    public boolean isPDFFolderEmpty(Context context) {
        File internalDocumentsDirectory = new File(context.getExternalFilesDir(null).getAbsolutePath(), "documents");

        // File[] contents = internalDocumentsDirectory.listFiles();
        String[] files = internalDocumentsDirectory.list();
        // the directory file is not really a directory..
        if (files == null) {
            return true;
        }
        // Folder is empty
        else if (files.length == 0) {
            return true;
        }
        // Folder contains files
        else {
            return false;
        }
    }

    public void emptyDocumentsDirectory(Context context) {
        File internalDocumentsDirectory = new File(context.getExternalFilesDir(null).getAbsolutePath(), "documents");
        File[] files = internalDocumentsDirectory.listFiles();

        if(files != null)
            for(int i=0; i<files.length; i++){
                files[i].delete();
            }
    }
}