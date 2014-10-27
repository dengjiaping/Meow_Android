package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.info.BaseListInfo;
import com.widen.http.info.ProductionsInfo2;
import com.widen.util.NetworkUtil;
import com.widen.util.Util;
import com.widen.util.ViewUtil;
import com.widen.widget.AppendList;
import com.widen.widget.AppendList.OnLoadingListener;

@EFragment(R.layout.product_list)
public class ProductFragment extends Fragment implements IHttpCallback{
	private BaseListInfo<ProductionsInfo2> infos;  
	@ViewById
	AppendList listview;
	private int der = -1;
	private boolean isResh;
	private int start = 1;
	private boolean hasFoot;
	private View footView;
	private MyAdapter adapt;
	@ViewById
	ProgressBar progressbar;	
	boolean flag = true;
	
	@ViewById
	TextView text_top;

	@AfterViews
	public void afterViews()
	{
		initView();
		getData();
	}
	
	
	
	public void refresh(){
		progressbar.setVisibility(View.VISIBLE);		
		infos.clear();
		if(adapt!=null){
			adapt.notifyDataSetChanged();
		}
		initListView();
		getData();
	}
	
	

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();	
		
		
		
		MobclickAgent.onPageStart("产品列表");
	}
	
	

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("产品列表");
	}



	

	private void initView(){
		infos = new BaseListInfo<ProductionsInfo2>();		
		RotateAnimation animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		RotateAnimation reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);
		listview.setTopAniamtionInfo(animation, reverseAnimation,
				R.id.image_top);
		listview.setBottomAnimationInfo(reverseAnimation, animation,
				R.id.image_bottom);
		
		listview.setLoadingListener(new OnLoadingListener() {
			TextView text_top = (TextView) getActivity().findViewById(
					R.id.text_top);;

			@Override
			public void prepareLoadding(View parentView, int derection) {   // TODO
																			// Auto-generated
																			// method
				isResh = true;															// stub
				
				
				if(!NetworkUtil.isNetworkAvailable(getActivity())){
					isResh = false;
					listview.compateRefresh(der);
					text_top.setText("网络不稳定...");
					return;
				}
				text_top.setText("释放立即刷新...");
				initListView();				

			}

			@Override
			public void cancel(View parentView, int derection) {
				// TODO Auto-generated method stub
			}

			@Override
			public void Loading(View parentView, int derection, int dataIndex) {
				// TODO Auto-generated method stub
				der = derection;
				text_top.setText("刷新中...");
				
				getData();

			}

			@Override
			public void LoadOver(View parentView, int derection) {
				// TODO Auto-generated method stub
				
			}
		});
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {				
				if (arg2 - 2 == infos.size()) {
					progressbar.setVisibility(View.VISIBLE);									
					start = start + 1;
					getData();
				}else{
					String Id = infos.get(arg2 - 1).Id;
					Intent intent = new Intent(getActivity(),ProductDetailAct_.class);
					intent.putExtra("Id", Id);
					startActivity(intent);
				}
				
			}
		});
		
		footView = ViewUtil.getFooterView(getActivity());		

	}
	
	
	

	private void initListView(){		
		if(hasFoot){
			listview.removeFooterView(footView);
			hasFoot = false;
		}
		start = 1;				
	}
	
	private void getData(){
		if(flag){
			progressbar.setVisibility(View.VISIBLE);
			flag = false;
		}
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.PRODUCTS_LIST_NEW);
		task.setParams(new String[]{start + ""});
		HttpTaskFactory.getFactory().sendRequest(this, task);
	}

	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {			
			progressbar.setVisibility(View.GONE);			
			if(msg.what == 1){				
				BaseListInfo<ProductionsInfo2> list = (BaseListInfo<ProductionsInfo2>) msg.obj;		
				if (isResh) {
					listview.compateRefresh(der);
					infos.clear();
					isResh = false;
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

			}else if(msg.what < 0){			
				Util.showToast(msg.obj.toString(), getActivity());
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


	//@EBean / @EViewGroup 不可用于内部类
	public class MyAdapter extends BaseAdapter{

		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infos.size();
		}

		@Override
		public ProductionsInfo2 getItem(int position) {
			// TODO Auto-generated method stub
			return infos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {			
			ProductItemView itemView = null;			
			if(convertView == null){
				itemView = ProductItemView_.build(getActivity());
			}else{
				itemView = (ProductItemView) convertView;
			}
			itemView.bind(getItem(position));			
			return itemView;
		}

	}
	
	

}



