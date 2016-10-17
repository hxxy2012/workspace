package com.hike.digitalgymnastic.view;

import com.hiko.enterprisedigital.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

public class SportTextView extends TextView implements OnClickListener {
    int normalWidth;
    int normalHeight;
    int checkedWidth;
    int checkedHeight;
    int normalBackground;
    int checkedBackground;
    int during;//动画时间
    MyScaleAnimation animation0;//缩放动画
    MyScaleAnimation animation1;//展开动画
    int textsize_big;
    int textsize_small;

    private boolean isChecked;
    private OnTextClickListener onTextClickListener;
    private OnCheckedChangedListener onCheckedChangedListener;

    public OnCheckedChangedListener getOnCheckedChangedListener() {
        return onCheckedChangedListener;
    }

    public void setOnCheckedChangedListener(
            OnCheckedChangedListener onCheckedChangedListener) {
        this.onCheckedChangedListener = onCheckedChangedListener;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;

    }

    /**
     * 启动正常大小动画
     */
    public void startAnimationNormal() {
//        SportTextView.this.setBackgroundResource(normalBackground);
        if (animation0 != null) {
            if(getLayoutParams().width==checkedWidth){
                this.startAnimation(animation0);
            }

        }
    }

    /**
     * 启动展开动画
     */
    public void startAnimationExpland() {
        if (animation1 != null) {
            if(getLayoutParams().width==normalWidth){
                SportTextView.this.setBackgroundResource(checkedBackground);
                getBackground().setAlpha(20);
                this.startAnimation(animation1);
            }

        }
    }

    public OnTextClickListener getOnTextClickListener() {
        return onTextClickListener;
    }

    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }

    public SportTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.TextSport);
        normalWidth = (int) array.getDimensionPixelSize(
                R.styleable.TextSport_normalWidth, 0);
        normalHeight = (int) array.getDimensionPixelSize(
                R.styleable.TextSport_normalHeight, 0);
        checkedWidth = (int) array.getDimensionPixelSize(
                R.styleable.TextSport_checkedWidth, 0);
        checkedHeight = (int) array.getDimensionPixelSize(
                R.styleable.TextSport_checkedHeight, 0);
        normalBackground = (int) array.getResourceId(
                R.styleable.TextSport_normalBackground, R.mipmap.bg_timeline_value_normal);
        checkedBackground = (int) array.getResourceId(
                R.styleable.TextSport_checkedBackground, R.drawable.bg_timeline_value_checked);
        during = array.getInteger(R.styleable.TextSport_during, 500);
        textsize_big = array.getDimensionPixelSize(R.styleable.TextSport_textsize_big, 0);
        textsize_small = array.getDimensionPixelSize(R.styleable.TextSport_textsize_small, 0);
        array.recycle();
        init();
    }

    public SportTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

        // get attrConfiguration
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.TextSport);
        normalWidth = (int) array.getDimensionPixelSize(
                R.styleable.TextSport_normalWidth, 0);
        normalHeight = (int) array.getDimensionPixelSize(
                R.styleable.TextSport_normalHeight, 0);
        checkedWidth = (int) array.getDimensionPixelSize(
                R.styleable.TextSport_checkedWidth, 0);
        checkedHeight = (int) array.getDimensionPixelSize(
                R.styleable.TextSport_checkedHeight, 0);
        normalBackground = (int) array.getResourceId(
                R.styleable.TextSport_normalBackground, R.mipmap.bg_timeline_value_normal);
        checkedBackground = (int) array.getResourceId(
                R.styleable.TextSport_checkedBackground, R.drawable.bg_timeline_value_checked);
        during = array.getInteger(R.styleable.TextSport_during, 500);

        textsize_big = array.getDimensionPixelSize(R.styleable.TextSport_textsize_big, 0);
        textsize_small = array.getDimensionPixelSize(R.styleable.TextSport_textsize_small, 0);
        array.recycle();
        init();
    }

    public SportTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        setOnClickListener(this);
        animation0 = new MyScaleAnimation(0);
        animation0.setDuration(500);
        animation1 = new MyScaleAnimation(1);
        animation1.setDuration(500);
        animation0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SportTextView.this.setBackgroundResource(normalBackground);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SportTextView.this.setBackgroundResource(checkedBackground);
                getBackground().setAlpha(20);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public void reset(){
        this.clearAnimation();
        getLayoutParams().width =normalWidth;
        getLayoutParams().height = normalHeight;
    }

    public interface OnTextClickListener {
        public void onTextClick();
    }

    public interface OnCheckedChangedListener {
        public void onCheckedChanged();
    }

    @Override
    public void onClick(View view) {
        if (!isChecked) {//没有被选中，设置成选中状态，同时更新背景图
            setChecked(true);
            onCheckedChangedListener.onCheckedChanged();
        } else {
            if (onTextClickListener != null)
                onTextClickListener.onTextClick();
        }
    }


    private class MyScaleAnimation extends Animation {
        int mode;//0为缩放动画，1为展开动画

        public MyScaleAnimation(int mode) {
            this.mode = mode;
        }

        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime <= 1.0f) {
                Log.d("MyLog", "interpolatedTime===" + interpolatedTime);

                if (mode == 0) {//
                    if (getLayoutParams().width > normalWidth) {
                        getLayoutParams().width = (int) (checkedWidth - (checkedWidth - normalWidth) * interpolatedTime);
                        getLayoutParams().height = (int) (checkedHeight - (checkedHeight - normalHeight) * interpolatedTime);
                        String text = getText().toString();
                        setTextScaleSize(interpolatedTime);
                    }

                } else {
                    if (getLayoutParams().width < checkedWidth) {
                        getLayoutParams().width = (int) (normalWidth + (checkedWidth - normalWidth) * interpolatedTime);
                        getLayoutParams().height = (int) (normalHeight + (checkedHeight - normalHeight) * interpolatedTime);
                        setTextScaleSize(interpolatedTime);
                    }
                }
                SportTextView.this.requestLayout();
            }else{//标志已经结束

            }

        }
    }

    private void setTextScaleSize(float scale) {

        String text = getText().toString();
        String[] values = {"", ""};
        if (text.contains("\n")) {
            values = text.split("\n", -1);
            SpannableStringBuilder spannable = new SpannableStringBuilder(
                    text);

            AbsoluteSizeSpan span_1 = new AbsoluteSizeSpan((int) (textsize_big * scale));// 字体大小
            AbsoluteSizeSpan span_2 = new AbsoluteSizeSpan((int) (textsize_small * scale));// 字体大小


            spannable.setSpan(span_1, 0,
                    values[0].length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            spannable.setSpan(span_2, values[0].length(),
                    text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(spannable);

        } else {
            setTextSize((int) (textsize_big * scale));
        }

    }

}
