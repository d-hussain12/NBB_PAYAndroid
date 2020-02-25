package com.example.almoyaedtech;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by salin.ks on 9/17/2018.
 */

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        showSplashDelay();
    }


    private void showSplashDelay() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {



                checkuser();

            }
        }, 3000);
    }

    private void checkuser() {


            goToNextActivity(Dashboard.class,1);

    }

    private void goToNextActivity(Class className, int REQ_CODE) {
        System.gc();
        Intent nextActivity = new Intent(LauncherActivity.this, className);
        nextActivity.putExtra("SET_FRAG", "true");
        startActivityForResult(nextActivity, REQ_CODE);
    }
}


