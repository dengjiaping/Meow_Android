package com.widen.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.widen.application.MyApplication;
import com.widen.util.threads.ThreadPool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;



public final class ImageManager {
	private static final String TAG = "ImageManager";
	public static String CACHE_PATH ;
	/*
	 * public static String SDCARD_IMG_PATH = Environment
	 * .getExternalStorageDirectory() + "/mapin/mapin_images/";
	 */

	public static String SDCARD_IMG_PATH_JC = Environment
			.getExternalStorageDirectory() + "/amp/amp_images/";
	public static String LOGO_IMAGE_PATH;
	public static String START_IMAGE_PATH;
	private static ImageManager instance;
	private ThreadPool mPool;
	private Map<String, TaskRunnable> map;
	private int w;
	private int h;

	public static ImageManager getInstance() {
		if (instance == null) {
			synchronized (ImageManager.class) {
				if (instance == null) {
					instance = new ImageManager();

				}
			}
		}

		return instance;
	}

	public void cancelTask(String url, ImageCallback cb) {
		if (!TextUtils.isEmpty(url)) {
			synchronized (map) {
				TaskRunnable t = map.get(url);
				if (t != null) {
					t.list.remove(cb);
					if (t.list.isEmpty()) {
						t.abort = true;
					}
				}
			}
		}
	}

	private ImageManager() {
		map = new HashMap<String, TaskRunnable>();
		mPool = ThreadPool.getInstance();
		DisplayMetrics dis = MyApplication.appContext.getResources()
				.getDisplayMetrics();
		w = dis.widthPixels;
		h = dis.heightPixels;
		File f = new File(CACHE_PATH);
		if (!f.exists()) {
			f.mkdirs();
		}
	}

	/**
	 * 根据 url获取图片
	 * 
	 * @param context
	 *            上下�?
	 * @param url
	 *            url地址
	 * @param callback
	 *            回调
	 */
	public void getImage(Context context, final String url,
			final ImageCallback callback) {
		if (callback == null || TextUtils.isEmpty(url)) {
			if (callback != null) {
				callback.onGetImage(null, url);
			}
			return;
		}

		synchronized (map) {
			if (map.containsKey(url)) {
				addCallback(url, callback);
				return;
			}
		}

		File f = new File(url);
		if (f.exists()) {
			Bitmap bm = null;
			try {
				bm = getBitmapFromFile(f.getPath());
			} catch (OutOfMemoryError error) {
				Log.e(TAG, "FATLE ERROR:" + error.toString());
				return;
			}
			callback.onGetImageInUIThread(bm, url);
			return;
		}

		String fileName = getFileNameOfURL(url);
		String fpath = CACHE_PATH;
		boolean sdcardExist = Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
		if (sdcardExist) {
			fpath = SDCARD_IMG_PATH_JC;
			File p = new File(SDCARD_IMG_PATH_JC);
			if (!p.exists()) {
				p.mkdirs();
			}
		}
		f = new File(fpath + fileName);
		if (!f.exists() && sdcardExist) {
			File t = new File(CACHE_PATH + fileName);
			if (t.exists()) {
				f = t;
				t = null;
			}
		}
		if (!f.exists()) {
			// 文件不存在异步获�?
			TaskRunnable task = new TaskRunnable(callback, url, f);
			map.put(url, task);
			mPool.addTaskToFront(task);
		} else {
			Bitmap bm = null;
			try {
				bm = getBitmapFromFile(f.getPath());
			} catch (OutOfMemoryError error) {
				Log.e(TAG, "FATLE ERROR:" + error.toString());
				return;
			}
			if (bm == null) {
				// 文件有问�?
				if (f.delete()) {
					getImage(context, url, callback);
				}
			} else {
				callback.onGetImageInUIThread(bm, url);
			}
		}
	}

