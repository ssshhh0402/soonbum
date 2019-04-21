package com.example.hong.dhproject3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class CustomDialog {
    private static final int LAYOUT = R.layout.dialog;

    private Context context;
    private Button bt1;
    private LinearLayout ll1,ll2;
    String u_id, addList_Url;
    private List<Devices> deviceList;

    public CustomDialog( Context context,String a, List<Devices> b){
        this.context=context;
        this.u_id = a;
        this.addList_Url = context.getResources().getString(R.string.base_Url)+"ud_create";
        this.deviceList = b;
    }
    public void callFunction(final FloatingActionButton fb){
        final Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog);

       ll1 = (LinearLayout)dlg.findViewById(R.id.linear1);
       ll2 = (LinearLayout)dlg.findViewById(R.id.linear2);
       bt1 = (Button)dlg.findViewById(R.id.btn1);
        dlg.show();
       ll1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               deviceAdd();
               dlg.dismiss();
           }
       });
       ll2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(context,"기기 추가 모드!", Toast.LENGTH_LONG).show();
           }
       });
       bt1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dlg.dismiss();
           }
       });
    }
    public void deviceAdd(){
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle("기기 추가");
        ad.setMessage("기기 코드를 입력해 주세요");
        final EditText et = new EditText(context);
        ad.setView(et);
        ad.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                String value = et.getText().toString();
                addList(value);
                dialog.dismiss();
            }
        } );
        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        ad.show();

    }
    public void addList(String a){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("u_id", u_id).add("d_code",a).build();
        Request request = new Request.Builder().url(addList_Url).post(formBody).build();
        client.newCall(request).enqueue(addListCallback);

    }
    public Callback addListCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }
        //status, message, data(id,d_name)
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responseData = response.body().string();
            Log.d("RESPONSEDATA 1235", responseData);

            /*try {
                SharedPreferences auto = context.getSharedPreferences("setting", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                String u_id = auto.getString("u_id","");
                JSONObject order = new JSONObject(responseData);
                JSONObject order2= order.getJSONObject("data");
                int deviceId = order2.getInt("id");
                String deviceName = order2.getString("d_name");
                String nickName = order2.getString("nickname");
                String user_type = order2.getString("user_type");
                String d_code = order2.getString("d_code");
                String master = order2.getString("master");
                Devices device = new Devices(u_id,deviceId, deviceName, nickName, user_type,d_code, master);
                deviceList.add(device);
                Log.d("RESPONSEDATA 123516", deviceList.toString());
            }catch(JSONException e){
                e.printStackTrace();
            }*/

        }
    };

    }

