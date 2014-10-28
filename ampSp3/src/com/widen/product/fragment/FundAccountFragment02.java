package com.widen.product.fragment;

import com.widen.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class FundAccountFragment02 extends Fragment implements OnClickListener {
	private String phoneNum;

	public static FundAccountFragment02 getInstance(String phoneNum) {
		FundAccountFragment02 instance = new FundAccountFragment02();
		Bundle args = new Bundle();
		args.putString("phoneNum", phoneNum);
		instance.setArguments(args);
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if (args != null) {
			phoneNum = args.getString("phoneNum");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fund_wait_verify, null);
		((TextView) view.findViewById(R.id.phone_num_tv)).setText(phoneNum);
		view.findViewById(R.id.know_more_tv).setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.know_more_tv:

			break;
		default:
			break;
		}
	}
}
