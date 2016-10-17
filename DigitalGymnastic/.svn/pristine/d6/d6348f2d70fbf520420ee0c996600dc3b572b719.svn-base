package com.hike.digitalgymnastic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.HttpUtil;
import com.hike.digitalgymnastic.utils.Utils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.hiko.enterprisedigital.R;
/*
 * changqi
 */
@ContentView(R.layout.activity_validcode_image)
public class ValidCodeImageActivity extends BaseActivity {
	
	
	@ViewInject(R.id.ll_root)
	LinearLayout ll_root;
	
	
	@ViewInject(R.id.et_input_validcode)
	EditText et_input_validcode;

	@ViewInject(R.id.iv_validcode_image)
	ImageView iv_validcode_image;

//	@ViewInject(R.id.btn_cancel)
//	Button btn_cancel;
	@ViewInject(R.id.btn_confirm)
	Button btn_confirm;
	@ViewInject(R.id.title)
	TextView title;
	@ViewInject(R.id.btn_left)
	ImageView btn_left;
	@ViewInject(R.id.btn_right)
	ImageView btn_right;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	@OnClick(value = { R.id.iv_validcode_image,
			R.id.btn_confirm ,R.id.ll_root,R.id.ll_btn_left})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_root:
//			finish();
			break;
		case R.id.iv_validcode_image:
			getValidCodeImage();

			break;
		case R.id.ll_btn_left:
			finish();
			break;
		case R.id.btn_confirm:
			validCodeCheck();
			break;
		default:
			break;
		}
	}

	boolean isSending = false;
	BaseDao dao;
	boolean isFormResetPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Utils.setTranslucentStatus(true,this);
		}
		ViewUtils.inject(this);

		init();

	}

	;

	String phone;
	String type;

	private void init() {
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		title.setText(getString(R.string.title_pwd_find));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		btn_left.setVisibility(View.VISIBLE);
		btn_right.setVisibility(View.INVISIBLE);
		phone = getIntent().getStringExtra("phone");

		btn_confirm.setBackgroundResource(R.drawable.btn_selector);
		isFormResetPwd = getIntent().getBooleanExtra("isFormResetPwd", false);
		if (isFormResetPwd)
			type = Constants.isForgetPwd;
		else
			type = Constants.isRegister;

		dao = new BaseDao(this, this);
		getValidCodeImage();

	}

	private void validCodeCheck() {

		dao.GetVerifyCode(phone, type, et_input_validcode.getText()
				.toString().trim());
	}

	private void getValidCodeImage() {
		if (!isSending) {
			isSending = true;
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					String url = HttpConnectUtils.ip
							+ HttpConnectUtils.api_getVerifyImage;
					byte[] data = null;
					try {
						data = HttpUtil.getImage(ValidCodeImageActivity.this,
								url, phone);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg = new Message();
					if (data != null) {
						msg.what = 0;
						msg.obj = data;
					} else {
						msg.what = 1;
					}
					handler.sendMessage(msg);
					isSending = false;
				}
			}).start();
		} else
			Utils.showMessage(getApplicationContext(), "正在获取验证码！");
	}

	private void setImageView(byte[] data) {
		Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
		if (bmp != null) {
			iv_validcode_image.setImageBitmap(bmp);
		} else {
			Utils.showMessage(ValidCodeImageActivity.this, "获取验证码失败");
		}
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 0) {
				byte[] data = (byte[]) msg.obj;
				setImageView(data);
			} else {
				Utils.showMessage(ValidCodeImageActivity.this, "获取验证码失败");
			}
		}

	};

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		;
	}

	@Override
	public void onRequestSuccess(int requestCode) {
		// TODO Auto-generated method stub
		super.onRequestSuccess(requestCode);
		if (requestCode == 66) {
			Intent intent = new Intent(ValidCodeImageActivity.this,
					ValidCodeActivity.class);
			intent.putExtra(Constants.mobile, phone);
			intent.putExtra(Constants.isFormResetPwd, isFormResetPwd);
			intent.putExtra(Constants.vertifyCode, dao.getVerifyCode().getVerifyCode());
			startActivity(intent);
			finish();
		}

	}

	@Override
	public void onRequestFaild(String errorNo, String errorMessage) {
		// TODO Auto-generated method stub
		super.onRequestFaild(errorNo, errorMessage);
//		Utils.showMessage(ValidCodeImageActivity.this, "验证码错误");
	}

}
