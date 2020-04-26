package com.tarabut.nbbpayments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;



public class NewDashboard extends AppCompatActivity {
    RelativeLayout card1,card2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dashboard);
        findid();
        linteners();

    }

    private void linteners() {
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //  Intent intent=new Intent(NewDashboard.this, OpenBankingAct.class);
                Intent intent=new Intent(NewDashboard.this, Pay_fill.class);
                startActivity(intent);

            }
        });

    }

    private void findid() {
        card1 = (RelativeLayout) findViewById(R.id.card1);
    }


}
