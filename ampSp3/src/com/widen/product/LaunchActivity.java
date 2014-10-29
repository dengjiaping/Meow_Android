package com.widen.product;

import java.io.File;
import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import cn.sharesdk.framework.ShareSDK;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.application.MyApplication;
import com.widen.db.Store;

import com.widen.http.HttpConfig;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.model.UpdateInfo;
import com.widen.product.main.FirstBannerAct_;
import com.widen.product.main.MainAct;
import com.widen.product.main.MainAct_;
import com.widen.product.main.OrderListAct_;
import com.widen.product.main.ProductDetailAct_;
import com.widen.product.main.RegisterStepOneAct_;
import com.widen.product.main.RegisterStepTwoAct_;
import com.widen.util.UpdateAct;

public class LaunchActivity extends Activity implements IHttpCallback,
		Serializable {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ShareSDK.initSDK(this);
		MobclickAgent.openActivityDurationTrack(false);
		MobclickAgent.updateOnlineConfig(this);
		MobclickAgent.setDebugMode(true);
	 
		showImageAnime(R.drawable.start_logo);
	}

	private void showImageAnime(int bm) {
		ImageView iv = new ImageView(this);
		iv.setScaleType(ScaleType.FIT_XY);
		iv.setImageResource(bm);
		setContentView(iv);
		AlphaAnimation a = new AlphaAnimation(0.1f, 1f);
		a.setInterpolator(new AccelerateInterpolator());
		a.setDuration(2200);
		a.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				getData();
			}
		});
		iv.startAnimation(a);
	}

	private void getData() {
		IHttpTask task = HttpTaskFactory.getFactory().createTask(
				HttpTaskFactory.CHECK_MOBILE_VERSION);
		task.setParams(new String[] { "jymstore", "android","5.0"});
		HttpTaskFactory.getFactory().sendRequest(this, task);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == 1) {
			if(Store.getsBoolean(LaunchActivity.this, "isFirst", true)){
				 Store.putsBoolean(LaunchActivity.this, "isFirst", false);
				 startActivity(new Intent(LaunchActivity.this,FirstBannerAct_.class));
			}else{
				startActivity(new Intent(LaunchActivity.this,MainAct_.class));
			}
			finish();
		} else if (resultCode == 2) {
			String filePath = data.getStringExtra("filePath");
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setDataAndType(Uri.fromFile(new File(filePath)),
					"application/vnd.android.package-archive");
			startActivity(i);
			finish();
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {	
			if (msg.what == 1) {			
				if (msg.obj instanceof UpdateInfo) {
					UpdateInfo info = (UpdateInfo) msg.obj;
					String status = info.status;
					String url = info.url;
					Intent it = new Intent(LaunchActivity.this, UpdateAct.class);				
					if (status.equals("0")) {
						it.putExtra("optional", true);
					} else if (status.equals("1")) {
						it.putExtra("optional", false);
					}
					it.putExtra("url", url);
					it.putExtra("msg", info.message);
					startActivityForResult(it, 1);
				} else if (msg.obj instanceof String) {
					if(Store.getsBoolean(LaunchActivity.this, "isFirst", true)){
						Store.putsBoolean(LaunchActivity.this, "isFirst", false);
						 startActivity(new Intent(LaunchActivity.this,FirstBannerAct_.class));
					}else{
						startActivity(new Intent(LaunchActivity.this,MainAct_.class));
					}
					finish();
				}

			} else if (msg.what < 0) {
				if(Store.getsBoolean(LaunchActivity.this, "isFirst", true)){
					Store.putsBoolean(LaunchActivity.this, "isFirst", false);
					 startActivity(new Intent(LaunchActivity.this,FirstBannerAct_.class));
				}else{
					startActivity(new Intent(LaunchActivity.this,MainAct_.class));
				}
				finish();
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
}
