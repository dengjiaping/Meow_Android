package com.widen.product.main;



import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.application.MyApplication;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.info.BannerInfo;
import com.widen.http.info.TopInfo;
import com.widen.widget.URLImageView;
@EFragment(R.layout.home)
public class HomeFragment extends Fragment implements IHttpCallback, SwipeRefreshLayout.OnRefreshListener{
	@ViewById 
	 SwipeRefreshLayout swipeRefreshLayout;
	
	@ViewById 
	HorizontalScrollView horizontalScrollView;
	@ViewById 
	LinearLayout banners_lay;
	
	@ViewById
	TextView Name;
	
	@ViewById
	ImageView cat_icon;
	
	@ViewById
	TextView BankName;
	
	@ViewById
	TextView Yield;
	
	@ViewById
	TextView txt_1;
	
	@ViewById
	TextView txt_2;
	
	TopInfo info;
	
	@ViewById
	TextView Unit;
	
	@ViewById
	TextView Duration;
	
	@ViewById
	LinearLayout no_login_lay;
	
	@ViewById
	FrameLayout logined_lay;
	
	@ViewById
	FrameLayout logined_lay2;
	
	@ViewById
	TextView txt_3;
	
	@ViewById
	TextView accrued_txt;
	
	@ViewById
	ProgressBar progressbar;
	
	@ViewById
	TextView act_txt2;
	/*@ViewById
	URLImageView img_t;*/
	

	
	
	
	public static boolean isLogin;
	
	public static final String FLAG = "HomeFragment";
	
	@Click
	public void product_lay(){
		MobclickAgent.onEvent(getActivity(), "首页推荐");
		Intent intent = new Intent(getActivity(),ProductDetailAct_.class);
		intent.putExtra("Id", info.topInfo2.Id);
		startActivity(intent);
	}
	
	@Click
	public void logined_lay(){
		startActivity(new Intent(getActivity(),WebViewAct_.class));
	}
	
	@Click
	public void logined_lay2(){		
		startActivity(new Intent(getActivity(),WebViewAct_.class));
	}
	
	public void loginRefresh(){
		
		if(MyApplication.appContext.isLogin()){			
			isLogin = true;
			getData();
			/*no_login_lay.setVisibility(View.GONE);
			logined_lay.setVisibility(View.VISIBLE);*/
		}else{			
			isLogin = false;			
			
			no_login_lay.setVisibility(View.VISIBLE);
			logined_lay.setVisibility(View.GONE);
			logined_lay2.setVisibility(View.GONE);
		}
		
	}
	
	
	
	

	@AfterViews
	public void afterViews() {	
		getData();
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(R.color.top_yellow, R.color.top_yellow,
				R.color.top_yellow, R.color.top_yellow);
	}
	
	@Click
	public void login()
	
