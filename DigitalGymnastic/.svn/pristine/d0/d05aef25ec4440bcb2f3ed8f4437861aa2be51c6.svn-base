package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

public class BackGroundImage extends View{
	private int mPosition;

    private float mDegree;

    private List<Drawable> mDrawableLists;

    private int mPrePosition = 0;

    private Drawable mNext;


    public void setmDrawableLists(List<Drawable> mDrawableLists) {

        this.mDrawableLists = mDrawableLists;

        mNext = mDrawableLists.get(1);//设置下一个背景图片的drawable

    }

 

    public void setmPosition(int mPosition) {

        this.mPosition = mPosition;

    }


    public void setmDegree(float mDegree) {

        this.mDegree = mDegree;

    }


    public BackGroundImage(Context context) {

        super(context);

        //setWillNotDraw(false);

    }


    public BackGroundImage(Context context, AttributeSet attrs) {

        super(context, attrs);

        //setWillNotDraw(false);

    }


    public BackGroundImage(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        //setWillNotDraw(false);

    }


    @Override

    protected void onDraw(Canvas canvas) {

        Log.i("111", "onDraw");

        if (null == mDrawableLists) {

            return;

        }

        int alpha1 = (int) (255 - (mDegree * 255));

        Drawable fore = mDrawableLists.get(mPosition);

        fore.setBounds(0, 0, getWidth(), getHeight());

        mNext.setBounds(0, 0, getWidth(), getHeight());

        if (mPrePosition != mPosition) {//边界判断

            if (mPosition != mDrawableLists.size() - 1) {

                mNext = mDrawableLists.get(mPosition + 1);

            } else {

                mNext = mDrawableLists.get(mPosition);

            }

        }

        fore.setAlpha(alpha1);//淡出

        mNext.setAlpha(255);

        mNext.draw(canvas);

        fore.draw(canvas);

        mPrePosition = mPosition;

        super.onDraw(canvas);

    }
}
