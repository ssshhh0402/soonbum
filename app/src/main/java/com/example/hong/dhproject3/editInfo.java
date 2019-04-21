package com.example.hong.dhproject3;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

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

public class editInfo extends AppCompatActivity {
    TextView tv1;
    EditText et1,et2;
    Button bt1;
    String userUpdate_Url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        tv1 = (TextView)findViewById(R.id.editId);
        et1 = (EditText)findViewById(R.id.editNick);
        et2 = (EditText)findViewById(R.id.editEmail);
        bt1 = (Button)findViewById(R.id.bt1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userUpdate_Url = getResources().getString(R.string.base_Url)+"user_update";
        setDefault();

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = et1.getText().toString();
                String b = et2.getText().toString();
                if(a.equals(""))
                    Toast.makeText(getApplicationContext(),"이름을 입력하여 주십시요",Toast.LENGTH_LONG).show();
                else if(b.equals(""))
                    Toast.makeText(getApplicationContext(),"이메일을 입력하여 주십시요", Toast.LENGTH_LONG).show();
                else
                    changeInfo(a,b);
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
    }

    public void setDefault(){
        SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = auto.edit();
        String id = auto.getString("ID","");
        String email=auto.getString("EMAIL","");
        String nick = auto.getString("NICKNAME","");
        tv1.setText(id);
        et1.setText(nick);
        et2.setText(email);
    }

    public void changeInfo(String a, String b){
        SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = auto.edit();
        String id = auto.getString("u_id", "");
        String token = FirebaseInstanceId.getInstance().getToken();
        editor.putString("NICKNAME", a);
        editor.putString("EMAIL", b);
        editor.commit();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("u_id", id).add("nickname", a).add("email", b).add("user_token", token).build();
        Request request = new Request.Builder().url(userUpdate_Url).post(formBody).build();
        client.newCall(request).enqueue(changeInfoCallback);
    }
    private Callback changeInfoCallback = new Callback(){
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
                            Toast.makeText(editInfo.this,"성공적으로 변경되었습니다", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(0);
    }
    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService((Context.INPUT_METHOD_SERVICE));
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
