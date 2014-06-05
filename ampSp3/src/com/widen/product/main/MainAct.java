package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import cn.sharesdk.framework.ShareSDK;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.application.MyApplication;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.product.BaseFragmentAct;
import com.widen.util.Util;
@EActivity(R.layout.main) 
public class MainAct extends BaseFragmentAct{

	@ViewById
	RadioButton home;
	
	@ViewById
	RadioButton product;
	
	@ViewById
	RadioButton timeline;
	
	@ViewById
	RadioButton more;
	
	@ViewById
	RadioGroup radioGroup;
	
	HomeFragment homeFragment;
	
	TimeLineFragment timeLineFragment;
	
	ProductFragment productFragment;
	
	MoreFragment moreFragment;
	
	
	@ViewById
	public ImageView red_point;
	
	boolean needCat;
	
	public String flag;
	
	@ViewById
	LinearLayout cat_lay;
	
	private long mExitTime;
	
	@AfterViews
	public void afterViews() {	
		
		/*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();				
		moreFragment = (MoreFragment_) getSupportFragmentManager().findFragmentByTag("moreFragment");
		
		
		if(moreFragment == null){

			moreFragment = new MoreFragment_();
			fragmentTransaction.add(R.id.containerBody, moreFragment, "moreFragment");
			
		}else{
		
			if(moreFragment.isDetached()){
				fragmentTransaction.attach(moreFragment);
			}
		}	
				
		fragmentTransaction.show(moreFragment);			
		fragmentTransaction.commit();*/
		
		
		
		/*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();				
		timeLineFragment = (TimeLineFragment_) getSupportFragmentManager().findFragmentByTag("timeLineFragment");
		
		
		if(timeLineFragment == null){

			timeLineFragment = new TimeLineFragment_();
			fragmentTransaction.add(R.id.containerBody, timeLineFragment, "timeLineFragment");
			
		}else{
		
			if(timeLineFragment.isDetached()){
				fragmentTransaction.attach(timeLineFragment);
			}
		}	
				
		fragmentTransaction.show(timeLineFragment);			
		fragmentTransaction.commit();
		
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();				
		homeFragment = (HomeFragment_) getSupportFragmentManager().findFragmentByTag("homeFragment");
		
		
		if(homeFragment == null){

			homeFragment = new HomeFragment_();
			fragmentTransaction.add(R.id.containerBody, homeFragment, "homeFragment");
			
		}else{
		
			if(homeFragment.isDetached()){
				fragmentTransaction.attach(homeFragment);
			}
		}	
				
		fragmentTransaction.show(homeFragment);			
		fragmentTransaction.commit();*/
		/*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();				
		homeFragment = (HomeFragment_) getSupportFragmentManager().findFragmentByTag("homeFragment");
		
		
		if(homeFragment == null){

			homeFragment = new HomeFragment_();
			fragmentTransaction.add(R.id.containerBody, homeFragment, "homeFragment");
			
		}else{
		
			if(homeFragment.isDetached()){
				fragmentTransaction.attach(homeFragment);
			}
		}	
				
		fragmentTransaction.show(homeFragment);			
		fragmentTransaction.commit();*/
		
		
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == home.getId()){					
					FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();				
					homeFragment = (HomeFragment_) getSupportFragmentManager().findFragmentByTag("homeFragment");	
				
					if(homeFragment == null){
						homeFragment = new HomeFragment_();						
						fragmentTransaction.add(R.id.containerBody, homeFragment, "homeFragment");
					}else{					
						if(homeFragment.isDetached()){
							fragmentTransaction.attach(homeFragment);
						}
					}					
					fragmentTransaction.show(homeFragment);
					
					
					
					if(productFragment != null){
						if(productFragment.isDetached()){
							fragmentTransaction.attach(productFragment);
						}					
						fragmentTransaction.hide(productFragment);
					}
					
					if(moreFragment != null){
						if(moreFragment.isDetached()){
							fragmentTransaction.attach(moreFragment);
						}					
						fragmentTransaction.hide(moreFragment);
					}
					
					 if(timeLineFragment != null){
						if(timeLineFragment.isDetached()){
							fragmentTransaction.attach(timeLineFragment);
						}					
						fragmentTransaction.hide(timeLineFragment);
					}
					
					fragmentTransaction.commit();
				}else if(checkedId == product.getId()){
					
					
					FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();				
					productFragment = (ProductFragment_) getSupportFragmentManager().findFragmentByTag("productFragment");
					if(productFragment == null){

						productFragment = new ProductFragment_();
						fragmentTransaction.add(R.id.containerBody, productFragment, "productFragment");
						
					}else{
					
						if(productFragment.isDetached()){
							fragmentTransaction.attach(productFragment);
						}
					}	
							
					fragmentTransaction.show(productFragment);		
					
					if(timeLineFragment != null){
						if(timeLineFragment.isDetached()){
							fragmentTransaction.attach(timeLineFragment);
						}					
						fragmentTransaction.hide(timeLineFragment);
					}
					
					if(homeFragment != null){
						if(homeFragment.isDetached()){
							fragmentTransaction.attach(homeFragment);
						}					
						fragmentTransaction.hide(homeFragment);
					}
					
					if(moreFragment != null){
						if(moreFragment.isDetached()){
							fragmentTransaction.attach(moreFragment);
						}					
						fragmentTransaction.hide(moreFragment);
					}
					fragmentTransaction.commit();		
					hideCat();
					
				}else if(checkedId == timeline.getId()){
					MobclickAgent.onEvent(MainAct.this, "查看时间轴");
					
					FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();				
					timeLineFragment = (TimeLineFragment_) getSupportFragmentManager().findFragmentByTag("timeLineFragment");
					
					
					if(timeLineFragment == null){

						timeLineFragment = new TimeLineFragment_();
						fragmentTransaction.add(R.id.containerBody, timeLineFragment, "timeLineFragment");
						
					}else{
					
						if(timeLineFragment.isDetached()){
							fragmentTransaction.attach(timeLineFragment);
						}
						
						if(MyApplication.appContext.isLogin() && !timeLineFragment.isLogin){
							timeLineFragment.loginRefresh();							
						}
						if(MyApplication.appContext.isLogin()){
							if(timeLineFragment.dataEnd && timeLineFragment.needRefresh){							
								timeLineFragment.refreshData();
							}else if(timeLineFragment.dataEnd){								
								TimeLineFragment.needRefresh = false;
								getTimeLineCheck();
							}	
						}
					}	
					
					
					
					if(homeFragment != null){
						if(homeFragment.isDetached()){
							fragmentTransaction.attach(homeFragment);
						}					
						fragmentTransaction.hide(homeFragment);
					}
					
					if(productFragment != null){
						if(productFragment.isDetached()){
							fragmentTransaction.attach(productFragment);
						}					
						fragmentTransaction.hide(productFragment);
					}
					
					if(moreFragment != null){
						if(moreFragment.isDetached()){
							fragmentTransaction.attach(moreFragment);
						}					
						fragmentTransaction.hide(moreFragment);
					}
					
							
					fragmentTransaction.show(timeLineFragment);			
					fragmentTransaction.commit();				
					
				}else if(checkedId == more.getId()){		
					
					FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();				
					moreFragment = (MoreFragment_) getSupportFragmentManager().findFragmentByTag("moreFragment");
					
					
					if(moreFragment == null){

						moreFragment = new MoreFragment_();
						fragmentTransaction.add(R.id.containerBody, moreFragment, "moreFragment");
						
					}else{
					
						if(moreFragment.isDetached()){
							fragmentTransaction.attach(moreFragment);
						}
						
						if(MyApplication.appContext.isLogin() && !moreFragment.isLogin){
							moreFragment.loginRefresh();
						}
					}	
					
					
					if(timeLineFragment != null){
						if(timeLineFragment.isDetached()){
							fragmentTransaction.attach(timeLineFragment);
						}					
						fragmentTransaction.hide(timeLineFragment);
					}
					
					
					if(homeFragment != null){
						if(homeFragment.isDetached()){
							fragmentTransaction.attach(homeFragment);
						}					
						fragmentTransaction.hide(homeFragment);
					}
					
					if(productFragment != null){
						if(productFragment.isDetached()){
							fragmentTransaction.attach(productFragment);
						}					
						fragmentTransaction.hide(productFragment);
					}
							
					fragmentTransaction.show(moreFragment);			
					fragmentTransaction.commit();						
				}
				
				
			}
		});
		
		//radioGroup.check(home.getId()); 
		home.performClick();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);		
		if(flag == MoreFragment.FLAG){
			moreFragment.loginRefresh();
			if(timeLineFragment != null){
				timeLineFragment.loginRefresh();
			}
			homeFragment.loginRefresh();
			
		}else if(flag == TimeLineFragment.FLAG){			
			timeLineFragment.loginRefresh();
			if(moreFragment != null){
				moreFragment.loginRefresh();
			}
			homeFragment.loginRefresh();
		}else if(flag == HomeFragment.FLAG){			
			homeFragment.loginRefresh();
			if(timeLineFragment != null){
				timeLineFragment.loginRefresh();
			}
			if(moreFragment != null){
				moreFragment.loginRefresh();
			}
		}
	}

	
	public void loginOut(){
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.remove(homeFragment);
		fragmentTransaction.remove(timeLineFragment);
		fragmentTransaction.remove(productFragment);
		fragmentTransaction.remove(moreFragment);
		fragmentTransaction.commit();
		radioGroup.check(home.getId()); 
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == 1){
				TimeLineFragment.needRefresh = (Boolean) msg.obj;
				if(TimeLineFragment.needRefresh && timeLineFragment.dataEnd){
					timeLineFragment.refreshData();
				}
			}
		}
		
	};
	
	private void getTimeLineCheck(){
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.TIMELINE_CHECK);		
		task.setParams(new String[]{"10",TimeLineFragment.timestamp});
		HttpTaskFactory.getFactory().sendRequest(new IHttpCallback() {
			
			@Override
			public void onGetData(Object data) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(1, data));
			}
			
			@Override
			public void onError(Object reason) {
				// TODO Auto-generated method stub
				
			}
		}, task);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		if(arg1 == UserCountAct_.USERCOUNTACT){
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					radioGroup.check(product.getId()); 
				}
			}, 100);
		}
	
	}
	
	
	
	public void showCat(){
		if(needCat){
			cat_lay.setVisibility(View.VISIBLE);
		}	
	}
	
	public void hideCat(){
		cat_lay.setVisibility(View.GONE);
		needCat = false;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 10000) {
				
				showToast("再按一次退出");
				mExitTime = System.currentTimeMillis();

			} else {
				ShareSDK.stopSDK(MainAct.this);
				Util.exit(this);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("主页TAB");
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		MobclickAgent.onPageStart("主页TAB");
	    MobclickAgent.onResume(this);
	}
	
	
	
	

	/*RadioButton home;
	

	RadioButton product;
	

	RadioButton timeline;
	

	RadioButton more;
	HomeFragment homeFragment;

	RadioGroup radioGroup;
	@Override
	protected void initContext() {
		// TODO Auto-generated method stub
		super.initContext();
		setContentView(R.layout.home);
		//initView();
		
		
	}
	
	private void initView(){
		home = (RadioButton) findViewById(R.id.home);
		product = (RadioButton) findViewById(R.id.product);
		timeline = (RadioButton) findViewById(R.id.timeline);
		more = (RadioButton) findViewById(R.id.more);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		
		
		
		
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();				
		homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
		
		
		if(homeFragment == null){

			homeFragment = new HomeFragment();
			fragmentTransaction.add(R.id.containerBody, homeFragment, "HomeFragment");
		}else{
		
			if(homeFragment.isDetached()){
				fragmentTransaction.attach(homeFragment);
			}
		}		
		fragmentTransaction.show(homeFragment);	
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == home.getId()){
					
					FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();				
					homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
					
					
					if(homeFragment == null){

						homeFragment = new HomeFragment();
						fragmentTransaction.add(R.id.containerBody, homeFragment, "homeFragment");
					}else{
					
						if(homeFragment.isDetached()){
							fragmentTransaction.attach(homeFragment);
						}
					}
					
					fragmentTransaction.show(homeFragment);
					
					
					
					
				}else if(checkedId == product.getId()){
					
				}else if(checkedId == timeline.getId()){
					
				}else if(checkedId == more.getId()){
					
				}
				
				
			}
		});
		
		
		
		
		
		
		
		
	}*/

	
	
	
	
	
	/*@ViewById
	RadioButton home;
	
	@ViewById
	RadioButton product;
	
	@ViewById
	RadioButton timeline;
	
	@ViewById
	RadioButton more;
	
	@ViewById
	RadioGroup radioGroup;
	
	HomeFragment homeFragment;
	
	@AfterViews
	public void afterViews() {	
		
		
		
	}
*/
}
