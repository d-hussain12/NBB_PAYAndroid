package com.example.almoyaedtech;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends AppCompatActivity {
    double health_pay=0.0,transport_pay=0.0,credit_pay=0.0,grocerry_pay=0.0;
    LinearLayout lin_1;
    LinearLayout lin_2;
    LinearLayout lin_3;
    LinearLayout lin_4;
    TextView tv_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        callWebService();

        buttonClick();
    }

    private void buttonClick() {
        lin_1=(LinearLayout)findViewById(R.id.lay_1);
        lin_2=(LinearLayout)findViewById(R.id.lay_2);
        lin_3=(LinearLayout)findViewById(R.id.lay_3);
        lin_4=(LinearLayout)findViewById(R.id.lay_4);
        tv_total=(TextView)findViewById(R.id.tv_total);
        lin_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.format("%.2f", health_pay);
                tv_total.setText("-"+s);
                lin_1.setBackgroundDrawable( getResources().getDrawable(R.drawable.circle1) );
                lin_4.setBackgroundDrawable( getResources().getDrawable(R.drawable.circled) );
                lin_3.setBackgroundColor(Color.parseColor("#ffffff"));
                lin_2.setBackgroundColor(Color.parseColor("#ffffff"));
                lin_1.setPadding(60,60,60,60);
                lin_4.setPadding(20,20,20,20);

            }
        });

        lin_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_total.setText("-"+transport_pay);
                lin_2.setBackgroundColor(Color.parseColor("#D3D3D3"));
                lin_3.setBackgroundColor(Color.parseColor("#ffffff"));
                lin_1.setBackgroundDrawable( getResources().getDrawable(R.drawable.circle) );
                lin_4.setBackgroundDrawable( getResources().getDrawable(R.drawable.circled) );
            }
        });
        lin_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_total.setText("-"+credit_pay);
                lin_3.setBackgroundColor(Color.parseColor("#D3D3D3"));
                lin_2.setBackgroundColor(Color.parseColor("#ffffff"));
                lin_1.setBackgroundDrawable( getResources().getDrawable(R.drawable.circle) );
                lin_4.setBackgroundDrawable( getResources().getDrawable(R.drawable.circled) );
            }
        });
        lin_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_total.setText("-"+grocerry_pay);
                lin_4.setBackgroundDrawable( getResources().getDrawable(R.drawable.circled2) );
                lin_1.setBackgroundDrawable( getResources().getDrawable(R.drawable.circle) );
                lin_3.setBackgroundColor(Color.parseColor("#ffffff"));
                lin_2.setBackgroundColor(Color.parseColor("#ffffff"));
                lin_1.setPadding(20,20,20,20);
                lin_4.setPadding(60,60,60,60);
            }
        });
    }

    private void callWebService() {
        RequestQueue pilha = Volley.newRequestQueue(this);

        String url="http://www.mocky.io/v2/5e54cae73100005e00eb3339";
        JSONObject object = new JSONObject();


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject json=new JSONObject(response.toString());


                    JSONArray jsonArray=json.getJSONArray("transactions");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String name=object.getString("date");
                        String type=object.getString("category");
                        String amount=object.getString("amount");
                        if(type.equals("transport")) {
                            transport_pay=transport_pay+Double.valueOf(amount);
                        }else  if(type.equals("health")) {
                            health_pay=health_pay+Double.valueOf(amount);
                        }
                       else if(type.equals("groceries")) {
                            grocerry_pay=grocerry_pay+Double.valueOf(amount);
                        }else{
                            credit_pay=credit_pay+Double.valueOf(amount);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                   // Toast.makeText(Dashboard.this, "hello"+transport_pay+"="+credit_pay, Toast.LENGTH_SHORT).show();


                }

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError erro) {

            }
        }); pilha.add(req);
    }
}
