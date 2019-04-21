package com.example.hong.dhproject3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

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

public class deviceInfo2 extends AppCompatActivity {
    Switch sw1,sw2,sw3,sw4;
    TextView tv0,tv1,tv2,tv3,tv4;
    ImageView iv;
    MenuItem menu1,menu2,menu3;
    EditText et1;
    Button btn1,btn2;
    String u_id,d_id, user_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info2);
        sw1 = (Switch)findViewById(R.id.switch1);
        sw2 = (Switch)findViewById(R.id.switch2);
        sw3 = (Switch)findViewById(R.id.switch3);
        sw4 = (Switch)findViewById(R.id.switch4);
        tv0 = (TextView)findViewById(R.id.tvNick);
        tv1 = (TextView)findViewById(R.id.wifi);
        tv2 = (TextView)findViewById(R.id.tvWID);
        tv3 = (TextView)findViewById(R.id.tvWPW);
        tv4 = (TextView)findViewById(R.id.alarmTap);
        iv= (ImageView)findViewById(R.id.nickEdit);
        menu1 = (MenuItem)findViewById(R.id.m1);
        menu2 = (MenuItem)findViewById(R.id.m2);
        menu3 = (MenuItem)findViewById(R.id.m3);
        et1 = (EditText)findViewById(R.id.et1);
        btn1=(Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
       IntentInformation();
        setDefault();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = tv0.getText().toString();
                tv0.setVisibility(View.GONE);
                et1.setVisibility(View.VISIBLE);
                et1.setText(a);
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.VISIBLE);
                et1.requestFocus();
                }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String edit_nick = et1.getText().toString();
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("이름 변경");
                alert.setMessage("변경하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       editNickName(edit_nick);
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                et1.setVisibility(View.GONE);
                tv0.setVisibility(View.VISIBLE);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et1.setVisibility(View.GONE);
                tv0.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
            }
        });
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                T_toggle(b);
            }
        });
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                V_toggle(b);
            }
        });
        sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                D_toggle(b);
            }
        });
    }
    public void T_toggle(boolean b){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("d_id", getD_id()).add("t_toggle",b+"").build();
        Request request = new Request.Builder().url("https://button-hanyoon1108.c9users.io/device_t_toggle").post(formBody).build();
        client.newCall(request).enqueue(T_toggleCallback);
        }
    public Callback T_toggleCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responsedata = response.body().string();
            Log.d("RESPONSEDATA4444 ", responsedata);
            setDefault();

        }
    };
    public void V_toggle(boolean b){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("d_id", getD_id()).add("v_toggle",b+"").build();
        Request request = new Request.Builder().url("https://button-hanyoon1108.c9users.io/device_v_toggle").post(formBody).build();
        client.newCall(request).enqueue(V_toggleCallback);
    }
    public Callback V_toggleCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responsedata = response.body().string();
            Log.d("RESPONSEDATA4444 ", responsedata);
            setDefault();

        }
    };
    public void D_toggle(boolean b){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("d_id", getD_id()).add("d_toggle",b+"").build();
        Request request = new Request.Builder().url("https://button-hanyoon1108.c9users.io/device_d_toggle").post(formBody).build();
        client.newCall(request).enqueue(D_toggleCallback);
    }
    public Callback D_toggleCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responsedata = response.body().string();
            Log.d("RESPONSEDATA4444 ", responsedata);
            setDefault();

        }
    };
    public void IntentInformation(){
        Intent intent= getIntent();
        String u_id = intent.getStringExtra("U_ID");
        String d_id = intent.getStringExtra("D_ID");
        String user_Type = intent.getStringExtra("USER_TYPE");
        setU_id(u_id);
        setD_id(d_id);
        setUser_type(user_Type);
    }
    public void editNickName(String a){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("u_id", getU_id()).add("d_id", getD_id()).add("nickname",a).build();
        Request request = new Request.Builder().url("https://button-hanyoon1108.c9users.io/device_update").post(formBody).build();
        client.newCall(request).enqueue(editNickNameCallback);
    }
    public Callback editNickNameCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            setDefault();

        }
    };

    public void setDefault(){
        if(getUser_type().equals("m"))
            masterMode();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("d_id", getD_id()).build();
        Request request = new Request.Builder().url("https://button-hanyoon1108.c9users.io/device_info").post(formBody).build();
        client.newCall(request).enqueue(setDefaultCallback);
    }
    public Callback setDefaultCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseData = response.body().string();
            Log.d("RESPONSEDATA333 ", responseData);
            try {
                JSONObject json = new JSONObject(responseData).getJSONObject("data");
                String nick = json.getString("nickname");
                boolean v_alarm = json.getBoolean("v_toggle");
                boolean t_alarm = json.getBoolean("t_toggle");
                boolean d_alarm = json.getBoolean("d_toggle");
                setting(nick, v_alarm, t_alarm, d_alarm);
            }catch(JSONException e){}
        }
    };

    public void setting(String nick, boolean v_alarm, boolean t_alarm, boolean d_alarm){
        Log.d("RESPONSEDATA333", nick+" "+ v_alarm + " " + t_alarm + " " + d_alarm);
        final String a = nick;
        final boolean c = v_alarm;
        final boolean b = t_alarm;
        final boolean d = d_alarm;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               tv0.setText(a);
               sw1.setChecked(b);
               sw2.setChecked(c);
               sw3.setChecked(d);
               }
        });
    }
    public void masterMode(){
        iv.setVisibility(View.VISIBLE);
        sw1.setClickable(true);
        sw2.setClickable(true);
        sw3.setClickable(true);
        sw4.setClickable(true);
        }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
