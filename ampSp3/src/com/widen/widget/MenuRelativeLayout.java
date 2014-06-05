package com.widen.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class MenuRelativeLayout extends RelativeLayout {

	
	public int window_width;
	public int window_height;
	private int lastX;
	private int lastY;
	public boolean isOpenUpLayout = false;

	public MenuRelativeLayout(Context context) {
		super(context);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public MenuRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public MenuRelativeLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public void initView(Context context){
		window_width = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getWidth();
		window_height = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getHeight();
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (isOpenUpLayout) {
			return super.onInterceptTouchEvent(ev);
		}else {
			return true;
		}
	}

}
