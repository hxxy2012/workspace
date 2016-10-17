package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class SportCircleLayout extends FrameLayout {

	public SportCircleLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SportCircleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SportCircleLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	private void  init(Context context){
		ImageButton ib_type=	new ImageButton(context);
		ImageButton ib_value=	new ImageButton(context);
		this.addView(ib_value);
		this.addView(ib_type);

	}
View.OnClickListener listener;
}