	/**
	 * 根据 url获取图片
	 * 
	 * @param context
	 *            上下�?
	 * @param url
	 *            url地址
	 * @param callback
	 *            回调
	 */
	public void getImage2(Context context, final String url,
			final ImageCallback callback) {
		if (callback == null || TextUtils.isEmpty(url)) {
			if (callback != null) {
				callback.onGetImage(null, url);
			}
			return;
		}

		synchronized (map) {
			if (map.containsKey(url)) {
				addCallback(url, callback);
				return;
			}
		}

		File f = new File(url.replaceAll("/", "").replace(":", "")
				.replace("http", ""));
		if (f.exists()) {
			Bitmap bm = null;
			try {
				bm = getBitmapFromFile(f.getPath());
			} catch (OutOfMemoryError error) {
				Log.e(TAG, "FATLE ERROR:" + error.toString());
				return;
			}
			callback.onGetImageInUIThread(bm, url);
			return;
		}

		String fileName = getFileNameOfURL(url.replaceAll("/", "")
				.replace(":", "").replace("http", ""));
		String fpath = CACHE_PATH;
		boolean sdcardExist = Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
		if (sdcardExist) {
			fpath = SDCARD_IMG_PATH_JC;
			File p = new File(SDCARD_IMG_PATH_JC);
			if (!p.exists()) {
				p.mkdirs();
			}
		}
		f = new File(fpath + fileName);
		if (!f.exists() && sdcardExist) {
			File t = new File(CACHE_PATH + fileName);
			if (t.exists()) {
				f = t;
				t = null;
			}
		}
		if (!f.exists()) {
			// 文件不存在异步获�?
			TaskRunnable task = new TaskRunnable(callback, url, f, false);
			map.put(url, task);
			mPool.addTaskToFront(task);
		} else {
			Bitmap bm = null;
			try {
				bm = getBitmapFromFile(f.getPath());
			} catch (OutOfMemoryError error) {
				Log.e(TAG, "FATLE ERROR:" + error.toString());
				return;
			}
			if (bm == null) {
				// 文件有问�?
				if (f.delete()) {
					getImage2(context, url, callback);
				}
			} else {
				callback.onGetImageInUIThread(bm, url);
			}
		}
	}

	/**
	 * 保证 名字不重复即�?
	 * 
	 * @param url
	 * @return
	 */
	private final String getFileNameOfURL(String url) {
		// URLEncoder.encode(url); //int i = url.lastIndexOf("/");i == -1 ? url
		// : url.substring(i + 1);
		
		//String str = url == null ? url : url.replaceAll("/", "");
		
		String str = url == null ? url : url.substring(url.lastIndexOf("/")+1);
		return str;
	}

	public String getFilePathByURL(String url) {
		if (!TextUtils.isEmpty(url)) {
			String fileName = getFileNameOfURL(url);

			File f = new File(SDCARD_IMG_PATH_JC + fileName);
			if (f.exists()) {
				return f.getPath();
			} else {
				f = new File(CACHE_PATH + fileName);
				if (f.exists()) {
					return f.getPath();
				}
			}
		}

		return "";
	}

