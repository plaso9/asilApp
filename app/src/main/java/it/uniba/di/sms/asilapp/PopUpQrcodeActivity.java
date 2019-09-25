package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class PopUpQrcodeActivity extends Activity {

    private static final String TAG = "PopUpQrcode"; //tag too long
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private ImageView imageViewQr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_qrcode);

        imageViewQr = findViewById(R.id.imageViewQr);
        setDialogDimensions();

        //GENERATE QR CODE FOR USER ID
        String uId = getIntent().getExtras().getString("userId");
        qrgEncoder = new QRGEncoder(uId, null, QRGContents.Type.TEXT, 100);
        try {
            // Getting QR-Code as Bitmap
            bitmap = qrgEncoder.encodeAsBitmap();
            // Setting Bitmap to ImageView
            imageViewQr.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.v(TAG, e.toString());

        }
    }

    public void setDialogDimensions(){
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.55);
            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
            getWindow().setLayout(width , height);
        } else {
            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.55);
            getWindow().setLayout(width , height);
        }
    }
}
