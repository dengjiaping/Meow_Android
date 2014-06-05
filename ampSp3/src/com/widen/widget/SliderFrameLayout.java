package com.widen.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class SliderFrameLayout extends FrameLayout {

	public int window_width;
	public int window_height;
	private int lastX;
	private int lastY;
	public boolean isOpenUpLayout = false;
	private IControlMenuOpen mControler;

	public IControlMenuOpen getmControler() {
		return mControler;
	}

	public void setmControler(IControlMenuOpen mControler) {
		this.mControler = mControler;
	}

	public SliderFrameLayout(Context context) {
		super(context);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public SliderFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public SliderFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public void initView(Context context) {
		mControler = new SliderFrameLayout.IControlMenuOpen() {

			@Override
			public boolean getStateOfControl() {
				// TODO Auto-generated method stub
				return true;
			}
		};

		window_width = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getWidth();
		window_height = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getHeight();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (ev.getX() < window_width / 20) {
			if (mControler.getStateOfControl()) {
				int action = ev.getAction();
				int x = (int) ev.getRawX();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					lastX = x;
					return true;

				case MotionEvent.ACTION_MOVE:
					// x移动坐标
					int m = x - lastX;
					// 记录下此刻x坐标
					this.lastX = x;
					return true;

				case MotionEvent.ACTION_UP:
					return true;
				}
			}
		} else if (isOpenUpLayout) {
			if (ev.getY() > window_height / 10) {
				int action = ev.getAction();
				int x = (int) ev.getRawX();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					lastX = x;
					return true;

				case MotionEvent.ACTION_MOVE:
					// x移动坐标
					int m = x - lastX;
					// 记录下此刻x坐标
					this.lastX = x;
					return true;

				case MotionEvent.ACTION_UP:
					return true;
				}
			}
		}
		return false;
	}

	public interface IControlMenuOpen {
		boolean getStateOfControl();
	}

}
