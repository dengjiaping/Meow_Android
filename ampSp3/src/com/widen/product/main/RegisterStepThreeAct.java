package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.db.Store;
import com.widen.http.HttpConfig;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.product.BaseActivity;
import com.widen.util.Util;
@EActivity(R.layout.register_step_three)
public class RegisterStepThreeAct extends BaseActivity implements IHttpCallback{
	@ViewById
	ProgressBar progressbar;
	private boolean isGettingData;
	@ViewById
	  EditText password;
	@ViewById
	EditText comfirm_password;	
	
	
	@ViewById
	TextView txt;

	int flag;
	
	@Extra("phone")
	String phone;
	
	@Extra("Token")
	String Token;
	
	@AfterViews
	public void afterViews() {	
		txt.setText("若您已阅读并同意我们的使用条款和个人信息保护政策请点击注册" + Html.fromHtml("<u>" + "用户条款" + "</u>"));
	}
	
	@Click
	public void txt(){
		Intent intent = new Intent(RegisterStepThreeAct.this,BannerWebViewAct_.class);
		intent.putExtra("url", HttpConfig.CURRENT_HOST + "/app/agreement/register" );
		startActivity(intent);
	}
	
	@Click
	public void back(){
		finish();
	}
	
	@Click
	public void sub(){					
		
		if(TextUtils.isEmpty(password.getText().toString().trim()) ||TextUtils.isEmpty(comfirm_password.getText().toString().trim()) ){
			showToast("信息不能为空");
			return;
		}else if(!password.getText().toString().trim().equals(comfirm_password.getText().toString().trim())){
			showToast("密码不一致");
			return;
		}else if(!Util.check("[0-9A-Za-z]{6,18}", password.getText().toString().trim())){
			showToast("6~18位字符位，支持数字，字母，区分大小写");
			return;
		}
		else{
			getData();
		}
	}
	
	private void getData(){
		if(!isGettingData){
			isGettingData = true;
			progressbar.setVisibility(View.VISIBLE);
			IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.REGISTER);
			task.setParams(new String[]{password.getText().toString(),Token});
			HttpTaskFactory.getFactory().sendRequest(this, task);
		}
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			isGettingData = false;
			progressbar.setVisibility(View.GONE);
			if(msg.what == 1){
				showToast("注册成功");
				MobclickAgent.onEvent(RegisterStepThreeAct.this, "注册成功");
				Store.puts(RegisterStepThreeAct.this, "phone", phone);
				Intent intent = new Intent(RegisterStepThreeAct.this,MainAct_.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				finish();
				/*showToast(msg.obj.toString());
				Store.puts(RegisterStepThreeAct.this, "phone", phone);
				
				ProductFragment.loginChangeFlag = true;
				BookManageFragment.loginChangeFlag = true;
				AddressesFragment.loginChangeFlag = true;
				MobclickAgent.onEvent(RegisterStepThreeAct.this, "注册成功");
				
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("注册电话",phone);
				MobclickAgent.onEvent(RegisterStepThreeAct.this, "注册电话", map);  
				Intent intent = new Intent(RegisterStepThreeAct.this,MainAct_.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("from", LoginAct.FLAG);
				startActivity(intent);*/
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("注册第三步");
	    MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("注册第三步");
	    MobclickAgent.onPause(this);
	}
	
	
	

}
