package com.example.hong.dhproject3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity3 extends AppCompatActivity {
    private ListView listview;
    private alertAdapter adapter;
    private List<alertInfo> alertList;
    private String aDlist_Url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent a = getIntent();
        int id = a.getExtras().getInt("ID");
        aDlist_Url=getResources().getString(R.string.base_Url)+"alert_list_device";
        listview = (ListView)findViewById(R.id.listView2);
        alertList = new ArrayList<alertInfo>();
        adapter = new alertAdapter(getApplicationContext(), alertList);
        listview.setAdapter(adapter);
        getNumList(id);

    }

    public void getNumList(int a){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("d_id", a+"").build();
        Request request = new Request.Builder().url(aDlist_Url).post(formBody).build();
        client.newCall(request).enqueue(getNumlistCallback);
    }

    public Callback getNumlistCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseData = response.body().string();

            Log.d("RESPONSEDATA", responseData);
            try {
                JSONObject jsonObject = new JSONObject(responseData);
                JSONArray ja = jsonObject.getJSONArray("data");
                for(int i= 0 ; i < ja.length(); i ++) {
                    JSONObject order = ja.getJSONObject(i);
                    //int deviceId = order.getInt("device_id");
                    int alertType = order.getInt("alert_type");
                    String time = order.getString("created_at");
                    alertInfo alerts = new alertInfo(alertType, time);
                    alertList.add(alerts);
                }
                notifyChanged();
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    };
    public void notifyChanged(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                adapter.notifyDataSetChanged();
            }
        });
    }
}
