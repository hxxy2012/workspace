package com.hike.digitalgymnastic.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.MainMenuActivity;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/*
 * 意见反馈不能为空
 */

public class AboutUsFrament extends BaseFragment {

	private View v;
//	@ViewInject(R.id.iv_left)
//	private ImageView iv_left;
//	@ViewInject(R.id.tv_setting_title)
//	private TextView tv_setting_title;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.btn_left)
	private ImageView btn_left;
	@ViewInject(R.id.btn_right)
	private ImageView btn_right;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	@ViewInject(R.id.tv_version_code)
	private TextView tv_version_code;

	@OnClick(value = { R.id.btn_left })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			// 标记是否需要更新页面
			((MainMenuActivity) activity).fragmentBack();

			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		this.v = inflater.inflate(R.layout.menu_set_about_us, container, false);
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
//		Utils.toolBarManager((MainMenuActivity) activity, R.color.app_bg_login);
		super.onHiddenChanged(hidden);
		if (!hidden) {
			init();
		}
	}

	private void init() {

		tv_version_code.setText(getVersion());
		title.setText(getString(R.string.about_us));
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		btn_left.setVisibility(View.VISIBLE);
		btn_right.setVisibility(View.INVISIBLE);
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public String getVersion() {
		try {
			PackageManager manager = ((MainMenuActivity) activity)
					.getPackageManager();
			PackageInfo info = manager.getPackageInfo(
					((MainMenuActivity) activity).getPackageName(), 0);
			String version = info.versionName;
			return this.getString(R.string.version_name) + version;
		} catch (Exception e) {
			e.printStackTrace();
			return this.getString(R.string.can_not_find_version_name);
		}
	}

	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);
		showProgress(false);
		// dao.getFeedback();
		// Utils.showMessage(activity, "提交成功,谢谢!");
		// ((MainMenuActivity) activity).fragmentBack();
	}

}
