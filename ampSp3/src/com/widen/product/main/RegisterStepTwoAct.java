package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.info.VerifyInfo;
import com.widen.product.BaseActivity;
@EActivity(R.layout.register_step_two)
public class RegisterStepTwoAct extends BaseActivity implements IHttpCallback{
	
	@ViewById
	ProgressBar progressbar;
	@Extra("phone")
	String phone;
	
	@ViewById
	TextView remind;
	@ViewById
	ImageView sendverification_icon;
	
	@ViewById
	TextView sendverification_txt;
	
	@ViewById
	EditText verification;
	
	@ViewById
	Button sendverification;
	
	String verification_txt;
	
	private MyCount myCount;
	Animation operatingAnim;
	@AfterViews
	public void afterViews()
	{		
		remind.setText("包含验证码的短信已经发送到 " + phone);
		myCount = new MyCount(60000,1000);
		//remind.setText("包含验证码短信已发送至" + phone);
		operatingAnim = AnimationUtils.loadAnimation(this, R.anim.tip); 
		 LinearInterpolator lin = new LinearInterpolator();  
		 operatingAnim.setInterpolator(lin);  
		
		 
		 	sendverification.setVisibility(View.GONE);
			sendverification_icon.setVisibility(View.VISIBLE);
			sendverification_txt.setVisibility(View.VISIBLE);
			sendverification_icon.startAnimation(operatingAnim);
			myCount.start();
	}
	
	@Click
	public void back(){
		finish();
	}
	
	@Click
	public void sendverification(){
		
		sendverification.setVisibility(View.GONE);
		sendverification_icon.setVisibility(View.VISIBLE);
		sendverification_txt.setVisibility(View.VISIBLE);
		sendverification_icon.startAnimation(operatingAnim);
		getData();
		myCount.start();
	}
	
	private void getData(){
		progressbar.setVisibility(View.VISIBLE);
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.VERIFICATIONS);
		task.setParams(new String[]{"10",phone});
		HttpTaskFactory.getFactory().sendRequest(this, task);
	}
	
	private void checkVerifyData(){
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.VERIFY);
		task.setParams(new String[]{"10",phone,verification_txt});
		HttpTaskFactory.getFactory().sendRequest(new IHttpCallback() {
			
			@Override
			public void onGetData(Object data) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(2, data));
			}
			
			@Override
			public void onError(Object reason) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(-2, reason));
			}
		}, task);
	}
	
	@Click
	public void sub(){
		verification_txt = verification.getText().toString().trim();
		if(!TextUtils.isEmpty(verification_txt)){	
			/*Intent intent = new Intent(RegisterStepTwoAct.this,RegisterStepThreeAct_.class);
			intent.putExtra("phone", phone);
			intent.putExtra("verification", verification_txt);
			startActivity(intent);*/
			checkVerifyData();
		}else{
			showToast("信息不能为空");
		}
	}
	
	/*@Click
	public void sub(){
		verification_txt = verification.getText().toString().trim();
		if(!TextUtils.isEmpty(verification_txt)){	
			Intent intent = new Intent(RegisterStepTwoAct.this,RegisterStepThreeAct_.class);
			intent.putExtra("phone", phone);
			intent.putExtra("verification", verification_txt);
			intent.putExtra("flag", flag);
			startActivity(intent);
		}else{
			showToast("信息不能为空");
		}
	}
	
	private void checkVerifyData(){
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.VERIFY);
		task.setParams(new String[]{"register",phone,verification_txt});
		HttpTaskFactory.getFactory().sendRequest(new IHttpCallback() {
			
			@Override
			public void onGetData(Object data) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(2, data));
			}
			
			@Override
			public void onError(Object reason) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(-2, reason));
			}
		}, task);
	}
	
	
	
	
	
	*/
	
	private class MyCount extends CountDownTimer {  
        
        public MyCount(long millisInFuture, long countDownInterval) {  
            super(millisInFuture, countDownInterval);  
        }  
  
        @Override  
        public void onFinish() {  
        	sendverification_icon.clearAnimation();
        	sendverification.setVisibility(View.VISIBLE);
    		sendverification_icon.setVisibility(View.GONE);
    		sendverification_txt.setVisibility(View.GONE);
        	
        }  
  
        @Override  
        public void onTick(long millisUntilFinished) {  
        	sendverification_txt.setText(millisUntilFinished / 1000 + "");  
        }  
  
    }  
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {	
			progressbar.setVisibility(View.GONE);
			if(msg.what == 2){
				
				VerifyInfo info = (VerifyInfo) msg.obj;
				
				if(info.Successful){
					Intent intent = new Intent(RegisterStepTwoAct.this,RegisterStepThreeAct_.class);
					intent.putExtra("phone", phone);
					intent.putExtra("Token", info.Token);
					startActivity(intent);
				}else{
					showToast("验证失败");
				/*	Toast toast = Toast.makeText(baseContext, "验证码有误", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.TOP , 0, 200);
					toast.show();*/
				}
				
			}else if(msg.what < 0){
				showToast("验证失败");
				/*Toast toast = Toast.makeText(baseContext, msg.obj.toString(), Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.TOP , 0, 200);
				toast.show();*/
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
		MobclickAgent.onPageStart("注册第二步");
	    MobclickAgent.onResume(this);
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("注册第二步");
	    MobclickAgent.onPause(this);
	}
	
	
	
	
	
}
