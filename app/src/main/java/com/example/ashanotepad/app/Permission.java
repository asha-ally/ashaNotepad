package com.example.ashanotepad.app;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by oi-dev-01 on 01/01/18.
 */

public final class Permission {

    // Camera group.
    public static final String CAMERA = Manifest.permission.CAMERA;

    // Contacts group.
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;

    // Location group.
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    // Microphone group.
    //public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;

    // Phone group.
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
//    public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
//    public static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
//    public static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
//    public static final String USE_SIP = Manifest.permission.USE_SIP;
//    public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;

    // Sensors group.
//    public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
//    public static final String USE_FINGERPRINT = Manifest.permission.USE_FINGERPRINT;

    // SMS group.
    public static final String SEND_SMS = Manifest.permission.SEND_SMS;
    public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static final String READ_SMS = Manifest.permission.READ_SMS;
//    public static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
//    public static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
//    public static final String READ_CELL_BROADCASTS = "android.permission.READ_CELL_BROADCASTS";



    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_CALL_PHONE = 2;
    public static final int REQUEST_SMS = 3;
    public static final int REQUEST_LOCATION = 4;
    public static final int REQUEST_PHONE_STATE = 5;
    //public static final int REQUEST_READ_CONTACTS = 6;


    public static final String[] PERMISSIONS_PHONE_STATE = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE    };

    public static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE    };

    public static final String[] PERMISSIONS_SMS = {
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS    };

    public static final String[] PERMISSION_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};

    public static final String[] PERMISSION_CALL_PHONE = {Manifest.permission.CALL_PHONE};



    public static boolean verifyPermission (Activity activity, String permission_name) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, permission_name); //Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (permission == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

}
