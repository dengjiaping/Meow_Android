package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.product.BaseActivity;
@EActivity(R.layout.feed_back)
public class FeedbackAct extends BaseActivity implements IHttpCallback{

	@ViewById
	EditText title;
	
	@ViewById
	EditText content;
	
	@ViewById
	Button send;
	
	@ViewById
	TextView number;
	
	@AfterViews
	public void afterViews() {	
		content.addTextChangedListener(new TextWatcher() {
			private  CharSequence mTemp;
			private int mSelectionStart;
            private int mSelectionEnd;
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				mTemp = s;
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				 mSelectionStart = content.getSelectionStart();
	                mSelectionEnd = content.getSelectionEnd();
				if(s.length() > 200){
					
					s.delete(mSelectionStart-1,mSelectionEnd);//删掉多输入的文字
                    int tempSelection = mSelectionEnd;
                    content.setText(s);
                    content.setSelection(tempSelection);
                    number.setTextColor(getResources().getColor(R.color.red));
				}else{
					 number.setTextColor(getResources().getColor(R.color.black));
				}
				number.setText(s.length() + "/200");
			}
		});
	}
	
	@Click
	public void back()
	{
		finish();
	}
	
	@Click
	public void send()
	{
		if(TextUtils.isEmpty(content.getText().toString().trim())){
			showToast("请填写内容");
		}else{
			getData();
		}
		
	}
	private void getData(){
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.FEED_BACK_NEW);
		task.setParams(new String[]{/*title.getText().toString().trim(),*/content.getText().toString().trim()});
		HttpTaskFactory.getFactory().sendRequest(this, task);
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what  == 1){
				showToast("提交成功");
				finish();
			}else if(msg.what < 0){
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
		MobclickAgent.onPageStart("反馈");
		MobclickAgent.onResume(this);		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("反馈");
		MobclickAgent.onPause(this);
	}
	
}
