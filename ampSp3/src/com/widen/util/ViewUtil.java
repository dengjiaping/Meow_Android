package com.widen.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public final class ViewUtil {
	public static TextView getFooterView(Context con) {
		TextView footerView = new TextView(con);
		footerView.setText("更多");
		footerView.setTextSize(18);
		footerView.setHeight((int) (45 * con.getResources().getDisplayMetrics().density));
//		footerView.setTextColor(con.getResources().getColor(R.color.more_black));
		footerView.setGravity(Gravity.CENTER);
//		footerView.setBackgroundResource(R.drawable.photo_input);
		return footerView;
	}
	
	public static TextView getFooterView(Context con,String str) {
		TextView footerView = new TextView(con);
		footerView.setText(str);
		footerView.setTextSize(18);
		footerView.setHeight((int) (45 * con.getResources().getDisplayMetrics().density));
//		footerView.setTextColor(con.getResources().getColor(R.color.more_black));
		footerView.setGravity(Gravity.CENTER);
//		footerView.setBackgroundResource(R.drawable.photo_input);
		return footerView;
	}

	public static View getDivider(Context con, int res) {
		ImageView v = new ImageView(con);
		v.setImageResource(res);
		return v;
	}

	private ViewUtil() {

	}
}
