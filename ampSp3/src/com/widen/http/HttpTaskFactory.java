package com.widen.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.google.gson.JsonArray;
import com.widen.application.MyApplication;
import com.widen.db.Store;
import com.widen.http.tasks.AccountBalanceTask;
import com.widen.http.tasks.AddressesCreateTask;
import com.widen.http.tasks.AddressesListTask;
import com.widen.http.tasks.AddressesModifyTask;
import com.widen.http.tasks.BatchTask;
import com.widen.http.tasks.BookTask;
import com.widen.http.tasks.BookingsTask;
import com.widen.http.tasks.CheckMobileVersionTask;
import com.widen.http.tasks.CheckNameUniqTask;
import com.widen.http.tasks.DeleteTask;
import com.widen.http.tasks.FeedBackNewTask;
import com.widen.http.tasks.ItemRemoveTask;
import com.widen.http.tasks.ItemsConsumeByOrderTask;
import com.widen.http.tasks.ItemsCountTask;
import com.widen.http.tasks.ItemsTask;
import com.widen.http.tasks.ItemsUseableTask;
import com.widen.http.tasks.OrderInfoTask;
import com.widen.http.tasks.OrdersForItemTask;
import com.widen.http.tasks.OrdersTask;
import com.widen.http.tasks.ProductTopNewTask;
import com.widen.http.tasks.ProductsDetailNewTask;
import com.widen.http.tasks.ProductsListNewTask;
import com.widen.http.tasks.RegisterTask;
import com.widen.http.tasks.ResearchesTask;
import com.widen.http.tasks.ResetPasswordTask;
import com.widen.http.tasks.SignInTask;
import com.widen.http.tasks.TimeLineCheckTask;
import com.widen.http.tasks.TimeLineCurrentTask;
import com.widen.http.tasks.TimeLineItemsTask;
import com.widen.http.tasks.TimeLineRefundInfoTask;
import com.widen.http.tasks.UsersSummaryInfoTask;
import com.widen.http.tasks.VerificationsTask;
import com.widen.http.tasks.VerifyTask;
import com.widen.util.Constant;
import com.widen.util.LogManager;
import com.widen.util.UpdateAct;
import com.widen.util.Util;
import com.widen.util.threads.ThreadPool;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

public class HttpTaskFactory {

	public static final String TAG = "HttpTaskFactory";

	// -------------------- requests begin -----------------------

	// -------------------- requests end -----------------------

	private static final int MAX_UPLOAD_IMG_SIZE = 128 * 1024; // 128KB

	public static final int PRODUCTIONS = 1;

	public static final int VERIFICATIONS = 2;

	public static final int REGISTER = 3;

	public static final int SIGNIN = 4;

	public static final int PRODUCTIONDETAIL = 5;

	public static final int BOOK = 6;

	public static final int BOOKING = 7;

	public static final int IDENTITIES = 8;

	public static final int ACCOUNT_BALANCE = 9;

	public static final int ADDRESSES_LIST = 10;

	public static final int ADDRESSES_CREATE = 11;

	public static final int ADDRESSES_MODIFY = 12;

	public static final int CHECK_NAME_UNIQ = 13;

	public static final int VERIFY = 14;

	public static final int DELETE = 15;

	public static final int CHECK_MOBILE_VERSION = 16;

	public static final int RESEARCHES = 17;

	public static final int PRODUCT_TOP_NEW = 18;

	public static final int FEED_BACK_NEW = 19;

	public static final int PRODUCTS_LIST_NEW = 20;

	public static final int PRODUCTS_DETAIL_NEW = 21;

	public static final int BATCH = 22;
	
	
	public static final int TIMELINE_ITEM = 23;
	
	
	public static final int TIMELINE_CURRENT = 24;
	
	public static final int ITEMS_COUNT = 25;
	
	public static final int ORDERS = 26;
	
	public static final int ITEMS = 27;
	
	public static final int ITEMS_USEABLE = 28;
	
	
	public static final int ITEMS_CONSUMEBYORDER = 29;
	
	public static final int ORDERS_FORITEM = 30;
	
	public static final int USERS_SUMMARYINFO = 31;
	
	public static final int ORDERINFO = 32;
	
	
	
	public static final int TIMELINE_CHECK = 33;
	
	public static final int TIMELINE_REFUNDINFO = 34;
	
	
	public static final int ITEM_REMOVE = 35;
	
