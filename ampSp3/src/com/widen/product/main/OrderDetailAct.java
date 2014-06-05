package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.info.OrderInfo;
import com.widen.product.BaseActivity;
import com.widen.util.Util;
@EActivity(R.layout.order_detail)
public class OrderDetailAct extends BaseActivity implements IHttpCallback{

	@Extra("Id")
	String Id;
	
	@ViewById
	TextView Name;
	
	@ViewById
	TextView CreatedAt;
	
	@ViewById
	TextView Price;
	
	
	@ViewById
	TextView ExpectedPrice;
	
	@ViewById
	TextView OrderNo;
	
	@ViewById
	FrameLayout no_use_item_lay;
	
	@ViewById
	FrameLayout used_item_lay;
	
	@ViewById
	TextView ExtraInterest;
	
	@ViewById
	TextView ProductIdentifier;
	
	@ViewById
	TextView SellingStatus;
	
	
	@ViewById
	TextView Yield;
	
	@ViewById
	TextView Duration;
	
	
	@ViewById
	TextView TotalNumber;
	
	@ViewById
	TextView SettleDay;
	
	@ViewById
	TextView BankName;
	
	OrderInfo info;
	@ViewById
	ProgressBar progressbar;
	
	@ViewById
	ScrollView root_lay;
	
	@ViewById
	TextView item_txt;
	
	@Click
	public void back(){
		finish();
		
	}
	
	public static final String FLAG = "OrderDetailAct";
	@AfterViews
	public void afterViews()
	{	
		getData();
	}
	
	
	private void getData(){
		progressbar.setVisibility(View.VISIBLE);
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.ORDERINFO);
		task.setParams(new String[]{Id});		
		HttpTaskFactory.getFactory().sendRequest(this, task);
	}
	
	@Click
	public void no_use_item_lay(){
		Intent intent = new  Intent(OrderDetailAct.this,MyItemAct_.class);
		intent.putExtra("flag", FLAG);
		intent.putExtra("orderId", info.OrderId);
		intent.putExtra("orderTitle", info.OrderNo);	
		startActivityForResult(intent,1);
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 1){
			getData();
		}
	}


	private void initView(){
		
		Name.setText(info.Name);
		CreatedAt.setText(info.CreatedAt);
		Price.setText(info.Price);
		ExpectedPrice.setText("预期收益: " + info.ExpectedPrice + "元");
		OrderNo.setText("订单号: " + info.OrderNo);
		
		if(info.UseableItemCount.equals("0")){
			no_use_item_lay.setVisibility(View.GONE);
			used_item_lay.setVisibility(View.GONE);
		}else{
			if(Util.isEmpty(info.ExtraInterest)){
				no_use_item_lay.setVisibility(View.VISIBLE);
				used_item_lay.setVisibility(View.GONE);
			}else{
				no_use_item_lay.setVisibility(View.GONE);
				used_item_lay.setVisibility(View.VISIBLE);
			}
		
		}
		
		java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");  
		double   d=Double.parseDouble(info.ExtraInterest); 
		ExtraInterest.setText("再增收" + df.format(d)+ "元");
		item_txt.setText("已使用" + info.ItemTitle);
		ProductIdentifier.setText(info.ProductIdentifier);
		
		if(info.SellingStatus.equals("20")){
			SellingStatus.setTextColor(getResources().getColor(R.color.red));
		}else if(info.SellingStatus.equals("10")){
			SellingStatus.setTextColor(getResources().getColor(R.color.green));
		}else{
			SellingStatus.setTextColor(getResources().getColor(R.color.grey));
		}
		
		SellingStatus.setText(Util.sellingStatusMap.get(info.SellingStatus));
		
		
		Yield.setText(info.Yield + "%");
		
		Duration.setText(info.Duration + "天");
		
		
		 TotalNumber.setText(info.TotalNumber + "元");
		
		 SettleDay.setText(info.SettleDay);
		
		BankName.setText(info.BankName);
	}

	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			progressbar.setVisibility(View.GONE);
			root_lay.setVisibility(View.VISIBLE);
			
			if(msg.what == 1){
				info = (OrderInfo) msg.obj;							
				initView();
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
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("订单详情");
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		MobclickAgent.onPageStart("订单详情");
	    MobclickAgent.onResume(this);
	}
}
