package com.example.hong.dhproject3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class findId extends AppCompatActivity {
            EditText et1;
            Button bt1;
            String a;
            String findId_Url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findid);
        et1 = (EditText)findViewById(R.id.inputEmail2);
        bt1 = (Button)findViewById(R.id.confirm);
        findId_Url = getResources().getString(R.string.base_Url)+"find_id";

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = et1.getText().toString();
                findIdByEmail(a);
            }
        });

    }
    public void findIdByEmail(String a){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("email", a).build();
        Request request = new Request.Builder().url(findId_Url).post(formBody).build();

        client.newCall(request).enqueue(findIdByEmailCallback);
    }

    public Callback findIdByEmailCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseData = response.body().string();
            Log.d("RESPONSEDATA333 ", responseData);
            try {
                JSONObject jo = new JSONObject(responseData);
               if(jo.getString("status").equals("Good"))
             runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     Toast.makeText(getApplicationContext(),"이메일로 전송되었습니다",Toast.LENGTH_LONG).show();
                 }
             });
            }catch(JSONException e){}

        }
    };}
