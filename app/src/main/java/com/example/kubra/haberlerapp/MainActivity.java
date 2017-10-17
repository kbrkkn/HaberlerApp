package com.example.kubra.haberlerapp;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressDialog pd=new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Lütfen bekleyiniz...");
        final ArrayList<HaberModel>haberListesi=new ArrayList<>();
        final Gson gson=new Gson();
        ListView listView= (ListView) findViewById(R.id.listView);
        final HaberAdapter adapter=new HaberAdapter(this,haberListesi);
        String url ="https://api.hurriyet.com.tr/v1/articles?%24filter=Path%20eq%20'/teknoloji/'";
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {/*dönen cevabı parse edeceğimiz yer*/
            pd.dismiss();
                JsonArray jsonArray=new JsonParser().parse(response).getAsJsonArray();
                for(int i=0;i<jsonArray.size();i++){
                    HaberModel haberModel=gson.fromJson(jsonArray.get(i),HaberModel.class);
                    haberListesi.add(haberModel);
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {/*hata mesajını alacağımız yer*/
                Log.e("responseErr",error.getMessage());
                pd.dismiss();
            }
        })
        {
            //sağ tık->generate->override methods->getHeaders()'ı seçiyoruz
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("accept","application/json");
                params.put("apikey","2f89d7b04c144314ae91ac479791a37a");
                return params;
            }
        };
        requestQueue.add(stringRequest);
        pd.show();
        listView.setAdapter(adapter);
listView.setDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url=haberListesi.get(position).getUrl();
                Intent intent=new Intent(getApplicationContext(),WebActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);

            }
        });
    }

}
