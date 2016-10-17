package com.hike.digitalgymnastic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.OnceSportData;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.SportTextView;
import com.hike.digitalgymnastic.view.SportTextView.OnCheckedChangedListener;
import com.hike.digitalgymnastic.view.SportTextView.OnTextClickListener;
import com.hiko.enterprisedigital.R;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeLineAdapter extends BaseAdapter {
	Map<Integer, Boolean> booleanMap1;
	Map<Integer, Boolean> booleanMap2;
	Context context;
	List<OnceSportData> list;
	LayoutInflater inflater;
	int time_color_selected;
	int time_color_normal;
	int text_color_normal;
	private btnTimeLineListener mListener = null;
	int size1;
	int size2;
	DecimalFormat decimalFormat = new DecimalFormat("#.#");

	public List<OnceSportData> getList() {
		return list;
	}

	public void setList(List<OnceSportData> list) {
		this.list = list;
	}

	public TimeLineAdapter(Context context, List<OnceSportData> list) {
		this.context = context;
		this.list = list;
		size1=(int) context.getResources().getDimensionPixelSize(R.dimen.x12);
		size2=(int) context.getResources().getDimensionPixelSize(R.dimen.x12);
//		normalWidth=(int) context.getResources().getDimensionPixelSize(R.dimen.x70);
//		normalHeight=(int) context.getResources().getDimensionPixelSize(R.dimen.x70);
//		checkedWidth=(int) context.getResources().getDimensionPixelSize(R.dimen.x110);
//		checkedHeight=(int) context.getResources().getDimensionPixelSize(R.dimen.x70);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		booleanMap1 = new HashMap<Integer, Boolean>();
		booleanMap2 = new HashMap<Integer, Boolean>();
		for (int i = 0; i < list.size(); i++) {
			booleanMap1.put(i, false);
			booleanMap2.put(i, false);
		}

		time_color_selected = context.getResources().getColor(
				R.color.white);
		time_color_normal = context.getResources().getColor(
				R.color.white);
		text_color_normal = context.getResources().getColor(
				R.color.white);
	}

	@Override
	public int getCount() {

		return list.size();

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return id;
	}

	public void initSelectView(ListView listView, int position) {
		for (int i = 0; i < list.size(); i++) {
			booleanMap1.put(i, false);
			booleanMap2.put(i, false);
		}
		booleanMap1.put(position, true);
		booleanMap2.put(position, true);
		notifyDataSetChanged();
		listView.setSelection(position);

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		// if (position == 0) {
		// convertView = new TextView(context);
		// return convertView;
		// }
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.item_sport_list, null);
			holder.time_line1 = (RelativeLayout) convertView
					.findViewById(R.id.time_line1);
			holder.tv_value1 = (SportTextView) convertView
					.findViewById(R.id.tv_value1);
			holder.tv_time1 = (TextView) convertView
					.findViewById(R.id.tv_time1);
			holder.iv_time_line_top1 = (LinearLayout) convertView
					.findViewById(R.id.iv_time_line_top1);
			holder.iv_time_line_bottom1 = (LinearLayout) convertView
					.findViewById(R.id.iv_time_line_bottom1);

			holder.time_line2 = (RelativeLayout) convertView
					.findViewById(R.id.time_line2);
			holder.tv_value2 = (SportTextView) convertView
					.findViewById(R.id.tv_value2);
			holder.tv_time2 = (TextView) convertView
					.findViewById(R.id.tv_time2);
			holder.iv_time_line_top2 = (LinearLayout) convertView
					.findViewById(R.id.iv_time_line_top2);
			holder.iv_time_line_bottom2 = (LinearLayout) convertView
					.findViewById(R.id.iv_time_line_bottom2);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final OnceSportData data = (OnceSportData) getItem(position);
		if (position == 0) {
			holder.iv_time_line_top1.setVisibility(View.INVISIBLE);
			holder.iv_time_line_top2.setVisibility(View.INVISIBLE);

		} else if (position == list.size() - 1) {
			holder.iv_time_line_bottom1.setVisibility(View.INVISIBLE);
			holder.iv_time_line_bottom2.setVisibility(View.INVISIBLE);
		} else {
			holder.iv_time_line_top1.setVisibility(View.VISIBLE);
			holder.iv_time_line_top2.setVisibility(View.VISIBLE);
			holder.iv_time_line_bottom1.setVisibility(View.VISIBLE);
			holder.iv_time_line_bottom2.setVisibility(View.VISIBLE);
		}

		if (position % 2 == 0) {
			holder.time_line1.setVisibility(View.VISIBLE);
			holder.time_line2.setVisibility(View.GONE);
			if (position == 0) {
				holder.iv_time_line_top1.setVisibility(View.INVISIBLE);
				holder.iv_time_line_top2.setVisibility(View.GONE);
				holder.iv_time_line_bottom1.setVisibility(View.VISIBLE);
				holder.iv_time_line_bottom2.setVisibility(View.GONE);
				if (getCount() == 1)
					holder.iv_time_line_bottom1.setVisibility(View.INVISIBLE);
			} else if (position == list.size() - 1) {
				holder.iv_time_line_top1.setVisibility(View.VISIBLE);
				holder.iv_time_line_top2.setVisibility(View.GONE);
				holder.iv_time_line_bottom1.setVisibility(View.INVISIBLE);
				holder.iv_time_line_bottom2.setVisibility(View.GONE);
				if (getCount() == 1)
					holder.iv_time_line_top1.setVisibility(View.INVISIBLE);
			} else {
				holder.iv_time_line_top1.setVisibility(View.VISIBLE);
				holder.iv_time_line_top2.setVisibility(View.GONE);
				holder.iv_time_line_bottom1.setVisibility(View.VISIBLE);
				holder.iv_time_line_bottom2.setVisibility(View.GONE);
			}
			holder.tv_time1.setText(data.getBeginTime() + "--"
					+ data.getEndTime());
			holder.tv_value1.setText(data.getSportName());

			holder.tv_value1.setChecked(booleanMap1.get(position));
			if (booleanMap1.get(position)) {
				String value = (int)(data.getBurnCalories()+0.5) + " kcal";
				String calValue=String.valueOf((int)(data.getBurnCalories()+0.5));
				String sport_name=data.getSportName()+"\n";
				if(data.getBurnCalories()<1){
					value=decimalFormat.format(data.getBurnCalories())+ " kcal";;
					calValue=decimalFormat.format(data.getBurnCalories());
				}
				SpannableStringBuilder spannable = new SpannableStringBuilder(
						sport_name+value);
				AbsoluteSizeSpan span_1 = new AbsoluteSizeSpan(size1);// 字体大小
				AbsoluteSizeSpan span_2 = new AbsoluteSizeSpan(size2);// 字体大小
				
				spannable.setSpan(span_1, 0,
						sport_name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				spannable.setSpan(span_2, sport_name.length(),
						(sport_name+value).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				holder.tv_value1.setText(spannable);
				holder.tv_value1.setTextColor(Color.WHITE);
				holder.tv_value1.startAnimationExpland();
				holder.tv_time1.setTextColor(time_color_selected);
				holder.tv_value2.reset();
				holder.tv_value2.setBackgroundResource(R.mipmap.bg_timeline_value_normal);

			} else {

				String value = data.getSportName();
				SpannableStringBuilder spannable = new SpannableStringBuilder(
						value);
				AbsoluteSizeSpan span_1 = new AbsoluteSizeSpan(size2);// 字体大小
				spannable.setSpan(span_1, 0, value.length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				holder.tv_value1.setText(spannable);
				// holder.tv_value1.setTextSize(dp2px(8));
				holder.tv_value1.setTextColor(text_color_normal);
//				holder.tv_value1.startAnimationNormal();
				holder.tv_value1.reset();
				holder.tv_value1.setBackgroundResource(R.mipmap.bg_timeline_value_normal);
				holder.tv_time1.setTextColor(time_color_normal);
				holder.tv_value2.reset();
				holder.tv_value2.setBackgroundResource(R.mipmap.bg_timeline_value_normal);
			}
		} else {
			holder.time_line1.setVisibility(View.GONE);
			holder.time_line2.setVisibility(View.VISIBLE);
			if (position == 0) {
				holder.iv_time_line_top1.setVisibility(View.GONE);
				holder.iv_time_line_top2.setVisibility(View.INVISIBLE);
				holder.iv_time_line_bottom1.setVisibility(View.GONE);
				holder.iv_time_line_bottom2.setVisibility(View.VISIBLE);
			} else if (position == list.size() - 1) {
				holder.iv_time_line_top1.setVisibility(View.GONE);
				holder.iv_time_line_top2.setVisibility(View.VISIBLE);
				holder.iv_time_line_bottom1.setVisibility(View.GONE);
				holder.iv_time_line_bottom2.setVisibility(View.INVISIBLE);
			} else {
				holder.iv_time_line_top1.setVisibility(View.GONE);
				holder.iv_time_line_top2.setVisibility(View.VISIBLE);
				holder.iv_time_line_bottom1.setVisibility(View.GONE);
				holder.iv_time_line_bottom2.setVisibility(View.VISIBLE);
			}
			holder.tv_time2.setText(data.getBeginTime() + "--"
					+ data.getEndTime());
			holder.tv_value2.setText(data.getSportName());

			holder.tv_value2.setChecked(booleanMap2.get(position));
			if (booleanMap2.get(position)) {
				String value = (int)(data.getBurnCalories()+0.5) + " kcal";
				String calValue=String.valueOf((int)(data.getBurnCalories()+0.5));
				String sport_name=data.getSportName()+"\n";
				if(data.getBurnCalories()<1){
					value=decimalFormat.format(data.getBurnCalories())+ " kcal";
					calValue=decimalFormat.format(data.getBurnCalories());
				}
				
				SpannableStringBuilder spannable = new SpannableStringBuilder(
						sport_name+value);
				
				AbsoluteSizeSpan span_1 = new AbsoluteSizeSpan(size1);// 字体大小
				AbsoluteSizeSpan span_2 = new AbsoluteSizeSpan(size2);// 字体大小
				
				
				spannable.setSpan(span_1, 0,
						sport_name.length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				spannable.setSpan(span_2, sport_name.length(),
						(sport_name+value).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				holder.tv_value2.setText(spannable);
				holder.tv_value2.setTextColor(Color.WHITE);
				holder.tv_value2.startAnimationExpland();
				holder.tv_time2.setTextColor(time_color_selected);

				holder.tv_value1.reset();
				holder.tv_value1.setBackgroundResource(R.mipmap.bg_timeline_value_normal);
			} else {
				String value = data.getSportName();
				SpannableStringBuilder spannable = new SpannableStringBuilder(
						value);
				AbsoluteSizeSpan span_1 = new AbsoluteSizeSpan(size2);// 字体大小
				spannable.setSpan(span_1, 0, value.length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				holder.tv_value2.setText(spannable);
				holder.tv_value2.setTextColor(text_color_normal);
//				holder.tv_value2.startAnimationNormal();
				holder.tv_value2.reset();
				holder.tv_value2.setBackgroundResource(R.mipmap.bg_timeline_value_normal);
				holder.tv_time2.setTextColor(time_color_normal);
				holder.tv_value1.reset();
				holder.tv_value1.setBackgroundResource(R.mipmap.bg_timeline_value_normal);
			}
		}
		holder.tv_value1.setOnTextClickListener(new OnTextClickListener() {

			@Override
			public void onTextClick() {
				if (mListener != null) {
					mListener.onSomeChange(data, 1);
				}
				// Utils.showMessage(context, data.getSportName());
			}
		});
		holder.tv_value2.setOnTextClickListener(new OnTextClickListener() {

			@Override
			public void onTextClick() {
				// Utils.showMessage(context, data.getSportName());
				if (mListener != null) {
					mListener.onSomeChange(data, 2);
				}
			}
		});
		holder.tv_value1
				.setOnCheckedChangedListener(new OnCheckedChangedListener() {
					@Override
					public void onCheckedChanged() {
						for (Integer i : booleanMap1.keySet()) {
							booleanMap1.put(i, false);
							booleanMap2.put(i, false);
						}
						booleanMap1.put(position, true);
						booleanMap2.put(position, true);
						notifyDataSetChanged();
					}
				});

		holder.tv_value2
				.setOnCheckedChangedListener(new OnCheckedChangedListener() {
					@Override
					public void onCheckedChanged() {
						for (Integer i : booleanMap1.keySet()) {
							booleanMap1.put(i, false);
							booleanMap2.put(i, false);
						}
						booleanMap1.put(position, true);
						booleanMap2.put(position, true);
						notifyDataSetChanged();
					}
				});
		return convertView;
	}

	class Holder {
		RelativeLayout time_line1;
		SportTextView tv_value1;
		TextView tv_time1;
		LinearLayout iv_time_line_top1;
		LinearLayout iv_time_line_bottom1;
		RelativeLayout time_line2;
		SportTextView tv_value2;
		TextView tv_time2;
		LinearLayout iv_time_line_top2;
		LinearLayout iv_time_line_bottom2;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();

	}

	/* 图片缩小的method */
	private void small(CheckBox box) {
		int bmpWidth = box.getWidth();
		int bmpHeight = box.getHeight();
		Bitmap bmp = Utils.drawable2Bitmap(box.getBackground());

		/* 设置图片缩小的比例 */
		double scale = 1.2;
		/* 计算出这次要缩小的比例 */
		float scaleWidth = (float) (bmpWidth * scale);
		float scaleHeight = (float) (bmpWidth * scale);
		/* 产生reSize后的Bitmap对象 */
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight,
				matrix, true);

		// box.setBackground(Utils.bitmap2Drawable(resizeBmp));
	}

	/* 图片放大的method */
	private void big(CheckBox box) {
		int bmpWidth = box.getWidth();
		int bmpHeight = box.getHeight();
		Bitmap bmp = Utils.drawable2Bitmap(box.getBackground());

		/* 设置图片缩小的比例 */
		double scale = 1.2;
		/* 计算出这次要缩小的比例 */
		float scaleWidth = (float) (bmpWidth * scale);
		float scaleHeight = (float) (bmpWidth * scale);
		/* 产生reSize后的Bitmap对象 */
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight,
				matrix, true);

		// box.setBackground(Utils.bitmap2Drawable(resizeBmp));
	}

	public interface btnTimeLineListener {
		public void onSomeChange(OnceSportData info, int i);
	}

	public void setbtnTimeLineListener(btnTimeLineListener Listener) {
		this.mListener = Listener;
	}

	private int dp2px(int value) {
		float v = context.getResources().getDisplayMetrics().density;
		return (int) (v * value + 0.5f);
	}

	private int sp2px(int value) {
		float v = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (v * value + 0.5f);
	}

	private void anim(final View view) {
		// 初始化
//		Animation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
//		// 设置动画时间
//		alphaAnimation.setDuration(100);
		// 初始化
		Animation scaleAnimation = new ScaleAnimation(0.9f, 1.1f, 0.9f, 1.1f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		// 设置动画时间
		scaleAnimation.setDuration(200);
		//设置动画之后回滚到原来尺寸
		scaleAnimation.setFillAfter(true);
		//设置动画加速器
		scaleAnimation.setInterpolator(AnimationUtils.loadInterpolator(context,
				 android.R.anim.accelerate_interpolator));
		
		scaleAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
				
				Animation scaleAnimation = new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				// 设置动画时间
				scaleAnimation.setDuration(300);
				//设置动画之后回滚到原来尺寸
				scaleAnimation.setFillBefore(true);
				//设置动画加速器
				scaleAnimation.setInterpolator(AnimationUtils.loadInterpolator(context,
						 android.R.anim.accelerate_interpolator));
				view.startAnimation(scaleAnimation);
			}
		});
		
		view.startAnimation(scaleAnimation);
	}
	
	/*private void anim2Normal(final View view){
		if(view.getLayoutParams().width!=checkedWidth){
			view.setBackgroundResource(R.mipmap.bg_timeline_value_normal);
			return ;
		}


		Animation scaleAnimation = new ScaleAnimation(1.0f, ((float)normalWidth/checkedWidth), 1.0f, 1.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		// 设置动画时间
		scaleAnimation.setDuration(500);
//		//设置动画之后回滚到原来尺寸
//		scaleAnimation.setFillAfter(true);
		//设置动画加速器
		scaleAnimation.setInterpolator(AnimationUtils.loadInterpolator(context,
				android.R.anim.accelerate_interpolator));

		scaleAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

				view.getLayoutParams().width=normalWidth;
				view.getLayoutParams().height=normalHeight;
				view.setBackgroundResource(R.mipmap.bg_timeline_value_normal);
//				Animation scaleAnimation = new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//				// 设置动画时间
//				scaleAnimation.setDuration(300);
//				//设置动画之后回滚到原来尺寸
//				scaleAnimation.setFillBefore(true);
//				//设置动画加速器
//				scaleAnimation.setInterpolator(AnimationUtils.loadInterpolator(context,
//						android.R.anim.accelerate_interpolator));
//				view.startAnimation(scaleAnimation);
			}
		});

		view.startAnimation(scaleAnimation);
	}
	private void anim2Checked(final View view){
//		((float)checkedWidth/normalWidth)
		Animation scaleAnimation = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		// 设置动画时间
		scaleAnimation.setDuration(500);
		//设置动画之后回滚到原来尺寸
//		scaleAnimation.setFillAfter(true);
		//设置动画加速器
		scaleAnimation.setInterpolator(AnimationUtils.loadInterpolator(context,
				android.R.anim.accelerate_interpolator));

		scaleAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

				view.getLayoutParams().width=checkedWidth;
				view.getLayoutParams().height=checkedHeight;
				view.setBackgroundResource(R.drawable.bg_timeline_value_checked);
				view.getBackground().setAlpha(50);
//				Animation scaleAnimation = new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//				// 设置动画时间
//				scaleAnimation.setDuration(300);
//				//设置动画之后回滚到原来尺寸
//				scaleAnimation.setFillBefore(true);
//				//设置动画加速器
//				scaleAnimation.setInterpolator(AnimationUtils.loadInterpolator(context,
//						android.R.anim.accelerate_interpolator));
//				view.startAnimation(scaleAnimation);
			}
		});

		view.startAnimation(scaleAnimation);
	}*/
}
