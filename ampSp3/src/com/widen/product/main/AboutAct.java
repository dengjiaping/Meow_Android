package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.http.HttpConfig;
import com.widen.product.BaseActivity;
@EActivity(R.layout.about)
public class AboutAct extends BaseActivity{
	
	@ViewById 
	TextView about;
	
	
	@AfterViews
	public void afterViews() {	
		about.setText("版本号: v" + HttpConfig.VERSION);
	}
	
	
	@Click
	void back(){
		finish();
	}
	
	@Click
	public void phone(){
		AlertDialog.Builder builder = new AlertDialog.Builder(
				AboutAct.this);
		builder.setTitle("提示")
				.setMessage("确认拨打 4008-556-333 ?")
				.setPositiveButton(
						"是",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									DialogInterface dialog,
									int which) {
								// TODO Auto-generated
								// method stub
								
								Intent phoneIntent = new Intent("android.intent.action.CALL",Uri.parse("tel:4008556333"));
								startActivity(phoneIntent);
								 
							}
						})
				.setNegativeButton(
						"否",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									DialogInterface dialog,
									int which) {

							}
		}).show();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("关于");
		MobclickAgent.onResume(this);		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("关于");
		MobclickAgent.onPause(this);
	}
	
}
