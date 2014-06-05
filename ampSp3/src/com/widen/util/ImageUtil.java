package com.widen.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.widen.application.MyApplication;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.text.TextUtils;


public final class ImageUtil {

	public static void intentToGetImg(Activity act, int req, int w, int h) {

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("outputX", w);
		intent.putExtra("outputY", h);
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent = Intent.createChooser(intent, "请选择");
		act.startActivityForResult(intent, req);
	}

	public static void intentToGetImg(Activity act, int req) {

		Intent it = new Intent(Intent.ACTION_GET_CONTENT);
		it.setType("image/*");
		it = Intent.createChooser(it, "请选择");
		act.startActivityForResult(it, req);
	}

	public static void intentToGetImgForKaixin(Activity act, int req , int x , int y) {

		Intent it = new Intent(Intent.ACTION_GET_CONTENT);
		it.setType("image/*");
		it.putExtra("crop", "true");
		it.putExtra("outputX", x);
		it.putExtra("outputY", y);	
		it.putExtra("aspectX", 8);
		it.putExtra("aspectY", 3);
		it.putExtra("scale", true);
		it.putExtra("return-data", true);
		it.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		it = Intent.createChooser(it, "请选择");
		act.startActivityForResult(it, req);

	}

	public static void cropImgFromCamera(Activity act, Uri uri, int req , int x , int y ) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setClassName("com.android.camera", "com.android.camera.CropImage");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);
		intent.putExtra("outputX", x);
		intent.putExtra("outputY", y);
		intent.putExtra("aspectX", 8);
		intent.putExtra("aspectY", 3);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent = Intent.createChooser(intent, "请选择");
		act.startActivityForResult(intent, req);
	}

	public static void toGetImgFromCamera(Activity act, int req) {
		toGetImgFromCamera(act, req, null);
	}

	public static void toGetImgFromCamera(Activity act, int req, String toPath) {
		Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (isSDCardExists() && !TextUtils.isEmpty(toPath)) {
			File f = new File(toPath);
			f.getParentFile().mkdir();
			it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		}
		it = Intent.createChooser(it, "请选择");

		act.startActivityForResult(it, req);
	}

	public static String getImagePath(Context ctx, Uri uri) {
		Cursor cur = ctx.getContentResolver().query(uri,
				new String[] { MediaColumns.DATA }, null, null, null);
		String str = null;
		if (cur != null) {
			if (cur.moveToFirst()) {
				str = cur.getString(0);
			}
			cur.close();
		}
		return str;
	}

	public static Bitmap getBitmap(String path, int maxSize) {
		BitmapFactory.Options op = new Options();
		op.inJustDecodeBounds = true;
		maxSize *= MyApplication.density;
		BitmapFactory.decodeFile(path, op);
		float outW = op.outWidth;
		float outH = op.outHeight;
		float scale = Math.max(outW / maxSize, outH / maxSize);
		if (scale > 2) {
			op.inSampleSize = (int) scale;
		} else {
			scale = 1;
		}
		op.inJustDecodeBounds = false;
		Bitmap bm = BitmapFactory.decodeFile(path, op);
		if (bm != null) {
			Bitmap temp = bm;
			bm = Bitmap.createScaledBitmap(bm, (int) (outW / scale),
					(int) (outH / scale), true);
			temp.recycle();
			temp = null;
		}
		return bm;
	}

	public static Bitmap getBitmap(String path, int w, int h) {
		BitmapFactory.Options op = new Options();
		w *= MyApplication.density;
		h *= MyApplication.density;
		op.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, op);
		float outW = op.outWidth;
		float outH = op.outHeight;
		float scale = Math.max(outW / w, outH / h);
		if (scale > 2) {
			op.inSampleSize = (int) scale;
		} else {
			scale = 1;
		}
		op.inJustDecodeBounds = false;
		Bitmap bm = BitmapFactory.decodeFile(path, op);
		if (bm != null) {
			Bitmap temp = bm;
			bm = Bitmap.createScaledBitmap(bm, w, h, true);
			temp.recycle();
			temp = null;
		}
		return bm;
	}

	public static boolean saveBitmap(Bitmap bm, String path) {
		if (!isSDCardExists()) {
			return false;
		}

		File f = new File(path);
		f.getParentFile().mkdirs();
		if (f.exists()) {
			f.delete();
		}
		FileOutputStream fos = null;
		try {
			f.createNewFile();
			fos = new FileOutputStream(f);
		} catch (IOException e) {
			return false;
		}

		bm.compress(CompressFormat.PNG, 0, fos);

		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public static boolean isSDCardExists() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	public static Bitmap getScaleBm(int max, Bitmap bm) {
		max *= MyApplication.density;
		Bitmap temp = bm;
		if (bm.getWidth() > bm.getHeight()) {
			bm = Bitmap.createScaledBitmap(bm, max,
					(int) ((float) bm.getHeight() * max / bm.getWidth()), true);
		} else {
			bm = Bitmap.createScaledBitmap(bm, (int) ((float) bm.getWidth()
					* max / bm.getHeight()), max, true);
		}
		temp.recycle();
		temp = null;
		return bm;
	}

	private ImageUtil() {
	}
}
