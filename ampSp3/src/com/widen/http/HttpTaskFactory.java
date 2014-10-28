package com.widen.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.widen.application.MyApplication;
import com.widen.db.Store;
import com.widen.http.tasks.AddBankCardTask;
import com.widen.http.tasks.CheckMobileVersionTask;
import com.widen.http.tasks.CheckNameUniqTask;
import com.widen.http.tasks.FailOrdersTaskForBA;
import com.widen.http.tasks.FailOrdersTaskForTA;
import com.widen.http.tasks.FeedBackNewTask;
import com.widen.http.tasks.GetBannerTask;
import com.widen.http.tasks.GetTopBAProductTask;
import com.widen.http.tasks.OrderDetailTask;
import com.widen.http.tasks.OrdersTaskForBA;
import com.widen.http.tasks.OrdersTaskForTA;
import com.widen.http.tasks.ProductsDetailForBATask;
import com.widen.http.tasks.ProductsListBANewTask;
import com.widen.http.tasks.ProductsListTANewTask;
import com.widen.http.tasks.RegisterTask;
import com.widen.http.tasks.ResetLoginPasswordTask;
import com.widen.http.tasks.SetDefaultBankCardTask;
import com.widen.http.tasks.SignInTask;
import com.widen.http.tasks.SignOutTask;
import com.widen.http.tasks.SignupPaymentTask;
import com.widen.http.tasks.UsersInfoTask;
import com.widen.http.tasks.VerificationsTask;
import com.widen.http.tasks.VerifyTask;
import com.widen.util.Constant;
import com.widen.util.LogManager;
import com.widen.util.Util;
import com.widen.util.threads.ThreadPool;

public class HttpTaskFactory {

	public static final String TAG = "HttpTaskFactory";

	// -------------------- requests begin -----------------------

	// -------------------- requests end -----------------------

	

	public static final int PRODUCTIONS = 1;

	public static final int VERIFICATIONS = 2;

	public static final int REGISTER = 3;

	public static final int SIGNIN = 4;

	public static final int PRODUCTIONDETAIL = 5;

	public static final int CHECK_NAME_UNIQ = 6;

	public static final int VERIFY = 7;

	public static final int CHECK_MOBILE_VERSION = 8;

	public static final int FEED_BACK_NEW = 9;

	public static final int PRODUCTS_LIST_FORBA = 10;
	
	public static final int PRODUCTS_LIST_FORTA = 11;

	public static final int PRODUCTS_DETAIL_FORBA = 12;

	public static final int GET_BANNER = 13;
	
	public static final int ORDERS_LIST_FOR_BA = 14;
	
	public static final int ORDERS_LIST_FOR_TA = 15;
	
	public static final int FAIL_ORDERS_LIST_FOR_BA = 16;
	
	public static final int FAIL_ORDERS_LIST_FOR_TA = 17;
	
	public static final int USERS_INFO = 18;
	
	public static final int ORDERDETAIL = 19;

	public static final int RESET_LOGIN_PASSWORD = 20;
	
	public static final int RESET_PAYMENT_PASSWORD = 21;
	
	public static final int SIGNUP_PAYMENT = 22;
	
	public static final int SET_DEFAULT_BANKCARD = 23;
	
	public static final int ADD_BANK_CARD = 24;
	
	public static final int SIGNOUT = 25;
	
	public static final int GET_TOP_PRODUCT_FOR_BA = 26;

	
	private Map<Integer, Class<? extends IHttpTask>> maps = new HashMap<Integer, Class<? extends IHttpTask>>();

	private static HttpTaskFactory instance;

	private HttpTaskFactory() {
		
	}

