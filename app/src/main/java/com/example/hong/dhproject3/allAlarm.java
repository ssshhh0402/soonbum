package com.example.hong.dhproject3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.swipedismiss.SwipeDismissListViewTouchListener;
import com.google.android.gms.common.util.ArrayUtils;

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

public class allAlarm extends AppCompatActivity {
    private ListView listview;
    private allAlertAdapter adapter;
    private List<allAlertInfo> alertList;
    Toolbar myToolbar;
    TextView tv1;
    ArrayList<String> a = new ArrayList<String>();
    private String allAlist_Url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_alarm);
        myToolbar = (Toolbar)findViewById(R.id.toolbar);
        tv1 = (TextView)findViewById(R.id.tv1);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        allAlist_Url = getResources().getString(R.string.base_Url)+"alert_list_user";
        Intent intent = getIntent();
        String id = intent.getStringExtra("U_ID");
        listview = (ListView)findViewById(R.id.listView1);
        alertList = new ArrayList<allAlertInfo>();
        adapter = new allAlertAdapter(this, alertList);
        listview.setAdapter(adapter);
        Log.d("RESPONSEDATA 12315", id);
        getAlist(id);
        SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor save = auto.edit();
        save.putInt("count",0);
        save.commit();
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = getIntent();
                    String a = intent.getStringExtra("U_ID");
                    deleteAll(a);
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                SharedPreferences.Editor save = auto.edit();
                String a=  auto.getString("u_id", "");
                String b = alertList.get(i).getD_id();
                String c = alertList.get(i).getM_name();
                Intent intent = new Intent(getApplicationContext(), Main4Activity.class);
                intent.putExtra("U_ID",a);
                intent.putExtra("D_ID", b);
                intent.putExtra("MASTER", c);
                startActivity(intent);
                finish();
            }
        });
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(listview, new SwipeDismissListViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                        for(int position : reverseSortedPositions){
                            final String c = alertList.get(position).getId();
                            alertList.remove(position);
                            a.add(c);
                        }
                    }
                });
        listview.setOnTouchListener(touchListener);
        }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:{
                deleteAlarm(a);
                finish();
                break;
            }
        }
        return true;
    }

    public void deleteAlarm(ArrayList<String> a){

        Log.d("ResponseData deleteAlarm", a.toString());
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("ua_ids", a.toString()).build();
        Request request = new Request.Builder().url("https://button-hanyoon1108.c9users.io/alert_list_user_delete").post(formBody).build();
        client.newCall(request).enqueue(deleteAlarmCallback);
    }
    public Callback deleteAlarmCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responsedata = response.body().string();
            Log.d("RESPONSEDATA4444 ", responsedata);
            Intent intent = getIntent();
            String id = intent.getStringExtra("U_ID");
            getAlist(id);
        }
    };
    public void getAlist(String a){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("u_id", a).build();
        Request request = new Request.Builder().url(allAlist_Url).post(formBody).build();
        client.newCall(request).enqueue(getAlistCallback);
    }
    public Callback getAlistCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {
            }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseData = response.body().string();

            Log.d("RESPONSEDATA allAlarm", responseData);
            try {
                JSONObject jsonObject = new JSONObject(responseData);
                JSONArray ja = jsonObject.getJSONArray("data");
                for(int i= 0 ; i < ja.length(); i ++) {
                     JSONObject order = ja.getJSONObject(i);
                     String master = order.getString("master_name");
                     String d_name = order.getString("nickname");
                     int alertType = order.getInt("alert_type");
                     String time = order.getString("created_at");
                     String id = order.getString("id");
                     String d_id = order.getString("d_id");
                     allAlertInfo alerts = new allAlertInfo(master, d_name, alertType, time, id, d_id);
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

        public void deleteAll(String a){
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder().add("u_id", a).build();
            Request request = new Request.Builder().url("https://button-hanyoon1108.c9users.io/alert_list_user_delete_all").post(formBody).build();
            client.newCall(request).enqueue(deleteAllCallback);
        }

    public Callback deleteAllCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseData = response.body().string();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    alertList.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),"모두 삭제되었습니다", Toast.LENGTH_LONG).show();

                }
            });
        }
    };
        }
