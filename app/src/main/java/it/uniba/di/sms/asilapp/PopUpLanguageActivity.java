package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class PopUpLanguageActivity extends Activity {
    //TextView
    private TextView textViewLangEng, textViewLangIt;
    //Images
    private ImageView imgEng, imgIt;


    private String langPref = "Language";
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.80);
        setContentView(R.layout.activity_pop_up_language);
        getWindow().setLayout(screenWidth, screenWidth);

        prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        //find R.id from xml
        textViewLangEng = findViewById(R.id.textViewDescription);
        textViewLangIt = findViewById(R.id.textViewLangIt);

        imgEng = findViewById(R.id.imgEng);
        imgIt = findViewById(R.id.imgIt);

        //Set listener value variable
        textViewLangEng.setOnClickListener(eng_listener);
        imgEng.setOnClickListener(eng_listener);

        textViewLangIt.setOnClickListener(it_listener);
        imgIt.setOnClickListener(it_listener);

    }


    //Set on click listener
    public View.OnClickListener eng_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Configuration config = getBaseContext().getResources().getConfiguration();
            if (!"".equals("en") && !config.locale.getLanguage().equals("en")) {

                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration conf = new Configuration(config);
                conf.locale = locale;
                getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());


                Bundle extras = getIntent().getExtras();
                String classname = extras.getString("callingActivity");
                String userClickedId = extras.getString("user_clicked");
                String parameter = extras.getString("_parameter");
                Class<?> previousClass = null;
                try {
                    previousClass = Class.forName(classname);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(PopUpLanguageActivity.this, previousClass);

                i.putExtra("user_clicked", userClickedId);
                i.putExtra("_parameter", parameter);
                setResult(1, i);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(langPref, "en");
                editor.commit();


                finish();
            }else{
                finish();
            }

        }
    };

    //Set on click listener
    public View.OnClickListener it_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Configuration config = getBaseContext().getResources().getConfiguration();
            if (!"".equals("it") && !config.locale.getLanguage().equals("it")) {

                Locale locale = new Locale("it");
                Locale.setDefault(locale);
                config = new Configuration(config);
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


                Bundle extras = getIntent().getExtras();

                String classname = extras.getString("callingActivity");
                String userClickedId = extras.getString("user_clicked");
                String parameter = extras.getString("_parameter");
                Class<?> previousClass = null;
                try {
                    previousClass = Class.forName(classname);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(PopUpLanguageActivity.this, previousClass);

                i.putExtra("user_clicked", userClickedId);
                i.putExtra("_parameter", parameter);
                setResult(1, i);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(langPref, "it");
                editor.commit();

                finish();

            }else{
                finish();
            }

        }
    };


}
