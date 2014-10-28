package com.widen.product.main;

import java.text.ParseException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.model.RefundInfo;
import com.widen.product.BaseActivity;
import com.widen.util.Util;
import com.widen.widget.MyListView;
import com.widen.widget.MySeekBar;
@EActivity(R.layout.timeline_product_detail)
public class TimeLineProductDetailAct extends BaseActivity implements IHttpCallback{
	
	@ViewById
	TextView Name;
	
	@ViewById
	TextView Yield;
	
	@ViewById
	TextView ExtraYield;
	
	@ViewById
	ImageView yield_star_icon;
	
	@ViewById
	TextView Duration;
	
	@ViewById
	TextView Unit_txt;
	
	@ViewById
	TextView TotalNumber;
	
	@ViewById
	TextView SellingStatus_txt;
	
	@ViewById
	TextView Price;
	
	@ViewById
	TextView ExpectedPrice;
	
	@ViewById
	TextView ExtraInterest;
	
	@ViewById
	MyListView listview;
	
	@Extra("Id")
	String Id;
	
	@ViewById
	ImageView SellingStatus_icon;
	
	
	@ViewById
	MySeekBar seekbar_blue;
	
	@ViewById
	MySeekBar seekbar_red;
	
	@ViewById
	ImageView cat_left_icon;
	
	@ViewById
	ImageView flag_icon;
	
	@ViewById
	FrameLayout over_lay;
	
	@ViewById
	FrameLayout item_expected_lay;
	
	@ViewById
	ScrollView root_lay;
	
	
	@ViewById
	ProgressBar progressbar;
	
	
	
	RefundInfo info;
	int i = 0;
	
	private int dayProgress;
	@AfterViews
	public void afterViews()
	{	
		getData();
	}
	
