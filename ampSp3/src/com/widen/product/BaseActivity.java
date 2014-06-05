package com.widen.product;

import java.io.IOException;

import com.widen.application.MyApplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {

	protected MyApplication appContext = null;
	protected Context baseContext = null;
	protected ProgressDialog mProgressDialog = null;

	protected void initSettings() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSettings();
		this.appContext = (MyApplication) getApplicationContext();
		this.baseContext = this;

		IntentFilter filter = new IntentFilter(getPackageName());
		registerReceiver(receiver, filter);

		initContext();
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			/*
			 * if (context.getPackageName().equals(getPackageName())) { try {
			 * Store.login(appContext, appContext.userId,
			 * appContext.getSynList()); } catch (IOException e) {
			 * Log.d("saveLogin", "" + e); }
			 * 
			 * }
			 */
			finish();
		}
	};

	protected void initContext() {

	}

	protected void startActivity(Class<?> cls) {
		baseContext.startActivity(new Intent(baseContext, cls));
	}

	protected void startActivity(Class<?> cls, Bundle extras) {
		Intent intent = new Intent();
		intent.setClass(baseContext, cls);
		intent.putExtras(extras);

		baseContext.startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(receiver!=null){
			unregisterReceiver(receiver);
			receiver = null;
		}

	}

	public void showToast(CharSequence msg) {
		Toast toast = Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER , 0, 0);
		toast.show();
	}

}
