package com.example.hong.dhproject3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class wifiInput extends AppCompatActivity {
        TextView tv1;
        EditText et1;
        Button bt1, bt2;
        String id,pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_input);
        tv1 = (TextView)findViewById(R.id.idInput);
        et1 = (EditText)findViewById(R.id.pwInput);
        bt1 = (Button)findViewById(R.id.send);
        bt2 = (Button)findViewById(R.id.cancel);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), wifiActivity.class);
                startActivityForResult(intent,0);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw = et1.getText().toString();
                send(id,pw);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case 0:
                id = data.getStringExtra("ID");
                if(id.equals("")){
                    Toast.makeText(getApplicationContext(), "아무것도 선택하지 않으셨습니다", Toast.LENGTH_LONG).show();
                break;
                }
                else{
                tv1.setText(id);
                break;}
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void send(String a, String b){
        Intent intent = new Intent();
        intent.putExtra("ID", a);
        intent.putExtra("PW", b);
        setResult(0, intent);
    }
}
