package com.widen.product.account;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.widen.R;
import com.widen.product.BaseActivity;

public class IdentityInfoActivity extends BaseActivity implements
		OnClickListener {
	public static void startActivity(Activity activity) {
		activity.startActivity(new Intent(activity, IdentityInfoActivity.class));
	}

	@Override
	protected void initContext() {
		super.initContext();
		setContentView(R.layout.identity_info);

		((TextView) findViewById(R.id.title_tv)).setText(getResources()
				.getString(R.string.identity_info));
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.next_btn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.next_btn:

			break;
		default:
			break;
		}
	}
}
