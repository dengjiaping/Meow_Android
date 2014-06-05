package com.widen.widget;

import com.widen.util.Dip2SpUtil;
import com.widen.util.MapForImg;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.FrameLayout.LayoutParams;

public class SilderView extends FrameLayout {

	public int window_width;
	public int window_height;
	private Scroller scroller;
	private LayoutInflater mInflater;
	public FrameLayout upLayout;
	public ImageView childLayout;
	private MapForImg mapForImg;
	private boolean isOpen;
	private Context context;

	public SilderView(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		initView(context, attrs);
		this.context = context;
	}

	public SilderView(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public SilderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
		this.context = context;
	}

	private void initView(Context context, AttributeSet attrs) {
		mInflater = LayoutInflater.from(context);
		scroller = new Scroller(context);
		window_width = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getWidth();
		window_height = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getHeight();
	}

	public void setMapInf(MapForImg mapForImg) {
		this.mapForImg = mapForImg;
	}

	public void openUpView() {

		scroller.startScroll(0, Dip2SpUtil.dip2px(context, 400), 0,
				-Dip2SpUtil.dip2px(context, 150), 50);
		invalidate();
		isOpen = true;

	}

	public void closeUpView() {
		isOpen = false;
		scroller.startScroll(0, Dip2SpUtil.dip2px(context, 250), 0,
				Dip2SpUtil.dip2px(context, 150), 50);
		invalidate();

	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if (scroller.computeScrollOffset()) {
			LayoutParams layoutParams = (LayoutParams) childLayout.getLayoutParams();
			layoutParams.height = this.scroller.getCurrY();
			childLayout.setLayoutParams(layoutParams);
			invalidate();
			
		} else {
			if (!isOpen ) {
				mapForImg.showViewAndMap(isOpen);
			}
		}
	}

	public FrameLayout getUpLayout() {
		return upLayout;
	}

	public void setChildLay(ImageView upLayout) {
		this.childLayout = upLayout;
	}

	public void setUpLayout(FrameLayout upLayout) {
		this.upLayout = upLayout;
	}

}
