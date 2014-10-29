package com.widen.product.main;

import java.util.Date;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.application.MyApplication;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.model.BaseListInfo;
import com.widen.http.model.TimeLineInfo;
import com.widen.widget.XListView;
import com.widen.widget.XListView.IXListViewListener;

@EFragment(R.layout.time_line)
public class TimeLineFragment extends Fragment implements IHttpCallback,
		IXListViewListener {

	@ViewById
	LinearLayout no_login_lay;

	@ViewById
	FrameLayout login_lay;

	BaseListInfo<TimeLineInfo> infos = new BaseListInfo<TimeLineInfo>();

	@ViewById
	XListView xListView;

	@ViewById
	Button today;

	@ViewById
	ImageView line_bg;

	private boolean isGettingData;

	@ViewById
	ProgressBar progressbar;

	private MyAdapter adapter;

	private int i;

	private int start;
	private int end;

	public static String timestamp;

	private String top = "";
	private String bottom = "";

	public static boolean needRefresh;
	public static boolean dataEnd;

	public static boolean isLogin;

	public static final String FLAG = "TimeLineFragment";

	public static int item_position = 0;

	public void refreshData() {
		reSetListview();
		getData();
	}

	public void loginRefresh() {
		if (MyApplication.appContext.isLogin()) {
			isLogin = true;
			refreshData();
			no_login_lay.setVisibility(View.GONE);
			line_bg.setVisibility(View.GONE);
			login_lay.setVisibility(View.VISIBLE);
		} else {
			isLogin = false;
			no_login_lay.setVisibility(View.VISIBLE);
			login_lay.setVisibility(View.GONE);
			line_bg.setVisibility(View.VISIBLE);
		}
	}

	@Click
	public void today() {
		xListView.setSelection(item_position);
	}

	@AfterViews
	public void afterViews() {
		if (MyApplication.appContext.isLogin()) {
			isLogin = true;
			no_login_lay.setVisibility(View.GONE);
			login_lay.setVisibility(View.VISIBLE);
			line_bg.setVisibility(View.GONE);
			getData();
		} else {
			isLogin = false;
			no_login_lay.setVisibility(View.VISIBLE);
			login_lay.setVisibility(View.GONE);
			line_bg.setVisibility(View.VISIBLE);
		}

		xListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (item_position > firstVisibleItem - 1
						&& item_position < firstVisibleItem + visibleItemCount
								- 1) {
					today.setVisibility(View.GONE);
				} else {
					today.setVisibility(View.VISIBLE);
				}
			}
		});

	}

	private void upData() {
		// xListView.setPullUp(false);

//		if (!isGettingData) {
//			isGettingData = true;
//			IHttpTask task = HttpTaskFactory.getFactory().createTask(
//					HttpTaskFactory.TIMELINE_ITEM);
//			task.setParams(new String[] { start + "", timestamp });
//			HttpTaskFactory.getFactory().sendRequest(new IHttpCallback() {
//
//				@Override
//				public void onGetData(Object data) {
//					// TODO Auto-generated method stub
//
//					BaseListInfo<TimeLineInfo> list = (BaseListInfo<TimeLineInfo>) data;
//
//					String Updated = (String) list.getExtraData("Updated");
//
//					if (Updated.equals("false")) {
//
//						if ((Integer) list.getExtraData("today") != null) {
//
//							i = (Integer) list.getExtraData("today");
//						}
//
//						top = (String) list.getExtraData("top");
//
//						start = start - list.size();
//
//						// handler.sendEmptyMessage(2);
//						handler.sendMessage(handler.obtainMessage(2, list));
//					} else {
//
//						handler.sendMessage(handler.obtainMessage(31, data));
//
//					}
//
//				}
//
//				@Override
//				public void onError(Object reason) {
//					// TODO Auto-generated method stub
//					handler.sendMessage(handler.obtainMessage(-2, reason));
//				}
//			}, task);
//		}
	}

	private void downData() {
	
//		if (!isGettingData) {
//			isGettingData = true;
//			IHttpTask task = HttpTaskFactory.getFactory().createTask(
//					HttpTaskFactory.TIMELINE_ITEM);
//			task.setParams(new String[] { end + "", timestamp });
//			HttpTaskFactory.getFactory().sendRequest(new IHttpCallback() {
//
//				@Override
//				public void onGetData(Object data) {
//					// TODO Auto-generated method stub
//
//					BaseListInfo<TimeLineInfo> list = (BaseListInfo<TimeLineInfo>) data;
//					// timestamp = (String) list.getExtraData("Timestamp");
//					String Updated = (String) list.getExtraData("Updated");
//					if (Updated.equals("false")) {
//						bottom = (String) list.getExtraData("bottom");
//						end = end + list.size();
//
//						// handler.sendEmptyMessage(3);
//						handler.sendMessage(handler.obtainMessage(3, list));
//					} else {
//						handler.sendMessage(handler.obtainMessage(31, data));
//					}
//				}
//
//				@Override
//				public void onError(Object reason) {
//					// TODO Auto-generated method stub
//					handler.sendMessage(handler.obtainMessage(-3, reason));
//				}
//			}, task);
//		}
	}

	@Click
	public void goto_login() {
		MobclickAgent.onEvent(getActivity(), "时间轴登录");
		((MainAct) getActivity()).flag = FLAG;
		startActivityForResult(new Intent(getActivity(), LoginAct_.class), 1);
	}

	private void getData() {
//		if (!isGettingData) {
//			isGettingData = true;
//			login_lay.setVisibility(View.GONE);
//			progressbar.setVisibility(View.VISIBLE);
//			IHttpTask task = HttpTaskFactory.getFactory().createTask(
//					HttpTaskFactory.TIMELINE_CURRENT);
//			task.setParams(new String[] { "10" });
//			HttpTaskFactory.getFactory().sendRequest(this, task);
//		}
		/*
		 * IHttpTask task =
		 * HttpTaskFactory.getFactory().createTask(HttpTaskFactory
		 * .TIMELINE_CHECK); task.setParams(new String[]{"10",""});
		 * HttpTaskFactory.getFactory().sendRequest(this, task);
		 */
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		no_login_lay.setVisibility(View.GONE);
		login_lay.setVisibility(View.VISIBLE);
		getData();
	}

	private void reSetListview() {
		xListView.setPullUp(true);
		xListView.setpullDown(true);
		xListView.resetFoot();
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			if (msg.what == 1) {
				login_lay.setVisibility(View.VISIBLE);
				progressbar.setVisibility(View.GONE);

				needRefresh = false;
				dataEnd = false;
				BaseListInfo<TimeLineInfo> list = (BaseListInfo<TimeLineInfo>) msg.obj;
				infos.clear();
				infos.addAll(0, list);
				bottom = (String) list.getExtraData("bottom");
				top = (String) list.getExtraData("top");
				start = (Integer) list.getExtraData("start");
				end = (Integer) list.getExtraData("end");
				initView();

				if (!TextUtils.isEmpty(top) && top.equals("top")) {
					xListView.setPullUp(false);
				}

				if (!TextUtils.isEmpty(bottom) && bottom.equals("bottom")) {
					xListView.setPullDown(false);
					xListView.removeFoot();
					dataEnd = true;
				}
				
//				if (infos != null && infos.size() == 3) {
//					((MainAct) getActivity()).needCat = true;
//					((MainAct) getActivity()).showCat();
//				} else {
//					((MainAct) getActivity()).hideCat();
//				}

			} else if (msg.what == 2) {

				infos.addAll(0, (BaseListInfo<TimeLineInfo>) msg.obj);
				TimeLineFragment.item_position = TimeLineFragment.item_position
						+ ((BaseListInfo<TimeLineInfo>) msg.obj).size();
				p_up_info();

				adapter.notifyDataSetChanged();
				xListView.setSelection(10);
				onLoad();
			} else if (msg.what == -2) {
				onLoad();
			} else if (msg.what == 3) {

				infos.addAll((BaseListInfo<TimeLineInfo>) msg.obj);
				p_down_info();

				adapter.notifyDataSetChanged();
				if (!TextUtils.isEmpty(bottom) && bottom.equals("bottom")) {
					xListView.setPullDown(false);
					xListView.removeFoot();
					dataEnd = true;
				}

				onLoad();
			} else if (msg.what == 31) {
				reSetListview();
				BaseListInfo<TimeLineInfo> list = (BaseListInfo<TimeLineInfo>) msg.obj;
				infos.clear();
				infos.addAll(list);

				bottom = (String) list.getExtraData("bottom");
				top = (String) list.getExtraData("top");
				start = (Integer) list.getExtraData("start");
				end = (Integer) list.getExtraData("end");
//timestamp = (String) list.getExtraData("Timestamp");
				initView();

				if (!TextUtils.isEmpty(bottom) && bottom.equals("bottom")) {
					xListView.setPullDown(false);
					xListView.removeFoot();
					dataEnd = true;
				}
			}

			else if (msg.what == -3) {
				onLoad();
			} else if (msg.what == -1) {
				login_lay.setVisibility(View.VISIBLE);
				progressbar.setVisibility(View.GONE);
			}

			

			isGettingData = false;

		}

	};

	private void p_down_info() {
		try {
			String yearhead = "";
			String dayhead = "";

			for (int i = 0; i < infos.size(); i++) {
				TimeLineInfo info = infos.get(i);
				info.isDayHead = false;
				info.isToday = false;
				info.isYearHead = false;

				if (!TextUtils.isEmpty(info.Time)) {
					if (!yearhead.equals(info.Time.substring(0, 4))) {
						info.isYearHead = true;
						yearhead = info.Time.substring(0, 4);
					}

					if (!dayhead.equals(info.Time)) {
						info.isDayHead = true;
						dayhead = info.Time;
					}
				}

				/*
				 * if(MyApplication.appContext.serverTime.equals(info.Time)){
				 * info.isToday = true; }
				 */
			}

		} catch (Exception e) {
			System.out.println("e   " + e);
		}

	}

	private void p_up_info() {
		String yearhead = "";
		String dayhead = "";

		for (int i = 0; i < infos.size(); i++) {
			TimeLineInfo info = infos.get(i);
			info.isDayHead = false;
			info.isToday = false;
			info.isYearHead = false;

			if (!TextUtils.isEmpty(info.Time)) {
				if (!yearhead.equals(info.Time.substring(0, 4))) {
					info.isYearHead = true;
					yearhead = info.Time.substring(0, 4);
				}

				if (!dayhead.equals(info.Time)) {
					info.isDayHead = true;
					dayhead = info.Time;
				}
			}

			if (info.Type.equals("0")) {
				xListView.setPullUp(false);
			}

			/*
			 * if(MyApplication.appContext.serverTime.equals(info.Time)){
			 * info.isToday = true; }
			 */
		}

	}

	private void initView() {
		xListView.setPullLoadEnable(true);
		adapter = new MyAdapter();
		xListView.setAdapter(adapter);
		xListView.setXListViewListener(this);
		xListView.setSelection(item_position);

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

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infos.size();
		}

		@Override
		public TimeLineInfo getItem(int position) {
			// TODO Auto-generated method stub
			return infos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			TimeLineItemView itemView = null;
			if (convertView == null) {
				itemView = TimeLineItemView_.build(getActivity());
			} else {
				itemView = (TimeLineItemView) convertView;
			}
			try {
				itemView.bind(getItem(position));
				itemView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						TimeLineInfo info = infos.get(position);
						if (info.Type.equals("10")) {
							
							MobclickAgent.onEvent(getActivity(), "通过时间轴查看订单详情");
							Intent intent = new Intent(getActivity(),
									OrderDetailAct_.class);
							intent.putExtra("Id", info.Identifier);
							startActivity(intent);
						} else if (info.Type.equals("30")) {
							MobclickAgent.onEvent(getActivity(), "通过时间轴查看结息详情");
							Intent intent = new Intent(getActivity(),
									TimeLineProductDetailAct_.class);
							intent.putExtra("Id", info.Identifier);
							startActivity(intent);
						}
					}
				});

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return itemView;
		}

	}

	private void onLoad() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		Date date = new Date();
		String time = date.getHours() + ":" + date.getMinutes() + ":"
				+ date.getSeconds();
		xListView.setRefreshTime(time);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		downData();
	}

	@Override
	public void onRefresh() {
		upData();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();	
		
		
		
		MobclickAgent.onPageStart("时间轴");
	}
	
	

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("时间轴");
	}

}
