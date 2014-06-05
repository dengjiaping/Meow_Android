package com.widen.util;

import android.util.Log;

public class LogManager {

	private static boolean isLogOpen = false;
	public static void i(String tag,String msg){
		if (isLogOpen) {
			Log.i(tag, msg);
		}
	}
	public static void d(String tag,String msg){
		if (isLogOpen) {
			Log.d(tag, msg);
		}
	}
	public static void e(String tag,String msg){
		if (isLogOpen) {
			Log.e(tag, msg);
		}
	}

}
