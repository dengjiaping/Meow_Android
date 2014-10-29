package com.widen.product.account;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.widen.R;
import com.widen.product.BaseActivity;

public class PayPwdSettingActivity extends BaseActivity implements
		OnClickListener {

	public static void startActivity(Activity activity) {
		activity.startActivity(new Intent(activity, PayPwdSettingActivity.class));
	}

	@Override
	protected void initContext() {
		super.initContext();
		setContentView(R.layout.pay_pwd_setting);

		((TextView) findViewById(R.id.title_tv)).setText(getResources()
				.getString(R.string.set_pay_pwd));
		findViewById(R.id.commit_btn).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.commit_btn:
			IdentityInfoActivity.startActivity(this, "15800726687", true,
					"wang", null, "1234567890-123");
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}
}
