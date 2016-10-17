package com.hike.digitalgymnastic.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hiko.enterprisedigital.R;
import com.hike.digitalgymnastic.entitiy.MyClock;
import com.hike.digitalgymnastic.view.PickerView;
import com.hike.digitalgymnastic.view.UISwitchButton;
import com.hike.digitalgymnastic.view.PickerView.onSelectListener;
import com.roamer.slidelistview.SlideBaseAdapter;

public class ClockAdapter extends SlideBaseAdapter{
	String[] weeks = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
	List<MyClock> list;
	Context context;
	public ClockAdapter(Context context) {
		super(context);
		this.context=context;
		calendar = Calendar.getInstance();
	}
	public ClockAdapter(Context context,List<MyClock> list) {
		super(context);
		this.context=context;
		this.list=list;
		calendar = Calendar.getInstance();
	}
	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressLint("InflateParams")
	public View getView(final int position, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
		final MyClock clock = (MyClock) getItem(position);
		if (convertView == null) {
			convertView = createConvertView(position);
			holder = new ViewHolder();
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			holder.tv_date = (TextView) convertView
					.findViewById(R.id.tv_date);
			holder.ck_open_or_close = (UISwitchButton) convertView
					.findViewById(R.id.ck_open_or_close);
			holder.cb_expand = (CheckBox) convertView
					.findViewById(R.id.cb_expand);
			holder.ll_clock_time = (LinearLayout) convertView
					.findViewById(R.id.ll_clock_time);
			holder.pv_hh = (PickerView) convertView
					.findViewById(R.id.pv_hh);
			holder.pv_mm = (PickerView) convertView
					.findViewById(R.id.pv_mm);
			holder.rb_one = (CheckBox) convertView
					.findViewById(R.id.rb_one);
			holder.rb_two = (CheckBox) convertView
					.findViewById(R.id.rb_two);
			holder.rb_three = (CheckBox) convertView
					.findViewById(R.id.rb_three);
			holder.rb_four = (CheckBox) convertView
					.findViewById(R.id.rb_four);
			holder.rb_five = (CheckBox) convertView
					.findViewById(R.id.rb_five);
			holder.rb_six = (CheckBox) convertView
					.findViewById(R.id.rb_six);
			holder.rb_seven = (CheckBox) convertView
					.findViewById(R.id.rb_seven);

			holder.btn_delete = (Button) convertView
					.findViewById(R.id.btn_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (holder.btn_delete != null) {
			holder.btn_delete
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
						}
					});
		}
		List<String> HH = new ArrayList<String>();// 时
		List<String> MM = new ArrayList<String>();// 分
		for (int i = 0; i < 25; i++) {
			HH.add(i < 10 ? "0" + i : "" + i);
		}
		for (int i = 0; i < 60; i++) {
			MM.add(i < 10 ? "0" + i : "" + i);
		}
		holder.pv_hh.setData(HH);
		holder.pv_mm.setData(MM);
		
		
		setOnCheckedChangedListener(holder, clock);
		updateView(holder, clock);
		return convertView;
	}

	@Override
	public int getFrontViewId(int position) {
		return R.layout.menu_my_clock_list_item;
	}

	@Override
	public int getRightBackViewId(int position) {
		return R.layout.menu_message_lv_item_righ_view;
	}

	@Override
	public int getItemViewType(int position) {
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getLeftBackViewId(int position) {
		return 0;
	}

	class ViewHolder {
		public TextView tv_time;
		public TextView tv_date;
		public UISwitchButton ck_open_or_close;
		public CheckBox cb_expand;
		public LinearLayout ll_clock_time;
		public PickerView pv_hh;
		public PickerView pv_mm;
		public Button btn_delete;

		CheckBox rb_one;
		CheckBox rb_two;
		CheckBox rb_three;
		CheckBox rb_four;
		CheckBox rb_five;
		CheckBox rb_six;
		CheckBox rb_seven;
	}

	Calendar calendar;

	private void setOnCheckedChangedListener(final ViewHolder holder,
			final MyClock clock) {
		holder.pv_hh.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				Log.v("MyLog", "---hh-----" + text);
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(text));
				updateView(holder, clock);
			}
		});
		holder.pv_mm.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				Log.v("MyLog", "--mm----" + text);
				calendar.set(Calendar.MINUTE, Integer.parseInt(text));
				updateView(holder, clock);
			}
		});
		
		MyListener listener=new MyListener(holder, clock);
		
		holder.ck_open_or_close.setOnCheckedChangeListener(listener);
		holder.cb_expand.setOnCheckedChangeListener(listener);
		holder.rb_one.setOnCheckedChangeListener(listener);
		holder.rb_two.setOnCheckedChangeListener(listener);
		holder.rb_three.setOnCheckedChangeListener(listener);
		holder.rb_four.setOnCheckedChangeListener(listener);
		holder.rb_five.setOnCheckedChangeListener(listener);
		holder.rb_six.setOnCheckedChangeListener(listener);
		holder.rb_seven.setOnCheckedChangeListener(listener);
	}

	/**
	 * @author changqi
	 * @category 更新视图
	 */
	private void updateView(ViewHolder holder, MyClock clock) {
		// 1.说明：若用户选择周一、周二、周三、周四、周五，则上一页面显示为工作日；
		// 2.若用户选择周六、周日，则上一页面显示为周末；
		// 3.若用户选择周一、周二、周三、周四、周五，周六、周日则上一页面显示为每天；
		// 4..若用户未选择日期，则上一页面闹钟按钮为关闭，且文案显示为永不。
		// 5.其他日期，上一页面则显示对应的日期。

		boolean firstState = holder.rb_one.isChecked()
				&& holder.rb_two.isChecked() && holder.rb_three.isChecked()
				&& holder.rb_four.isChecked() && holder.rb_five.isChecked()
				&& !holder.rb_six.isChecked()
				&& !holder.rb_seven.isChecked();
		boolean secondState = !holder.rb_one.isChecked()
				&& !holder.rb_two.isChecked()
				&& !holder.rb_three.isChecked()
				&& !holder.rb_four.isChecked()
				&& !holder.rb_five.isChecked() && holder.rb_six.isChecked()
				&& holder.rb_seven.isChecked();
		boolean thirdState = holder.rb_one.isChecked()
				&& holder.rb_two.isChecked() && holder.rb_three.isChecked()
				&& holder.rb_four.isChecked() && holder.rb_five.isChecked()
				&& holder.rb_six.isChecked() && holder.rb_seven.isChecked();
		String title = "";// 显示使用的title
		String ids = "";
		if (firstState) {
			title = "工作日";
		} else if (secondState) {
			title = "周末";
		} else if (thirdState) {
			title = "每天";
		} else {
			StringBuilder builder = new StringBuilder();
			int i = 0;
			for (Boolean ischecked : clock.getIdList()) {
				if (ischecked) {
					builder.append(weeks[i]);
					builder.append("、");
				}
				i++;
			}
			if (builder.toString().length() > 0)
				builder.deleteCharAt(builder.toString().length() - 1);
			title = builder.toString();
		}

		holder.tv_date.setText(title);// 更新星期文本
		String timestr = holder.pv_hh.getSelected() + ":"
				+ holder.pv_mm.getSelected();
		holder.tv_time.setText(timestr);// 更新时间文本
		// 更新对象

		clock.setHour(Integer.parseInt(holder.pv_hh.getSelected()));
		clock.setMin(Integer.parseInt(holder.pv_mm.getSelected()));
	}

	
	
	class MyListener  implements OnCheckedChangeListener{
		MyClock clock;
		ViewHolder holder;
		public MyListener(ViewHolder holder,MyClock clock){
			this.clock=clock;
			this.holder=holder;
		}
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			if (buttonView.getId() == R.id.cb_expand) {// 改变扩展开关
				clock.setExpanded(isChecked);
				if (isChecked) {
					holder.ll_clock_time.setVisibility(View.VISIBLE);
				} else {
					holder.ll_clock_time.setVisibility(View.GONE);
				}
//				if (listener != null) {
//					listener.onTimePickerExpandedStateChanged(isChecked, clock);
//				}
			} else if (buttonView.getId() == R.id.ck_open_or_close) {// 改变打开关闭开关
//				if (listener != null) {
//					listener.onTunerStateChanged(isChecked, clock);
//				}
			} else {
				updateView(holder,clock);
			}
		}
		
	}
}
