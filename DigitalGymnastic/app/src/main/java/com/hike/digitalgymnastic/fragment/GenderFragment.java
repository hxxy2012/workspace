package com.hike.digitalgymnastic.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hike.digitalgymnastic.MainFragment;
import com.hike.digitalgymnastic.MainMenuActivity;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/*
 * 个人信息修改--性别
 */
public class GenderFragment extends BaseFragment {
	@ViewInject(R.id.btn_left)
	private ImageView btn_left;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.btn_right_text)
	private Button btn_right_text;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	
	
	@ViewInject(R.id.top_ly)
	private LinearLayout top_ly;
	@ViewInject(R.id.btn_next_one)
	private Button btn_next_one;
	@ViewInject(R.id.rb_male)
	private RadioButton rb_male;
	@ViewInject(R.id.rb_sex)
	private RadioButton rb_sex;

	private View v;
	private int gender;
	private int screenWidth;

	//
	@OnClick(value = { R.id.btn_left,R.id.ll_btn_left, R.id.btn_next_one, R.id.btn_right_text,
			R.id.rb_male, R.id.rb_sex })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			submit();
			// ((MainMenuActivity) activity).fragmentBack();
			break;
		case R.id.ll_btn_left:
			submit();
			// ((MainMenuActivity) activity).fragmentBack();
			break;
			
		case R.id.btn_next_one:
			if(setData()){
				((MainFragment) activity).setGender(gender);// 数据提交给主activity
			}
			break;
		case R.id.btn_right_text:
			// ((MainMenuActivity) activity).setGender(gender + "");//
			// 数据提交给主activity
			break;
		case R.id.rb_male:
			gender = 1;
			break;
		case R.id.rb_sex:
			gender = 2;
			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
//		Utils.toolBarManager((MainMenuActivity) activity,R.color.app_bg_login);
		this.v = inflater.inflate(R.layout.personalinfo_one, container, false);
		ViewUtils.inject(this, v);
		return v;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();// 初始化

	}

	private void init() {
		title.setText(getString(R.string.per_info));
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		btn_left.setVisibility(View.VISIBLE);
		btn_right_text.setVisibility(View.INVISIBLE);
		screenWidth = activity.getWindowManager().getDefaultDisplay()
				.getWidth();
		// Bitmap male_select = BitmapFactory.decodeResource(getResources(),
		// R.drawable.male_select);
		// int drawableWidth = male_select.getWidth();
		// int drawableHeight = male_select.getHeight();
		// int height = (int) (screenWidth / 2 * ((float) drawableHeight /
		// (float) drawableWidth));
		// male_select.recycle();
		// RadioGroup.LayoutParams defaultParams = null;
		// defaultParams = (RadioGroup.LayoutParams) rb_male.getLayoutParams();
		// defaultParams.width = screenWidth / 2;
		// defaultParams.height = height;
		// defaultParams = (RadioGroup.LayoutParams) rb_sex.getLayoutParams();
		// defaultParams.width = screenWidth / 2;
		// defaultParams.height = height;

		// Drawable[] drawables =rb_male. getCompoundDrawables();
		// Rect rect=new Rect();
		// Rect rect=drawables[1].getBounds();
		// drawables[1].setBounds(rect);
		// rb_male.setCompoundDrawables(drawables[0],drawables[1],drawables[2].drawables[3]);
		// rb_male.setCompoundDrawablesWithIntrinsicBounds(rect.left, rect.top,
		// rect.right, rect.bottom);
		changeData();
	}

	private void changeData() {
		if (activity instanceof MainMenuActivity) {
			top_ly.setVisibility(View.VISIBLE);
			// btn_right_text.setVisibility(View.VISIBLE);
			// btn_right_text.setText("保存");
			if (((MainMenuActivity) activity).getGender() != null) {
				gender = Integer.parseInt(((MainMenuActivity) activity)
						.getGender());
			}
		}
		if (activity instanceof MainFragment) {
			btn_next_one.setVisibility(View.VISIBLE);
			if (((MainFragment) activity).getCustomer().getGender() != null) {
				gender = Integer.parseInt(((MainFragment) activity)
						.getCustomer().getGender());
			}
		}
		if (gender == 1) {
			rb_male.setChecked(true);
		} else if (gender == 2) {
			rb_sex.setChecked(true);
		}
	}

	protected boolean setData() {
		if (!rb_male.isChecked() && !rb_sex.isChecked()) {
			Utils.showMessage(activity, getString(R.string.selcetsex));
			return false;
		} else {
			if (rb_male.isChecked()) {
				Log.v("MyLog", "男");
				gender = 1;
			} else {
				Log.v("MyLog", "女");
				gender = 2;
			}
			return true;
		}
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
//		Utils.toolBarManager((MainMenuActivity) activity, R.color.app_bg);
		if (activity instanceof MainFragment) {
			MainFragment mf = (MainFragment) activity;
			if (mf.fromWhich == MainFragment.fromMenu)
				changeData();
		} else if (activity instanceof MainMenuActivity) {
			changeData();
		}

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if (activity instanceof MainMenuActivity) {
			MainMenuActivity mma = (MainMenuActivity) activity;
			mma.setGenderDataCommucation(new GenderDataCommucation() {

				@Override
				public void onKeyDown() {
					// TODO Auto-generated method stub
					submit();
				}
			});
		}
		;
	}

	void submit() {
		if (activity instanceof MainMenuActivity) {
			((MainMenuActivity) activity).setGender(gender + "");// 数据提交给主activity
		}
	}

	public interface GenderDataCommucation {
		public void onKeyDown();
	}
}
