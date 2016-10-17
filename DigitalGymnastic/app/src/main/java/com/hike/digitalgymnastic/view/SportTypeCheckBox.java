package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SportTypeCheckBox extends TextView implements OnClickListener{

	Context context;
	boolean isChecked;
	
	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
		if(isChecked)
			setBackgroundResource(resid_selected);
		else
			setBackgroundResource(resid_normal);
	}
	
	
	public SportTypeCheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}

	public SportTypeCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}

	public SportTypeCheckBox(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}

	
	private void init(){
		setOnClickListener(this);
		
	}
	
	int resid_normal, resid_selected;
	/**
	 * 
	 * @param resid_normal
	 * @param resid_selected
	 * @category 设置选中和未选中状态图片
	 */
	public void setBackGround(int resid_normal,int resid_selected){
		this.resid_normal=resid_normal;
		this.resid_selected=resid_selected;
		if(isChecked)
			setBackgroundResource(resid_selected);
		else
			setBackgroundResource(resid_normal);
	}
	int resid_textcolor_normal, resid_textcolor_selected;
	/**
	 * 
	 * @param resid_textcolor_normal
	 * @param resid_textcolor_selected
	 * @category  设置字体颜色
	 */
	public void setTextColorID(int resid_textcolor_normal,int resid_textcolor_selected){
		this.resid_textcolor_normal=resid_textcolor_normal;
		this.resid_textcolor_selected=resid_textcolor_selected;
		if(isChecked){
			getPaint().setAlpha(200);//20%透明度
			setTextColor(getResources().getColor(resid_textcolor_selected));
		}

		else{
			getPaint().setAlpha(200);
			setTextColor(getResources().getColor(resid_textcolor_normal));
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		isChecked=!isChecked;
		if(isChecked)
			setBackgroundResource(resid_selected);
		else
			setBackgroundResource(resid_normal);
		if(onCheckedChangeListener!=null){
			onCheckedChangeListener.onCheckedChanged(v,isChecked);
			this.clearFocus();
		}
		
	}
	
	OnCheckedChangeListener  onCheckedChangeListener;

	public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
		this.onCheckedChangeListener = onCheckedChangeListener;
	}
	public interface  OnCheckedChangeListener{
		public void onCheckedChanged(View view, boolean checked);
	}
}
