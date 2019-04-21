package com.example.hong.dhproject3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

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

public class loading extends AppCompatActivity {
    String login_Url="";
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);
        iv= (ImageView)findViewById(R.id.imageView3);
        login_Url= this.getResources().getString(R.string.base_Url)+"user_login";
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate);
        iv.startAnimation(animation);
        startLoading();
    }



    private void startLoading(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                if(auto.getBoolean("AUTO", false)){
                    String u_id=auto.getString("u_id","");
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    intent.putExtra("ID",u_id);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent2);
                }
            }
        }, 3000);
    }



}
