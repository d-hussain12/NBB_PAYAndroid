package com.tarabut.nbbpayments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;



/**
 * Created by Danish on 9/17/2018.
 */

public class LauncherActivity extends AppCompatActivity {
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        findViewById(com.tarabut.openbankinglib.R.id.loading_icon).startAnimation(AnimationUtils.loadAnimation(LauncherActivity.this, com.tarabut.openbankinglib.R.anim.rotate360));
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



            goToNextActivity(NewDashboard.class,1);

    }

    private void goToNextActivity(Class className, int REQ_CODE) {

        Intent nextActivity = new Intent(LauncherActivity.this, NewDashboard.class);
        startActivity(nextActivity);
        //nextActivity.putExtra("SET_FRAG", "true");
        //startActivityForResult(nextActivity, REQ_CODE);
    }
}


