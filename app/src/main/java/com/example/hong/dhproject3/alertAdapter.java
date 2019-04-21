package com.example.hong.dhproject3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class alertAdapter extends BaseAdapter {
    private Context context;
    private List<alertInfo> alertList;

    public alertAdapter(Context context, List<alertInfo> alertList) {
        this.context = context;
        this.alertList = alertList;
    }

    @Override
    public int getCount() {
        return alertList.size();
    }

    @Override
    public Object getItem(int i) {
        return alertList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.alert,null);
        TextView tv1 = (TextView)v.findViewById(R.id.tv1);
        TextView tv2 = (TextView)v.findViewById(R.id.tv2);
        TextView tv3 = (TextView)v.findViewById(R.id.tv3);
        ImageView iv1 = (ImageView)v.findViewById(R.id.iv1);

        tv1.setText(String.valueOf(alertList.get(i).getAlertStatus()));
        tv2.setText(alertList.get(i).getAlertDate());
        tv3.setText(alertList.get(i).getAlertTime());
        if(alertList.get(i).getAlertStatus().equals("기기연결"))
            iv1.setImageResource(R.mipmap.scos_star);

        v.setTag(alertList.get(i).getAlertStatus());

        return v;
    }
}
