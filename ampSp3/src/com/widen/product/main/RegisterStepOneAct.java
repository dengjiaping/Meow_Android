package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.product.BaseActivity;
import com.widen.util.Util;
@EActivity(R.layout.register_step_one)
public class RegisterStepOneAct extends BaseActivity implements IHttpCallback{
	@ViewById
	EditText phone;	
	@ViewById
	ProgressBar progressbar;
	String phoneStr;
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			if(msg.what == 1){		
				progressbar.setVisibility(View.GONE);
				if((Boolean) msg.obj){
					Intent intent = new Intent(RegisterStepOneAct.this,RegisterStepTwoAct_.class);
					intent.putExtra("phone", phoneStr);
					startActivity(intent);
				}else{
					showToast("发送失败");
				}
				
			 }else if(msg.what == 2){					
				if(!(Boolean) msg.obj){
					getData();
				}else if((Boolean) msg.obj){
					progressbar.setVisibility(View.GONE);
					showToast("手机号已存在");
				}
			}
			else if(msg.what < 0){	
				  progressbar.setVisibility(View.GONE);
				  showToast(msg.obj.toString());
			}
		}
		
	};
	
	private void getData(){
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.VERIFICATIONS);
		task.setParams(new String[]{"10",phoneStr});
		HttpTaskFactory.getFactory().sendRequest(this, task);
	}
	
	private void getCheckNameData(){
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.CHECK_NAME_UNIQ);
		task.setParams(new String[]{phoneStr});
		HttpTaskFactory.getFactory().sendRequest(new IHttpCallback() {
			
			@Override
			public void onGetData(Object data) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(2, data ));
			}
			
			@Override
			public void onError(Object reason) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(-2, reason));
			}
		}, task);
	}
	
	@Click
	public void sub()
	{				
		if(!TextUtils.isEmpty(phone.getText().toString().trim())){
			phoneStr = phone.getText().toString().trim();
			if(!Util.isMobileNO(phoneStr)){
				showToast("手机号码格式错误");
				return;
			}
			progressbar.setVisibility(View.VISIBLE);
			getCheckNameData();
			
			
		}else{
			showToast("信息不能为空");
		}
	}
	
	
	
	@Click
	public void back(){
		finish();
	}

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
		MobclickAgent.onPageStart("注册第一步");
	    MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("注册第一步");
	    MobclickAgent.onPause(this);
	}
	
	
	
	
}
