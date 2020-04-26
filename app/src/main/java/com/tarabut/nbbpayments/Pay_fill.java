package com.tarabut.nbbpayments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tarabut.openbankinglib.OpenBankingAct;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class Pay_fill extends AppCompatActivity {

    String[] Debit = {"Tarun NBB - BH85NBOB00000266666078","Rashid - BH75KHCB00200089088001","Tarun KHCB - BH54KHCB00200089702001"};
    String[] Credit = {"Tarun KHCB - BH54KHCB00200089702001","Rashid - BH75KHCB00200089088001","Tarun NBB - BH85NBOB00000266666078"};
    int flags[] = {R.drawable.bank, R.drawable.bank, R.drawable.bank, R.drawable.bank, R.drawable.bank, R.drawable.bank, R.drawable.bank};
    private Typeface myFont;
    Button btnNext,btnCancel;
    Dialog dialog;
    Spinner spin,spin1;
    EditText et_amount,et_cpr;
    String spin_debit;
    String spin_credit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fill);
        btnNext=(Button)findViewById(R.id.btnNext);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        et_amount=(EditText)findViewById(R.id.et_amount);
        et_cpr=(EditText)findViewById(R.id.et_cpr);
      //  spin.setOnItemSelectedListener(this);
       loadSpinners();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if(et_amount.getText().length()>0&&et_cpr.getText().length()>0){
                 callWebService1();

                // Toast.makeText(Pay_fill.this, ""+spin_debit+"Credit"+spin_credit, Toast.LENGTH_SHORT).show();
             }else {
                 Toast.makeText(Pay_fill.this, "Please enter all the mandatory fields", Toast.LENGTH_SHORT).show();
             }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void loadSpinners() {
        spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin1 = (Spinner) findViewById(R.id.simpleSpinner2);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), Debit.length, Debit);
        spin.setAdapter(customAdapter);
        customAdapter = new CustomAdapter(getApplicationContext(), Credit.length, Credit);
        spin1.setAdapter(customAdapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                spin_debit = Debit[position].substring(Debit[position].lastIndexOf(' ') + 1);
               // Toast.makeText(getApplicationContext(), "The option is:" + spin_debit, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 spin_credit = Credit[position].substring(Credit[position].lastIndexOf(' ') + 1);
                //Toast.makeText(getApplicationContext(), "The option is:" + spin_credit, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }



    private void callWebService1(){
        LocalDataPass localDataPass=new LocalDataPass();
        String baseUrl=localDataPass.baseUrl;
        String url=baseUrl+"auth/v1/validate/";

        showDialog();

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Pay_fill.this);

            JSONObject jsonBody = new JSONObject();
            //form data in body parameters
            jsonBody.put("merchant_id","20001000");
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        String message=jsonObject.getString("message");
                        if(message.equals("Success")){

                            //get secure certificateamount + currency + credit_iban + debit_iban + merchant_id
                            String amount=et_amount.getText().toString();
                            String cpr=et_cpr.getText().toString();
                            String currency="BHD";
                            String credit_iban=spin_credit;
                            String debit_iban=spin_debit;
                            String merchant_id="20001000";
                            String payment_reference_local="Test Description";


                            String securehash=sha256(amount + currency +credit_iban+debit_iban+ merchant_id+payment_reference_local);
                            Intent intent=new Intent(Pay_fill.this, OpenBankingAct.class);
                            intent.putExtra("access_token",jsonObject.getString("access_token"));
                            intent.putExtra("cpr",cpr);
                            intent.putExtra("amount",amount);
                            intent.putExtra("mobile_country_code","+973");
                            intent.putExtra("mobile_number","33263467");
                            intent.putExtra("currency",currency);
                            intent.putExtra("payment_description","Test Description");
                            intent.putExtra("payment_reference_local","Test Description");
                            intent.putExtra("is_token_payment","NO");
                            intent.putExtra("debit_iban",debit_iban);
                            intent.putExtra("credit_iban",credit_iban);
                            intent.putExtra("txn_source","NA");
                             intent.putExtra("secure_hash",securehash);
                             intent.putExtra("merchant_id",merchant_id);

                            startActivity(intent);
                        }else {
                            Toast.makeText(Pay_fill.this, ""+message, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            dialog.hide();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                  }
            }) {


 /*
            This is for checking only
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {

                    responseString = String.valueOf(response.data);


                }else {
                    responseString = String.valueOf("No Data Found");
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }


  */


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzZWNyZXRfa2V5IjoiZHNmamhkamdoZGZnZHJqZ2JzZGhiZnNkaGZnYmRmIiwibWVyY2hhbnRfaWQiOiIyMDAwMTIzNCIsImlzc3VlciI6Imh0dHBzOi8vdGFyYWJ1dGdhdGV3YXkuY29tIiwiZXhwaXJ5IjoiMzYwMCJ9.Z0okHnLIYSQUOOh1UM4_tmXaIl7XQCZSWeO_hWwqjEkSUEaz6hvulUNTOWgU7N7uJ-N8YwUc2HVtBeSjsvR0zOVj-GJAUvGCoxqJsKVoXJRWC9heHDhfOoQk7XW6DPMBBKofuhViSLlx4C-8asTmonVlVjOrFurMVksR6d8KiEA");
                  //  params.put("Content-Type", "application/x-www-form-urlencoded");


                    return params;
                }
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> jsonBody = new HashMap<String, String>();
                    jsonBody.put("merchant_id","20001000");

                    return jsonBody;
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void callWebService2(final String access_token){
        String url="http://paymentsalpha.tarabutgateway.net:8000/payment/create/";

       // showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(Pay_fill.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String message=jsonObject.getString("message");
                    if(message.equals("Success")){
                      //  Toast.makeText(Pay_fill.this, ""+jsonObject.getString("payment_url"), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Pay_fill.this, OpenBankingAct.class);
                        String value="Hello world";
                        intent.putExtra("keyUrl",jsonObject.getString("payment_url"));
                        startActivity(intent);
                    }else {
                        Toast.makeText(Pay_fill.this, ""+message, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        dialog.hide();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
                dialog.dismiss();
                dialog.hide();

            }
        }) {



            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            /*
            This is for checking only
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {

                    responseString = String.valueOf(response.data);


                }else {
                    responseString = String.valueOf("No Data Found");
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }

             */


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Authorization", access_token);
                params.put("Content-Type", "application/x-www-form-urlencoded");


                return params;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> jsonBody = new HashMap<String, String>();
                jsonBody.put("cpr","861072952");
                jsonBody.put("mobile_number","33263467");
                jsonBody.put("amount","4.123");
                jsonBody.put("currency","BHD");
                jsonBody.put("merchant_id","20001000");
                jsonBody.put("payment_description","Test");
                jsonBody.put("return_url","https://www.redirecturl.com");
                jsonBody.put("payment_type","11");
                jsonBody.put("payment_reference","TEST Reference");
                jsonBody.put("billing_address","Building 111, Road 222, Block 333");
                jsonBody.put("billing_city","Seef");
                jsonBody.put("billing_state","Manama");
                jsonBody.put("billing_country","Bahrain");
                jsonBody.put("shipping_address","Building 111, Road 222, Block 333");
                jsonBody.put("shipping_city","Seef");
                jsonBody.put("shipping_state","Manama");
                jsonBody.put("shipping_country","Bahrain");
                return jsonBody;
            }
        };

        requestQueue.add(stringRequest);

    }
    private void showDialog() {
        dialog = new Dialog(Pay_fill.this,R.style.AppTheme);
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(true);
        dialog.show();
        dialog.findViewById(R.id.loading_icon).startAnimation(AnimationUtils.loadAnimation(Pay_fill.this, R.anim.rotate360));

    }

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }




/*

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //Toast.makeText(getApplicationContext(), countryNames[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

*/


}