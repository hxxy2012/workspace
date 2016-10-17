package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
public class MutexImageView extends ImageView {

	public MutexImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MutexImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MutexImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private int resNormal;
	private int resChecked;

	public void initImageSrcID(int resNormal, int resChecked) {
		this.resChecked = resChecked;
		this.resNormal = resNormal;
	}

	private boolean isChecked = false;

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
//		if (isChecked)
//			setImageResource(resChecked);
//		else
//			setImageResource(resNormal);
		if (isChecked)
			setBackgroundResource(resChecked);
		else
			setBackgroundResource(resNormal);
	}
}