	{
		MobclickAgent.onEvent(getActivity(), "首页登录");
		((MainAct)getActivity()).flag = FLAG;
		   startActivity(new Intent(getActivity(), LoginAct_.class));
	}
	
	
	@Click
	public void register()
	{
		((MainAct)getActivity()).flag = FLAG;
		startActivity(new Intent(getActivity(), RegisterStepOneAct_.class));
	}
	
	
	 private void initView(){
		Name.setText(info.topInfo2.Name);
		BankName.setText(info.topInfo2.BankName);
		Yield.setText(info.topInfo2.Yield);
		Unit.setText(info.topInfo2.Unit + "元起投");
		Duration.setText(info.topInfo2.Duration + "天");
		banners_lay.removeAllViews();
		if(MyApplication.appContext.dm.widthPixels <= 480){
			for(int i=0 ; i	< info.bannerInfos.size();i++){
				View view = LayoutInflater.from(getActivity()).inflate(R.layout.top_img_item, null);
				URLImageView imageView = (URLImageView) view.findViewById(R.id.img);
				imageView.setImageURL(info.bannerInfos.get(i).Src_480);
				view.setTag(info.bannerInfos.get(i));
				view.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final BannerInfo info = (BannerInfo) v.getTag();
						if(info.Type.equals("10")){
							Intent intent = new Intent(getActivity(),BannerWebViewAct_.class);
							intent.putExtra("url", info.Url);
							startActivity(intent);
						}else if(info.Type.equals("20")){
							AlertDialog.Builder builder = new AlertDialog.Builder(
									getActivity());
							builder.setTitle("提示")
									.setMessage("确认拨打 " + info.Url + "?")
									.setPositiveButton(
											"是",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method stub
													
													Intent phoneIntent = new Intent("android.intent.action.CALL",Uri.parse("tel:" + info.Url));
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
					}
				});
				
				banners_lay.addView(view);
			}
		}else if(MyApplication.appContext.dm.widthPixels > 480 && MyApplication.appContext.dm.widthPixels <= 640){
			for(int i=0 ; i	< info.bannerInfos.size();i++){
				View view = LayoutInflater.from(getActivity()).inflate(R.layout.top_img_item, null);
				URLImageView imageView = (URLImageView) view.findViewById(R.id.img);
				imageView.setImageURL(info.bannerInfos.get(i).Src_640);
				view.setTag(info.bannerInfos.get(i));
				view.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final BannerInfo info = (BannerInfo) v.getTag();
						if(info.Type.equals("10")){
							Intent intent = new Intent(getActivity(),BannerWebViewAct_.class);
							intent.putExtra("url", info.Url);
							startActivity(intent);
						}else if(info.Type.equals("20")){
							AlertDialog.Builder builder = new AlertDialog.Builder(
									getActivity());
							builder.setTitle("提示")
									.setMessage("确认拨打 " + info.Url + "?")
									.setPositiveButton(
											"是",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method stub
													
													Intent phoneIntent = new Intent("android.intent.action.CALL",Uri.parse("tel:" + info.Url));
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
						
					}
				});
				banners_lay.addView(view);
			}
		}else if(MyApplication.appContext.dm.widthPixels >640){
			for(int i=0 ; i	< info.bannerInfos.size();i++){
				View view = LayoutInflater.from(getActivity()).inflate(R.layout.top_img_item, null);
				URLImageView imageView = (URLImageView) view.findViewById(R.id.img);
				imageView.setImageURL(info.bannerInfos.get(i).Src_720);
				view.setTag(info.bannerInfos.get(i));
				view.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final  BannerInfo info = (BannerInfo) v.getTag();
						if(info.Type.equals("10")){
							Intent intent = new Intent(getActivity(),BannerWebViewAct_.class);
							intent.putExtra("url", info.Url);
							startActivity(intent);
						}else if(info.Type.equals("20")){
							AlertDialog.Builder builder = new AlertDialog.Builder(
									getActivity());
							builder.setTitle("提示")
									.setMessage("确认拨打 " + info.Url )
									.setPositiveButton(
											"是",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method stub
													
													Intent phoneIntent = new Intent("android.intent.action.CALL",Uri.parse("tel:" + info.Url));
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
					}
				});
				banners_lay.addView(view);
			}
		}
		
		if(info.code.equals("200")){
			
			logined_lay.setVisibility(View.VISIBLE);
			no_login_lay.setVisibility(View.GONE);
			logined_lay2.setVisibility(View.GONE);
			if(info.HasOrders){							
				if(info.HasShown){		
					
				logined_lay.setVisibility(View.VISIBLE);
				no_login_lay.setVisibility(View.GONE);
				logined_lay2.setVisibility(View.GONE);
				txt_1.setText("过去" + timeString(Double.parseDouble(info.EarningsAgainDuration))  
						+ "中,喵喵帮你赚了");
				
				
				 java.text.DecimalFormat   df2=new   java.text.DecimalFormat("#.########");  
					double   d2=Double.parseDouble(info.EarningsAgain); 
				txt_2.setText(df2.format(d2));
				
				txt_3.setText("元");
				
				//accrued_txt.setText("又赚了" + df2.format(d2) + "元,赚钱速度击败了" + info.DefeatedPercent + "%的用户");
				accrued_txt.setText("您的赚钱速度击败了" + info.DefeatedPercent + "%的用户!");
				cat_icon.setVisibility(View.GONE);
				}else{
									
					logined_lay.setVisibility(View.GONE);
					no_login_lay.setVisibility(View.GONE);
					logined_lay2.setVisibility(View.VISIBLE);
					act_txt2.setText("您的赚钱速度击败了" + info.DefeatedPercent + "%的用户!");
					
				}
			}else{				
				logined_lay.setVisibility(View.VISIBLE);
				no_login_lay.setVisibility(View.GONE);
				logined_lay2.setVisibility(View.GONE);
				
				txt_1.setText("今日平均赚钱速度  ");
				java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.########");  
				double   d=Double.parseDouble(info.AppEearningSpeed); 
				txt_2.setText(df.format(d));
				txt_3.setText("元/分钟");
				accrued_txt.setText("这样的机会您怎能错过!");
				cat_icon.setVisibility(View.GONE);
			}		
		}else{				
			logined_lay2.setVisibility(View.GONE);
			logined_lay.setVisibility(View.GONE);
			no_login_lay.setVisibility(View.VISIBLE);			
		}
		
	}
	
	private void getData(){
		progressbar.setVisibility(View.VISIBLE);
	    IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.BATCH);
		HttpTaskFactory.getFactory().sendRequest(this, task);
	}
	
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			progressbar.setVisibility(View.GONE);
			if(msg.what == 1){
				swipeRefreshLayout.setVisibility(View.VISIBLE);			
				info = (TopInfo) msg.obj;		
				if(info==null){
					return;
				}
				initView();				
			}else if(msg.what < 0){
				
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
	public void onRefresh() {
		// TODO Auto-generated method stub
		//getData();	
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.BATCH);
		HttpTaskFactory.getFactory().sendRequest(this, task);
		new Handler().postDelayed(new Runnable() {
			public void run() {				
				swipeRefreshLayout.setRefreshing(false);				
			}
		}, 1000);
	}
	
	public String timeString (Double time){
        time = Math.ceil(time);
        String tpl = "";
        int t_d = 24 * 60 * 60;
        int day = (int) Math.floor(time / t_d);
        int day_time = (int) (time % t_d);
        int hours = (int) Math.floor(day_time / 3600);
        int hours_time = day_time % 3600;
        int minutes = (int) Math.floor(hours_time / 60);
        int seconds = hours_time % 60;
        if(day >= 100){
            tpl = (day_time > 0 ? day + 1 : day)+"天";
        } else if(day > 0){
            tpl = day+"天";
            if(hours_time > 0){ hours++; }
            if(hours>0){ tpl += hours+"小时"; }
        } else if(hours > 0){
            if(hours_time > 0){ hours++; }
            tpl = hours+"小时";
        } else if(minutes > 0){
            if(seconds > 0){ minutes++; }
            tpl = minutes+"分钟";
        } else {
            tpl = seconds+"秒";
        }
        return tpl;
    }

	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("主页");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("主页");
	}

}