	private void initView(){
		Name.setText(info.Name);
		Yield.setText(info.Yield + "%");
		if(info.IsRecommand.equals("1")){
			yield_star_icon.setVisibility(View.VISIBLE);		
		}else{			
			yield_star_icon.setVisibility(View.GONE);
		}
		
		
		String tExtraYield = info.ExtraYield.replace("0", "").replace(".", "");
		if(!TextUtils.isEmpty(tExtraYield)){
			ExtraYield.setText("%+" + info.ExtraYield + "%");
		}
		
		Duration.setText(info.Duration + "天");
		Unit_txt.setText(info.Unit + "元起投");
		TotalNumber.setText(info.TotalNumber + "元");
		
		switch (Integer.parseInt(info.SellingStatus)) {
			case 10:
				SellingStatus_icon.setImageResource(R.drawable.product_wait_icon);
				SellingStatus_txt.setVisibility(View.GONE);
				seekbar_blue.setVisibility(View.GONE);
				seekbar_red.setVisibility(View.GONE);
				cat_left_icon.setVisibility(View.VISIBLE);
				flag_icon.setVisibility(View.VISIBLE);
				break;
				
				
			case 20:
				
				SellingStatus_icon.setImageResource(R.drawable.product_sale_icon);
				SellingStatus_txt.setVisibility(View.VISIBLE);
				SellingStatus_txt.setText("已售出:" + info.FundedPercentage + "%");
				seekbar_blue.setVisibility(View.GONE);
				seekbar_red.setVisibility(View.VISIBLE);
				
				cat_left_icon.setVisibility(View.GONE);
				flag_icon.setVisibility(View.VISIBLE);

				new Thread()
				{
					public void run()
					{					
							 while(i<=Integer.parseInt(info.FundedPercentage))
							{
								handler.sendMessage(handler.obtainMessage(2, i));
								i++;
								try {
									sleep(10);
								} catch (InterruptedException e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
							}
					}
				}.start();
				
				
				break;	
				
			case 30:
				SellingStatus_icon.setImageResource(R.drawable.product_finish_icon);
				SellingStatus_txt.setVisibility(View.VISIBLE);
				//SellingStatus_txt.setText("请等待起息: " + info.ValueDay);
				SellingStatus_txt.setText("购买成功，即刻起息");
				seekbar_blue.setVisibility(View.GONE);
				seekbar_red.setVisibility(View.VISIBLE);
				seekbar_red.setProgress(100);
				seekbar_red.setThumb(getResources().getDrawable(R.drawable.cat_finish_icon));
				seekbar_red.setThumbOffset(0);			
				cat_left_icon.setVisibility(View.GONE);
				flag_icon.setVisibility(View.VISIBLE);
				
				break;
				
			case 40:
			case 50:
				
				
				
				dayProgress = (int) (((double)getDaySub(info.PubBegin,info.Now )/getDaySub(info.PubBegin,info.DueDate )) * 100);

				SellingStatus_icon.setImageResource(R.drawable.product_back_icon);
				SellingStatus_txt.setVisibility(View.VISIBLE);
				SellingStatus_txt.setText("距还款还有  " + (int)getDaySub(info.Now,info.DueDate ) + "天");
				seekbar_blue.setVisibility(View.VISIBLE);
				seekbar_red.setVisibility(View.GONE);			
				cat_left_icon.setVisibility(View.GONE);
				flag_icon.setVisibility(View.VISIBLE);
				flag_icon.setImageResource(R.drawable.cat_left_icon_10);
				new Thread()
				{
					public void run()
					{					

							while(i<=dayProgress)
							{						
								handler.sendMessage(handler.obtainMessage(3, i));
								i++;
								try {
									sleep(10);
								} catch (InterruptedException e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
							}
					}
				}.start();
				
				break;		
				
			case 60:
				
				SellingStatus_icon.setImageResource(R.drawable.product_over_icon);
				SellingStatus_txt.setVisibility(View.VISIBLE);
				SellingStatus_txt.setText("项目结束,本息已全部归还");
				seekbar_blue.setVisibility(View.GONE);
				seekbar_red.setVisibility(View.GONE);		
				cat_left_icon.setVisibility(View.GONE);
				flag_icon.setVisibility(View.GONE);
				over_lay.setVisibility(View.VISIBLE);
				
			
				
				break;		

		}	
		Price.setText(allPrice()+ " 元");
		ExpectedPrice.setText(allExpectedPrice() + " 元");
		
		
		if(Util.isEmpty(allExtraInterest())){
			item_expected_lay.setVisibility(View.VISIBLE);
			float count = Float.parseFloat(allPrice()) + Float.parseFloat(allExpectedPrice());//Integer.parseInt(allPrice() + allExpectedPrice());
			ExtraInterest.setText("本息共" + count + "元");
			
		}else{
			item_expected_lay.setVisibility(View.VISIBLE);
			
			float count = Integer.parseInt(allPrice()) + Float.parseFloat((allExpectedPrice()));
			
			ExtraInterest.setText("本息共" + count + "元，道具再增收" + allExtraInterest() + "元");
		}
		
		listview.setAdapter(new MyAdapt());
		
		
	}
	
	@Click
	public void back(){
		finish();
	}
	
	private class MyAdapt extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return info.infos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return info.infos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				convertView = LayoutInflater.from(TimeLineProductDetailAct.this).inflate(R.layout.timeline_product_item, null);
			}
			String[] creatat = info.infos.get(position).CreatedAt.split("T");
			((TextView)convertView.findViewById(R.id.date)).setText(creatat[0]);
			((TextView)convertView.findViewById(R.id.time)).setText(creatat[1]);
			
			((TextView)convertView.findViewById(R.id.OrderNo)).setText(info.infos.get(position).OrderNo);
			
			((TextView)convertView.findViewById(R.id.Price)).setText((int)Float.parseFloat(info.infos.get(position).Price) + "");
			
			((TextView)convertView.findViewById(R.id.ExpectedPrice)).setText(info.infos.get(position).ExpectedPrice);
			
			return convertView;
		}
		
	}
	
	private String allExtraInterest(){
		float conut = 0;
		for(int i =0; i< info.infos.size();i++){
			conut =conut +  Float.parseFloat(info.infos.get(i).ExtraInterest);
		}
		
		java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");  
		double   d=Double.parseDouble(conut + ""); 			
		return df.format(d);
	}
	
	private String allExpectedPrice(){
		float conut = 0;
		for(int i =0; i< info.infos.size();i++){

			conut =conut + Float.parseFloat(info.infos.get(i).ExpectedPrice);
		}
		return conut + "";
	}
	
	private String allPrice(){
		
		
		int conut = 0;	
		for(int i =0; i< info.infos.size();i++){
			conut = conut +  (int)Float.parseFloat(info.infos.get(i).Price);
		}	
		return conut + "";
	}
	
	private void getData(){
		progressbar.setVisibility(View.VISIBLE);
		
//		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.TIMELINE_REFUNDINFO);
//		//task.setParams(new String[]{(String) getIntent().getSerializableExtra("Id")});
//		task.setParams(new String[]{Id});
//		HttpTaskFactory.getFactory().sendRequest(this, task);
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			progressbar.setVisibility(View.GONE);
			if(msg.what == 1){
				root_lay.setVisibility(View.VISIBLE);
				info = (RefundInfo) msg.obj;
				initView();
			}else if(msg.what == 2){
				seekbar_red.setProgress(i);
			}else if(msg.what == 3){
			
				seekbar_blue.setProgress(i);
			}else if(msg.what < 0){
				
			}
		}	
	};
	
	public long getDaySub(String beginDateStr,String endDateStr)
    {
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate;
        java.util.Date endDate;
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);   
				day=(int) ((endDate.getTime()-beginDate.getTime())/(24*60*60*1000));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
       
        return day;
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
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("时间轴产品详情");
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		MobclickAgent.onPageStart("时间轴产品详情");
	    MobclickAgent.onResume(this);
	}
}
