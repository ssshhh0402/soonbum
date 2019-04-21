package com.example.hong.dhproject3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class DevicesAdapter extends BaseAdapter{
    //String pw,pwCheck;
    private Context context;
    private List<Devices> list;
    final static String updateList_Url =  "https://button-hanyoon1108.c9users.io/device_update";
    String t_Toggle_Url, d_Toggle_Url, v_Toggle_Url;
    Switch sw1,sw2,sw3;
    View v;
    public DevicesAdapter(Context context, List<Devices> devicelist){
        this.context = context;
        this.list = devicelist;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
         v = View.inflate(context, R.layout.devices, null);
         sw1 = (Switch)v.findViewById(R.id.switch11);
         sw2 = (Switch)v.findViewById(R.id.switch22);
         sw3 = (Switch)v.findViewById(R.id.switch33);
        TextView d_nick = (TextView)v.findViewById(R.id.dev_nick);
        TextView d_master = (TextView)v.findViewById(R.id.dev_master);
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        d_nick.setText(list.get(i).getDeviceNick());
        d_master.setText(list.get(i).get_master());
        sw1.setChecked(list.get(i).getaAlarm());
        sw2.setChecked(list.get(i).getbAlarm());
        sw3.setChecked(list.get(i).getcAlarm());
        t_Toggle_Url = v.getResources().getString(R.string.base_Url)+"device_t_toggle";
        d_Toggle_Url = v.getResources().getString(R.string.base_Url)+"device_d_toggle";
        v_Toggle_Url = v.getResources().getString(R.string.base_Url)+"device_v_toggle";
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            String d_id = Integer.toString(list.get(i).getDeviceId());
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              /*  if(!b){
                    sw1.setTrackDrawable(v.getResources().getDrawable(R.drawable.switch_track_custom_false));
                    sw1.setThumbDrawable(v.getResources().getDrawable(R.drawable.switch_thumb_custom_false));
                }
                else{
                    sw1.setTrackDrawable(v.getResources().getDrawable(R.drawable.switch_track_custom));
                    sw1.setThumbDrawable(v.getResources().getDrawable(R.drawable.switch_thumb_custom));
                }*/
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder().add("d_id", d_id).add("v_toggle",b+"").build();
                Request request = new Request.Builder().url(v_Toggle_Url).post(formBody).build();
                client.newCall(request).enqueue(V_toggleCallback);
            }
        });
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            String d_id = Integer.toString(list.get(i).getDeviceId());
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              /*  if(!b){
                    sw2.setTrackDrawable(v.getResources().getDrawable(R.drawable.switch_track_custom_false));
                    sw2.setThumbDrawable(v.getResources().getDrawable(R.drawable.switch_thumb_custom_false));
                }else{
                    sw2.setTrackDrawable(v.getResources().getDrawable(R.drawable.switch_track_custom));
                    sw2.setThumbDrawable(v.getResources().getDrawable(R.drawable.switch_thumb_custom));
                }*/
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder().add("d_id", d_id).add("t_toggle",b+"").build();
                Request request = new Request.Builder().url(t_Toggle_Url).post(formBody).build();
                client.newCall(request).enqueue(T_toggleCallback);
            }
        });
        sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            String d_id = Integer.toString(list.get(i).getDeviceId());
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                /*if(!b){
                    sw3.setTrackDrawable(v.getResources().getDrawable(R.drawable.switch_track_custom_false));
                    sw3.setThumbDrawable(v.getResources().getDrawable(R.drawable.switch_thumb_custom_false));
                }else{
                    sw3.setTrackDrawable(v.getResources().getDrawable(R.drawable.switch_track_custom));
                    sw3.setThumbDrawable(v.getResources().getDrawable(R.drawable.switch_thumb_custom));
                }*/
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder().add("d_id", d_id).add("d_toggle",b+"").build();
                Request request = new Request.Builder().url(d_Toggle_Url).post(formBody).build();
                client.newCall(request).enqueue(D_toggleCallback);
            }
        });
        v.setTag(list.get(i).getDeviceId());

        return v;
    }
    public Callback T_toggleCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responsedata = response.body().string();
            Log.d("RESPONSEDATA4444 ", responsedata);
        }
    };
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



    public void deleteDevice(String u_id,int d_id){

            String device_id = Integer.toString(d_id);
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder().add("u_id", u_id).add("d_id", device_id).build();
            Request request = new Request.Builder().url(updateList_Url).post(formBody).build();

            client.newCall(request).enqueue(deleteDeviceCallback);
        }

    public Callback deleteDeviceCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Toast.makeText(context,"수정되었습니다",Toast.LENGTH_LONG).show();
        }
    };



}
