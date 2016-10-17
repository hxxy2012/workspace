package com.hike.digitalgymnastic.tools;

import com.hiko.enterprisedigital.R;

import android.app.Activity;

public class AnimUtil {
	/** slip in bottom */
	public static void intentSlidBottomIn(Activity activity) {
		activity.overridePendingTransition(0, R.anim.push_bot_in);
	}

	/** slip out bottom */
	public static void intentSlidBottomOut(Activity activity) {
		activity.overridePendingTransition(0, R.anim.push_bot_out);
	}

	/** push up */
	public static void intentPushUp(Activity activity) {
		activity.overridePendingTransition(R.anim.push_up, R.anim.push_out);
	}

	/** push down */
	public static void intentPushDown(Activity activity) {
		activity.overridePendingTransition(R.anim.push_out, R.anim.push_down);
	}

	/** slip in */
	public static void intentSlidIn(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	/** Slip off */
	public static void intentSlidOut(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