	private Class<? extends IHttpTask> getTask(int taskid) {

		Class<? extends IHttpTask> task = null;
		
		switch(taskid)
		{
			case VERIFICATIONS:
				
				task = maps.get(VERIFICATIONS);
				
				if(task == null){
					
					task = VerificationsTask.class;
					maps.put(VERIFICATIONS, task);
				}
				return task;
			
			case REGISTER:
				
				task = maps.get(REGISTER);
				
				if(task == null){
					
					task = RegisterTask.class;
					maps.put(REGISTER, task);
				}
				return task;
				
			case SIGNIN:
				
				task = maps.get(SIGNIN);
				
				if(task == null){
					
					task = SignInTask.class;
					maps.put(SIGNIN, task);
				}
				return task;
	
			case CHECK_NAME_UNIQ:
				
				task = maps.get(CHECK_NAME_UNIQ);
				
				if(task == null){
					
					task = CheckNameUniqTask.class;
					maps.put(CHECK_NAME_UNIQ, task);
				}
				return task;
			
			case VERIFY:
				
				task = maps.get(VERIFY);
				
				if(task == null){
					
					task = VerifyTask.class;
					maps.put(VERIFY, task);
				}
				return task;
				
			case CHECK_MOBILE_VERSION:
				
				task = maps.get(CHECK_MOBILE_VERSION);
				
				if(task == null){
					
					task = CheckMobileVersionTask.class;
					maps.put(CHECK_MOBILE_VERSION, task);
				}
				return task;
				
			case FEED_BACK_NEW:
				
				task = maps.get(FEED_BACK_NEW);
				
				if(task == null){
					
					task = FeedBackNewTask.class;
					maps.put(FEED_BACK_NEW, task);
				}
				return task;
				
			case PRODUCTS_LIST_FORBA:
					
					task = maps.get(PRODUCTS_LIST_FORBA);
					
					if(task == null){
						
						task = ProductsListBANewTask.class;
						maps.put(PRODUCTS_LIST_FORBA, task);
					}
					return task;
					
			case PRODUCTS_LIST_FORTA:
				
				task = maps.get(PRODUCTS_LIST_FORTA);
				
				if(task == null){
					
					task = ProductsListTANewTask.class;
					maps.put(PRODUCTS_LIST_FORTA, task);
				}
				return task;
			
			case PRODUCTS_DETAIL_FORBA:
				
				task = maps.get(PRODUCTS_DETAIL_FORBA);
				
				if(task == null){
					
					task = ProductsDetailForBATask.class;
					maps.put(PRODUCTS_DETAIL_FORBA, task);
				}
				return task;
				
			case RESET_LOGIN_PASSWORD:
				
				task = maps.get(RESET_LOGIN_PASSWORD);
				
				if(task == null){
					
					task = ResetLoginPasswordTask.class;
					maps.put(RESET_LOGIN_PASSWORD, task);
				}
				return task;
		
			case RESET_PAYMENT_PASSWORD:
				
				task = maps.get(RESET_PAYMENT_PASSWORD);
				
				if(task == null){
					
					task = ResetLoginPasswordTask.class;
					maps.put(RESET_PAYMENT_PASSWORD, task);
				}
				return task;
				
			case SIGNUP_PAYMENT:
				//开通快捷支付，并且绑定第一张借记卡
				task = maps.get(SIGNUP_PAYMENT);
				
				if(task == null){
					
					task = SignupPaymentTask.class;
					maps.put(SIGNUP_PAYMENT, task);
				}
				return task;
				
			case SET_DEFAULT_BANKCARD:
				//设置默认银行卡
				task = maps.get(SET_DEFAULT_BANKCARD);
				
				if(task == null){
					
					task = SetDefaultBankCardTask.class;
					maps.put(SET_DEFAULT_BANKCARD, task);
				}
				return task;
			case ADD_BANK_CARD:
				task = maps.get(ADD_BANK_CARD);
				
				if(task == null){
					
					task = AddBankCardTask.class;
					maps.put(ADD_BANK_CARD, task);
				}
				return task;
				
			case SIGNOUT:
				task = maps.get(SIGNOUT);
				
				if(task == null){
					
					task = SignOutTask.class;
					maps.put(SIGNOUT, task);
				}
				return task;
				
			case GET_BANNER:
				task = maps.get(GET_BANNER);
				
				if(task == null){
					
					task = GetBannerTask.class;
					maps.put(GET_BANNER, task);
				}
				return task;
				
			case ORDERS_LIST_FOR_BA:
				task = maps.get(ORDERS_LIST_FOR_BA);
				
				if(task == null){
					
					task = OrdersTaskForBA.class;
					maps.put(ORDERS_LIST_FOR_BA, task);
				}
				return task;
				
			case ORDERS_LIST_FOR_TA:
				task = maps.get(ORDERS_LIST_FOR_TA);
				
				if(task == null){
					
					task = OrdersTaskForTA.class;
					maps.put(ORDERS_LIST_FOR_TA, task);
				}
				return task;
				
			case ORDERDETAIL:
				//订单详情
				task = maps.get(ORDERDETAIL);
				
				if(task == null){
					
					task = OrderDetailTask.class;
					maps.put(ORDERDETAIL, task);
				}
				return task;
			case FAIL_ORDERS_LIST_FOR_BA:
				
				task = maps.get(FAIL_ORDERS_LIST_FOR_BA);
				
				if(task == null){
					
					task = FailOrdersTaskForBA.class;
					maps.put(FAIL_ORDERS_LIST_FOR_BA, task);
				}
				return task;	
				
			case FAIL_ORDERS_LIST_FOR_TA:
				
				task = maps.get(FAIL_ORDERS_LIST_FOR_TA);
				
				if(task == null){
					
					task = FailOrdersTaskForTA.class;
					maps.put(FAIL_ORDERS_LIST_FOR_TA, task);
				}
				return task;	
				
			case USERS_INFO:
				
				task = maps.get(USERS_INFO);
				
				if(task == null){
					
					task = UsersInfoTask.class;
					maps.put(USERS_INFO, task);
				}
				return task;	
				
			case GET_TOP_PRODUCT_FOR_BA:
				
				task = maps.get(GET_TOP_PRODUCT_FOR_BA);
				
				if(task == null){
					
					task = GetTopBAProductTask.class;
					maps.put(GET_TOP_PRODUCT_FOR_BA, task);
				}
				return task;
		}
		
		
		
		
		return null;
	
		
	}

