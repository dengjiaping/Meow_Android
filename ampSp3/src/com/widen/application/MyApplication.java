package com.widen.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.JSONException;
import org.json.JSONObject;

import com.widen.db.Store;
import com.widen.http.model.BaseListInfo;
import com.widen.util.ImageManager;
import com.widen.util.gps.GPSInfo;
import com.widen.util.gps.GaoDeLocationService;
import com.widen.util.gps.MPLocationService;

import android.R.integer;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;


public class MyApplication extends Application {
	public static MyApplication appContext;
	public static final String RT_MSG_ACTION = MyApplication.class.getPackage()
			.getName();
	
	public static boolean isNetworkAvailable;

	public static float density;
	public Boolean isLogin = false;


	public static int densityDpi;

	public static int launch_bg;

	public static DisplayMetrics dm;
	public static GPSInfo gpsInfo;
	
	
	public static String ampAuthToken;

	
	public static String phone;


	
	@Override
	public void onCreate() {		
		
		appContext = this;
		density = getResources().getDisplayMetrics().density;
		dm = getResources().getDisplayMetrics();
		densityDpi = getResources().getDisplayMetrics().densityDpi;

		ImageManager.CACHE_PATH = "/data/data/" + getPackageName()
				+ "/image_cache/";

//		ImageManager.cleanCache();


		delMapabcData();
		
		ampAuthToken = Store.gets(getApplicationContext(),
				"ampAuthToken", "");
		
		
		phone = Store.gets(getApplicationContext(),
				"phone", "");
		
	}

	public void saveLogin(String userIdT, String mailT, String userNameT) {
		try {
			Store.login(this, userIdT, mailT, userNameT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

/*	private void readLogin() {
		try {
			// Store.login(this);

			

			if (!TextUtils.isEmpty(appContext.userId)
					&& !"0".equals(appContext.userId)) {
				this.isLogin = true;
			}
		} catch (Exception e) {
			Log.d("login", "" + e);
		}
	}*/

/*	public static boolean isLogin() {
		return (!("0".equals(appContext.userId) || TextUtils
				.isEmpty(appContext.userId)));

	}*/

	public void logout() {
		/*this.userId = "0";
		saveLogin(userId, "", "");*/
		this.isLogin = false;
		Toast.makeText(this, "注销成功", Toast.LENGTH_SHORT).show();
	}

	private void delMapabcData() {
		File f = new File("/data/data/" + getPackageName() + "/files/Data.dat");
		if (f.exists()) {
			f.delete();
		}
		f = new File("/data/data/" + getPackageName() + "/files/Index.dat");
		if (f.exists()) {
			f.delete();
		}
		f = new File("/data/data/" + getPackageName()
				+ "/files/autonavi_api_1_store.data");
		if (f.exists()) {
			f.delete();
		}
		f = new File("/data/data/" + getPackageName()
				+ "/files/autonavi_api_2_store.data");
		if (f.exists()) {
			f.delete();
		}
		f = new File("/data/data/" + getPackageName()
				+ "/files/autonavi_api_3_store.data");
		if (f.exists()) {
			f.delete();
		}
	}
	
	public boolean isLogin(){
		return (!TextUtils.isEmpty(ampAuthToken));
	}
}
