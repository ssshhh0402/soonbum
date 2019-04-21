package com.example.hong.dhproject3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WifiAdapter extends ArrayAdapter<WifiData> {
    Context context;
    int resource;
    List<WifiData> objects;
    LayoutInflater inflater;

    public WifiAdapter(Context context, int resource, List<WifiData> objects){
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(resource, null);

            holder = new ViewHolder();

            holder.tvBSSID = (TextView)convertView.findViewById(R.id.tvBSSID);
            holder.tvSSID = (TextView)convertView.findViewById(R.id.tvSSID);
            convertView.setTag(holder);
            System.out.println("convertViews Null " + position);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            System.out.println("convertViews Not null " + position);
        }
        WifiData wifiData = objects.get(position);
        holder.tvSSID.setText(wifiData.getSSID());
        holder.tvBSSID.setText(wifiData.getBSSID());

        return convertView;
    }

    class ViewHolder{
        TextView tvBSSID;
        TextView tvSSID;
    }
}
