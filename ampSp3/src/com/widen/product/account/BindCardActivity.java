package com.widen.product.account;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.widen.R;
import com.widen.product.BaseActivity;

public class BindCardActivity extends BaseActivity implements OnClickListener {
	
	private String phoneNum, name, idCard;

	public static void startActivity(Activity activity, String phoneNum,
			String name, String idCard) {
		Intent it = new Intent(activity, BindCardActivity.class);
		it.putExtra("phoneNum", phoneNum);
		it.putExtra("name", name);
		it.putExtra("idCard", idCard);
		activity.startActivity(it);
	}

	@Override
	protected void initSettings() {
		super.initSettings();
		Intent it = getIntent();
		phoneNum = it.getStringExtra("phoneNum");
		name = it.getStringExtra("name");
		idCard = it.getStringExtra("idCard");
	}

	@Override
	protected void initContext() {
		super.initContext();
		setContentView(R.layout.bind_card);

		((TextView) findViewById(R.id.title_tv)).setText(getResources()
				.getString(R.string.bind_card));
		((TextView) findViewById(R.id.phone_num_tv)).setText(phoneNum);
		((TextView) findViewById(R.id.name_tv)).setText(name);
		((TextView) findViewById(R.id.id_card_num_tv)).setText(idCard);

		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.next_btn).setOnClickListener(this);
		findViewById(R.id.opening_bank_layout).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.next_btn:
			VerifyWaitActivity.startActivity(this);
			finish();
			break;
		case R.id.opening_bank_layout:

			break;
		default:
			break;
		}
	}
}
