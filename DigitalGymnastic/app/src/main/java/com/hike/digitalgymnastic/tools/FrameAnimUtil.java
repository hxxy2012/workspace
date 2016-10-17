package com.hike.digitalgymnastic.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.hike.digitalgymnastic.HikoDigitalgyApplication;

import java.util.ArrayList;

public class FrameAnimUtil {

	private static boolean moneshot;
	private static int mduration;

	/**
	 * 帧动画代码实现
	 *
	 * @param context
	 *            上下文
	 * @param list
	 *            String类型或者bitmap类型
	 * @param clazz
	 *            list元素的Class
	 * @param oneshot
	 *            是否重复播放
	 * @param duration
	 *            间隔时间
	 *
	 * */
	public static AnimationDrawable GetFrameAnim(Context context,
												 ArrayList list, Class clazz, boolean oneshot, int duration) {

		moneshot = oneshot;
		mduration = duration;

		if (clazz.getName().equals("java.lang.String")) {
			System.out.println("strlist=="+list.size());
			// 根据路径获取相应的bitmap对象集合
			ArrayList<Drawable> drawableslist = GetBitmapListFromPath(list);
			// 生成对应的帧动画
			if (drawableslist.size() != 0) {
				return newFrameAnim(drawableslist);
			} else {
				return null;
			}

		} else if (clazz.getName().equals("android.graphics.Bitmap")) {
			// 根据传进来的Bitmap集合生成Drawable集合
			ArrayList<Drawable> drawableslist2 = GetBitmapListFromBmp(list);
			// 生成对应的帧动画
			if (drawableslist2.size() != 0) {
				return newFrameAnim(drawableslist2);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static ArrayList<Drawable> GetBitmapListFromPath(
			ArrayList<String> list) {
		ArrayList<Drawable> listb = new ArrayList<Drawable>();
		for (int i = 0; i < list.size(); i++) {
			Drawable drawable = Drawable.createFromPath(list.get(i));
			System.out.println("drawable==1"+drawable);
			System.out.println("list.get(i)==1"+list.get(i));
			if (drawable == null) {

			} else {
				listb.add(drawable);
			}
		}
		return listb;
	}

	public static ArrayList<Drawable> GetBitmapListFromBmp(
			ArrayList<Bitmap> list) {
		ArrayList<Drawable> listb = new ArrayList<Drawable>();
		for (int i = 0; i < list.size(); i++) {
			BitmapDrawable drawable = new BitmapDrawable(
					HikoDigitalgyApplication.getInstance().getResources(), list.get(i));
			System.out.println("drawable=="+drawable);
			System.out.println("list.get(i)=="+list.get(i));
			if (drawable == null) {

			} else {
				listb.add(drawable);
			}

		}
		return listb;
	}

	public static AnimationDrawable newFrameAnim(ArrayList<Drawable> list) {
		AnimationDrawable anim = new AnimationDrawable();
		for (int i = 0; i < list.size(); i++) {
			Drawable drawable = list.get(i);

			anim.addFrame(drawable, mduration);

		}
		anim.setOneShot(moneshot);
		// image.setBackgroundDrawable(anim);
		// anim.start();

		return anim;
	}
}
