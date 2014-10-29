package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.product.BaseActivity;
import com.widen.util.Util;
@EActivity(R.layout.forget_password_step_three)
public class ForgetPasswordStepThreeAct extends BaseActivity implements IHttpCallback{

	@ViewById
	ProgressBar progressbar;
	
	@ViewById
	EditText password;
	
	@ViewById
	EditText comfirm_password;
	

	
	@ViewById
	TextView txt;
	
	@Extra("phone")
	String phone;
	
	@Extra("Token")
	String Token;
	
	@AfterViews
	public void afterViews()
	{
		
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
			showToast("密码格式错误");
			return;
			
		}
		else{
			getData();
		}
	}
	
	@Click
	public void back(){
		finish();
	}
	
	private void getData(){
		progressbar.setVisibility(View.VISIBLE);
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.RESET_LOGIN_PASSWORD);
		task.setParams(new String[]{password.getText().toString().trim(),Token});
		HttpTaskFactory.getFactory().sendRequest(this, task);
	}
	
	private Dialog dialog;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			progressbar.setVisibility(View.GONE);
			if(msg.what == 1){
				dialog = new Dialog(ForgetPasswordStepThreeAct.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.dailog);
				final TextView txt = (TextView) dialog
						.findViewById(R.id.txt);
				txt.setText("密码已修改成功!");
				
				final Button btn = (Button) dialog.findViewById(R.id.btn);
				btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.cancel();
						Intent intent = new Intent(ForgetPasswordStepThreeAct.this,LoginAct_.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						finish();
					}
				});
				dialog.setCancelable(false);
				dialog.show();		
								
			}else if(msg.what < 0){
				showToast(msg.obj.toString());
			}
		}
		
	};

	@Override
	public void onGetData(Object data) {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(1);
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
		MobclickAgent.onPageStart("忘记密码3");
		MobclickAgent.onResume(this);		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("忘记密码3");
		MobclickAgent.onPause(this);
	}
	
}
