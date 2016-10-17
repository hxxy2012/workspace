package com.hike.digitalgymnastic.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.hiko.enterprisedigital.R;

public class ZanButton extends Button {
	int zanCount;
	

	public ZanButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ZanButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ZanButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public int getZanCount() {
		return zanCount;
	}

	public void setZanCount(int zanCount) {
		this.zanCount = zanCount;
		if(zanCount>0){
			setBackgroundResource(R.mipmap.zan_click);
		}else{
			setBackgroundResource(R.mipmap.zan_normal);
		}
	}
}
