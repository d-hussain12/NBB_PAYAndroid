package com.tarabut.openbankinglib;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class OpenBankingAct extends AppCompatActivity {
    Dialog dialog;
    String payment_reference_local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_banking);
        Intent intent = getIntent();
        String access_token = intent.getStringExtra("access_token");
        String cpr = intent.getStringExtra("cpr");
        String amount = intent.getStringExtra("amount");
        String mobile_number = intent.getStringExtra("mobile_number");
        String payment_description = intent.getStringExtra("payment_description");
        payment_reference_local = intent.getStringExtra("payment_reference_local");
        String is_token_payment = intent.getStringExtra("is_token_payment");
        String debit_iban = intent.getStringExtra("debit_iban");
        String credit_iban = intent.getStringExtra("credit_iban");
        String txn_source = intent.getStringExtra("txn_source");
        String secure_hash = intent.getStringExtra("secure_hash");
        String merchant_id = intent.getStringExtra("merchant_id");
        String currency = intent.getStringExtra("currency");


     //  openWebview(url);
       String securehash=sha256(amount + currency+credit_iban+debit_iban+merchant_id+payment_reference_local);
        if(secure_hash.equals(securehash)) {
            Log.d("A="+secure_hash,"B="+securehash);
            callWebService2(access_token, cpr, amount, mobile_number, payment_description, payment_reference_local, is_token_payment, debit_iban, credit_iban, txn_source, secure_hash);
        }else {
           // Toast.makeText(this, "Details Doesn't Match", Toast.LENGTH_SHORT).show();
            Log.d("A="+secure_hash,"B="+securehash);
        }

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

    private void openWebview(String url, final String payment_reference) throws MalformedURLException {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WebView webView = (WebView) findViewById(R.id.webview);



        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        URL myURL = new URL(url);

        webView.loadUrl(url);
       // webView.loadUrl("https://web-app.sandbox.token.io/app/pisp/select-bank-first");


        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                // Here you can check your new URL.
                super.onPageStarted(view, url, favicon);
                // String url ="https://web-app.token.io/app/request-token/rq:2dxzj8w2k1tuoqrCpHdNQKonheB3:5zKtXEAq?state=eyJjc3JmVG9rZW5IYXNoIjoiZTNiMGM0NDI5OGZjMWMxNDlhZmJmNGM4OTk2ZmI5MjQyN2FlNDFlNDY0OWI5MzRjYTQ5NTk5MWI3ODUyYjg1NSIsImlubmVyU3RhdGUiOiIifQ";

                //  https://web-app.token.io/app/request-token/rq:2dxzj8w2k1tuoqrCpHdNQKonheB3:5zKtXEAq?state=eyJjc3JmVG9rZW5IYXNoIjoiZTNiMGM0NDI5OGZjMWMxNDlhZmJmNGM4OTk2ZmI5MjQyN2FlNDFlNDY0OWI5MzRjYTQ5NTk5MWI3ODUyYjg1NSIsImlubmVyU3RhdGUiOiIifQ
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if(url.startsWith("http://paymentsalpha.tarabutgateway.net:8000/payment/nontoken_auth_complete")){
                   // Toast.makeText(getApplicationContext(), "Verifying your Request", Toast.LENGTH_SHORT).show();
                    // showDialog();
                    callWebService3(payment_reference);

                }else {
                   //  Toast.makeText(OpenBankingAct.this, "Something Went Wrong"+url, Toast.LENGTH_SHORT).show();
                }


                if(url.startsWith("http://paymentsalpha.tarabutgateway.net:8000/payment/auth_complete")){
                    // Toast.makeText(getApplicationContext(), "yepiee Verifying your Request", Toast.LENGTH_SHORT).show();
                    // showDialog();
                    callWebService3(payment_reference);

                }

            }
        });
    }

    private void callWebService2(final String accessToken, final String cpr, final String amount, final String mobile_number, final String payment_description, String payment_reference_local, String is_token_payment, final String debit_iban, final String credit_iban, String txn_source, final String access_token){
        LocalDataPass1 localDataPass1=new LocalDataPass1();
        String baseUrl=localDataPass1.baseUrl;
        String url=baseUrl+"payment/v1/create/";

         showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(OpenBankingAct.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String message=jsonObject.getString("message");
                    if(message.equals("Success")){
                        //  Toast.makeText(Pay_fill.this, ""+jsonObject.getString("payment_url"), Toast.LENGTH_SHORT).show();
                     //   Intent intent=new Intent(Pay_fill.this, OpenBankingAct.class);
                       // intent.putExtra("keyUrl",jsonObject.getString("payment_url"));
                      //  startActivity(intent);

                        dialog.dismiss();
                        dialog.hide();
                        String payment_reference=jsonObject.getString("payment_reference");
                        openWebview(jsonObject.getString("payment_url"),payment_reference);


                    }else {
                        Toast.makeText(OpenBankingAct.this, ""+message, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        dialog.hide();

                    }
                } catch (JSONException | MalformedURLException e) {
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

                params.put("Authorization", accessToken);
               // params.put("Content-Type", "application/x-www-form-urlencoded");


                return params;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //parameters in url encoded check postman
                Map<String, String> jsonBody = new HashMap<String, String>();
                jsonBody.put("cpr",cpr);
                jsonBody.put("mobile_number",mobile_number);
                jsonBody.put("amount",amount);
                jsonBody.put("currency","BHD");
                jsonBody.put("merchant_id","20001000");
                jsonBody.put("payment_description",payment_description);
              //  jsonBody.put("return_url","https://www.redirecturl.com");
                jsonBody.put("payment_type","12");
               // jsonBody.put("debit_bankid","1");
                jsonBody.put("billing_address","Building 111, Road 222, Block 333");
                jsonBody.put("billing_city","Seef");
                jsonBody.put("billing_state","Manama");
                jsonBody.put("billing_country","Bahrain");
                jsonBody.put("shipping_address","Building 111, Road 222, Block 333");
                jsonBody.put("shipping_city","Seef");
                jsonBody.put("shipping_state","Manama");
                jsonBody.put("shipping_country","Bahrain");
                jsonBody.put("debit_iban",debit_iban);
                jsonBody.put("credit_iban",credit_iban);
                return jsonBody;
            }
        };

        requestQueue.add(stringRequest);

    }


    private void callWebService3(final String payment_reference){
        LocalDataPass1 localDataPass1=new LocalDataPass1();
        String url=localDataPass1.baseUrl+"payment/enquire/";

        showDialog();

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(OpenBankingAct.this);

            JSONObject jsonBody = new JSONObject();
            //form data in body parameters
            jsonBody.put("merchant_id","20001000");
            //final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        String message=jsonObject.getString("response_message");
                        if(message.equals("Success")){
                            // Toast.makeText(Pay_fill.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            // callWebService2(jsonObject.getString("access_token"));
                            Intent intent=new Intent(OpenBankingAct.this, OpenBankingAct.class);
                            intent.putExtra("access_token",jsonObject.getString("access_token"));
                            startActivity(intent);
                        }else {



                            Intent intent = null;
                            try {
                                // add package name of customer app and gif dependency
                                intent = new Intent(OpenBankingAct.this,
                                        Class.forName("com.tarabut.nbbpayments.TGPayment"));
                                intent.putExtra("response_message",jsonObject.getString("response_message"));
                                intent.putExtra("response_code",jsonObject.getString("response_code"));
                                intent.putExtra("payment_reference_local",payment_reference_local);
                                startActivity(intent);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
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
         //   This is for checking only
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
                    jsonBody.put("payment_reference",payment_reference);


                    return jsonBody;
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        dialog = new Dialog(OpenBankingAct.this,R.style.AppTheme1);
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(false);
        dialog.show();
        dialog.findViewById(R.id.loading_icon).startAnimation(AnimationUtils.loadAnimation(OpenBankingAct.this, R.anim.rotate360));

    }

}
