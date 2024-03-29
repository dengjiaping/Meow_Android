package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.application.MyApplication;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.model.BannerInfo;
import com.widen.http.model.TopInfo;
import com.widen.widget.URLImageView;

@EFragment(R.layout.home)
public class HomeFragment extends Fragment implements IHttpCallback,
		SwipeRefreshLayout.OnRefreshListener {
	@ViewById
	SwipeRefreshLayout swipeRefreshLayout;

	@ViewById
	HorizontalScrollView horizontalScrollView;
	@ViewById
	LinearLayout banners_lay;

	@ViewById
	TextView Name;

	@ViewById
	TextView BankName;

	@ViewById
	TextView Yield;

	TopInfo info;

	@ViewById
	TextView Unit;

	@ViewById
	TextView Duration;

	@ViewById
	ProgressBar progressbar;

	public static boolean isLogin;

	public static final String FLAG = "HomeFragment";

	public void loginRefresh() {

		if (MyApplication.appContext.isLogin()) {
			isLogin = true;
			getData();
		} else {
			isLogin = false;
		}

	}

	@AfterViews
	public void afterViews() {
		getData();
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(R.color.top_yellow,
				R.color.top_yellow, R.color.top_yellow, R.color.top_yellow);

		((Button) (getActivity().findViewById(R.id.makeMoney)))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						makeMoney();
					}
				});
	}

	@Click
	public void login()

	{
		MobclickAgent.onEvent(getActivity(), "首页登录");
		((MainAct) getActivity()).flag = FLAG;
		startActivity(new Intent(getActivity(), LoginAct_.class));
	}

	public void makeMoney() {
		MobclickAgent.onEvent(getActivity(), "首页推荐");
		Intent intent = new Intent(getActivity(), ProductDetailAct_.class);
		if (info != null) {
			intent.putExtra("Id", info.topInfo2.productIdentifier);
		}
		startActivity(intent);
	}

	private void initView() {
		
		Name.setText(String.format("%s第%d期", info.topInfo2.productName,info.topInfo2.productNumber));
		BankName.setText(info.topInfo2.bankName);
		Yield.setText(info.topInfo2.yield+"");
		Unit.setText(info.topInfo2.unitPrice + "元起投");
		Duration.setText(info.topInfo2.period + "天");
		banners_lay.removeAllViews();
		if (MyApplication.appContext.dm.widthPixels <= 480) {
			for (int i = 0; i < info.bannerInfos.size(); i++) {
				View view = LayoutInflater.from(getActivity()).inflate(
						R.layout.top_img_item, null);
				URLImageView imageView = (URLImageView) view
						.findViewById(R.id.img);
				imageView.setImageURL(info.bannerInfos.get(i).Src_480);
				view.setTag(info.bannerInfos.get(i));
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final BannerInfo info = (BannerInfo) v.getTag();
						if (info.Type.equals("10")) {
							Intent intent = new Intent(getActivity(),
									BannerWebViewAct_.class);
							intent.putExtra("url", info.Url);
							startActivity(intent);
						} else if (info.Type.equals("20")) {
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

													Intent phoneIntent = new Intent(
															"android.intent.action.CALL",
															Uri.parse("tel:"
																	+ info.Url));
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
		} else if (MyApplication.appContext.dm.widthPixels > 480
				&& MyApplication.appContext.dm.widthPixels <= 640) {
			for (int i = 0; i < info.bannerInfos.size(); i++) {
				View view = LayoutInflater.from(getActivity()).inflate(
						R.layout.top_img_item, null);
				URLImageView imageView = (URLImageView) view
						.findViewById(R.id.img);
				imageView.setImageURL(info.bannerInfos.get(i).Src_640);
				view.setTag(info.bannerInfos.get(i));
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final BannerInfo info = (BannerInfo) v.getTag();
						if (info.Type.equals("10")) {
							Intent intent = new Intent(getActivity(),
									BannerWebViewAct_.class);
							intent.putExtra("url", info.Url);
							startActivity(intent);
						} else if (info.Type.equals("20")) {
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

													Intent phoneIntent = new Intent(
															"android.intent.action.CALL",
															Uri.parse("tel:"
																	+ info.Url));
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
		} else if (MyApplication.appContext.dm.widthPixels > 640) {
			for (int i = 0; i < info.bannerInfos.size(); i++) {
				View view = LayoutInflater.from(getActivity()).inflate(
						R.layout.top_img_item, null);
				URLImageView imageView = (URLImageView) view
						.findViewById(R.id.img);
				imageView.setImageURL(info.bannerInfos.get(i).Src_720);
				view.setTag(info.bannerInfos.get(i));
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final BannerInfo info = (BannerInfo) v.getTag();
						if (info.Type.equals("10")) {
							Intent intent = new Intent(getActivity(),
									BannerWebViewAct_.class);
							intent.putExtra("url", info.Url);
							startActivity(intent);
						} else if (info.Type.equals("20")) {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									getActivity());
							builder.setTitle("提示")
									.setMessage("确认拨打 " + info.Url)
									.setPositiveButton(
											"是",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method stub

													Intent phoneIntent = new Intent(
															"android.intent.action.CALL",
															Uri.parse("tel:"
																	+ info.Url));
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

	}

	private void getData() {
//		progressbar.setVisibility(View.VISIBLE);
//		IHttpTask task = HttpTaskFactory.getFactory().createTask(
//				HttpTaskFactory.GET_BANNER);
//		HttpTaskFactory.getFactory().sendRequest(this, task);
//		
//		task = HttpTaskFactory.getFactory().createTask(
//				HttpTaskFactory.GET_TOP_PRODUCT_FOR_BA);
//		HttpTaskFactory.getFactory().sendRequest(this, task);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			progressbar.setVisibility(View.GONE);
			if (msg.what == 1) {
//				swipeRefreshLayout.setVisibility(View.VISIBLE);
//				info = (TopInfo) msg.obj;
//				if (info == null) {
//					return;
//				}
//				initView();
			} else if (msg.what < 0) {

			}
		}

	};

	@Override
	public void onGetData(Object data) {
		if (data != null) {
			handler.sendMessage(handler.obtainMessage(1, data));
		}
	}

	@Override
	public void onError(Object reason) {
		handler.sendMessage(handler.obtainMessage(-1, reason));
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		// getData();
		IHttpTask task = HttpTaskFactory.getFactory().createTask(
				HttpTaskFactory.GET_BANNER);
		HttpTaskFactory.getFactory().sendRequest(this, task);
		
		task = HttpTaskFactory.getFactory().createTask(
				HttpTaskFactory.GET_TOP_PRODUCT_FOR_BA);
		HttpTaskFactory.getFactory().sendRequest(this, task);
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				swipeRefreshLayout.setRefreshing(false);
			}
		}, 1000);
	}

	public String timeString(Double time) {
		time = Math.ceil(time);
		String tpl = "";
		int t_d = 24 * 60 * 60;
		int day = (int) Math.floor(time / t_d);
		int day_time = (int) (time % t_d);
		int hours = (int) Math.floor(day_time / 3600);
		int hours_time = day_time % 3600;
		int minutes = (int) Math.floor(hours_time / 60);
		int seconds = hours_time % 60;
		if (day >= 100) {
			tpl = (day_time > 0 ? day + 1 : day) + "天";
		} else if (day > 0) {
			tpl = day + "天";
			if (hours_time > 0) {
				hours++;
			}
			if (hours > 0) {
				tpl += hours + "小时";
			}
		} else if (hours > 0) {
			if (hours_time > 0) {
				hours++;
			}
			tpl = hours + "小时";
		} else if (minutes > 0) {
			if (seconds > 0) {
				minutes++;
			}
			tpl = minutes + "分钟";
		} else {
			tpl = seconds + "秒";
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
