package com.example.hong.dhproject3;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
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

public class Tab1Content extends Fragment {
    Switch sw1,sw2,sw3;
    TextView tv0,tv1,tv2,tv3,tv4,tv5;
    ImageView iv;
    EditText et1;
    Button btn1,btn2;
    String u_id,d_id, user_type,master;
    private String alertList_Url,t_toggle_Url,v_toggle_Url,d_toggle_Url,update_Url,deviceInfo_Url ="";

    private ListView listview;
    private RelationsAdapter adapter;
    private List<Relations> relationList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);
        sw1 = (Switch)rootView.findViewById(R.id.switch1);
        sw2 = (Switch)rootView.findViewById(R.id.switch2);
        sw3 = (Switch)rootView.findViewById(R.id.switch3);
        tv0 = (TextView)rootView.findViewById(R.id.tvNick);
        tv1 = (TextView)rootView.findViewById(R.id.wifi);
        tv2 = (TextView)rootView.findViewById(R.id.tvWID);
        tv3 = (TextView)rootView.findViewById(R.id.tvWPW);
        tv4 = (TextView)rootView.findViewById(R.id.alarmTap);
        tv5 = (TextView)rootView.findViewById(R.id.mName);
        iv= (ImageView)rootView.findViewById(R.id.nickEdit);
        et1 = (EditText)rootView.findViewById(R.id.et1);
        btn1=(Button)rootView.findViewById(R.id.btn1);
        btn2 = (Button)rootView.findViewById(R.id.btn2);
        alertList_Url =getResources().getString(R.string.base_Url)+"alert_list_device";
        t_toggle_Url = getResources().getString(R.string.base_Url)+"device_t_toggle";
        v_toggle_Url = getResources().getString(R.string.base_Url)+"device_v_toggle";
        d_toggle_Url = getResources().getString(R.string.base_Url)+"device_d_toggle";
        update_Url = getResources().getString(R.string.base_Url)+"device_update";
        deviceInfo_Url = getResources().getString(R.string.base_Url)+"device_info";
        getIds();
        setDefault();
        listview = (ListView)rootView.findViewById(R.id.listView1);
        relationList = new ArrayList<Relations>();
        adapter = new RelationsAdapter(rootView.getContext(), relationList);
        listview.setAdapter(adapter);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = tv0.getText().toString();
                iv.setVisibility(View.GONE);
                tv0.setVisibility(View.GONE);
                et1.setVisibility(View.VISIBLE);
                et1.setText(a);
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.VISIBLE);
                et1.selectAll();
                et1.requestFocus();
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
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
                iv.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                et1.setVisibility(View.GONE);
                tv0.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et1.setVisibility(View.GONE);
                tv0.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
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


        return rootView;
    }

    public void notifyChanged(){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
    public void getIds(){
        String a = getArguments().getString("U_ID");
        String b = getArguments().getString("D_ID");
    //    String c = getArguments().getString("U_TYPE");
        String d = getArguments().getString("MASTER");
        setU_id(a);
        setD_id(b);
     //   setUser_type(c);
        setMaster(d);
    }
    public void T_toggle(boolean b){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("d_id", getD_id()).add("t_toggle",b+"").build();
        Request request = new Request.Builder().url(t_toggle_Url).post(formBody).build();
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
            //setDefault();

        }
    };
    public void V_toggle(boolean b){

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("d_id", getD_id()).add("v_toggle",b+"").build();
        Request request = new Request.Builder().url(v_toggle_Url).post(formBody).build();
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

        }
    };
    public void D_toggle(boolean b){

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("d_id", getD_id()).add("d_toggle",b+"").build();
        Request request = new Request.Builder().url(d_toggle_Url).post(formBody).build();
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
        }
    };

    public void editNickName(String a){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("u_id", getU_id()).add("d_id", getD_id()).add("nickname",a).build();
        Request request = new Request.Builder().url(update_Url).post(formBody).build();
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
     //   if(getUser_type().equals("m"))
     //      masterMode();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("d_id", getD_id()).build();
        Request request = new Request.Builder().url(deviceInfo_Url).post(formBody).build();
        client.newCall(request).enqueue(setDefaultCallback);
    }
    public Callback setDefaultCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseData = response.body().string();
            Log.d("ResponseData Tab1Content", responseData);
            try {
                JSONObject json = new JSONObject(responseData).getJSONObject("data");
                JSONArray ja  = new JSONObject(responseData).getJSONArray("list");
                String nick = json.getString("nickname");
                boolean v_alarm = json.getBoolean("v_toggle");
                boolean t_alarm = json.getBoolean("t_toggle");
                boolean d_alarm = json.getBoolean("d_toggle");
                setting(nick, v_alarm, t_alarm, d_alarm);
                for(int i = 0 ; i < ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    String u_name = jo.getString("u_name");
                    String u_type = jo.getString("user_type");
                    Relations relation = new Relations(u_name,u_type);
                    relationList.add(relation);
                }
            }catch(JSONException e){e.printStackTrace();
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };

    public void setting(String nick, boolean v_alarm, boolean t_alarm, boolean d_alarm){
        Log.d("RESPONSEDATA333", nick+" "+ v_alarm + " " + t_alarm + " " + d_alarm);
        final String a = nick;
        final boolean b = v_alarm;
        final boolean c = t_alarm;
        final boolean d = d_alarm;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv0.setText(a);
                tv5.setText(master);
                sw1.setChecked(c);
                sw2.setChecked(b);
                sw3.setChecked(d);

                }
        });
    }
    public void masterMode(){
        iv.setVisibility(View.VISIBLE);
        sw1.setClickable(true);
        sw2.setClickable(true);
        sw3.setClickable(true);
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

    public static void setListViewHeightBasedOnChildren(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter == null){
            return;
        }
        int totalHeight = 0;
        int desireWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),View.MeasureSpec.AT_MOST);
        for(int i = 0 ; i < listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desireWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * listAdapter.getCount()-1);
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    public void setMaster(String a){
        this.master = a;
    }

    public String getMaster(){
        return this.master;
    }
}
