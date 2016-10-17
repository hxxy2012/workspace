package com.hike.digitalgymnastic.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.BaseActivity;
import com.hiko.enterprisedigital.R;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.view.PickerView;
import com.hike.digitalgymnastic.view.PickerView.onSelectListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;

@ContentView(R.layout.activity_menu_my_clock)
public class MyClockFrament extends BaseActivity {
	View v;
	@ViewInject(R.id.btn_left)
	ImageView btn_left;
	@ViewInject(R.id.btn_right)
	ImageView btn_right;
	@ViewInject(R.id.title)
	TextView title;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	
	@ViewInject(R.id.HH_pv)
	PickerView HH_pv;
	@ViewInject(R.id.MM_pv)
	PickerView MM_pv;
	@ViewInject(R.id.btn_save)
	Button btn_save;

	@ViewInject(R.id.rb_one)
	CheckBox rb_one;
	@ViewInject(R.id.rb_two)
	CheckBox rb_two;
	@ViewInject(R.id.rb_three)
	CheckBox rb_three;
	@ViewInject(R.id.rb_four)
	CheckBox rb_four;
	@ViewInject(R.id.rb_five)
	CheckBox rb_five;
	@ViewInject(R.id.rb_six)
	CheckBox rb_six;
	@ViewInject(R.id.rb_seven)
	CheckBox rb_seven;

	@OnClick(value = { R.id.btn_left, R.id.btn_save,R.id.ll_btn_left })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			// ((MainMenuActivity) activity).fragmentBack();
			finish();
			break;
		case R.id.ll_btn_left:
			// ((MainMenuActivity) activity).fragmentBack();
			finish();
			break;
			
		case R.id.btn_save:
			save();
			break;
		default:
			break;
		}
	}

	@OnCompoundButtonCheckedChange(value = { R.id.rb_one, R.id.rb_two,
			R.id.rb_three, R.id.rb_four, R.id.rb_five, R.id.rb_six,
			R.id.rb_seven })
	public void onCompoundButtonCheckedChange(CompoundButton button,
			boolean isChecked) {
		checkedMap.put(button.getId(), isChecked);
	}

	Map<Integer, Boolean> checkedMap = new HashMap<Integer, Boolean>();
	Map<Integer, String> checkedTitleMap = new HashMap<Integer, String>();
	Calendar calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		initData();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	private void initData() {

//		ll_btn_right.setVisibility(View.VISIBLE);
//		btn_right.setImageResource(R.mipmap.add_3x);
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		calendar = Calendar.getInstance();
		checkedMap.put(R.id.rb_one, false);
		checkedMap.put(R.id.rb_two, false);
		checkedMap.put(R.id.rb_three, false);
		checkedMap.put(R.id.rb_four, false);
		checkedMap.put(R.id.rb_five, false);
		checkedMap.put(R.id.rb_six, false);
		checkedMap.put(R.id.rb_seven, false);
		
		checkedTitleMap.put(R.id.rb_one, "周一");
		checkedTitleMap.put(R.id.rb_two,  "周二");
		checkedTitleMap.put(R.id.rb_three,  "周三");
		checkedTitleMap.put(R.id.rb_four,  "周四");
		checkedTitleMap.put(R.id.rb_five,  "周五");
		checkedTitleMap.put(R.id.rb_six,  "周六");
		checkedTitleMap.put(R.id.rb_seven,  "周日");
		

		List<String> HH = new ArrayList<String>();// 时
		List<String> MM = new ArrayList<String>();// 分
		for (int i = 0; i < 25; i++) {
			HH.add(i < 10 ? "0" + i : "" + i);
		}
		for (int i = 0; i < 60; i++) {
			MM.add(i < 10 ? "0" + i : "" + i);
		}
		HH_pv.setData(HH);
		MM_pv.setData(MM);
		title.setText(getString(R.string.menu_clock_str));
		HH_pv.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				Log.v("MyLog", "---hh-----" + text);
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(text));
			}
		});
		MM_pv.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				Log.v("MyLog", "--mm----" + text);
				calendar.set(Calendar.MINUTE, Integer.parseInt(text));
			}
		});
	}

	private void save(){
//		1.说明：若用户选择周一、周二、周三、周四、周五，则上一页面显示为工作日；
//		2.若用户选择周六、周日，则上一页面显示为周末；
//		3.若用户选择周一、周二、周三、周四、周五，周六、周日则上一页面显示为每天；
//		4..若用户未选择日期，则上一页面闹钟按钮为关闭，且文案显示为永不。
//		5.其他日期，上一页面则显示对应的日期。
		List<Integer> checkedList=new ArrayList<Integer>();
		boolean firstState=checkedMap.get(rb_one)&&checkedMap.get(rb_two)&&checkedMap.get(rb_three)
				&&checkedMap.get(rb_four)&&checkedMap.get(rb_five)
				&&!checkedMap.get(rb_six)&&!checkedMap.get(rb_seven);
		boolean secondState=!checkedMap.get(rb_one)&&!checkedMap.get(rb_two)&&!checkedMap.get(rb_three)
				&&!checkedMap.get(rb_four)&&!checkedMap.get(rb_five)
				&&checkedMap.get(rb_six)&&checkedMap.get(rb_seven);
		boolean thirdState=checkedMap.get(rb_one)&&checkedMap.get(rb_two)&&checkedMap.get(rb_three)
				&&checkedMap.get(rb_four)&&checkedMap.get(rb_five)
				&&checkedMap.get(rb_six)&&checkedMap.get(rb_seven);
		String title="";
		if(firstState) {
			title="工作日";
			checkedList.add(R.id.rb_one);
			checkedList.add(R.id.rb_two);
			checkedList.add(R.id.rb_three);
			checkedList.add(R.id.rb_four);
			checkedList.add(R.id.rb_five);
		}else if(secondState){
			title="周末";
			checkedList.add(R.id.rb_six);
			checkedList.add(R.id.rb_seven);
		}else if(thirdState){
			title="每天";
			checkedList.add(R.id.rb_one);
			checkedList.add(R.id.rb_two);
			checkedList.add(R.id.rb_three);
			checkedList.add(R.id.rb_four);
			checkedList.add(R.id.rb_five);
			checkedList.add(R.id.rb_six);
			checkedList.add(R.id.rb_seven);
		}else{
			StringBuilder builder=new StringBuilder();
			Set<Integer>  keySet=checkedMap.keySet();
			for(Integer key:keySet){
				if(checkedMap.get(key)){
					checkedList.add(key);
					builder.append(checkedTitleMap.get(key));
					builder.append("、");
				}
			}
			if(builder.toString().length()>0)
				builder.deleteCharAt(builder.toString().length()-1);
			title=builder.toString();
		}
		
		
	}
	
	private void setAlarm(){
		AlarmManager alarms=(AlarmManager)getSystemService(Context.ALARM_SERVICE); 
		PendingIntent rtcIntent=PendingIntent.getBroadcast(this,0,new Intent(Constants.MY_RTC_ALARM),1);
		//Wakeupandfireintentin5hours.(注释可能有错) 
		Date t=new Date(); 
		t.setTime(java.lang.System.currentTimeMillis()+60*1000*5); 
		alarms.set(AlarmManager.RTC_WAKEUP,t.getTime(),rtcIntent); 
		alarms.cancel(rtcIntent);
	}
}
