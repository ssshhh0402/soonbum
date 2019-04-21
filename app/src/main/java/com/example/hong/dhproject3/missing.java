package com.example.hong.dhproject3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class missing extends AppCompatActivity {
    EditText et1;
    Button bt1, bt2;
    private String missing_Url = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing);
        et1 = (EditText) findViewById(R.id.findId);
        bt1 = (Button) findViewById(R.id.find);

        missing_Url = getResources().getString(R.string.base_Url)+"reset_pwd";
        et1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String a = et1.getText().toString();
                findEmail(a);
                finish();
            }
        });
       et1.addTextChangedListener(textWatcher);
       }
    TextWatcher textWatcher = new TextWatcher() {
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
    public static final boolean isValidEmail(String a) {
        return !TextUtils.isEmpty(a) && android.util.Patterns.EMAIL_ADDRESS.matcher(a).matches();
    }

    public void findEmail(String a) {
        if (!isValidEmail(a)) {
            Toast.makeText(getApplicationContext(), "이메일을 잘 못 입력하셨습니다", Toast.LENGTH_LONG).show();
        } else {
            String token = FirebaseInstanceId.getInstance().getToken();
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder().add("email", a).add("user_token", token).build();
            Request request = new Request.Builder().url(missing_Url).post(formBody).build();
            client.newCall(request).enqueue(findEmailCallback);
        }
    }

    private Callback findEmailCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("TEST", "ERROR MESSAGE : " + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responseData = response.body().string();
            Log.d("RESPONSEDATA6666", responseData);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "이메일로 보냈습니다", Toast.LENGTH_LONG).show();
                }
            });
        }




    };
}