package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.application.MyApplication;
import com.widen.db.Store;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.model.SignInfo;
import com.widen.product.BaseActivity;
@EActivity(R.layout.login)
public class LoginAct extends BaseActivity implements IHttpCallback{
	@ViewById
	ProgressBar progressbar;
	private boolean isGettingData;
	@ViewById
	EditText phone;
	@ViewById
	EditText password;
	
	@ViewById
	FrameLayout error_lay;
	
	@ViewById
	TextView error_txt;
	
	@ViewById
	ImageView forget_icon;
	
	String phoneStr;
	
	public static final String FLAG = "LoginAct";
	
	int flag;	
	@AfterViews
	public void afterViews() {		
		flag = getIntent().getIntExtra("flag", 0);			
	}
	
	@Click
	void forget_icon(){
		startActivity(new Intent(LoginAct.this,ForgetPasswordStepOneAct_.class));
	}
	
	@Click
	void back(){
		finish();
	}
	
	@Click
	void login(){
		MobclickAgent.onEvent(LoginAct.this, "登录按钮点击");
		
		phoneStr = phone.getText().toString().trim();
		if(TextUtils.isEmpty(phoneStr) || TextUtils.isEmpty(phoneStr)){
			showToast("信息不能为空");
			return;
		}	
		getData();
	}
	
	private void getData(){
		if(!isGettingData){
			progressbar.setVisibility(View.VISIBLE);
			isGettingData = true;
			IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.SIGNIN);
			task.setParams(new String[]{phoneStr,password.getText().toString().trim()});
			HttpTaskFactory.getFactory().sendRequest(this, task);
		}
	}
	
	@Click
	void register(){	
		startActivity(new Intent(LoginAct.this,RegisterStepOneAct_.class));
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			isGettingData = false;
			progressbar.setVisibility(View.GONE);
			if(msg.what == 1){
				error_lay.setVisibility(View.GONE);
				SignInfo info = (SignInfo) msg.obj;
		
				if(info.Successful){
					Store.puts(LoginAct.this, "phone", phoneStr);
					Intent intent = new Intent(LoginAct.this,MainAct_.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					finish();
				}else{
					
					if(!info.UserExist){
						error_lay.setVisibility(View.VISIBLE);
						error_txt.setText("对不起，该用户名不存在");
					}else if(info.Lock.equals("true")){
						error_lay.setVisibility(View.VISIBLE);
						error_txt.setText("账户已锁定，请修改密码或者明天再试");
					}else {
						error_lay.setVisibility(View.VISIBLE);
						error_txt.setText("密码错误，您今天还有" + info.RemainCount + "次机会");
						forget_icon.setVisibility(View.VISIBLE);
					}					
					 MyApplication.appContext.ampAuthToken = "";
					 Store.remove(LoginAct.this, "ampAuthToken");
					 Store.remove(LoginAct.this, "phone"); 
				}
			
				MobclickAgent.onEvent(LoginAct.this, "登录成功");
			}else if(msg.what < 0){
				 MyApplication.appContext.ampAuthToken = "";
				 Store.remove(LoginAct.this, "ampAuthToken");
				 Store.remove(LoginAct.this, "phone");
				 
				 Toast toast = Toast.makeText(baseContext, msg.obj.toString(), Toast.LENGTH_SHORT);
				 toast.setGravity(Gravity.TOP , 0, 200);
				 toast.show();
				 
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
		MobclickAgent.onPageStart("登录");
		MobclickAgent.onResume(this);		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("登录");
		MobclickAgent.onPause(this);
	}
	
	
	
}
