package com.hike.digitalgymnastic.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.hike.digitalgymnastic.MainMenuActivity;
import com.hiko.enterprisedigital.R;
import com.hike.digitalgymnastic.adapter.MenuMessageAdapter;
import com.hike.digitalgymnastic.request.MessageDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
/*
 * 消息页面 
 */

public class MessageFrament extends BaseFragment {
	@ViewInject(R.id.btn_left)
	private ImageView btn_left;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.lv_message)
	private ListView lv_message;
	@ViewInject(R.id.iv_hasnomessage)
	private ImageView iv_hasnomessage;
	
	private View v;
	private MessageDao dao;
	private MenuMessageAdapter adapter;
	@OnClick(value = { R.id.btn_left,R.id.ll_btn_left})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			((MainMenuActivity) activity).fragmentBack();
			break;
		case R.id.ll_btn_left:
			((MainMenuActivity) activity).fragmentBack();
			break;	
			
		default:
			break;
		}
	}
	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);
		if (requestCode == Constants.deleteMessage) {
			dao.QueryMessage();// 获取消息
		}
        if (requestCode == Constants.queryMessage) {
        	showProgress(false);
        	adapter.setMessagecontext(dao.getMessagecontext().getMessageList());
        	if(adapter.getCount()==0){
    			lv_message.setVisibility(View.GONE);
    			iv_hasnomessage.setVisibility(View.VISIBLE);
    		}else{
    			lv_message.setVisibility(View.VISIBLE);
    			iv_hasnomessage.setVisibility(View.GONE);
    		}
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		this.v = inflater.inflate(R.layout.activity_menu_message, container, false);
		ViewUtils.inject(this, v);
		return v;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
			showProgress(true);
			getMessage();
		}
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Init();
	}

	private void Init() {
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		iv_hasnomessage.setVisibility(View.VISIBLE);
		dao = new MessageDao(this,activity);
		title.setText(getString(R.string.menu_message_str));
		adapter = new MenuMessageAdapter(activity,dao,this);
		lv_message.setVisibility(View.GONE);
		iv_hasnomessage.setVisibility(View.VISIBLE);
		lv_message.setAdapter(adapter) ;
		showProgress(true);
//		dao.QueryMessage();// 获取消息
		getMessage();
		
	}
	private void getMessage(){
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				dao.QueryMessage();// 获取消息
			}
		}, 500);
	}
}