	public Bitmap getImageFromLocal(Context context, final String url) {
		Bitmap bm = null;

		String fileName = getFileNameOfURL(url);
		File f = new File(SDCARD_IMG_PATH_JC + fileName);
		try {
			if (f.exists()) {
				bm = getBitmapFromFile(f.getPath());
			} else {
				f = new File(CACHE_PATH + fileName);
				if (f.exists()) {
					bm = getBitmapFromFile(f.getPath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bm;
	}

	public static final void cleanCache() {
		File f = new File(CACHE_PATH);
		delete(f);
	}

	private static void delete(File f) {
		if (f != null && f.exists()) {
			if (f.isDirectory()) {
				for (File ff : f.listFiles()) {
					delete(ff);
				}
			} else {
				f.delete();
			}
		}
	}

	public static interface ImageCallback {
		/**
		 * 获取到图片回�?
		 * 
		 * @param bm
		 *            网络出错返回null.
		 */
		void onGetImage(Bitmap bm, String url);

		void onGetImageInUIThread(Bitmap bm, String url);
	}

	private void addCallback(String url, ImageCallback call) {
		TaskRunnable t = map.get(url);
		if (t != null) {
			t.list.add(call);
		}
	}

	private class TaskRunnable implements Runnable {
		private String mUrl;
		private File mFile;
		private boolean abort;
		private List<ImageCallback> list;
		private boolean isSub = true;

		public TaskRunnable(ImageCallback cb, String url, File file) {
			list = new ArrayList<ImageCallback>();
			list.add(cb);
			mUrl = url;
			mFile = file;
		}

		public TaskRunnable(ImageCallback cb, String url, File file,
				boolean isSub) {
			list = new ArrayList<ImageCallback>();
			list.add(cb);
			mUrl = url;
			mFile = file;
			this.isSub = isSub;
		}

		@Override
		public void run() {
			synchronized (map) {
				if (abort) {
					map.remove(mUrl);
					return;
				}
			}

			HttpURLConnection conn = null;
			InputStream is = null;
			FileOutputStream fos = null;
			Bitmap bm = null;
			try {
				mFile.createNewFile();
				/*if (isSub) {
					conn = (HttpURLConnection) new URL(HttpConfig.IMAGE_SERVER
							+ mUrl).openConnection();
				} else {*/	
							
					conn = (HttpURLConnection) new URL(mUrl).openConnection();
				//}
				mFile.createNewFile();
				fos = new FileOutputStream(mFile);
				is = conn.getInputStream();
				byte[] buff = new byte[512];
				while (true) {
					int i = is.read(buff);
					if (i <= 0) {
						break;
					}
					fos.write(buff, 0, i);
				}
				fos.flush();
				if (list == null || list.isEmpty()) {
					return;
				}
				 bm = getBitmapFromFile(mFile.getPath());
			} catch (Exception e) {
				e.printStackTrace();
				mFile.delete();
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
				close(fos);
				close(is);
				synchronized (map) {
					map.remove(mUrl);
				}
			}
			for (ImageCallback cb : list) {
				if (cb != null) {
					cb.onGetImage(bm, mUrl);
				}
			}
		}

	}

	private Bitmap getBitmapFromFile(String path) {
		Bitmap bm = null;
		bm = BitmapFactory.decodeFile(path);
		return bm;
		/*Bitmap bm = null;
		Options op = new Options();
		op.inJustDecodeBounds = true;
		op.inTargetDensity = MyApplication.densityDpi;
		op.inDensity = 160;
		BitmapFactory.decodeFile(path, op);
		op.inJustDecodeBounds = false;
		
		if (op.outWidth > w) {
			op.inSampleSize = (int) ((float) op.outWidth / w + 1);
			bm = BitmapFactory.decodeFile(path, op);
			if (bm != null) {
				Bitmap temp = bm;
				bm = Bitmap.createScaledBitmap(bm, w,
						(int) (op.outHeight * w / (float) op.outWidth), true);
				  temp.recycle();
				 temp = null;
			}
		} else {
			bm = BitmapFactory.decodeFile(path, op);
		}
		return bm;*/
	}

	private void close(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static class BannerSize {
		private static String size;
		private static final String SIZE_SMALL = "240_30";
		private static final String SIZE_NORMAL = "320_40";
		private static final String SIZE_LARGE = "480_60";

		public static String getSize() {
			if (size == null) {
				int dpi = MyApplication.densityDpi;
				if (dpi == 120) {
					size = SIZE_SMALL;
				} else if (dpi == 160) {
					size = SIZE_NORMAL;
				} else if (dpi == 240) {
					size = SIZE_LARGE;
				} else {
					size = SIZE_LARGE;
				}
			}
			return size;
		}
	}

	public static class StartPageSize {
		private static String size;
		private static final String SIZE_SMALL = "320_480";
		private static final String SIZE_NORMAL = "320_480";
		private static final String SIZE_LARGE = "480_800";

		public static String getSize() {
			if (size == null) {
				int dpi = MyApplication.densityDpi;
				if (dpi == 120) {
					size = SIZE_SMALL;
				} else if (dpi == 160) {
					size = SIZE_NORMAL;
				} else if (dpi == 240) {
					size = SIZE_LARGE;
				} else {
					size = SIZE_LARGE;
				}
			}
			return size;
		}
	}

	public static class AdItemSize {
		private static String size;
		private static final String SIZE_SMALL = "60_60";
		private static final String SIZE_NORMAL = "80_80";
		private static final String SIZE_LARGE = "120_120";

		public static String getSize() {
			if (size == null) {
				int dpi = MyApplication.densityDpi;
				if (dpi == 120) {
					size = SIZE_SMALL;
				} else if (dpi == 160) {
					size = SIZE_NORMAL;
				} else if (dpi == 240) {
					size = SIZE_LARGE;
				} else {
					size = SIZE_LARGE;
				}
			}
			return size;
		}
	}

	public static class AdDetailSize {
		private static String size;
		private static final String SIZE_SMALL = "160_160";
		private static final String SIZE_NORMAL = "240_240";
		private static final String SIZE_LARGE = "320_320";

		public static String getSize() {
			if (size == null) {
				int dpi = MyApplication.densityDpi;
				if (dpi == 120) {
					size = SIZE_SMALL;
				} else if (dpi == 160) {
					size = SIZE_NORMAL;
				} else if (dpi == 240) {
					size = SIZE_LARGE;
				} else {
					size = SIZE_LARGE;
				}
			}
			return size;
		}
	}

	public static class UserIconSize {
		private static String size;
		private static final String SIZE_SMALL = "60_60";
		private static final String SIZE_NORMAL = "80_80";
		private static final String SIZE_LARGE = "120_120";

		public static String getSize() {
			if (size == null) {
				int dpi = MyApplication.densityDpi;
				if (dpi == 120) {
					size = SIZE_SMALL;
				} else if (dpi == 160) {
					size = SIZE_NORMAL;
				} else if (dpi == 240) {
					size = SIZE_LARGE;
				} else {
					size = SIZE_LARGE;
				}
			}
			return size;
		}
	}

	public static class BoutiqueItemSize {
		private static String size;
		private static final String SIZE_SMALL = "60_60";
		private static final String SIZE_NORMAL = "80_80";
		private static final String SIZE_LARGE = "120_120";

		public static String getSize() {
			if (size == null) {
				int dpi = MyApplication.densityDpi;
				if (dpi == 120) {
					size = SIZE_SMALL;
				} else if (dpi == 160) {
					size = SIZE_NORMAL;
				} else if (dpi == 240) {
					size = SIZE_LARGE;
				} else {
					size = SIZE_LARGE;
				}
			}
			return size;
		}
	}

	// 点评，签到，大喇叭，留言
	public static class OtherSize {
		private static String size;
		private static final String SIZE_SMALL = "240_160";
		private static final String SIZE_NORMAL = "360_270";
		private static final String SIZE_LARGE = "480_360";

		public static String getSize() {
			if (size == null) {
				int dpi = MyApplication.densityDpi;
				if (dpi == 120) {
					size = SIZE_SMALL;
				} else if (dpi == 160) {
					size = SIZE_NORMAL;
				} else if (dpi == 240) {
					size = SIZE_LARGE;
				} else {
					size = SIZE_LARGE;
				}
			}
			return size;
		}
	}

	/**
	 * 商户详情的图�?
	 */
	public static class ShopIconSizes {
		public static String getSize() {
			return "140_140";
		}
	}

	/**
	 * 商户应用图标
	 */
	public static class PoiIconSizes {
		public static String getSize() {
			return "114_114";
		}
	}

	public static class ThemeOneSizes {
		public static String GOODS_SIZE = "200_200";
		public static String PROM_SIZE = "480_178";
	}

	public static class ThemeTwoSizes {
		public static String ALL_SIZE = "140_140";
	}

	public static class ThemeThreeSizes {
		public static String ALL_SIZE = "200_200";
		public static String GOODS_SIZE_SMALL = "60_60";
	}

	public static class BizSize {
		public static String SMALL_SIZE = "80_80";
		public static String MIDDLE_SIZE = "120_120";
	}

	public static class GoodsSize {
		public static String getDetailSize() {
			return "320_320";
		}
	}

	public static class PromSize {
		public static String getDetailSize() {
			return "320_320";
		}
	}

	public static class LOGOSize {
		public static String getSize() {
			return "200_200";
		}
	}

	public static class ChannelImageSize {
		public static final String scan_size = "60_60";
		public static final String scan_size2 = "120_120";
		public static final String detail_size = "320_320";
	}

}
