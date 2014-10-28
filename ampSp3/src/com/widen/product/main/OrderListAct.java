package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.model.BaseListInfo;
import com.widen.http.model.ItemInfo;
import com.widen.http.model.OrderInfo;
import com.widen.product.BaseActivity;
import com.widen.util.ViewUtil;
@EActivity(R.layout.order_list)
public class OrderListAct extends BaseActivity implements IHttpCallback{
	
	@ViewById
	ListView listview;
	
	@ViewById
	LinearLayout no_data_lay;
	private int start = 1;
	
	
	private boolean hasFoot;
	private View footView;
	private MyAdapter adapt;
	
	@Extra("flag")
	String flag;
	@Extra("itemInfo")
	ItemInfo itemInfo;

	@Extra("info")
	BaseListInfo<OrderInfo> infos;
	
	@ViewById
	ProgressBar progressbar;
	
	
	
	public static final String FLAG = "OrderListAct";
	
	@Click
	void back(){
		finish();
	}
	
	@AfterViews
	public void afterViews(){	
		if(flag.equals(MoreFragment.FLAG)){
			infos = new BaseListInfo<OrderInfo>();
			getData();
			footView = ViewUtil.getFooterView(this);
			
			
		}else if(flag.equals(MyItemAct.FLAG)){
			adapt = new MyAdapter();
			listview.setAdapter(adapt);
		}
		
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {	
				if(flag.equals(MoreFragment.FLAG)){
					if (arg2 == infos.size()) {									
						start = start + 1;
						getData();
					}else{			
						
						MobclickAgent.onEvent(OrderListAct.this, "通过订单列表查看订单详情");
						String Id = infos.get(arg2).OrderIdentifier;										
						Intent intent = new Intent(OrderListAct.this,OrderDetailAct_.class);
						intent.putExtra("Id", Id);
						startActivity(intent);
					}
				}
			}
		});
	}
	
	private void getData(){
		progressbar.setVisibility(View.VISIBLE);
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.ORDERS_LIST_FOR_BA);
		task.setParams(new String[]{start + ""});
		HttpTaskFactory.getFactory().sendRequest(this, task);
	}
	
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 1){
			initListView();
			getData();
		}
	}




	private Dialog dialog;
	
	public class MyAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infos.size();
		}

		@Override
		public OrderInfo getItem(int position) {
			// TODO Auto-generated method stub
			return infos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {			

			if(convertView == null){
				convertView = LayoutInflater.from(OrderListAct.this).inflate(R.layout.order_item, null);
			}
			
			
			TextView OrderIdentifier = (TextView) convertView.findViewById(R.id.OrderIdentifier);
			
			
			Button use = (Button) convertView.findViewById(R.id.use);
			
			
			TextView used = (TextView) convertView.findViewById(R.id.used);
			
			
			TextView Principal = (TextView) convertView.findViewById(R.id.Principal);
			
			
			TextView Interest = (TextView) convertView.findViewById(R.id.Interest);
			
			
			TextView OrderTime = (TextView) convertView.findViewById(R.id.OrderTime);
			
			
			ImageView navi_arrow = (ImageView) convertView.findViewById(R.id.navi_arrow);
			Principal.setText(infos.get(position).Principal + "元");
			Interest.setText(infos.get(position).Interest + "元");
			OrderTime.setText(infos.get(position).OrderTime.replace("T", " "));
			OrderIdentifier.setText(infos.get(position).OrderIdentifier);
			
			if(flag.equals(MoreFragment.FLAG)){
				navi_arrow.setVisibility(View.VISIBLE);
				if(infos.get(position).UseableItemCount.equals("-1")){
					used.setVisibility(View.VISIBLE);
					use.setVisibility(View.GONE);
				}else if(infos.get(position).UseableItemCount.equals("0")){
					used.setVisibility(View.GONE);
					use.setVisibility(View.GONE);
				}else{
					used.setVisibility(View.GONE);
					use.setVisibility(View.VISIBLE);
				}
				
				use.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent  = new Intent(OrderListAct.this, MyItemAct_.class);
						intent.putExtra("orderId", infos.get(position).OrderIdentifier);
						intent.putExtra("orderTitle", infos.get(position).OrderIdentifier);
						intent.putExtra("flag", OrderListAct.FLAG);
						startActivityForResult(intent,1);
					}
				});
				
			}else if(flag.equals(MyItemAct.FLAG)){
				navi_arrow.setVisibility(View.GONE);
				used.setVisibility(View.GONE);
				use.setVisibility(View.VISIBLE);
				use.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						dialog = new Dialog(OrderListAct.this);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.item_order_dialog);
						final TextView txt = (TextView) dialog
								.findViewById(R.id.txt);
						txt.setText("将" + itemInfo.Title + "使用在订单" + infos.get(position).OrderIdentifier + "上?");
						final Button cancel = (Button) dialog.findViewById(R.id.cancel);
						cancel.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						});
						
						
						final Button ok = (Button) dialog.findViewById(R.id.ok);
						ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								getUseItemData(itemInfo.Id, infos.get(position).OrderIdentifier);
								dialog.cancel();
							}
						});
						dialog.setCancelable(false);
						dialog.show();
						
				
						
					}
				});
				
			}
			return convertView;
		}

	}

	private void getUseItemData(String ItemId , String OrderId){
	progressbar.setVisibility(View.VISIBLE);
//	IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.ITEMS_CONSUMEBYORDER);
//	task.setParams(new String[]{ItemId , OrderId});
//	HttpTaskFactory.getFactory().sendRequest(new IHttpCallback() {
//		
//		@Override
//		public void onGetData(Object data) {
//			// TODO Auto-generated method stub
//			handler.sendMessage(handler.obtainMessage(3, data));
//		}
//		
//		@Override
//		public void onError(Object reason) {
//			// TODO Auto-generated method stub
//			handler.sendMessage(handler.obtainMessage(-3, reason));
//		}
//	}, task);
}


	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			progressbar.setVisibility(View.GONE);
			if(msg.what == 1){
				
				BaseListInfo<OrderInfo> list = (BaseListInfo<OrderInfo>) msg.obj;		
				if(list.size() == 0 && infos.size() == 0){
					no_data_lay.setVisibility(View.VISIBLE);
					listview.setVisibility(View.GONE);
					return;
				}
				
				if (infos.size() > 0 ) {
					infos.addAll(list);
					adapt.notifyDataSetChanged();
					if (!infos.hasMore) {
						if (hasFoot) {
							listview.removeFooterView(footView);
							hasFoot = false;
						}
					}
				}else{
					if (list.size() > 0) {
						infos.addAll(list);
						adapt = new MyAdapter();
						if (!hasFoot) {
							listview.addFooterView(footView);
							hasFoot = true;
						}

						listview.setAdapter(adapt);
					}else{
						if(adapt!=null){
							adapt.notifyDataSetChanged();
						}
					}
				}
				if (infos.size() < 20 && hasFoot) {
					listview.removeFooterView(footView);
					hasFoot = false;
				}
				
				
			}else if(msg.what == 3){
				dialog = new Dialog(OrderListAct.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.item_use_success_dialog);
				final Button button = (Button) dialog
						.findViewById(R.id.btn);
				final TextView success_txt = (TextView) dialog
						.findViewById(R.id.success_txt);
				success_txt.setText("带来了" + msg.obj.toString() + "元的额外收益哦!");
				
				button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						/*initListView();
						getForItemData(itemInfo.Id,"1");*/
						dialog.cancel();
						setResult(100);
						OrderListAct.this.finish();
					}
				});
				dialog.setCancelable(false);
				dialog.show();
			}
			
			
			
			else if(msg.what < 0){
				
			}
		}
		
	};
	
	private void getForItemData(String ItemId,String PageIndex){
//		progressbar.setVisibility(View.VISIBLE);
//		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.ORDERS_FORITEM);
//		task.setParams(new String[]{ItemId,PageIndex});
//		HttpTaskFactory.getFactory().sendRequest(new IHttpCallback() {
//			
//			@Override
//			public void onGetData(Object data) {
//				// TODO Auto-generated method stub
//				handler.sendMessage(handler.obtainMessage(1, data));
//			}
//			
//			@Override
//			public void onError(Object reason) {
//				// TODO Auto-generated method stub
//				handler.sendMessage(handler.obtainMessage(-1, reason));
//			}
//		}, task);
	}
	
	private void initListView(){		
		if(hasFoot){
			listview.removeFooterView(footView);
			hasFoot = false;
		}
		start = 1;		
		infos.clear();
		adapt.notifyDataSetChanged();
		
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
		MobclickAgent.onPageEnd("订单列表");
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		MobclickAgent.onPageStart("订单列表");
	    MobclickAgent.onResume(this);
	}
}
