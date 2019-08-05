package it.uniba.di.sms.asilapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.logging.Logger;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
        private ZXingScannerView mScannerView;

        @Override
        public void onCreate(Bundle state) {
            super.onCreate(state);
            // Programmatically initialize the scanner view
            mScannerView = new ZXingScannerView(this);
            // Set the scanner view as the content view
            setContentView(mScannerView);
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
        public void handleResult(Result rawResult) {

            //If you would like to resume scanning, call this method below:
            //mScannerView.resumeCameraPreview(this);
            Intent intent = new Intent();
            SearchPatientActivity.editTextCode.setText(rawResult.toString());
            onBackPressed();

            setResult(RESULT_OK, intent);
            finish();
        }
    }