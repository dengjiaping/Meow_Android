package com.widen.widget;


import com.widen.R;
import com.widen.application.MyApplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class PointsView extends View {

	private Paint p;

	private int index = 0;
	private int radius = 4;
	private int count = 3;
	private int distance = 4;

	public PointsView(Context context, AttributeSet attr) {
		super(context, attr);
		p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setStyle(Style.FILL);
		if (attr != null) {
			radius = (int) (attr.getAttributeIntValue(null, "radius", 4) * MyApplication.density);
			distance = (int) (attr.getAttributeIntValue(null, "dis", 4) * MyApplication.density);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(radius * 2 * count + (count - 1) * distance + getPaddingLeft() + getPaddingRight(), radius
				* 2 + getPaddingTop() + getPaddingBottom());
	}

	public void setCount(int c) {
		count = c;
		requestLayout();
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(radius + getPaddingLeft(), radius + getPaddingTop());
		for (int i = 0; i < count; i++) {
			p.setColor(i == index ? getResources().getColor(R.color.red) :getResources().getColor(R.color.point_grey));
			canvas.drawCircle(0, 0, radius, p);
			canvas.translate(radius * 2 + distance, 0);
		}
	}

	public void setIndex(int i) {
		index = i;
		invalidate();
	}
}
