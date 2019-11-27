package com.example.titlebarcolorgradient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.titlebarcolorgradient.R;

import java.util.List;

/**
 * Created by zhaoyuejun on 2017/2/7.
 */

public class MyAdapter extends BaseAdapter {

    Context mcontext;
    List<String> list;

    public MyAdapter(Context mcontext, List<String> list) {
        this.mcontext = mcontext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mcontext).inflate(R.layout.adapter_mlv, null);
        TextView name = (TextView) convertView.findViewById(R.id.name_tv);
        name.setText(list.get(position));
        return convertView;
    }
}
