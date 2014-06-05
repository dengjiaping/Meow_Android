package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.R.integer;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.info.UserInfo;
import com.widen.product.BaseActivity;
@EActivity(R.layout.user_count)
public class UserCountAct extends BaseActivity implements IHttpCallback{
	
	UserInfo userInfo;
	@ViewById
	LinearLayout user_lay;
	
	@ViewById
	LinearLayout no_data_lay;
	
	@ViewById
	LinearLayout data_lay;
	
	

	
	@ViewById
	TextView Earnings;
	
	@ViewById
	TextView ExpectedEarnings;
	
	@ViewById
	TextView InvestingPrice;
	
	@ViewById
	TextView RealName;
	
	@ViewById
	TextView Cellphone;
	@ViewById
	TextView IdCard;
	
	@ViewById
	LinearLayout no_user_lay;
	
	@ViewById
	ProgressBar progressbar;
	
	@ViewById
	TextView phone;
	
	public static final int USERCOUNTACT = 10;
	
	@AfterViews
	public void afterViews(){
		getData();
	}
	
	@Click
	public void back(){
		finish();
		
	}
	
	private void getData(){
		progressbar.setVisibility(View.VISIBLE);
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.USERS_SUMMARYINFO);
		HttpTaskFactory.getFactory().sendRequest(this, task);
	}
	
	@Click
	public void go_to_product(){
		setResult(USERCOUNTACT);
		finish();
	}
	
	
	private void initView(){
		if(userInfo.Verified){
			if(!TextUtils.isEmpty( userInfo.InvestingPrice)){
				String tString = userInfo.InvestingPrice.replace("0", "").replace(".", "");
				if(TextUtils.isEmpty(tString)){
					data_lay.setVisibility(View.GONE);
					no_data_lay.setVisibility(View.VISIBLE);
				}else{
					data_lay.setVisibility(View.VISIBLE);
					no_data_lay.setVisibility(View.GONE);
				}
			}
			
		}else{
			phone.setText("用户: " + userInfo.Cellphone);
			no_user_lay.setVisibility(View.VISIBLE);
			user_lay.setVisibility(View.GONE);
		}
		
		
		ExpectedEarnings.setText(userInfo.ExpectedEarnings);
		InvestingPrice.setText("在投资金 " + userInfo.InvestingPrice + "元");
		
		Earnings.setText("已获收益 " + userInfo.Earnings + "元");
		
		RealName.setText(userInfo.RealName);
		Cellphone.setText(userInfo.Cellphone);
		IdCard.setText(userInfo.IdCard);
		
		
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			progressbar.setVisibility(View.GONE);
			user_lay.setVisibility(View.VISIBLE);
			if(msg.what == 1){
				userInfo = (UserInfo) msg.obj;				
				initView();
			}else{
				showToast(msg.obj.toString());
			}
		}
		
	};

	@Override
	public void onGetData(Object data) {
		// TODO Auto-generated method stub
		handler.sendMessage(handler.obtainMessage(1, data));
	}

	@Override
	public void onError(Object reason) {
		// TODO Auto-generated method stub
		handler.sendMessage(handler.obtainMessage(-1, reason));
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("帐户信息");
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		MobclickAgent.onPageStart("帐户信息");
	    MobclickAgent.onResume(this);
	}
	
}
