package it.uniba.di.sms.asilapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

public class UsefulNumbersActivity extends AppCompatActivity {
    ImageView imageViewAmbulance,
    imageViewPolice,
    imageViewMilitaryPolice,
    imageViewFinanceGuard,
    imageViewSeaEmergency,
    imageViewFireBrigade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usefulnumbers);

        imageViewAmbulance = findViewById(R.id.imageViewAmbulance);
        imageViewPolice = findViewById(R.id.imageViewPolice);
        imageViewMilitaryPolice = findViewById(R.id.imageViewMilitaryPolice);
        imageViewFinanceGuard = findViewById(R.id.imageViewFinanceGuard);
        imageViewSeaEmergency = findViewById(R.id.imageViewSeaEmergency);
        imageViewFireBrigade = findViewById(R.id.imageViewFireBrigade);

        imageViewAmbulance.setOnClickListener(imageViewAmbulance_listener);
        imageViewPolice.setOnClickListener(imageViewPolice_listener);
        imageViewMilitaryPolice.setOnClickListener(imageViewMilitaryPolice_listener);
        imageViewFinanceGuard.setOnClickListener(imageViewFinanceGuard_listener);
        imageViewSeaEmergency.setOnClickListener(imageViewSeaEmergency_listener);
        imageViewFireBrigade.setOnClickListener(imageViewFireBrigade_listener);


    }

    public View.OnClickListener imageViewAmbulance_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:118"));
            startActivity(intent);
        }
    };
    public View.OnClickListener imageViewPolice_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:113"));
            startActivity(intent);
        }
    };
    public View.OnClickListener imageViewMilitaryPolice_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:112"));
            startActivity(intent);
        }
    };
    public View.OnClickListener imageViewFinanceGuard_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:117"));
            startActivity(intent);
        }
    };
    public View.OnClickListener imageViewSeaEmergency_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1530"));
            startActivity(intent);
        }
    };
    public View.OnClickListener imageViewFireBrigade_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:115"));
            startActivity(intent);
        }
    };


}
