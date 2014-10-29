package com.widen.product.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.widen.R;
import com.widen.product.BaseActivity;

public class IdentityInfoActivity extends BaseActivity implements
		OnClickListener {

	private TextView idCardTypeTv;
	private EditText nameEt, idCardNumEt;
	private FrameLayout idCardLayout;

	private String phoneNum, name, idCardType, idCardNum;
	private boolean editAble = true;

	public static void startActivity(Activity activity, String phoneNum,
			boolean editAble, String name, String idCardType, String idCardNum) {
		Intent it = new Intent(activity, IdentityInfoActivity.class);
		it.putExtra("phoneNum", phoneNum);
		it.putExtra("editAble", editAble);
		if (!editAble) {
			it.putExtra("name", name);
			it.putExtra("idCardType", idCardType);
			it.putExtra("idCardNum", idCardNum);
		}
		activity.startActivity(it);
	}

	@Override
	protected void initSettings() {
		super.initSettings();
		Intent it = getIntent();
		phoneNum = it.getStringExtra("phoneNum");
		editAble = it.getBooleanExtra("editAble", true);
		if (!editAble) {
			name = it.getStringExtra("name");
			idCardType = it.getStringExtra("idCardType");
			idCardNum = it.getStringExtra("idCardNum");
		}
	}

	@Override
	protected void initContext() {
		super.initContext();
		setContentView(R.layout.identity_info);

		nameEt = (EditText) findViewById(R.id.name_et);
		idCardTypeTv = (TextView) findViewById(R.id.id_card_type_tv);
		idCardNumEt = (EditText) findViewById(R.id.id_card_num_et);
		idCardLayout = (FrameLayout) findViewById(R.id.id_card_layout);

		((TextView) findViewById(R.id.title_tv)).setText(getResources()
				.getString(R.string.identity_info));
		((TextView) findViewById(R.id.phone_num_tv)).setText(phoneNum);

		if (!editAble) {
			nameEt.setText(name);
			idCardTypeTv.setText(idCardType);
			idCardNumEt.setText(idCardNum);
			nameEt.setEnabled(false);
			idCardLayout.setEnabled(false);
			idCardNumEt.setEnabled(false);
		}

		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.next_btn).setOnClickListener(this);
		idCardLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.next_btn:
			BindCardActivity.startActivity(this, phoneNum, name, idCardNum);
			break;
		case R.id.id_card_layout:

			break;
		default:
			break;
		}
	}
}
