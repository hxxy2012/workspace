package com.hike.digitalgymnastic.view;

import com.hiko.enterprisedigital.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageViewWihtBonder extends LinearLayout {
	Context context;
	boolean isChecked;

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
		if(isChecked){
			root.setBackgroundResource(R.drawable.bg_border);
		}else{
			root.setBackgroundColor(Color.parseColor("#3b3f4a"));
		}
	}

	public boolean isChecked() {
		return isChecked;
	}

	public ImageViewWihtBonder(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}

	public ImageViewWihtBonder(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}

	public ImageViewWihtBonder(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}
	
	ImageView iv_type;
	LinearLayout root;
	private void init(){
		inflate(context, R.layout.sporttype_item, this);
		root=(LinearLayout) findViewById(R.id.root);
		iv_type=(ImageView) findViewById(R.id.iv_type);
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isChecked=!isChecked;
				if(isChecked){
					root.setBackgroundResource(R.drawable.bg_border);
				}else{
					root.setBackgroundColor(Color.parseColor("#3b3f4a"));
				}
				if(listener!=null){
					listener.onCheckStateChanged(isChecked,ImageViewWihtBonder.this);
				}
			}
		});
	}
	int resId;

	public Bitmap getBitmap() {
		return bitmap;
	}

	Bitmap bitmap;
	public int getResId() {
		return resId;
	}

	public void setImageResouce(int resId){
		this.resId=resId;
		iv_type.setImageResource(resId);
	}
	public void setImageBitmap(Bitmap bitmap){
		this.bitmap=bitmap;
		iv_type.setImageBitmap(bitmap);
	}
	OnCheckStateChangedListener listener;
	
	public OnCheckStateChangedListener getListener() {
		return listener;
	}

	public void setListener(OnCheckStateChangedListener listener) {
		this.listener = listener;
	}

	public interface OnCheckStateChangedListener{
		public void onCheckStateChanged(boolean isChecked,ImageViewWihtBonder iv);
	}
}
