package com.example.hong.dhproject3;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class Tab2Content extends Fragment {
    private ListView listview;
    private alertAdapter adapter;
    private List<alertInfo> alertList;
    String u_id,d_id,user_type;
    private String alertList_Url ="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);
        alertList_Url =getResources().getString(R.string.base_Url)+"alert_list_device";
        getIds();
        getNumList(getD_id());
        listview = (ListView)rootView.findViewById(R.id.tablistView2);
        alertList = new ArrayList<alertInfo>();
        adapter = new alertAdapter(rootView.getContext(), alertList);
        listview.setAdapter(adapter);
        return rootView;
    }
    public void getIds(){
        String a = getArguments().getString("U_ID");
        String b = getArguments().getString("D_ID");
        String c = getArguments().getString("U_TYPE");
        setU_id(a);
        setD_id(b);
        setUser_type(c);
    }
    public void getNumList(String a){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("d_id", a).build();
        Request request = new Request.Builder().url(alertList_Url).post(formBody).build();
        client.newCall(request).enqueue(getNumlistCallback);
    }

    public Callback getNumlistCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseData = response.body().string();

            Log.d("RESPONSEDATA", responseData);
            try {
                JSONObject jsonObject = new JSONObject(responseData);
                JSONArray ja = jsonObject.getJSONArray("data");
                for(int i= 0 ; i < ja.length(); i ++) {
                    JSONObject order = ja.getJSONObject(i);
                    int alertType = order.getInt("alert_type");
                    String time = order.getString("created_at");
                    alertInfo alerts = new alertInfo(alertType, time);
                    alertList.add(alerts);
                }
                notifyChanged();
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    };
    public void notifyChanged(){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                adapter.notifyDataSetChanged();
            }
        });
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
}
