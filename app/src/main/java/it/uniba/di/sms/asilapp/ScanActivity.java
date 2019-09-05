package it.uniba.di.sms.asilapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;


import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    //Define a ZXingScannerView object
    private ZXingScannerView mScannerView;

    // Assign an arbitrary value to PERMISSION_REQUEST_CODE
    private static final int PERMISSION_REQUEST_CODE = 200;


    @Override
    public void onCreate(Bundle state) {    //Called when the activity is starting.
        super.onCreate(state);

        // Initialize the ZxingScannerView
        mScannerView = new ZXingScannerView(this);

        // Call checkPermission() or request the permission if missing
        if (checkPermission()) {
            // Set the ZxingScannerView as the content view
            setContentView(mScannerView);
        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        //Checks if the app has the CAMERA permission from the Manifest
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        //Permission is granted
        return true;
    }

    private void requestPermission() {
        /* Requests CAMERA permission to be granted to the app. PERMISSION_REQUEST_CODE is used
        to identify the request */
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }


    @Override
    //This interface is the contract for receiving the results for permission requests.
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:

                /* We are checking for CAMERA permission. if it's granted make a "Permission Granted" toast
                and start the activity SearchPatientActivity */
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ScanActivity.this, SearchPatientActivity.class);
                    startActivity(intent);

                    // If it's not granted, make a "Permission Denied" toast
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    // Check if the SDK Version is equal or higher than Marshmallow
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        //Checks if the app has the CAMERA permission from the Manifest
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {

                            // If not granted, call showMessageOKCancel with this message
                            showMessageOKCancel("You need to allow access permissions",

                                    // This method will be invoked when a button in the dialog is clicked
                                    new DialogInterface.OnClickListener() {
                                        @Override

                                        /* if the SDK Version is equal or higher than Marshmallow,
                                        request permission will be called on click */
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    // This method will open an Alert Dialog with a message and 2 buttons, Ok anc Cancel
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ScanActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        mScannerView.setResultHandler(this);
        // Start camera on resume
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop camera on pause
        mScannerView.stopCamera();
    }

    @Override
    // This method will handle the result provided by the scanning
    public void handleResult(Result rawResult) {
        // We will open the patient detail page
        Intent intent = new Intent(ScanActivity.this, PatientDetailActivity.class);
        intent.putExtra("user_clicked",rawResult.toString());
        startActivity(intent);
        onBackPressed();

        finish();
    }
}