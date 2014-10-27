package com.widen.product.main;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.widen.R;
import com.widen.product.BaseActivity;

public class SettingActivity extends BaseActivity implements OnClickListener {
	@Override
	protected void initContext() {
		super.initContext();
		findViewById(R.id.feedback_lay).setOnClickListener(this);
		findViewById(R.id.about_lay).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.feedback_lay:
			startActivity(new Intent(this, FeedbackAct_.class));
			break;
		case R.id.about_lay:
			startActivity(new Intent(this, AboutAct_.class));
			break;
		default:
			break;
		}
	}
}
