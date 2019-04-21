package com.example.hong.dhproject3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class editPage extends AppCompatActivity {
        TextView tv1,tv2,tv3;
        Button btn1,btn2;
        String id,email,pw, userUpdate_Url,nick ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv1 = (TextView)findViewById(R.id.editId);
        tv2 = (TextView)findViewById(R.id.editNick);
        tv3 = (TextView)findViewById(R.id.editEmail);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        userUpdate_Url = getResources().getString(R.string.base_Url)+"user_update";
        setDefault();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), editInfo.class);
                startActivityForResult(intent,0);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), edit.class);
                startActivity(intent);
            }
        });

    }
    public void upDateEmail(String a){
        SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = auto.edit();
        String id = auto.getString("u_id", "");
        String token = FirebaseInstanceId.getInstance().getToken();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("u_id", id).add("email", a).add("user_token", token).build();
        Request request = new Request.Builder().url(userUpdate_Url).post(formBody).build();

        client.newCall(request).enqueue(updateCallback);
    }
    public void updateNick(String a){
        SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = auto.edit();
        String id = auto.getString("u_id", "");
        String token = FirebaseInstanceId.getInstance().getToken();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("u_id", id).add("u_name", a).add("user_token", token).build();
        Request request = new Request.Builder().url(userUpdate_Url).post(formBody).build();
        client.newCall(request).enqueue(updateNickCallback);
    }
    private Callback updateCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e){
            Log.d("TEST", "ERROR MESSAGE : " + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseData = response.body().string();
           try{ JSONObject jo = new JSONObject(responseData);
               String status = jo.getString("status");
               if(status.equals("Good")) {
                   JSONObject ja = jo.getJSONObject("data");
                   String email = ja.getString("email");
                   SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                   SharedPreferences.Editor save = auto.edit();
                   save.putString("EMAIL", email);
                   save.commit();
               }
               }catch(JSONException e){}
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    tv3.setVisibility(View.VISIBLE);
                    Toast.makeText(editPage.this,"성공적으로 변경되었습니다", Toast.LENGTH_LONG).show();
                }
            });
            setDefault();
        }
    };
    private Callback updateNickCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e){

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseData = response.body().string();
            try{ JSONObject jo = new JSONObject(responseData);
                String status = jo.getString("status");
                if(status.equals("Good")) {
                    JSONObject ja = jo.getJSONObject("data");
                    String nick = ja.getString("u_name");
                    SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor save = auto.edit();
                    save.putString("NICKNAME", nick);
                    save.commit();
                }
            }catch(JSONException e){}
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(getApplicationContext(),"성공적으로 변경되었습니다", Toast.LENGTH_LONG).show();
                }
            });
            setDefault();
        }
    };
    public void setDefault(){
        SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = auto.edit();
        id = auto.getString("ID","");
        email=auto.getString("EMAIL","");
        nick = auto.getString("NICKNAME","");
        Log.d("ResponseData12345", id + " " + email + " " + nick );
        tv1.setText(id);
        tv2.setText(nick);
        tv3.setText(email);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){

            case android.R.id.home:{
                finish();
                break;
            }
        }
        return true;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 0:
                setDefault();
            default:
                setDefault();
        }
    }
}
