package com.example.almoyaedtech;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Categories extends AppCompatActivity {
    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    ArrayList<SubjectData> arrayList;
     ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

         list = findViewById(R.id.list);
         arrayList = new ArrayList<SubjectData>();

        // Set the ArrayAdapter as the ListView's adapt

        callWebService();






    }


    private void callWebService() {
        RequestQueue pilha = Volley.newRequestQueue(this);

        String url="http://snow.webjobsuk.com/handyx/api/user/services";
        JSONObject object = new JSONObject();


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject json=new JSONObject(response.toString());


                    JSONArray jsonArray=json.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        String name=object.getString("name");
                        String link=object.getString("image");
                        String idvalue=object.getString("id");

                        arrayList.add(new SubjectData(name,idvalue, link));

                        CustomAdapter customAdapter = new CustomAdapter(Categories.this, arrayList);
                        list.setAdapter(customAdapter);




                        AdapterView.OnItemClickListener myListViewClicked = new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(getApplicationContext(), "Clicked at positon = " + position, Toast.LENGTH_SHORT).show();

                            }
                        };
                        list.setOnItemClickListener(myListViewClicked);
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError erro) {

            }
        }); pilha.add(req);
    }
}
