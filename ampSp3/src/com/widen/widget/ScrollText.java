package com.widen.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.TextView;

public class ScrollText extends TextView {

	private boolean scroll;
	private float leng;
	private float current = 0;
	private long time;
	private int direct = 1;

	public ScrollText(Context context, AttributeSet attrs) {
		super(context, attrs);
		setLines(1);
	}

	@Override
	public void setLines(int lines) {
		super.setLines(1);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		leng = getPaint().measureText(getText().toString());
		if (leng > getWidth()) {
			scroll = true;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.translate(-current, -getPaint().ascent());
		// 设置颜色
		getPaint().setColor(getCurrentTextColor());
		canvas.drawText(getText().toString()
				+ (current == 0 ? "" : "   " + getText().toString()), 0, 0,
				getPaint());
		if (scroll) {
			float w = getPaint().measureText(getText().toString() + "   ");
			postInvalidateDelayed(50);
			if (time != 0) {
				current += (SystemClock.elapsedRealtime() - time) / 80f
						* direct;
				// if (current > (leng - getWidth()) && direct == 1) {
				// current = (int) (leng - getWidth());
				// direct = -1;
				// } else if (direct == -1 && current < 0) {
				// direct = 1;
				// current = 0;
				// }
				if (current >= w) {
					current = 0;
				}
			}
		}
		time = SystemClock.elapsedRealtime();
	}
}
