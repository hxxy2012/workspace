package com.hike.digitalgymnastic.service;


import android.content.Context;
import android.os.Binder;
import android.os.Handler;

public class BrainBind extends Binder {


	private String m_filesDir;

	public BrainBind(Context context)
	{
		m_filesDir = context.getFilesDir().toString();
	}

}
