package com.widen.product.account;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.widen.R;
import com.widen.application.MyApplication;
import com.widen.product.BaseActivity;
import com.widen.product.main.AboutAct_;
import com.widen.product.main.FeedbackAct_;

public class SettingActivity extends BaseActivity implements OnClickListener {
	@Override
	protected void initContext() {
		super.initContext();

		setContentView(R.layout.setting);
		findViewById(R.id.feedback_lay).setOnClickListener(this);
		findViewById(R.id.about_lay).setOnClickListener(this);
		findViewById(R.id.security_setting_lay).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);

		((TextView) findViewById(R.id.title_tv)).setText(getResources()
				.getString(R.string.setting));

		if (!MyApplication.appContext.isLogin()) {
			findViewById(R.id.security_setting).setVisibility(View.GONE);
		}
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
		case R.id.security_setting_lay:

			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}
}
