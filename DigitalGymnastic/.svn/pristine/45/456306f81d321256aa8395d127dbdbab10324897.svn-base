package com.hike.digitalgymnastic.adapter;

import java.util.List;
import com.hiko.enterprisedigital.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TestAdapter extends BaseAdapter{
	private List<String> test_datalists;
	private LayoutInflater test_inflater;
  
	public TestAdapter(Context applicationContext, List<String> datalists) {
		this.test_inflater = LayoutInflater.from(applicationContext);
		this.test_datalists = datalists;
	}

	@Override
	public int getCount() {
		return test_datalists.size();
	}

	@Override
	public Object getItem(int arg0) {
		return test_datalists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	public final class ViewHolder{
		 public TextView test_tv ;
	 }
	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
	    if (convertView == null) {
		  holder=new ViewHolder();
		  convertView = test_inflater.inflate(R.layout.test_list_item, null);
		  holder.test_tv = (TextView)convertView.findViewById(R.id.test_tv);
		  convertView.setTag(holder);
	    }else{
		  holder = (ViewHolder)convertView.getTag();
	    }
	    holder.test_tv.setText(test_datalists.get(position));
		return convertView;
	}
}
