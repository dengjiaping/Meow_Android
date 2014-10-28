package com.widen.product.account;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.widen.R;
import com.widen.product.BaseFragmentAct;
import com.widen.product.fragment.FundAccountFragment01;

public class FundAccountActivity extends BaseFragmentAct implements
		OnClickListener {

	public static final int NO_REAL = 0x01; // 未实名制认证
	public static final int WAIT_VERIFY = 0x02; // 认证处理中
	public static final int NO_BUY = 0x03; // 已认证但未购买产品
	public static final int HAS_BUY = 0x04; // 已认证且购买过产品

	private FrameLayout container;
	private int type;

	public static void startActivity(Activity activity, int type) {
		Intent intent = new Intent(activity, FundAccountActivity.class);
		intent.putExtra("type", type);
		activity.startActivity(intent);
	}

	@Override
	protected void initContext() {
		super.initContext();
		setContentView(R.layout.fund_account);

		type = getIntent().getIntExtra("type", 1);

		((TextView) findViewById(R.id.title_tv)).setText(getResources()
				.getString(R.string.fund_account));

		container = (FrameLayout) findViewById(R.id.fund_container);
		findViewById(R.id.back).setOnClickListener(this);

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		switch (type) {
		case NO_REAL:
			FundAccountFragment01 fragment01 = FundAccountFragment01
					.getInstance("15800726687");
			transaction.add(R.id.fund_container, fragment01);
			transaction.commitAllowingStateLoss();
			break;
		case WAIT_VERIFY:

			break;
		case NO_BUY:

			break;
		case HAS_BUY:

			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}

}
