package com.example.hong.dhproject3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.readystatesoftware.viewbadger.BadgeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
        EditText et1,et2;
        Button bt1,bt2;
        TextView tv1,tv2;
        String id, pw;

        private final long FINISH_INTERVAL_TIME = 2000;
        private long backPressedTime = 0;
        public String login_Url ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.idInput);
        et2 = (EditText)findViewById(R.id.pwInput);
        bt1 = (Button)findViewById(R.id.logIn);
        bt2 = (Button)findViewById(R.id.signUp);
        tv1 = (TextView)findViewById(R.id.missing);
        tv2 = (TextView)findViewById(R.id.missingId);
        login_Url = this.getResources().getString(R.string.base_Url)+"user_login";
        SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = auto.edit();
        if(auto.getBoolean("AUTO", false)){
                id = auto.getString("ID", "");
                pw = auto.getString("PW","");
                et1.setText(id);
                et2.setText(pw);
                String token = FirebaseInstanceId.getInstance().getToken();
                loginInfo(id,pw, token);
        }
            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent intent = new Intent(getApplicationContext(), signUp.class);
                    startActivity(intent);

                }
            });
            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), missing.class);
                    startActivity(intent);
                }
            });
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id = et1.getText().toString();
                    pw = et2.getText().toString();
                    String token = FirebaseInstanceId.getInstance().getToken();
                    loginInfo(id, pw, token);
                    }


                });

            et1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(!b){
                        hideKeyboard();
                    }
                }
            });
            et2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(!b){
                       hideKeyboard();
                    }
                }
            });
           // et1.addTextChangedListener(watcher1);
           // et2.addTextChangedListener(watcher2);
            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), findId.class);
                    startActivity(intent);
                }
            });
            }
/*
    TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String s = editable.toString();
            if(s.length()==0)
                et1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user2,0,0,0);
            else
                et1.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }
    };
    TextWatcher watcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String s = editable.toString();
            if(s.length()==0)
                et2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password,0,0,0);
            else
                et2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }
    };*/
            public void hideKeyboard(){
                View view = this.getCurrentFocus();
                if(view != null){
                    InputMethodManager imm = (InputMethodManager)getSystemService((Context.INPUT_METHOD_SERVICE));
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }


   public void loginInfo(String a, String b, String c){
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        b = encryptSHA512(encryptSHA512(b)+ date);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("user_id", a).add("password", b).add("user_token", c).build();
        Request request = new Request.Builder().url(login_Url).post(formBody).build();

        client.newCall(request).enqueue(loginInfoCallback);
    }
    private Callback loginInfoCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e){
            Log.d("TEST", "ERROR MESSAGE : " + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final String responseData = response.body().string();
            try{
                JSONObject jo = new JSONObject(responseData);
                String status = jo.getString("status");
                if(status.equals("Good")){
                    JSONObject ja = jo.getJSONObject("data");
                    String id2 = ja.getString("id");
                    String token = ja.getString("user_token");
                    String email=ja.getString("email");
                    String nick = ja.getString("u_name");
                    SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor save = auto.edit();
                    save.putString("ID", et1.getText().toString());
                    save.putString("PW", et2.getText().toString());
                    save.putString("u_id", id2);
                    save.putString("TOKEN", token);
                    save.putString("EMAIL", email);
                    save.putString("NICKNAME",nick);
                    save.putBoolean("AUTO", true);
                    save.commit();
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    intent.putExtra("ID", id2);
                    finish();
                    startActivity(intent);
                }
                else if(status.equals("Error1")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "아이디를 잘 못 입력하셨습니다", Toast.LENGTH_LONG).show();
                            et1.setText("");
                            et2.setText("");
                        }
                    });
                   }
                   else if(status.equals("Error2")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "비밀번호를 잘 못 입력하셨습니다", Toast.LENGTH_LONG).show();
                            et2.setText("");
                        }
                    });
                }
            } catch(JSONException e){}

        }
    };

    public static String encryptSHA512(String target){
        String sha = "";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(target.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i< byteData.length; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            sha = sb.toString();


            return sha;
        } catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if(0 < intervalTime && FINISH_INTERVAL_TIME >= intervalTime){
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "버튼을 한번 더 입력하시면 어플리케이션이 종료됩니다", Toast.LENGTH_LONG).show();
        }
    }
}
