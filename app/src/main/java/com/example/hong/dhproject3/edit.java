package com.example.hong.dhproject3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class edit extends AppCompatActivity {
            EditText et1,et3,et4;
            Button bt1, bt2;
            private String userUpdate_Url ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        et1 = (EditText)findViewById(R.id.currentPw);
        et3 = (EditText)findViewById(R.id.editPw);
        et4 = (EditText)findViewById(R.id.checkPw);
        bt1 = (Button)findViewById(R.id.edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userUpdate_Url = getResources().getString(R.string.base_Url)+"user_update";

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPw = et1.getText().toString();
                String change_Pw = et3.getText().toString();
                String check_Pw = et4.getText().toString();
                if(checkPassword(currentPw)){
                        if(checkSame(change_Pw,check_Pw)){
                            changePassword(change_Pw);
                        }
                    }
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
        et3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    hideKeyboard();
                }
            }
        });
        et4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    hideKeyboard();
                }
            }
        });

    }

    public boolean checkPassword(String a){
        SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = auto.edit();

        String pw = auto.getString("PW","");
        if(a.equals(pw))
        return true;
        else{
            Toast.makeText(getApplicationContext(), "현재 비밀번호를 잘못 입력",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public boolean checkSame(String a, String b){
        if(a.equals(b))
                return true;
        else
            Toast.makeText(getApplicationContext(), "두개 불 일치",Toast.LENGTH_LONG).show();
            return false;
    }
    public void changePassword(String a){
        SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = auto.edit();
        String id = auto.getString("u_id", "");
        String token = FirebaseInstanceId.getInstance().getToken();
        editor.putString("PW", a);
        editor.commit();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("u_id", id).add("password", encryptSHA512(a)).add("user_token", token).build();
        Request request = new Request.Builder().url(userUpdate_Url).post(formBody).build();
        client.newCall(request).enqueue(changePasswordCallback);
    }
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
    private Callback changePasswordCallback = new Callback(){
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(edit.this,"성공적으로 변경되었습니다", Toast.LENGTH_LONG).show();
                        }
                    });
                    finish();
                }
            } catch(JSONException e){}

        }
    };
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){

            case android.R.id.home:{
                finish();
                break;
            }
        }
        return true;
    }
    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService((Context.INPUT_METHOD_SERVICE));
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
