package com.hike.digitalgymnastic.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class HistoryAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
	private List<View> viewList;
	private Context context;

	public HistoryAdapter(Context context, List<View> viewList) {
		this.context = context;
		this.viewList = viewList;
	}

	@Override
	public int getCount() {
		return viewList.size();
	}
	
	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
	}
	@Override  
    public int getItemPosition(Object object) {  
        return POSITION_NONE;  
    }
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(viewList.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		return viewList.get(position);
	}

	@Override
	public boolean isViewFromObject(View view, Object o) {
		return view == o;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
