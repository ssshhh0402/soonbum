package com.example.hong.dhproject3;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RelationsAdapter extends BaseAdapter {

    private Context context;
    private List<Relations> list;

    public RelationsAdapter(Context context, List<Relations> relationList){
        this. context= context;
        this.list = relationList;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.relations, null);
        TextView tv1 = (TextView)v.findViewById(R.id.u_name);
        tv1.setText(list.get(i).getU_name());

        v.setTag(list.get(i).getU_name());

        return v;
    }
}
