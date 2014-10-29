package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.application.MyApplication;
import com.widen.db.Store;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.model.ItemsCountInfo;
import com.widen.product.account.FundAccountActivity;
import com.widen.product.account.MyBankCardActivity;
import com.widen.product.account.SettingActivity;

@EFragment(R.layout.more)
public class MoreFragment extends Fragment implements IHttpCallback,
		OnClickListener {

	@ViewById
	LinearLayout no_login_lay;

	@ViewById
	LinearLayout login_lay;

	public static final String FLAG = "MoreFragment";
	public static boolean isLogin;

	@AfterViews
	public void afterViews() {
		loginRefresh();

		getActivity().findViewById(R.id.setting_lay).setOnClickListener(this);
		getActivity().findViewById(R.id.bankcard_lay).setOnClickListener(this);
	}

	public void loginRefresh() {
		if (MyApplication.appContext.isLogin()) {
			isLogin = true;
			getData();
			no_login_lay.setVisibility(View.GONE);
			login_lay.setVisibility(View.VISIBLE);
		} else {
			isLogin = false;
			no_login_lay.setVisibility(View.VISIBLE);
			login_lay.setVisibility(View.GONE);
			// red_point.setVisibility(View.GONE);
			// ((MainAct)(getActivity())).red_point.setVisibility(View.GONE);

		}

	}

	private void getData() {
		// IHttpTask task = HttpTaskFactory.getFactory().createTask(
		// HttpTaskFactory.ITEMS_COUNT);
		// HttpTaskFactory.getFactory().sendRequest(this, task);
	}

	@Click
	public void login() {
		MobclickAgent.onEvent(getActivity(), "更多登录");
		((MainAct) getActivity()).flag = FLAG;
		startActivity(new Intent(getActivity(), LoginAct_.class));
	}

	@Click
	public void register() {
		((MainAct) getActivity()).flag = FLAG;
		startActivity(new Intent(getActivity(), RegisterStepOneAct_.class));
	}

	@Click
	public void login_out() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("提示").setMessage("是否退出 ?")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						MyApplication.appContext.ampAuthToken = "";
						Store.remove(getActivity(), "ampAuthToken");
						Store.remove(getActivity(), "phone");
						loginRefresh();
						((MainAct) getActivity()).homeFragment.loginRefresh();
						// if(((MainAct)getActivity()).timeLineFragment!=null){
						// ((MainAct)getActivity()).timeLineFragment.loginRefresh();
						// }
						// ((MainAct)getActivity()).hideCat();

					}
				})
				.setNegativeButton("否", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();

	}

	// @Click
	// public void my_item_lay() {
	// if (MyApplication.appContext.isLogin()) {
	// // red_point.setVisibility(View.GONE);
	// // ((MainAct)(getActivity())).red_point.setVisibility(View.GONE);
	// Intent intent = new Intent(getActivity(), MyItemAct_.class);
	// intent.putExtra("flag", MoreFragment.FLAG);
	// startActivity(intent);
	//
	// } else {
	// ((MainAct) getActivity()).flag = FLAG;
	// startActivity(new Intent(getActivity(), LoginAct_.class));
	// }
	// }

	@Click
	public void account_lay() {
		if (MyApplication.appContext.isLogin()) {
			FundAccountActivity.startActivity(getActivity(), 1);
		} else {
			((MainAct) getActivity()).flag = FLAG;
			startActivity(new Intent(getActivity(), LoginAct_.class));
		}
	}

	@Click
	public void order_lay() {
		if (MyApplication.appContext.isLogin()) {

			Intent intent = new Intent(getActivity(), OrderListAct_.class);
			intent.putExtra("flag", FLAG);
			startActivity(intent);
		} else {
			((MainAct) getActivity()).flag = FLAG;
			startActivity(new Intent(getActivity(), LoginAct_.class));
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				ItemsCountInfo info = (ItemsCountInfo) msg.obj;
				// if(info.New){
				// red_point.setVisibility(View.VISIBLE);
				// ((MainAct)(getActivity())).red_point.setVisibility(View.VISIBLE);
				// }

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
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		MobclickAgent.onPageStart("更多");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("更多");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_lay:
			Intent intent = new Intent(getActivity(), SettingActivity.class);
			startActivity(intent);
			break;
		case R.id.bankcard_lay:
			if (MyApplication.appContext.isLogin()) {
				Intent it = new Intent(getActivity(), MyBankCardActivity.class);
				startActivity(it);
			} else {
				((MainAct) getActivity()).flag = FLAG;
				startActivity(new Intent(getActivity(), LoginAct_.class));
			}
			break;
		default:
			break;
		}
	}

}
