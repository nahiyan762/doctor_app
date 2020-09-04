package com.sftelehealth.doctor.video.helper;

public class PermissionHelper {

    /*private final String TAG = "PermissionHelper";

    public enum PermissionTypes {
        CAMERA_AND_GALLERY, ACCOUNTS, CONTACTS, READ_WRITE_STORAGE
    }

    public boolean checkSelfPermission(String permission, int requestCode, Activity context) {
        Log.i(TAG, "checkSelfPermission " + permission + " " + requestCode);
        if (ContextCompat.checkSelfPermission(context,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(context,
                    new String[]{permission},
                    requestCode);
            return false;
        }
        return true;
    }

    public void requestCameraAudioPermissions(final Context context, final Activity returningActivity, PermissionCallback callback) {

        int recordAudio = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
        int camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int modifyAudioSetting = ContextCompat.checkSelfPermission(context, Manifest.permission.MODIFY_AUDIO_SETTINGS);

        final List<String> listPermissionsNeeded = new ArrayList<>();

        if (recordAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (modifyAudioSetting != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        }

        if (listPermissionsNeeded.size() > 0) {

            int i=0;
            boolean showRationale = true;

            while (i<listPermissionsNeeded.size()) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(returningActivity,
                        listPermissionsNeeded.get(i))) {
                    showRationale = true;
                }
                i++;
            }

            if(showRationale) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.i("Permission", "Permission info required for CAMERA and STORAGE");
                new AlertDialog.Builder(context)
                        //.setTitle("Delete entry")
                        .setCancelable(false)
                        .setMessage("Please provide access to your camera and gallery.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                // No explanation needed, we can request the permission.
                                ActivityCompat.requestPermissions(
                                        returningActivity,
                                        listPermissionsNeeded,
                                        Constant.PERMISSIONS_REQUEST_CAMERA_AUDIO);
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(
                        returningActivity,
                        listPermissionsNeeded,
                        Constant.PERMISSIONS_REQUEST_CAMERA_AUDIO);
            }

        } else {
            callback.permissionGranted(Constant.PERMISSIONS_REQUEST_CAMERA_AUDIO);
        }
    }

    public interface PermissionCallback {
        void permissionGranted(PermissionTypes permissionType);
        void permissionDenied(PermissionTypes permissionType);
    }*/
}
