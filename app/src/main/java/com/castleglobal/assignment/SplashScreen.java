package com.castleglobal.assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.castleglobal.assignment.Utils.Utilities;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView internetText;
    private Button buttonGotoSetting, buttonGotoFav;
    Utilities utils = new Utilities();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        internetText = (TextView) findViewById(R.id.internet_text);
        buttonGotoSetting = (Button) findViewById(R.id.goto_setting);
        buttonGotoFav = (Button) findViewById(R.id.goto_fav);
        // check if any sort of connection is alowed or not


        buttonGotoSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSettings();
            }
        });
        buttonGotoFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

    }


    // Start main activty
    private void goToMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    // Open setting to start data/wifi
    private void goToSettings(){
        startActivity(new Intent(Settings.ACTION_SETTINGS));

    }



    @Override
    protected void onResume() {
        super.onResume();

        internetText.setText("Checking your internet connection");
        progressBar.setVisibility(View.VISIBLE);
        buttonGotoSetting.setVisibility(View.INVISIBLE);

        //check for internet
        if(!utils.haveNetworkConnection(getBaseContext()))
        {
            internetText.setText("Your data and wifi are disabled");
            progressBar.setVisibility(View.GONE);
            buttonGotoSetting.setVisibility(View.VISIBLE);
        } else
        {
            goToMainActivity();
        }

    }
}
