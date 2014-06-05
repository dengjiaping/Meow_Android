package com.widen.product.main;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.product.BaseActivity;
@EActivity(R.layout.help)
public class HelpAct extends BaseActivity {

	
	@Click
	public void back() {
		finish();
	}
	
	@AfterViews
	public void afterViews() {	
		MobclickAgent.onEvent(HelpAct.this, "如何购买");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("帮助");
		MobclickAgent.onResume(this);		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("帮助");
		MobclickAgent.onPause(this);
	}
}
