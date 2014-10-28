package com.widen.product.fragment;

import com.widen.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class FundAccountFragment04 extends Fragment implements OnClickListener {
	private String name, phoneNum, idCard;

	public static FundAccountFragment04 getInstance(Bundle args) {
		FundAccountFragment04 instance = new FundAccountFragment04();
		instance.setArguments(args);
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if (args != null) {
			name = args.getString("name");
			phoneNum = args.getString("phoneNum");
			idCard = args.getString("idCard");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fund_has_buy, null);
		((TextView) view.findViewById(R.id.name_tv)).setText(name);
		((TextView) view.findViewById(R.id.phone_num_tv)).setText(phoneNum);
		((TextView) view.findViewById(R.id.id_card_tv)).setText(idCard);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}
}
