package com.widen.product.main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.info.ProductionsInfo2;
import com.widen.product.BaseActivity;
import com.widen.util.Util;
@EActivity(R.layout.product_detail)
public class ProductDetailAct extends BaseActivity implements IHttpCallback{

	@ViewById
	ProgressBar progressbar;
		
	@ViewById
	TextView Name;
	
	@ViewById
	TextView Yield;
	
	@ViewById
	TextView ExtraYield;
	
	
	@ViewById
	TextView Duration;
	
	@ViewById
	TextView Unit_txt;
	
	@ViewById
	TextView TotalNumber;
	
	
	@ViewById
	ImageView SellingStatus_icon;
	
	@ViewById
	TextView SellingStatus_txt;
	
	@ViewById
	SeekBar seekbar_blue;
	
	
	@ViewById
	SeekBar seekbar_red;
	
	
	@ViewById
	EditText number;
	
	
	@ViewById
	TextView income;
	
	
	
	@ViewById
	TextView ProductIdentifier;
	
	
	@ViewById
	TextView SellingStatus;
	
	
	@ViewById
	TextView Unit;
	
	
	@ViewById
	TextView MinNumber;
	
	@ViewById
	TextView MaxNumber;
	@ViewById
	TextView PubBegin;
	@ViewById
	TextView PubEnd;
/*	@ViewById
	TextView ValueDay;*/
	@ViewById
	TextView SettleDay;
	
	
/*	@ViewById
	TextView DueDate;*/
	
	@ViewById
	ImageView cat_left_icon;
	
	@ViewById
	ImageView flag_icon;
	
	@ViewById
	FrameLayout over_lay;
	
	@ViewById
	ScrollView root_lay;
	
	@ViewById
	ImageView yield_star_icon;
	
	
	@ViewById
	FrameLayout buy_number_lay;
	 
	private ProductionsInfo2 info;
	
	private int dayProgress;
	
	int i = 0;
	
	@Extra("Id")
	String Id;
	
	@Click
	public void howbuy(){
		startActivity(new Intent(ProductDetailAct.this,HelpAct_.class));
	}
	
	@Click
	public void back(){
		finish();
	}
	
	@AfterViews
	public void afterViews()
	{
		
		getData();
		number.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
					buy_number_lay.setBackgroundResource(R.drawable.buy_number_red_bg);
				}else{
					buy_number_lay.setBackgroundResource(R.drawable.buy_number_bg);
				}
			}
		});
		
	}
	
	private void initView(){
		Name.setText(info.Name);
		
		if(Util.isEmpty(info.ExtraYield)){
			Yield.setText(info.Yield);
			ExtraYield.setText("%");
		}else{
			Yield.setText(info.Yield);
			ExtraYield.setText("%+" + info.ExtraYield + "%");
		}
		
		if(info.IsRecommand.equals("0")){
			yield_star_icon.setVisibility(View.INVISIBLE);
		}else{
			yield_star_icon.setVisibility(View.VISIBLE);
		}
		
		Duration.setText(info.Duration + " 天");
		Unit_txt.setText(info.begin + "元起投");
		TotalNumber.setText(info.TotalNumber + "元");
		
		ProductIdentifier.setText(info.ProductIdentifier);
		switch (Integer.parseInt(info.SellingStatus)) {
		case 10:
			SellingStatus_icon.setImageResource(R.drawable.product_wait_icon);
			SellingStatus_txt.setVisibility(View.VISIBLE);
			SellingStatus_txt.setText("发售日期: " + info.PubBegin);
			seekbar_blue.setVisibility(View.GONE);
			seekbar_red.setVisibility(View.GONE);
			cat_left_icon.setVisibility(View.VISIBLE);
			cat_left_icon.setImageResource(R.drawable.cat_left_icon_10);
			flag_icon.setVisibility(View.VISIBLE);
			
			SellingStatus.setText("待发售");
			SellingStatus.setTextColor(getResources().getColor(R.color.green));
			
			
			break;
			
		case 20:
			
			SellingStatus_icon.setImageResource(R.drawable.product_sale_icon);
			SellingStatus_txt.setVisibility(View.VISIBLE);
			SellingStatus_txt.setText("已售出:" + info.FundedPercentage + "%");
			seekbar_blue.setVisibility(View.GONE);
			seekbar_red.setVisibility(View.VISIBLE);
			seekbar_red.setThumb(getResources().getDrawable(R.drawable.cat_progress_icon));
			seekbar_red.setThumbOffset(0);	
			
			
			cat_left_icon.setVisibility(View.GONE);

			flag_icon.setVisibility(View.VISIBLE);
			
			
			SellingStatus.setText("抢购");
			SellingStatus.setTextColor(getResources().getColor(R.color.red));
			if(!Util.isEmpty(info.FundedPercentage)){
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
			}
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
			SellingStatus.setText("已起息");
			SellingStatus.setTextColor(getResources().getColor(R.color.zi_grey));
			
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
			SellingStatus.setText("待还款");
			SellingStatus.setTextColor(getResources().getColor(R.color.zi_grey));
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
			SellingStatus_txt.setText("项目结束，本息已全部归还");
			seekbar_blue.setVisibility(View.GONE);
			seekbar_red.setVisibility(View.GONE);		
			cat_left_icon.setVisibility(View.GONE);
			flag_icon.setVisibility(View.GONE);
			over_lay.setVisibility(View.VISIBLE);
			
			SellingStatus.setText("已结束");
			SellingStatus.setTextColor(getResources().getColor(R.color.zi_grey));
			
			break;		
			
		}
		
		
		
		Unit.setText(info.Unit + "元");
		MinNumber.setText(info.MinNumber + "份");
		MaxNumber.setText(info.MaxNumber + "份");
		PubBegin.setText(info.PubBegin);		
		PubEnd.setText(info.PubEnd);
		SettleDay.setText(info.SettleDay);
		//DueDate.setText(info.DueDate);


		number.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
					try{
					int count_v = Integer.parseInt(s.toString().trim());
					double p = Double.parseDouble(info.Yield) + Double.parseDouble(info.ExtraYield);
					int duration = Integer.parseInt(info.Duration);
					double result = (count_v * p / 100 * duration)/360;		
					BigDecimal a = new BigDecimal(result);
					//income.setText(String.format("%.2f", result));
					income.setText(a.setScale(2, RoundingMode.DOWN) + " 元");
				}catch(Exception e){
					showToast("数据有错误");
				}
			}
		});
		
		number.setText("10000");
		
	}
	
	private void getData(){
		progressbar.setVisibility(View.VISIBLE);
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.PRODUCTS_DETAIL_NEW);
		//task.setParams(new String[]{(String) getIntent().getSerializableExtra("Id")});
		task.setParams(new String[]{Id});//93
		HttpTaskFactory.getFactory().sendRequest(this, task);
	}

	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			progressbar.setVisibility(View.GONE);
			root_lay.setVisibility(View.VISIBLE);
			if(msg.what == 1){
				info = (ProductionsInfo2) msg.obj;
				initView();
			}else if(msg.what == 2){
				seekbar_red.setProgress(i);
			}else if(msg.what == 3){
			
				seekbar_blue.setProgress(i);
			}
			
			else if(msg.what < 0){
				showToast(msg.obj.toString());
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
		MobclickAgent.onPageEnd("产品详情");
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		MobclickAgent.onPageStart("产品详情");
	    MobclickAgent.onResume(this);
	}
}