	public static HttpTaskFactory getFactory() {
		if (instance == null) {
			synchronized (HttpTaskFactory.class) {
				if (instance == null) {
					instance = new HttpTaskFactory();
				}
			}
		}
		return instance;
	}

	public IHttpTask createTask(int req) {
		try {
			return getTask(req).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}

	public void sendRequest(IHttpCallback callback, IHttpTask tasks) {
		handleRequest(callback, tasks);
	}


	private void handleRequest(IHttpCallback callback, IHttpTask tasks) {

		
			WorkerForNewApi wk = new WorkerForNewApi(callback, tasks);
			
			if (ThreadPool.getInstance().tasks.size() != 0) {
				for (int i = 0; i < ThreadPool.getInstance().tasks.size(); i++) {
					Runnable runnable = ThreadPool.getInstance().tasks.get(i);
					if (runnable instanceof WorkerForNewApi) {
						if (((WorkerForNewApi) runnable).label.equals(wk.label)) {
							return;
						}
					}
				}
			}
			ThreadPool.getInstance().addTask(wk);
		
	}

	

	private static class WorkerForNewApi implements Runnable {

		private IHttpCallback cb;
		public IHttpTask tks;
		
		public String label;

	
		public WorkerForNewApi(IHttpCallback callback,IHttpTask task){
			cb = callback;
			tks = task;
		}

		private String reqHeadUrl(IHttpTask task) {

			if (!TextUtils.isEmpty(task.getParams())) {
				String params = "";
				try {
					params = (URLEncoder.encode(task.getParams(), "utf-8"))
							.replace("%3D", "=").replace("%26", "&");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return task.getSubUrl() + "?" + params;
			} else {
				return task.getSubUrl();
			}
		}

		public void run() {

			HttpsURLConnection conn = null;
			try {
				if (tks.getType().equals(Constant.GET)
						|| tks.getType().equals(Constant.DELETE)) {
				
					
					String urlStr;
					
					if (tks.isRalativeUrl()){
						urlStr = HttpConfig.URL_SERVER
							+ reqHeadUrl(tks);
					}else {
						urlStr = tks.getSubUrl();
					}
					conn = (HttpsURLConnection) new URL(urlStr).openConnection();
					conn.setReadTimeout(20000);
					conn.setConnectTimeout(20000);
					conn.setRequestMethod(tks.getType());
					
					conn.setRequestProperty("Content-Type",tks.getContentType());
					conn.setRequestProperty("Accept-Encoding", "gzip");
					
					if (!TextUtils
							.isEmpty(MyApplication.appContext.ampAuthToken)) {
						conn.setRequestProperty("Cookie",
								MyApplication.appContext.ampAuthToken);
					}

					LogManager.d(TAG, urlStr);
					
				} else if (tks.getType().equals(Constant.POST)
						|| tks.getType().equals(Constant.PUT)) {
					
					String urlStr;	
					
					if (tks.isRalativeUrl()){
						urlStr = HttpConfig.URL_SERVER
							+ tks.getSubUrl();
					}else {
						urlStr = tks.getSubUrl();
					}
					
					conn = (HttpsURLConnection) new URL(urlStr).openConnection();
					conn.setReadTimeout(20000);
					conn.setConnectTimeout(20000);
					
					conn.setRequestMethod(tks.getType());
					conn.setDoInput(true);
					conn.setDoOutput(true);
					
					conn.setRequestProperty("Content-Type",tks.getContentType());
					
					
					conn.setRequestProperty("Accept-Encoding", "gzip");
					if (!TextUtils
							.isEmpty(MyApplication.appContext.ampAuthToken)) {
						conn.setRequestProperty("Cookie",
								MyApplication.appContext.ampAuthToken);
					}

					OutputStream out = conn.getOutputStream();
					LogManager.d(TAG, tks.getParams());
					out.write(tks.getParams().getBytes());
					out.flush();
					out.close();

				}

				int code = conn.getResponseCode();		
				if (code >= 500) {
					if (cb != null) {
						cb.onError("请求失败");
					}
					return;
				}						
				
				if (TextUtils.isEmpty(MyApplication.appContext.ampAuthToken)) {
					String ampAuthToken = conn.getHeaderField("Set-Cookie");
					if (!TextUtils.isEmpty(ampAuthToken)) {
						MyApplication.appContext.ampAuthToken = ampAuthToken;
						Store.puts(MyApplication.appContext, "ampAuthToken",
								ampAuthToken);
					}
				}

				if (code == 204) {
					cb.onGetData(null);
					return;
				}

				if (code > 300) {	
					BufferedReader br = new BufferedReader(
							new InputStreamReader(conn.getErrorStream()));
					String s = "";
					StringBuffer sb = new StringBuffer("");

					while ((s = br.readLine()) != null) {
						sb.append(s).append("\n");
					}

					br.close();

					String response = sb.toString();

					LogManager.d(TAG, response);				
					JSONObject jsonObject = new JSONObject(response);

					if (cb != null) {		
						String errMsg = Util
								.getJsonString(jsonObject, "Message");
						cb.onError(errMsg);
						
					}
					return;
				}
				
				try{
				GZIPInputStream gzinput=new GZIPInputStream(conn.getInputStream()); 
				BufferedReader br = new BufferedReader(
							new InputStreamReader(gzinput));
				
				/*BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));*/
				String s = "";
				StringBuffer sb = new StringBuffer("");

				while ((s = br.readLine()) != null) {
					sb.append(s).append("\n");
				}

				br.close();
				String response = sb.toString();
				LogManager.d(TAG, response);
					
					try{
						
						JSONObject jsonObject = new JSONObject(response);
						Object object = tks.getData(jsonObject);						
						cb.onGetData(object);				
					}catch(Exception e){
						JSONArray jsonObject = new JSONArray(response);					
						cb.onGetData(tks.getData(jsonObject));						
					}
					
					
				}catch (Exception e) {					
					cb.onGetData(null);
					return;
				}	
			} catch (UnknownHostException e) {
				e.printStackTrace();
				if (cb != null) {
					cb.onError("请检查网络是否正常!");
				}
			} catch (SocketException e) {
				e.printStackTrace();
				if (cb != null) {
					cb.onError("请检查网络是否正常!");
				}
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
				if (cb != null) {
					cb.onError("请求超时，网络不稳定!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (cb != null) {
					cb.onError("请求失败!");
				}
			}

		}

	}



}
