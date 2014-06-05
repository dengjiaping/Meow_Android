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
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.application.MyApplication;
import com.widen.db.Store;
import com.widen.http.HttpTaskFactory;
import com.widen.http.IHttpCallback;
import com.widen.http.IHttpTask;
import com.widen.http.info.ItemsCountInfo;
@EFragment(R.layout.more)
public class MoreFragment extends Fragment implements IHttpCallback{

	@ViewById
	LinearLayout no_login_lay;
	
	@ViewById
	LinearLayout login_lay;
	

	public static final String FLAG = "MoreFragment";
	public static boolean isLogin;
	@ViewById
	ImageView red_point;
	
	@AfterViews
	public void afterViews() {	
		loginRefresh();
	}
	
	public void loginRefresh(){
		if(MyApplication.appContext.isLogin()){
			isLogin = true;
			getData();
			no_login_lay.setVisibility(View.GONE);
			login_lay.setVisibility(View.VISIBLE);
		}else{
			isLogin = false;
			no_login_lay.setVisibility(View.VISIBLE);
			login_lay.setVisibility(View.GONE);
			red_point.setVisibility(View.GONE);
			((MainAct)(getActivity())).red_point.setVisibility(View.GONE);

		}
		
	}
	
	private void getData(){
		IHttpTask task = HttpTaskFactory.getFactory().createTask(HttpTaskFactory.ITEMS_COUNT);
		HttpTaskFactory.getFactory().sendRequest(this, task);
	}
	
	
	@Click
	public void login(){
		MobclickAgent.onEvent(getActivity(), "更多登录");
		((MainAct)getActivity()).flag = FLAG;
		startActivity(new Intent(getActivity(),LoginAct_.class));
	}
	
	@Click
	public void register(){
		((MainAct)getActivity()).flag = FLAG;
		startActivity(new Intent(getActivity(),RegisterStepOneAct_.class));
	}
	
	
	@Click
	public void about_lay(){
		startActivity(new Intent(getActivity(),AboutAct_.class));
	}
	
	@Click
	public void login_out(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				getActivity());
		builder.setTitle("提示")
				.setMessage("是否退出 ?")
				.setPositiveButton(
						"是",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									DialogInterface dialog,
									int which) {
								MyApplication.appContext.ampAuthToken = "";
								 Store.remove(getActivity(), "ampAuthToken");
								 Store.remove(getActivity(), "phone"); 
								loginRefresh();
								((MainAct)getActivity()).homeFragment.loginRefresh();
								if(((MainAct)getActivity()).timeLineFragment!=null){
									((MainAct)getActivity()).timeLineFragment.loginRefresh();
								}
								((MainAct)getActivity()).hideCat();
								 
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
	
	@Click
	public void my_item_lay(){
		if(MyApplication.appContext.isLogin()){
			red_point.setVisibility(View.GONE);
			((MainAct)(getActivity())).red_point.setVisibility(View.GONE);
			Intent intent = new Intent(getActivity(), MyItemAct_.class);
			intent.putExtra("flag", MoreFragment.FLAG);
			startActivity(intent);
			
		}else{
			((MainAct)getActivity()).flag = FLAG;
			startActivity(new Intent(getActivity(),LoginAct_.class));
		}
	}
	
	@Click
	public void account_lay(){
		if(MyApplication.appContext.isLogin()){
			startActivityForResult(new Intent(getActivity(),UserCountAct_.class) , UserCountAct.USERCOUNTACT);
		}else{
			((MainAct)getActivity()).flag = FLAG;
			startActivity(new Intent(getActivity(),LoginAct_.class));
		}
	}
	
	@Click
	public void order_lay(){
		if(MyApplication.appContext.isLogin()){
			
			Intent intent =new Intent(getActivity(),OrderListAct_.class);
			intent.putExtra("flag", FLAG);
			startActivity(intent);
		}else{
			((MainAct)getActivity()).flag = FLAG;
			startActivity(new Intent(getActivity(),LoginAct_.class));
		}
	}
	
	@Click
	public void feedback_lay(){
		startActivity(new Intent(getActivity(),FeedbackAct_.class));
	}
	
	
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 1){
				ItemsCountInfo info = (ItemsCountInfo) msg.obj;
				if(info.New){
					red_point.setVisibility(View.VISIBLE);
					((MainAct)(getActivity())).red_point.setVisibility(View.VISIBLE);
				}

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

}


	