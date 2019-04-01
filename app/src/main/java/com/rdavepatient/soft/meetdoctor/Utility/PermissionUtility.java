package com.rdavepatient.soft.meetdoctor.Utility;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;


import com.rdavepatient.soft.meetdoctor.R;

import java.util.ArrayList;
import java.util.List;


public final class PermissionUtility {

    public static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2;
    public static final int REQUEST_PERMISSION_READ_PHONE_STATE = 3;
    public static final int REQUEST_PERMISSION_CAMERA = 4;
    public static final int REQUEST_PERMISSION_READ_SEND_RECEIVE_SMS = 5;
    public static final int REQUEST_PERMISSION_READ_PHONE_READ_SMS = 7;


    public static boolean checkPermissionCameraAccessAndReadExternalStorage(final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return check(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    new int[]{R.string.permission_read_external_storage, R.string.permission_camera_access},
                    REQUEST_PERMISSION_CAMERA);
        } else {
            return true;
        }
    }

    public static boolean checkPermissionReadPhoneAndReadSMS(final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return check(activity,
                    new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_SMS},
                    new int[]{R.string.permission_read_phone_state, R.string.permission_read_sms},
                    REQUEST_PERMISSION_READ_PHONE_READ_SMS);
        } else {
            return true;
        }
    }


    public static boolean checkPermissionReadPhoneState(final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return check(activity, Manifest.permission.READ_PHONE_STATE, R.string.permission_read_phone_state, REQUEST_PERMISSION_READ_PHONE_STATE);
        } else {
            return true;
        }
    }


    public static boolean checkPermissionReadExternalStorage(final Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return check(activity, Manifest.permission.READ_EXTERNAL_STORAGE, R.string.permission_read_external_storage, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
        } else {
            return true;
        }

    }

    public static boolean checkPermissionWriteExternalStorage(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return check(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    R.string.permission_write_external_storage,
                    REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            return true;
        }
    }


    public static boolean checkPermissionsForSMS(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return check(activity,
                    new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS},
                    new int[]{R.string.permission_send_sms, R.string.permission_receive_sms, R.string.permission_read_sms},
                    REQUEST_PERMISSION_READ_SEND_RECEIVE_SMS);
        } else {
            return true;
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private static boolean check(final Activity activity, final String permission, final int explanation, final int requestCode) {
        // check permission
        final int result = ContextCompat.checkSelfPermission(activity, permission);

        // ask for permission
        if (result != PackageManager.PERMISSION_GRANTED) {
            // check if explanation is needed
            if (activity.shouldShowRequestPermissionRationale(permission)) {
                // show explanation
                Snackbar
                        .make(activity.getCurrentFocus(), explanation, Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // try again
                                activity.requestPermissions(new String[]{permission}, requestCode);
                            }
                        }).show();
            } else {
                // no explanation needed
                activity.requestPermissions(new String[]{permission}, requestCode);
            }
        }
        // result
        return result == PackageManager.PERMISSION_GRANTED;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private static boolean check(final Activity activity, final String[] permissions, final int[] explanations, final int requestCode) {
        // check permissions
        final int[] results = new int[permissions.length];
        for (int i = 0; i < permissions.length; i++) {
            results[i] = ContextCompat.checkSelfPermission(activity, permissions[i]);
        }

        // get denied permissions
        final List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < results.length; i++) {
            if (results[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }

        // ask for permissions
        if (!deniedPermissions.isEmpty()) {
            final String[] params = deniedPermissions.toArray(new String[deniedPermissions.size()]);

            // check if explanation is needed
            boolean explanationShown = false;
            for (int i = 0; i < permissions.length; i++) {
                if (activity.shouldShowRequestPermissionRationale(permissions[i])) {
                    // show explanation
                    Snackbar
                            .make(activity.getCurrentFocus(), explanations[i], Snackbar.LENGTH_INDEFINITE)
                            .setAction(android.R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // try again
                                    activity.requestPermissions(params, requestCode);
                                }
                            }).show();
                    explanationShown = true;
                    break;
                }
            }

            // no explanation needed
            if (!explanationShown) {
                activity.requestPermissions(params, requestCode);
            }
        }

        // result
        return deniedPermissions.isEmpty();
    }

    public static void gotoAppSettings(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }
}