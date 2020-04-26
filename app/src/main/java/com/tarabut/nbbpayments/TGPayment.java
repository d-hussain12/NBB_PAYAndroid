package com.tarabut.nbbpayments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import pl.droidsonroids.gif.GifImageView;

public class TGPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        resultMethod();
    }

   public void resultMethod(){
       GifImageView imageView=(GifImageView)findViewById(R.id.gif_image);
       Button btnHome=(Button)findViewById(R.id.btnHome);
       TextView tvtoast=(TextView)findViewById(R.id.toast_error);
       Intent intent = getIntent();
       String response_code = intent.getStringExtra("response_code");
       String response_message = intent.getStringExtra("response_message");
       String payment_reference_local = intent.getStringExtra("payment_reference_local");
       if(response_code.equals("200")){
        imageView.setImageResource(R.drawable.gif_result);
        tvtoast.setText(""+response_message);
       }else {
           imageView.setImageResource(R.drawable.cross_gif);
           tvtoast.setText(""+response_message);
       }
      // Toast.makeText(this, "Welcome"+response_code+response_message, Toast.LENGTH_SHORT).show();
      btnHome.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent home=new Intent(TGPayment.this,NewDashboard.class);
              startActivity(home);

          }
      });

    }

}
