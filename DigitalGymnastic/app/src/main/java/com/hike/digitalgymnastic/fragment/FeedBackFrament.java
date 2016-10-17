package com.hike.digitalgymnastic.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.MainMenuActivity;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/*
 * 意见反馈不能为空
 */

public class FeedBackFrament extends BaseFragment {

	private View v;
	@ViewInject(R.id.btn_left)
	private ImageView btn_left;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;

//	@ViewInject(R.id.btn_submit)
//	private Button btn_submit;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.btn_right)
	private TextView btn_right;
	@ViewInject(R.id.tv_message_length)
	private TextView tv_message_length;
	@ViewInject(R.id.ed_message)
	private EditText ed_message;
	@ViewInject(R.id.ed_phone_email)
	private EditText ed_phone_email;
	@ViewInject(R.id.feedback_ly)
	private RelativeLayout feedback_ly;
	private BaseDao dao;

	@OnClick(value = { R.id.btn_left, R.id.btn_right, R.id.ll_btn_left })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			((MainMenuActivity) activity).fragmentBack();
			break;
		case R.id.ll_btn_left:
			((MainMenuActivity) activity).fragmentBack();
			break;
		case R.id.btn_right:
			if (TextUtils.isEmpty(ed_message.getText().toString())) {
				Utils.showMessage(activity, "意见不能为空，请重新输入！");
			}
			// else if
			// (TextUtils.isEmpty(ed_phone_email.getText().toString().trim())) {
			// Utils.showMessage(activity, "意见不能为空，请重新输入！");
			// }
			else {
				if (check(ed_phone_email.getText().toString())) {
					showProgress(true);
					dao.CommitFeedback(ed_message.getText().toString(),
							ed_phone_email.getText().toString());// 获取消息
				}
			}
			break;
			default:
					break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		this.inflater = inflater;
		this.v = inflater.inflate(R.layout.activity_menu_set_feedback,
				container, false);
		ViewUtils.inject(this, v);
		return v;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden) {
			init();
		}
	}

	private void init() {

		title.setText(getString(R.string.menu_feedback));
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		btn_left.setVisibility(View.VISIBLE);
		btn_right.setText(getString(R.string.submit));
		btn_right.setTextColor(this.getResources().getColor(R.color.light_green));
		btn_right.setVisibility(View.VISIBLE);
		dao = new BaseDao(this, activity);
		feedback_ly.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				Utils.closeInputMetherUI(activity);
				return true;
			}
		});
		ed_message.setText("");
		ed_message.addTextChangedListener(mEditText);
	}

	TextWatcher mEditText = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			int l = ed_message.getText().length();
			if (l < 201) {
				tv_message_length.setText(l + "/200");
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	};

	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);
		showProgress(false);
		dao.getFeedback();
		Utils.showMessage(activity, "提交成功,谢谢!");
		((MainMenuActivity) activity).fragmentBack();
	}

	String emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";

	/**
	 * 手机号码 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
	 * 联通：130,131,132,152,155,156,185,186 电信：133,1349,153,180,189
	 */
	String MOBILE_Regex = "^1(3[0-9]|5[0-35-9]|8[025-9]|78|47)\\d{8}$";
	/**
	 * 10 * 中国移动：China Mobile 11 *
	 * 134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188 12
	 */
	String CM_Regex = "^1(34[0-8]|(3[5-9]|5[017-9]|8[2378]|45|76)\\d)\\d{7}$";
	/**
	 * 15 * 中国联通：China Unicom 16 * 130,131,132,152,155,156,185,186 17
	 */
	String CU_Regex = "^1(3[0-2]|5[256]|8[56]|81|77|70)\\d{8}$";
	/**
	 * 20 * 中国电信：China Telecom 21 * 133,1349,153,180,189 22
	 */
	String CT_Regex = "^1((33|53|8[09])[0-9]|349)\\d{7}$";

	/**
	 * 25 * 大陆地区固话及小灵通 26 * 区号：010,020,021,022,023,024,025,027,028,029 27 *
	 * 号码：七位或八位 28
	 */
	// NSString * PHS = @"^0(10|2[0-5789]|\\d{3})\\d{7,8}$";
	private boolean check(String str) {
		if (str.matches(emailRegex) || str.matches(MOBILE_Regex)
				|| str.matches(CM_Regex) || str.matches(CU_Regex)
				|| str.matches(CT_Regex))
			return true;
		else {
			Utils.showMessage(activity, "请输入正确的手机号或者邮箱");
			return false;
		}

	}
}
