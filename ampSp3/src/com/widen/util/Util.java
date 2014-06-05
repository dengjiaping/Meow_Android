package com.widen.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;
import com.widen.db.Store;
import com.widen.http.HttpConfig;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;



public class Util {
	
	public static HashMap<String, String> sellingStatusMap = new HashMap<String, String>();
	
	
	static{
		sellingStatusMap.put("10","待售");
		sellingStatusMap.put("20","抢购");
		sellingStatusMap.put("30","已起息");
		sellingStatusMap.put("40","待还款");
		sellingStatusMap.put("50","还款中");
		sellingStatusMap.put("60","已结束");
	}
	
	
	public static HashMap<Integer, String> time = new HashMap<Integer, String>();
	
	
	static{
		time.put(0,"一月");
		time.put(1,"二月");
		time.put(2,"三月");
		time.put(3,"四月");
		time.put(4,"五月");
		time.put(5,"六月");
		time.put(6,"七月");
		time.put(7,"八月");
		time.put(8,"九月");
		time.put(9,"十月");
		time.put(10,"十一月");
		time.put(11,"十二月");
	}
	
	
	public static String getSizedImg(String imageSrc, String strSize) {
		if (TextUtils.isEmpty(imageSrc) || TextUtils.isEmpty(strSize)) {
			return imageSrc;
		}
		if (imageSrc.indexOf("/") == -1) {
			return imageSrc;
		} else if (new File(imageSrc).exists()) {
			return imageSrc;
		}
		String fileName = imageSrc.substring(imageSrc.lastIndexOf("/") + 1,
				imageSrc.length());
		String strPath = imageSrc.substring(0, imageSrc.lastIndexOf("/"));
		String str;
		try {
			str = strPath + "/" + strSize + "/"
					+ URLEncoder.encode(fileName.replace("%20", " "), "utf-8");
		} catch (UnsupportedEncodingException e) {
			str = strPath + "/" + strSize + "/"
					+ URLEncoder.encode(fileName.replace("%20", " "));
		}
		return str;
	}

	public static String getDeviceId(final Context context) {
		TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (!TextUtils.isEmpty(tManager.getDeviceId())) {
			return tManager.getDeviceId();
		} else {
			String tempStr = "";
			try {
				tempStr = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
			} catch (Exception e) {
			}
			if (!TextUtils.isEmpty(tempStr)) {
				return tempStr;
			} else {
				return getSelfDeviceId(context);
			}

		}
	}
	public static String getSelfDeviceId(Context context) {

		String deviceKey = "deviceKey";
		String deviceFile = "deviceFile";
		String temp = gets(context, deviceKey, "", deviceFile);
		if (!"".equals(temp)) {
			return temp;
		} else {
			String s = UUID.randomUUID().toString();
			save(context, deviceKey, "ega" + s, deviceFile);
			return "ega" + s;
		}

	}
	public static void save(Context context, String key, String value, String fileName) {
		SharedPreferences deviceSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		deviceSharedPreferences.edit().putString(key, value).commit();
	}

	public static String gets(Context context, String key, String defVal, String fileName) {
		SharedPreferences deviceSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return deviceSharedPreferences.getString(key, defVal);
	}
	public static void exit(Activity act) {
		act.finish();
		act.sendBroadcast(new Intent(act.getPackageName()));
//		act.stopService(new Intent(act.getApplicationContext(), MPLocationService.class));
		MobclickAgent.onKillProcess(act);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				System.exit(0);
			}
		}, 500);
	}
	
	
/*	public static void JsonUtil(String name,JSONObject object, String value){
		if(object.has(value)){
			try {
				name = object.getString(value);
			} catch (JSONException e) {
				System.out.println("err json " + name);
				e.printStackTrace();
			}
		}
	}*/
	
	
	public static JSONObject getJsonObj(JSONObject object , String value){
		JSONObject name = null;
			try {
				name = object.getJSONObject(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return name;
	}
	
	/*public static int getJsonInt(JSONObject object , String value){
		int name = 0;
		try {
		if(object.has(value)){
			
				name = object.getInt(value);
			 
				}
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}*/
	
	
	public static String getJsonString(JSONObject object , String value){
		String name = "";
		try {
		if(object.has(value)){
			
				name = object.getString(value);
			 
				}
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	
	
	public static boolean isEmpty(String string){
		String tExtraYield = string.replace("0", "").replace(".", "");

		return TextUtils.isEmpty(tExtraYield);
	}
	
	
	public static Boolean getJsonBoolean(JSONObject object , String value){
		Boolean name = false;
		try {
		if(object.has(value)){
			
				name = object.getBoolean(value);
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	
	public static void showToast(String String ,Context context){
		Toast.makeText(context, String, Toast.LENGTH_SHORT).show();
	}
	
	public static boolean isMobileNO(String mobiles){  
	      boolean flag = false;  
	      try{  
	       Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
	       Matcher m = p.matcher(mobiles);  
	       flag = m.matches();  
	      }catch(Exception e){  
	       flag = false;  
	      }  
	      return flag;  
	}  
	
	public static String toJpgUrl(String url){  
	      
		  StringBuilder sb = new StringBuilder();
		  sb.append(HttpConfig.URL_SERVER_NEW_API).deleteCharAt(HttpConfig.URL_SERVER_NEW_API.length()-1).append(url);
		
	      return sb.toString();  
	}  
	
	
	public static boolean check(String regEx,String data){  
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(data);
		return m.matches();
		
	}
	      
		
}
