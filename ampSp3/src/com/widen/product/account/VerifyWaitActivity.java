package com.widen.product.account;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.widen.R;
import com.widen.product.BaseActivity;
import com.widen.product.main.MainAct_;

public class VerifyWaitActivity extends BaseActivity implements OnClickListener {

	public static void startActivity(Activity activity) {
		activity.startActivity(new Intent(activity, VerifyWaitActivity.class));
	}

	@Override
	protected void initContext() {
		super.initContext();
		setContentView(R.layout.verify_wait);

		((TextView) findViewById(R.id.title_tv)).setText(getResources()
				.getString(R.string.veriry_wait));
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.look_and_wait_btn).setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			Intent it = new Intent(this, MainAct_.class);
//			it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(it);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
//			Intent it = new Intent(this, MainAct_.class);
//			it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(it);
			finish();
			break;
		case R.id.look_and_wait_btn:
			
			break;
		default:
			break;
		}
	}
}