	public static final int RESET_PASSWORD = 36;

	private static boolean notified;
	@SuppressWarnings("unchecked")
	private Map<Integer, Class<? extends IHttpTask>> maps = new HashMap<Integer, Class<? extends IHttpTask>>();

	private static HttpTaskFactory instance;

	private HttpTaskFactory() {
		initMap();
	}

	private void initMap() {

		maps.put(VERIFICATIONS, VerificationsTask.class);
		maps.put(REGISTER, RegisterTask.class);
		maps.put(SIGNIN, SignInTask.class);
		maps.put(BOOK, BookTask.class);
		maps.put(BOOKING, BookingsTask.class);
		maps.put(ACCOUNT_BALANCE, AccountBalanceTask.class);
		maps.put(ADDRESSES_LIST, AddressesListTask.class);
		maps.put(ADDRESSES_CREATE, AddressesCreateTask.class);
		maps.put(ADDRESSES_MODIFY, AddressesModifyTask.class);
		maps.put(CHECK_NAME_UNIQ, CheckNameUniqTask.class);
		maps.put(VERIFY, VerifyTask.class);
		maps.put(DELETE, DeleteTask.class);
		maps.put(CHECK_MOBILE_VERSION, CheckMobileVersionTask.class);
		maps.put(RESEARCHES, ResearchesTask.class);
		maps.put(PRODUCT_TOP_NEW, ProductTopNewTask.class);
		maps.put(FEED_BACK_NEW, FeedBackNewTask.class);

		maps.put(PRODUCTS_LIST_NEW, ProductsListNewTask.class);
		maps.put(PRODUCTS_DETAIL_NEW, ProductsDetailNewTask.class);
		
		maps.put(BATCH, BatchTask.class);
		maps.put(TIMELINE_ITEM, TimeLineItemsTask.class);
		maps.put(TIMELINE_CURRENT, TimeLineCurrentTask.class);
		maps.put(ITEMS_COUNT, ItemsCountTask.class);
		maps.put(ORDERS, OrdersTask.class);
		maps.put(ITEMS, ItemsTask.class);
		maps.put(ITEMS_USEABLE, ItemsUseableTask.class);
		maps.put(ITEMS_CONSUMEBYORDER, ItemsConsumeByOrderTask.class);
		
		maps.put(ORDERS_FORITEM, OrdersForItemTask.class);
		maps.put(USERS_SUMMARYINFO, UsersSummaryInfoTask.class);
		
		maps.put(ORDERINFO,OrderInfoTask.class);
		maps.put(TIMELINE_CHECK, TimeLineCheckTask.class);
		
		maps.put(TIMELINE_REFUNDINFO, TimeLineRefundInfoTask.class);
		maps.put(ITEM_REMOVE, ItemRemoveTask.class);
		maps.put(RESET_PASSWORD, ResetPasswordTask.class);
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
			return maps.get(req).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			// Log.v(TAG, "FIX ME->req:" + req);
		}
		return null;
	}

	public void sendRequest(IHttpCallback callback, IHttpTask tasks) {
		sendRequest(callback, null, tasks);
	}

	/**
	 * 广场调用
	 * 
	 * @param callback
	 * @param path
	 *            只能为图片路径。。
	 * @param tasks
	 */
	public void sendRequest(IHttpCallback callback, String path,
			IHttpTask<?> tasks) {
		handleRequest(callback, tasks, path, false);
	}

	/**
	 * 特定的商户发送请求调用
	 * 
	 * @param callback
	 * @param path
	 *            只能为图片路径。。
	 * @param tasks
	 */
	public void sendRequestOfSpecialPL(IHttpCallback callback, String path,
			IHttpTask<?> tasks) {
		handleRequest(callback, tasks, path, true);
	}

	/**
	 * 名城、名店 进大广场 点击名城、名店
	 * 
	 * @param callback
	 * @param path
	 * @param tasks
	 */
	public void sendRequestOfSpecialPLMaapin(IHttpCallback callback,
			String path, IHttpTask<?> tasks) {
		handleRequestMaapin(callback, tasks, path, true);
	}

	/**
	 * for special HTTP request
	 * 
	 * @param callback
	 * @param url
	 */
	public void sendRequest(final IHttpCallback callback, final String url) {

		Runnable r = new Runnable() {
			@Override
			public void run() {
				HttpURLConnection conn = null;
				InputStream is = null;
				try {
					conn = (HttpURLConnection) new URL(url).openConnection();
					conn.connect();
					is = conn.getInputStream();
					ByteArrayOutputStream bos = new ByteArrayOutputStream(
							is.available());
					int i = 0;
					byte[] buff = new byte[512];
					while (true) {
						i = is.read(buff);
						if (i <= 0) {
							break;
						}

						bos.write(buff, 0, i);

					}
					bos.close();
					if (callback != null) {
						callback.onGetData(new Object[] { bos.toByteArray() });
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (callback != null) {
						callback.onError(e.getMessage());
					}
				} finally {
					close(is);
					if (conn != null) {
						conn.disconnect();
					}
				}
			}
		};
		ThreadPool.getInstance().addTask(r);
	}

	private void handleRequest(IHttpCallback callback, IHttpTask<?> tasks,
			String filepath, boolean specialPL) {

		if (!tasks.isNewApi()) {
			Worker wk = new Worker(callback, tasks, filepath, specialPL);
			if (ThreadPool.getInstance().tasks.size() != 0) {
				for (int i = 0; i < ThreadPool.getInstance().tasks.size(); i++) {
					Runnable runnable = ThreadPool.getInstance().tasks.get(i);
					if (runnable instanceof Worker) {
						if (((Worker) runnable).label.equals(wk.label)) {
							return;
						}
					}
				}
			}
			ThreadPool.getInstance().addTask(wk);
		} else {
			WorkerForNewApi wk = new WorkerForNewApi(callback, tasks, filepath,
					specialPL);
			if (ThreadPool.getInstance().tasks.size() != 0) {
				for (int i = 0; i < ThreadPool.getInstance().tasks.size(); i++) {
					Runnable runnable = ThreadPool.getInstance().tasks.get(i);
					if (runnable instanceof Worker) {
						if (((Worker) runnable).label.equals(wk.label)) {
							return;
						}
					}
				}
			}
			ThreadPool.getInstance().addTask(wk);
		}
	}

	private void handleRequestMaapin(IHttpCallback callback,
			IHttpTask<?> tasks, String filepath, boolean specialPL) {
		Worker wk = new Worker(callback, tasks, filepath, specialPL, false);
		if (ThreadPool.getInstance().tasks.size() != 0) {
			for (int i = 0; i < ThreadPool.getInstance().tasks.size(); i++) {
				Runnable runnable = ThreadPool.getInstance().tasks.get(i);
				if (runnable instanceof Worker) {
					if (((Worker) runnable).label.equals(wk.label)) {
						return;
					}
				}
			}
		}
		ThreadPool.getInstance().addTask(wk);
	}

	private static class WorkerForNewApi implements Runnable {

		private IHttpCallback cb;
		public IHttpTask<?> tks;
		private String path;
		private boolean sp;
		private boolean spNotMaapin = true;
		public String label;

		public WorkerForNewApi(IHttpCallback callback, IHttpTask<?> tasks,
				String filepath, boolean specialPL) {
			cb = callback;
			tks = tasks;
			path = filepath;
			sp = specialPL;
			label = tasks.getLabel();
		}

		public WorkerForNewApi(IHttpCallback callback, IHttpTask<?> tasks,
				String filepath, boolean specialPL, boolean spMaapin) {
			cb = callback;
			tks = tasks;
			path = filepath;
			sp = specialPL;
			spNotMaapin = false;
			label = tasks.getLabel();
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

			HttpURLConnection conn = null;
			try {
				if (tks.getType().equals(Constant.GET)
						|| tks.getType().equals(Constant.DELETE)) {
					String urlStr = HttpConfig.URL_SERVER_NEW_API
							+ reqHeadUrl(tks);
					conn = (HttpURLConnection) new URL(urlStr).openConnection();
					conn.setReadTimeout(20000);
					conn.setConnectTimeout(20000);
					conn.setRequestMethod(tks.getType());
					/*conn.setRequestProperty("Content-Type",
							"application/json; charset=utf-8");*/
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
					String urlStr = HttpConfig.URL_SERVER_NEW_API
							+ tks.getSubUrl();
					conn = (HttpURLConnection) new URL(urlStr).openConnection();
					conn.setReadTimeout(20000);
					conn.setConnectTimeout(20000);
					// conn.setRequestProperty("Host"
					// ,"apidev.jinyinmao.com.cn");
					conn.setRequestMethod(tks.getType());
					conn.setDoInput(true);
					conn.setDoOutput(true);
					/*conn.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded; charset=utf-8");*/
					conn.setRequestProperty("Content-Type",tks.getContentType());
					/*conn.setRequestProperty("Content-Type",
							"application/json; charset=utf-8");*/
					
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
						/*if (tks.getType().equals(Constant.GET)
								|| tks.getType().equals(Constant.DELETE)) {

							String errMsg = Util
									.getJsonString(jsonObject, "Message");
							cb.onError(errMsg);
						} else if (tks.getType().equals(Constant.POST)
								|| tks.getType().equals(Constant.PUT)) {
							JSONObject ModelState = jsonObject
									.getJSONObject("ModelState");
							Iterator iterator = ModelState.keys();
							String firstKey = (String) iterator.next();
							JSONArray array = ModelState.getJSONArray(firstKey);
							String errMsg = array.getString(0);
							cb.onError(errMsg);
						}*/
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

	private static class Worker implements Runnable {
		private IHttpCallback cb;
		public IHttpTask<?> tks;
		private String path;
		private boolean sp;
		private boolean spNotMaapin = true;
		public String label;

		public Worker(IHttpCallback callback, IHttpTask<?> tasks,
				String filepath, boolean specialPL) {
			cb = callback;
			tks = tasks;
			path = filepath;
			sp = specialPL;
			label = tasks.getLabel();
		}

		public Worker(IHttpCallback callback, IHttpTask<?> tasks,
				String filepath, boolean specialPL, boolean spMaapin) {
			cb = callback;
			tks = tasks;
			path = filepath;
			sp = specialPL;
			spNotMaapin = false;
			label = tasks.getLabel();
		}

		/*
		 * private String getURlString(IHttpTask task) {
		 * 
		 * StringBuilder sb = new StringBuilder();
		 * 
		 * if (task.getType().equals(Constant.GET)) {
		 * sb.append(HttpConfig.IMAGE_SERVER + task.getSubUrl()); if
		 * (task.getParams() != null) { sb.append("?"); for (int i = 0; i <
		 * task.getParams().length; i++) { sb.append(task.getParams()[i]);
		 * sb.append("&"); } sb.deleteCharAt(sb.length() - 1); }
		 * 
		 * } else if (task.getType().equals(Constant.POST)) {
		 * 
		 * } else if (task.getType().equals(Constant.PUT)) {
		 * 
		 * } else if (task.getType().equals(Constant.DELETE)) {
		 * 
		 * }
		 * 
		 * return sb.toString(); }
		 */

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

			HttpURLConnection conn = null;
			try {
				if (tks.getType().equals(Constant.GET)
						|| tks.getType().equals(Constant.DELETE)) {
					String urlStr = HttpConfig.URL_SERVER + reqHeadUrl(tks);
					conn = (HttpURLConnection) new URL(urlStr).openConnection();
					conn.setReadTimeout(20000);
					conn.setConnectTimeout(20000);
					conn.setRequestMethod(tks.getType());
					/*
					 * conn.setRequestProperty("Content-Type",
					 * "application/json; charset=utf-8");
					 */
					// conn.setRequestProperty("Host",
					// "apidev.jinyinmao.com.cn");
					if (!TextUtils
							.isEmpty(MyApplication.appContext.ampAuthToken)) {
						conn.setRequestProperty("AmpAuthToken",
								MyApplication.appContext.ampAuthToken);
					}

				/*	if (!TextUtils
							.isEmpty(MyApplication.appContext.goldCatAuthToken)) {
						conn.setRequestProperty("GoldCatAuthToken",
								MyApplication.appContext.goldCatAuthToken);
					}*/
					LogManager.d(TAG, urlStr);
				} else if (tks.getType().equals(Constant.POST)
						|| tks.getType().equals(Constant.PUT)) {
					String urlStr = HttpConfig.URL_SERVER + tks.getSubUrl();
					conn = (HttpURLConnection) new URL(urlStr).openConnection();
					conn.setReadTimeout(20000);
					conn.setConnectTimeout(20000);
					// conn.setRequestProperty("Host"
					// ,"apidev.jinyinmao.com.cn");
					conn.setRequestMethod(tks.getType());
					conn.setDoInput(true);
					conn.setDoOutput(true);
					conn.setRequestProperty("Contet-Type",
							"application/x-www-form-urlencoded");
					if (!TextUtils
							.isEmpty(MyApplication.appContext.ampAuthToken)) {
						conn.setRequestProperty("AmpAuthToken",
								MyApplication.appContext.ampAuthToken);
					}

					/*if (!TextUtils
							.isEmpty(MyApplication.appContext.goldCatAuthToken)) {
						conn.setRequestProperty("GoldCatAuthToken",
								MyApplication.appContext.goldCatAuthToken);
					}*/

					OutputStream out = conn.getOutputStream();
					LogManager.d(TAG, tks.getParams());
					out.write(tks.getParams().getBytes());
					out.flush();
					out.close();
				}

				int code = conn.getResponseCode();			
				if (code >= 500) {
					if (cb != null) {
						cb.onError("服务器正在维护中，请稍后再试。");
					}
					return;
				} else if (code >= 400 && code < 500) {
					if (cb != null) {
						cb.onError("请求失败。");
					}
					return;
				}

				if (TextUtils.isEmpty(MyApplication.appContext.ampAuthToken)) {
					String ampAuthToken = conn.getHeaderField("AmpAuthToken");
					if (!TextUtils.isEmpty(ampAuthToken)) {
						MyApplication.appContext.ampAuthToken = ampAuthToken;
						Store.puts(MyApplication.appContext, "ampAuthToken",
								ampAuthToken);
					}
				}

			/*	if (TextUtils
						.isEmpty(MyApplication.appContext.goldCatAuthToken)) {
					String goldCatAuthToken = conn
							.getHeaderField("GoldCatAuthToken");
					if (!TextUtils.isEmpty(goldCatAuthToken)) {

						MyApplication.appContext.goldCatAuthToken = goldCatAuthToken;
						Store.puts(MyApplication.appContext,
								"goldCatAuthToken", goldCatAuthToken);
					}
				}*/

				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String s = "";
				StringBuffer sb = new StringBuffer("");
				while ((s = br.readLine()) != null) {
					sb.append(s).append("\n");
				}
				br.close();
				String response = sb.toString();
				LogManager.d(TAG, response);
				//JSONObject jsonObject = new JSONObject(response);
				JSONObject cat = new JSONObject(response);
				/*JSONObject cat = jsonObject.getJSONObject("cat");
				String msg = cat.getString("msg");
				String status = cat.getString("status");*/
				String msg = cat.getString("msg");
				String status = cat.getString("status");
				if (status.equals("200")) {
					try {
						JSONArray data = cat.getJSONArray("data");
						cb.onGetData(tks.getData(data));
					} catch (Exception e) {
						try {
							JSONObject data = cat.getJSONObject("data");
							//data.put("msg", msg);
							cb.onGetData(tks.getData(data));
						} catch (JSONException e1) {
							cb.onGetData(msg);
						}
					}
					/*
					 * if(!notified){ try { JSONObject additional_options =
					 * cat.getJSONObject("additional_options"); JSONObject
					 * android_upgrade =
					 * additional_options.getJSONObject("android_upgrade");
					 * String and_status = Util.JsonTool(android_upgrade,
					 * "status"); String and_url =
					 * Util.JsonTool(android_upgrade, "url"); Intent it = new
					 * Intent(MyApplication.appContext, UpdateAct.class);
					 * it.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
					 * Intent.FLAG_ACTIVITY_NEW_TASK);
					 * 
					 * if(and_status.equals("0")){ it.putExtra("optional",
					 * true); } else if(and_status.equals("1")){
					 * it.putExtra("optional", false); } it.putExtra("url",
					 * and_url); MyApplication.appContext.startActivity(it); }
					 * catch (Exception e) { e.printStackTrace(); } notified =
					 * true; }
					 */

				} else {
					try {
						JSONObject jObject = new JSONObject(msg);
						Iterator iterator = jObject.keys();
						msg = jObject.getString((String) iterator.next());
					} catch (Exception e) {
					}
					cb.onError(msg);
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

	private static class HeadInfo {
		String updInfo;
		String dldUrl = "";
		int code;
		String mt;
		String[] appInfo;
	}

	private static void close(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
