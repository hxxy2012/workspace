package com.hike.digitalgymnastic.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.hiko.enterprisedigital.R;


public class MyProgressDialog extends Dialog{
	TextView message;
	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
		setContentView(R.layout.progressdialog);
		 message=(TextView) findViewById(R.id.message);
		
		setCancelable(false);
//		setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {
//		public void onCancel(DialogInterface dialog) {
//			dialog.dismiss();
//		}
//		});
	}
	public void setMessage(String msg){
		if(msg!=null&&msg.length()>0)
			message.setText(msg);
		else
			message.setText("加载中....");
	}
}
