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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.widen.widget.URLImageView;
@EActivity(R.layout.my_item)
public class MyItemAct extends BaseActivity implements IHttpCallback{

	@ViewById
	ListView listview;
	
	@ViewById
	LinearLayout no_data_lay;
	
	@ViewById
	ProgressBar progressbar;
	

	private int start = 1;
	BaseListInfo<ItemInfo> infos;
	
	private boolean hasFoot;
	private View footView;
	private MyAdapter adapt;
	
	@Extra("flag")
	String flag;
	
	
	@Extra("orderId")
	String orderId;
	
	
	@Extra("orderTitle")
	String orderTitle;
	
	private ItemInfo itemInfo;
	
	public static final String FLAG = "MyItemAct";
	@AfterViews
	public void afterViews(){	


		
		if(!TextUtils.isEmpty(flag)){
			if(flag.equals(OrderListAct.FLAG)){				
				getDataForOrder();
			}else if(flag.equals(MoreFragment.FLAG)){
				getDataForMyItem();
			}else if(flag.equals(OrderDetailAct.FLAG)){
				getDataForOrder();
			}
		}

		
		infos = new BaseListInfo<ItemInfo>();
		footView = ViewUtil.getFooterView(this);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if (arg2 == infos.size()) {								
					start = start + 1;
					if(flag.equals(OrderListAct.FLAG)){				
						getDataForOrder();
					}else if(flag.equals(MoreFragment.FLAG)){
						getDataForMyItem();
					}else if(flag.equals(OrderDetailAct.FLAG)){
						getDataForOrder();
					}
				}
				
				/*if (arg2 == infos.size()) {
					progressbar.setVisibility(View.VISIBLE);									
					start = start + 1;
					getData();
				}else{
					String Id = infos.get(arg2 - 1).Id;
					Intent intent = new Intent(getActivity(),ProductDetailAct_.class);
					intent.putExtra("Id", Id);
					startActivity(intent);
				}*/
			}
		});
		
		
		
		
		
		
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
	
	private void getUseItemData(String ItemId , String OrderId){
		progressbar.setVisibility(View.VISIBLE);
//		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.ITEMS_CONSUMEBYORDER);
//		//task.setParams(new String[]{(String) getIntent().getSerializableExtra("Id")});
//		task.setParams(new String[]{ItemId , OrderId});
//		HttpTaskFactory.getFactory().sendRequest(new IHttpCallback() {
//			
//			@Override
//			public void onGetData(Object data) {
//				// TODO Auto-generated method stub
//				handler.sendMessage(handler.obtainMessage(2, data));
//			}
//			
//			@Override
//			public void onError(Object reason) {
//				// TODO Auto-generated method stub
//				handler.sendMessage(handler.obtainMessage(-2, reason));
//			}
//		}, task);
	}
	
	@Click
	void back(){
		finish();
	}
	
	private void getDataForMyItem(){
		progressbar.setVisibility(View.VISIBLE);
//		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.ITEMS);
//		//task.setParams(new String[]{(String) getIntent().getSerializableExtra("Id")});
//		task.setParams(new String[]{start + ""});
//		HttpTaskFactory.getFactory().sendRequest(this, task);
	}
	
	
	private void getDataForOrder(){		
		progressbar.setVisibility(View.VISIBLE);
//		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.ITEMS_USEABLE);
//		//task.setParams(new String[]{(String) getIntent().getSerializableExtra("Id")});
//		task.setParams(new String[]{orderId,start + ""});
//		HttpTaskFactory.getFactory().sendRequest(this, task);
	}
	
	
	private void getDeleteItem(String id){
		progressbar.setVisibility(View.VISIBLE);
//		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.ITEM_REMOVE);
//		//task.setParams(new String[]{(String) getIntent().getSerializableExtra("Id")});
//		task.setParams(new String[]{id});
//		HttpTaskFactory.getFactory().sendRequest(new IHttpCallback() {
//			
//			@Override
//			public void onGetData(Object data) {
//				// TODO Auto-generated method stub
//				handler.sendEmptyMessage(5);
//			}
//			
//			@Override
//			public void onError(Object reason) {
//				// TODO Auto-generated method stub
//				handler.sendEmptyMessage(-5);
//			}
//		}, task);
	}
	
	private Dialog dialog;
	
	public class MyAdapter extends BaseAdapter{

		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infos.size();
		}

		@Override
		public ItemInfo getItem(int position) {
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
				convertView = LayoutInflater.from(MyItemAct.this).inflate(R.layout.item_item, null);
			}
			URLImageView img = (URLImageView) convertView.findViewById(R.id.img);
			img.setImageURL(infos.get(position).ImageSrc);
			Button use = (Button) convertView.findViewById(R.id.use);
			Button delete = (Button) convertView.findViewById(R.id.delete);
			TextView name = (TextView) convertView.findViewById(R.id.name);
			TextView InterestDescription = (TextView) convertView.findViewById(R.id.InterestDescription);
			TextView HasExpired = (TextView) convertView.findViewById(R.id.HasExpired);
			TextView Expires = (TextView) convertView.findViewById(R.id.Expires);
			
			
			if(infos.get(position).HasExpired){
				use.setVisibility(View.GONE);
				delete.setVisibility(View.VISIBLE);
				HasExpired.setTextColor(getResources().getColor(R.color.red));
				HasExpired.setText("已过期:");
				delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						getDeleteItem(infos.get(position).Id);
					}
				});
				
			}else{
				use.setVisibility(View.VISIBLE);
				delete.setVisibility(View.GONE);
				HasExpired.setTextColor(getResources().getColor(R.color.grey));
				HasExpired.setText("有效期:");
				use.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
				
						if(flag.equals(OrderListAct.FLAG) || flag.equals(OrderDetailAct.FLAG)){											
							dialog = new Dialog(MyItemAct.this);
							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.item_order_dialog);
							final TextView txt = (TextView) dialog
									.findViewById(R.id.txt);
							txt.setText("将" + infos.get(position).Title + "使用在订单" + orderTitle + "上?");
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
									getUseItemData(infos.get(position).Id, orderId);
									dialog.cancel();
									
								}
							});
							dialog.setCancelable(false);
							dialog.show();							
							
						}else if(flag.equals(MoreFragment.FLAG)){							
							
							getForItemData(infos.get(position).Id,"1");
							itemInfo = infos.get(position);
						}
											
					}
				});
			}
			
			name.setText(infos.get(position).Title);
			InterestDescription.setText(infos.get(position).Description);
			Expires.setText(infos.get(position).Expires);
		
			return convertView;
		}

	}
	
	private void getForItemData(String ItemId,String PageIndex){
//		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.ORDERS_FORITEM);
//		task.setParams(new String[]{ItemId,PageIndex});
//		HttpTaskFactory.getFactory().sendRequest(new IHttpCallback() {
//			
//			@Override
//			public void onGetData(Object data) {
//				// TODO Auto-generated method stub
//				handler.sendMessage(handler.obtainMessage(3, data));
//			}
//			
//			@Override
//			public void onError(Object reason) {
//				// TODO Auto-generated method stub
//				handler.sendMessage(handler.obtainMessage(-3, reason));
//			}
//		}, task);
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			progressbar.setVisibility(View.GONE);
			if(msg.what == 1){
				BaseListInfo<OrderInfo> list = (BaseListInfo<OrderInfo>) msg.obj;	
				
				if(infos.size() == 0 && list.size() == 0){
					listview.setVisibility(View.GONE);
					no_data_lay.setVisibility(View.VISIBLE);
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
				
				
			}else if(msg.what == 2){
				MobclickAgent.onEvent(MyItemAct.this, "道具使用");
				
				dialog = new Dialog(MyItemAct.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.item_use_success_dialog);
				final Button button = (Button) dialog
						.findViewById(R.id.btn);
				button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						/*initListView();
						if(flag.equals(OrderListAct.FLAG)){				
							getDataForOrder();
						}else if(flag.equals(MoreFragment.FLAG)){
							getDataForMyItem();
						}*/
						dialog.cancel();
						setResult(1);
						MyItemAct.this.finish();
					}
				});
				dialog.setCancelable(false);
				dialog.show();
				
				
			}else if(msg.what == -2){
				
			}else if(msg.what == 3){
				
				BaseListInfo<OrderInfo> infos = (BaseListInfo<OrderInfo>) msg.obj;
				if(infos.size() == 0){
					dialog = new Dialog(MyItemAct.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.no_item_dailog);
					final Button button = (Button) dialog
							.findViewById(R.id.btn);
					
					button.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
					
					dialog.setCancelable(false);
					dialog.show();				

				}else{
					Intent intent = new Intent(MyItemAct.this, OrderListAct_.class);
					intent.putExtra("info", infos);
					intent.putExtra("flag", MyItemAct.FLAG);
					intent.putExtra("itemInfo", itemInfo);
					startActivityForResult(intent,100);
				}
				
				
			}else if(msg.what == 5){
				showToast("删除成功");
				initListView();
				if(!TextUtils.isEmpty(flag)){
					if(flag.equals(OrderListAct.FLAG)){				
						getDataForOrder();
					}else if(flag.equals(MoreFragment.FLAG)){					
						getDataForMyItem();
					}else if(flag.equals(OrderDetailAct.FLAG)){
						getDataForOrder();
					}
				}
			}
			
			else if(msg.what < 0){
				
			}
		}
		
	};
	
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == 100){
			initListView();
			if(!TextUtils.isEmpty(flag)){
				if(flag.equals(OrderListAct.FLAG)){				
					getDataForOrder();
				}else if(flag.equals(MoreFragment.FLAG)){
					getDataForMyItem();
				}else if(flag.equals(OrderDetailAct.FLAG)){
					getDataForOrder();
				}
			}
		}
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
		MobclickAgent.onPageEnd("道具列表");
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		MobclickAgent.onPageStart("道具列表");
	    MobclickAgent.onResume(this);
	}
	
	
}
