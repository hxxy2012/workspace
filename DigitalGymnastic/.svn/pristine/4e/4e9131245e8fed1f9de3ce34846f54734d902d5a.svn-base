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
import com.hike.digitalgymnastic.utils.PreferencesUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.Ruler;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/*
 * 个人信息修改--性别
 */
public class PersonalInfoFragment extends BaseFragment {
	@ViewInject(R.id.btn_left)
	private ImageView btn_left;
	@ViewInject(R.id.btn_right_text)
	private Button btn_right_text;
	@ViewInject(R.id.ll_btn_right2)
	private LinearLayout ll_btn_right2;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;


	@ViewInject(R.id.top_ly)
	private LinearLayout top_ly;

	@ViewInject(R.id.rb_male)
	private RadioButton rb_male;
	@ViewInject(R.id.rb_sex)
	private RadioButton rb_sex;

	@ViewInject(R.id.ruler_year)
	private Ruler ruler_year;
	@ViewInject(R.id.ruler_height)
	private Ruler ruler_height;
	@ViewInject(R.id.ruler_weight)
	private Ruler ruler_weight;

	@ViewInject(R.id.tv_year)
	private TextView tv_year;
	@ViewInject(R.id.tv_height)
	private TextView tv_height;
	@ViewInject(R.id.tv_weight)
	private TextView tv_weight;

	private View v;
	private int gender = 2;
	private int screenWidth;
	private int year_value;
	private boolean isSetYear = false;
	private int height_value;
	private boolean isSetHeight = false;
	private int weight_value;
	private boolean isSetWeight = false;

	//
	@OnClick(value = { R.id.btn_left,R.id.ll_btn_left, R.id.btn_right_text,R.id.ll_btn_right,
			R.id.rb_male, R.id.rb_sex,R.id.ll_btn_right2 })
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
		case R.id.ll_btn_right2:
		case R.id.btn_right_text:
			// ((MainMenuActivity) activity).setGender(gender + "");//
			// 数据提交给主activity
			if(setData()){
				((MainFragment) activity).setPerInfo(gender,year_value,height_value,weight_value);// 数据提交给主activity
				submit();
//				((MainFragment) activity).setYear(year_value);// 数据提交给主activity
//				((MainFragment) activity).setHeight(height_value);// 数据提交给主activity
//				((MainFragment) activity).setWeight(weight_value);// 数据提交给主activity
			}
			break;
		case R.id.rb_male:
			gender = 1;
			PreferencesUtils.putInt((MainFragment) activity,"gender_personal",gender);
			((MainFragment) activity).setPerInfo(gender, year_value, height_value, weight_value);// 数据提交给主activity
			break;
		case R.id.rb_sex:
			gender = 2;
			PreferencesUtils.putInt((MainFragment) activity,"gender_personal",gender);
			((MainFragment) activity).setPerInfo(gender,year_value,height_value,weight_value);// 数据提交给主activity
			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
//		Utils.toolBarManager((MainFragment) activity, R.color.app_bg_login);
		this.v = inflater.inflate(R.layout.personal_info_guide, container, false);
		ViewUtils.inject(this, v);
		return v;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();// 初始化

	}

	private void init() {

		btn_right_text.setVisibility(View.VISIBLE);
		btn_right_text.setTextColor(getResources().getColor(R.color.white));
		btn_right_text.setText(getString(R.string.next));
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
		gender = 2;
		rb_sex.setChecked(true);
		ruler_year.initViewParam(1930,1990, 2030, 10);
		ruler_height.initViewParam(50, 170, 250, 10);
		ruler_weight.initViewParam(0, 50, 150, 10);
		tv_year.setText(ruler_year.getCurrentValue() + getString(R.string.year_def));
		year_value = (int)ruler_year.getCurrentValue();
		tv_height.setText(ruler_height.getCurrentValue() + getString(R.string.cm));
		height_value = (int)ruler_height.getCurrentValue();
		tv_weight.setText(ruler_weight.getCurrentValue() + getString(R.string.kg));
		weight_value = (int)ruler_weight.getCurrentValue();
		((MainFragment) activity).setPerInfo(gender, year_value, height_value, weight_value);// 数据提交给主activity
		setListner();

//		changeData();
	}
	private void setListner(){
		ruler_year.setValueChangeListener(new Ruler.OnValueChangeListener() {
			@Override
			public void onValueChange(float value) {
				tv_year.setText((int)value + getString(R.string.year_def));
				year_value = (int)value;
				isSetYear = true;
				((MainFragment) activity).setPerInfo(gender,year_value,height_value,weight_value);// 数据提交给主activity
			}
		});

		ruler_height.setValueChangeListener(new Ruler.OnValueChangeListener() {
			@Override
			public void onValueChange(float value) {
				tv_height.setText((int)value+getString(R.string.cm));
				height_value = (int)value;
				isSetHeight = true;
				((MainFragment) activity).setPerInfo(gender,year_value,height_value,weight_value);// 数据提交给主activity
			}
		});

		ruler_weight.setValueChangeListener(new Ruler.OnValueChangeListener() {
			@Override
			public void onValueChange(float value) {
				tv_weight.setText((int)value+getString(R.string.kg));
				weight_value = (int)value;
				isSetWeight = true;
				((MainFragment) activity).setPerInfo(gender,year_value,height_value,weight_value);// 数据提交给主activity
			}
		});

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
//			btn_next_one.setVisibility(View.VISIBLE);
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
//		Utils.toolBarManager((MainFragment) activity, R.color.app_bg_login);
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
			mma.setPersonalInfoCommucation(new PersonalInfoCommucation() {

				@Override
				public void onKeyDown() {
					// TODO Auto-generated method stub
					submit();
				}
			});
		};

	}

	void submit() {

		if (activity instanceof MainMenuActivity) {
			((MainMenuActivity) activity).setGender(gender + "");// 数据提交给主activity
			((MainMenuActivity) activity).setYear(year_value+"");// 数据提交给主activity
			((MainMenuActivity) activity).setHeight(height_value+"");// 数据提交给主activity
			((MainMenuActivity) activity).setWeight(weight_value+"");// 数据提交给主activity
		}
	}

	public interface PersonalInfoCommucation {
		public void onKeyDown();
	}
}
