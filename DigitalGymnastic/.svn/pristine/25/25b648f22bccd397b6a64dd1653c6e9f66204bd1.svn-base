package com.hike.digitalgymnastic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by huwei on 2016/2/19.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    public LayoutInflater inflater;
    public Context context;
    public List<T> list;

    public MyBaseAdapter(List<T> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getConvertView(position, convertView, parent);
    }

    public abstract View getConvertView(int position, View convertView, ViewGroup parent);
}

