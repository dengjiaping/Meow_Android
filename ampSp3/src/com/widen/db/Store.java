package com.widen.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;

import com.widen.application.MyApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class Store {

	private static final String PACK_NAME = Store.class.getPackage().getName()
			+ "amp";

	private Store() {
	};

	private static SharedPreferences getSharedPreferences(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PACK_NAME, Context.MODE_PRIVATE);
		return sharedPreferences;
	}

	public static void puts(Context context, String key, String value) {
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		sharedPreferences.edit().putString(key, value).commit();
	}
	
	public static void puts(Context context, String key, int value) {
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		sharedPreferences.edit().putInt(key, value).commit();
	}
	
	public static void clear(Context context) {
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		sharedPreferences.edit().clear().commit();	
	}

	public static String gets(Context context, String key, String defVal) {
		return getSharedPreferences(context).getString(key, defVal);
	}
	
	public static int gets(Context context, String key, int defVal) {
		return getSharedPreferences(context).getInt(key, defVal);
	}

	public static void putsBoolean(Context context, String key, Boolean value) {
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		sharedPreferences.edit().putBoolean(key, value).commit();
	}

	public static Boolean getsBoolean(Context context, String key,
			Boolean defVal) {
		return getSharedPreferences(context).getBoolean(key, defVal);
	}

	public static boolean login(Context context, String userId, String mail,
			String userName) throws IOException {
		if (userId == null)
			return false;
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			HashMap<String, Object> map = new HashMap<String, Object>(2);
			map.put(KEY_USER_ID, userId);
			map.put(KEY_USER_MAIL, mail);
			map.put(KEY_USER_NAME, userName);
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);

			oos.writeObject(map);
			String str64 = new String(Base64.encodeBase64(baos.toByteArray()));
			editor.putString(KEY_DATA_MAP, str64);
		} finally {
			if (oos != null) {
				oos.close();
			}
			if (baos != null) {
				baos.close();
			}
		}

		return editor.commit();
	}

	@SuppressWarnings("unchecked")
	public static void login(MyApplication appContext) throws IOException,
			ClassNotFoundException {
		SharedPreferences preferences = getSharedPreferences(appContext);
		String str64 = preferences.getString(KEY_DATA_MAP, "");
		ObjectInputStream ois = null;
		ByteArrayInputStream bais = null;

		try {
			byte[] base64Bytes = Base64.decodeBase64(str64.getBytes());
			bais = new ByteArrayInputStream(base64Bytes);
			ois = new ObjectInputStream(bais);
			Object obj = ois.readObject();
			HashMap<String, Object> map = (HashMap<String, Object>) obj;

			// appContext.setSynList((ArrayList<SynInfo>)
			// map.get(KEY_SYN_LIST));
		} finally {
			if (ois != null) {
				ois.close();
			}
			if (bais != null) {
				bais.close();
			}
		}
	}

	public static final String KEY_PRIVICY_FOOTPRINT = "footprint";
	public static final String KEY_PRIVICY_MSG = "msg";
	public static final String KEY_PRIVICY_MAIL = "mail";
	public static final String KEY_USER_ID = "userId";
	public static final String KEY_SYN_LIST = "synList";
	public static final String KEY_SYS_TIME = "mmt";
	public static final String KEY_DATA_MAP = "dataMap";
	public static final String VERIFY_CODE = "verify_code";
	public static final String IS_SHOW = "is_show";
	public static final String KEY_USER_MAIL = "key_user_mail";
	public static final String KEY_USER_NAME = "userName";
	public static final String KEY_REAL_NAME = "realName";
	public static final String KEY_USER_PASSWORD = "key_user_password";

	public static final String KEY_USER_LOGO = "key_user_logo";
	public static final String KEY_LOGIN_INFO = "login_info";
	public static final String KEY_FIRST_ANIM = "isFirst";

	public static final String KEY_FIRST_BTN = "isFirstBtn";

	public static final String KEY_PRE_LOGININFO = "pre_login_info";

	public static final String[][] CONSTELLATION = { { "0", "保密" },

	{ "2", "水瓶座" },

	{ "4", "双鱼座" },

	{ "6", "白羊座" },

	{ "8", "金牛座" },

	{ "10", "双子座" },

	{ "12", "巨蟹座" },

	{ "14", "狮子座" },

	{ "16", "处女座" },

	{ "18", "天秤座" },

	{ "20", "天蝎座" },

	{ "22", "射手座" },

	{ "24", "魔羯座" } };

	public static String getConstellation(String id) {
		if (TextUtils.isEmpty(id) || !TextUtils.isDigitsOnly(id)) {
			return CONSTELLATION[0][1];
		}
		for (String[] str : CONSTELLATION) {
			if (str[0].trim().equals(String.valueOf(id))) {
				return str[1];
			}
		}
		return "";
	}

	// 存储对象类型
	public static boolean saveObject(Context context, String key, Object obj)
			throws IOException {
		if (obj == null)
			return false;
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			String str64 = new String(Base64.encodeBase64(baos.toByteArray()));
			editor.putString(key, str64);
		} catch (Exception e) {
		} finally {
			if (oos != null) {
				oos.close();
			}
			if (baos != null) {
				baos.close();
			}
		}

		return editor.commit();
	}

	// 获取对象类型
	public static Object getObject(Context context, String key)
			throws IOException {
		SharedPreferences preferences = getSharedPreferences(context);
		String str64 = preferences.getString(key, "");
		ObjectInputStream ois = null;
		ByteArrayInputStream bais = null;
		try {
			byte[] base64Bytes = Base64.decodeBase64(str64.getBytes());
			bais = new ByteArrayInputStream(base64Bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			return null;
		}
	}

	public static void remove(Context context, String key) {
		SharedPreferences preferences = getSharedPreferences(context);
		Editor editor = preferences.edit();
		editor.remove(key);
		editor.commit();
	}

}
