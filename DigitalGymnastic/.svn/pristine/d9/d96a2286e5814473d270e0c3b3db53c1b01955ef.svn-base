package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.InputStream;

public class MyImageView extends ImageView {

		Context context;
	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	public MyImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	public void setImageResource(int resId) {
		
		setImageBitmap(readBitMap(context,resId));
	}
	
//	/**
//	* 以最省内存的方式读取本地资源的图片
//	* 
//	* @param context
//	* @param resId
//	* @return
//	*/
//	public static Bitmap readBitMap(Context context, int resId) {
//		BitmapFactory.Options opt = new BitmapFactory.Options();
//		opt.inPreferredConfig = Bitmap.Config.RGB_565;
//		opt.inPurgeable = true;
//		opt.inInputShareable = true;
//		opt.inSampleSize=3;
//		BitmapFactory.decodeResource(context.getResources(), resId, opt);
//		// 获取资源图片
//		return BitmapFactory.decodeResource(context.getResources(), resId, opt);
//	}
	
	/**
	* 以最省内存的方式读取本地资源的图片
	* 
	* @param context
	* @param resId
	* @return
	*/
	public static Bitmap readBitMap(Context context, int resId) {
	BitmapFactory.Options opt = new BitmapFactory.Options();
	opt.inPreferredConfig = Bitmap.Config.RGB_565;
	opt.inPurgeable = true;
	opt.inInputShareable = true;
	// 获取资源图片
	InputStream is = context.getResources().openRawResource(resId);
	return BitmapFactory.decodeStream(is, null, opt);
	}
}
