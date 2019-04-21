package com.example.hong.dhproject3;


import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class signUp extends AppCompatActivity {
        EditText et1, et2, et3, et4,et5;
        Button bt1;
        TextView tv1;
        String id, pw, email, pw2, nick;
        boolean check;


        private String signUp_Url,checkId_Url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tv1 = (TextView)findViewById(R.id.hint);
        et1 = (EditText) findViewById(R.id.etId);
        et2 = (EditText) findViewById(R.id.etNick);
        et3 = (EditText) findViewById(R.id.etemail);
        et4 = (EditText) findViewById(R.id.etPw);
        et5 = (EditText) findViewById(R.id.checkPw);
        bt1 = (Button) findViewById(R.id.register);
        checkId_Url = getResources().getString(R.string.base_Url)+"check_id";
        signUp_Url = getResources().getString(R.string.base_Url)+"user_create";
        et3.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = et1.getText().toString();
                email = et3.getText().toString();
                nick = et2.getText().toString();
                pw = et4.getText().toString();
                pw2 = et5.getText().toString();
                String token = FirebaseInstanceId.getInstance().getToken();
                if(pw.equals(pw2))
                signUpInfo(id, nick, email, pw, token);
                else
                    Toast.makeText(getApplicationContext(), "비밀번호를 잘못 입력하셨습니다", Toast.LENGTH_LONG).show();
                }


        });
        et1.addTextChangedListener(watcher1);
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
    public void signUpInfo(String a, String b, String c, String d, String e){
        d = encryptSHA512(d);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("user_id", a).add("u_name", b).add("email", c).add("password", d).add("user_token", e).build();
        Request request = new Request.Builder().url(signUp_Url).post(formBody).build();

        client.newCall(request).enqueue(signUpInfoCallback);
    }
    private Callback signUpInfoCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("TEST", "ERROR MESSAGE : " + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseData = response.body().string();
            try {
                JSONObject jo = new JSONObject(responseData);
                String status = jo.getString("status");
                Log.d("STATUS:", status);
                if (status.equals("Good")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"회원가입에 되셨습니다!", Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    finish();
                }


            } catch (JSONException e) {
            }
        }
        };
    public static String encryptSHA512(String target){
        /*try{
            MessageDigest sh = MessageDigest.getInstance("SHA-512");
            sh.update(target.getBytes());
            StringBuffer sb = new StringBuffer();

            for(byte b : sh.digest())
                sb.append(Integer.toHexString(0xff & b));
            return sb.toString();
        } catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }*/
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
    TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String s= editable.toString();
            if(s.length() < 3) {
                tv1.setHint("아이디는 3글자 이상 입력하셔야 합니다");
            }else
                checkId(s);
            }


    /*
    public static final boolean isValidEmail(String a){
        return !TextUtils.isEmpty(a) && android.util.Patterns.EMAIL_ADDRESS.matcher(a).matches();
    }*/
};
    public void checkId(String a){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("user_id", a).build();
        Request request = new Request.Builder().url(checkId_Url).post(formBody).build();

        client.newCall(request).enqueue(checkIdCallback);
    }
    private Callback checkIdCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("TEST", "ERROR MESSAGE : " + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseData = response.body().string();
            try {
                JSONObject jo = new JSONObject(responseData);
                String status = jo.getString("status");
                Log.d("STATUS:", status);
                if (status.equals("Good")) {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           tv1.setHint("사용 가능한 아이디입니다");
                       }
                   });
                }  else if(status.equals("Error")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv1.setHint("사용하실 수 없는 아이디입니다");
                            tv1.setHintTextColor(Color.RED);
                        }
                    });
                }


            } catch (JSONException e) {
            }
        }
    };
    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService((Context.INPUT_METHOD_SERVICE));
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    }
