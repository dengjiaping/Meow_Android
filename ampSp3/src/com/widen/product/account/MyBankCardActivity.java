package com.widen.product.account;

import android.content.Intent;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.widen.R;
import com.widen.application.MyApplication;
import com.widen.product.BaseActivity;

public class MyBankCardActivity extends BaseActivity implements OnClickListener {

	private ListView listView;

	@Override
	protected void initContext() {
		super.initContext();
		setContentView(R.layout.my_bankcard);

		((TextView) findViewById(R.id.title_tv)).setText(getResources()
				.getString(R.string.my_bankcard));
		listView = (ListView) findViewById(R.id.bankcard_lv);

		View footer = getLayoutInflater()
				.inflate(R.layout.addcard_footer, null);
		footer.setLayoutParams(new ListView.LayoutParams(
				LayoutParams.MATCH_PARENT, (int) (MyApplication.density * 50)));
		footer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FundAccountActivity.startActivity(MyBankCardActivity.this,
						FundAccountActivity.NO_REAL);
			}
		});

		listView.addFooterView(footer);
		listView.setAdapter(adapter);

		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.know_more_tv).setOnClickListener(this);
	}

	BaseAdapter adapter = new BaseAdapter() {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.know_more_tv:

			break;
		default:
			break;
		}
	}
}
