package com.widen.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.FutureTask;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.widen.R;
import com.widen.product.BaseActivity;
import com.widen.product.LaunchActivity;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class UpdateAct extends BaseActivity {
	private boolean optional;
	private Activity mAct;
	private static final String LOG_TAG = "AppUpdate";
	private final static int NETWORK_ERROR_DLG = 2009;
	private final static int NO_SD_DLG = 2008;

	private static final int MSG_REPORT = 1, MSG_CHECK = 2, MSG_NETWORK_ERROR = 3, MSG_COMPLETE = 4;
	private String url, pkgName;
	private DefaultHttpClient mHttpClient;
	private HttpGet mHttpGet;
	private Button cancelBtn, okBtn;
	private ProgressBar updps;

	private long downloaded = 0;
	private long fileSize;
	private boolean cancel;
	private String filePath;
	private Thread downloadThread;
	private NotificationManager mNM;
	
	private TextView textView;

	
	private String msg;
	ActivityManager am;
	
	@Override
	protected void initContext() {
		setContentView(R.layout.app_update);		
		textView = (TextView) findViewById(R.id.msgText);		
		mAct = this;
		go_on();
	}
	
	private void go_on(){
		Intent intent = getIntent();
		String updInfo = intent.getStringExtra(IntentExtras.UPD_INFO);
		optional = intent.getBooleanExtra("optional", true);
		url = intent.getStringExtra("url");
		msg = intent.getStringExtra("msg");
		
		if(!TextUtils.isEmpty(msg)){
			textView.setText(msg);
		}
		
		okBtn = (Button) findViewById(R.id.btnUpdate);
		cancelBtn = (Button) findViewById(R.id.btnCancel);
		updps = (ProgressBar) findViewById(R.id.updps);


		if (TextUtils.isEmpty(url)) {
			Toast.makeText(this, "url is empty", Toast.LENGTH_LONG).show();
			return;
		}
		am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		mHttpClient = new DefaultHttpClient();

		downloadThread = new Thread(new DownloadRunnable());
		downloadThread.setPriority(Thread.NORM_PRIORITY - 1);

		  cancelBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {				
				if (downloadThread.isAlive()) {	
					try {
						downloadThread.stop();
						cancel = true;
					} catch (Exception e) {
						// TODO: handle exception
					}					
				}
				
				if (!optional) {					
					Util.exit(mAct);
				}else{					
					setResult(1);
					finish();
				}
			}
		});

		okBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				
				if(checkSdard()){
					downloadThread.start();
					updps.setVisibility(View.VISIBLE);
					okBtn.setEnabled(false);
				}else{
					showDialog(NO_SD_DLG);
				}
			}
		});
		
		
		
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			try {
				switch (msg.what) {
				case MSG_REPORT: {
					updps.setProgress((int) ((downloaded * 100 / fileSize)));
					Log.d(LOG_TAG, "downloaded.." + downloaded + "  " + fileSize);
					Log.d(LOG_TAG, "pecent" + (int) ((downloaded * 100 / fileSize)) + "");
					break;
				}
				case MSG_NETWORK_ERROR: {
					Log.d(LOG_TAG, "process msg ");
					showDialog(NETWORK_ERROR_DLG);
					break;
				}
				case MSG_COMPLETE:
					okBtn.setEnabled(true);

				default:
					super.handleMessage(msg);
				}
			} catch (Exception e) {

			}
		}
	};

	private class DownloadRunnable implements Runnable {

		public void run() {
			try {			
				String filePath = download(url.trim());				
				if (filePath != null) {					
					if (cancel) {
						File file = new File(filePath);
						file.delete();
						return;
					}				
					installApp();				
				}
			} catch (Exception e) {
				Message msg = handler.obtainMessage(MSG_NETWORK_ERROR);
				handler.sendMessage(msg);
				Log.e(LOG_TAG, e.getMessage());
			}
		}
	}

	private String download(String url) throws Exception {	
		mHttpGet = new HttpGet(url);		
		HttpResponse response = mHttpClient.execute(mHttpGet);
		int statusCode = response.getStatusLine().getStatusCode();
		InputStream is;
		if (statusCode == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Header[] clHeaders = response.getHeaders("Content-Length");
			if (clHeaders.length > 0) {
				Header header = clHeaders[0];
				fileSize = Long.parseLong(header.getValue());
			}
		} else {
			Message msg = handler.obtainMessage(MSG_NETWORK_ERROR);
			handler.sendMessage(msg);
			return null;
		}
		BufferedInputStream bis = new BufferedInputStream(is);
		FileOutputStream fos = null;
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File file = new File(Environment.getExternalStorageDirectory() + "/amp");
			if (!file.exists()) {
				file.mkdir();
			}		
			String filename = url.substring(url.lastIndexOf("/") + 1);	
			filePath = file.getPath() + "/" + filename;
			Log.d(LOG_TAG, "save path.." + filePath);
			fos = new FileOutputStream(filePath);
		}else{
			fos = openFileOutput("amp.apk", MODE_WORLD_READABLE);
			filePath = getFilesDir() + "/amp.apk";		
		}
		
		
		
	
		byte[] bytes = new byte[1024];
		int len = bis.read(bytes);
		while (len > 0 && !cancel) {
			fos.write(bytes, 0, len);
			downloaded = downloaded + len;
			len = bis.read(bytes);
			Message msg = handler.obtainMessage(MSG_REPORT);
			handler.sendMessageDelayed(msg, 200);
		}
		bis.close();
		is.close();
		fos.flush();
		fos.close();

		Message msg = handler.obtainMessage(MSG_COMPLETE);
		handler.sendMessage(msg);

		return filePath;
	}

	private boolean checkSdard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	@Override
	public void onBackPressed() {
//		YeetouchUtil.exit(this);
//		super.onBackPressed();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case NETWORK_ERROR_DLG:
			final Dialog dialog = new AlertDialog.Builder(mAct).setIcon(android.R.drawable.ic_dialog_info).setTitle(
					"网络出现异常").setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					okBtn.setEnabled(true);
					downloadThread = new Thread(new DownloadRunnable());
					downloadThread.setPriority(Thread.NORM_PRIORITY - 1);
				}
			}).create();
			return dialog;
		case NO_SD_DLG: {
			final Dialog sddialog = new AlertDialog.Builder(mAct).setIcon(android.R.drawable.ic_dialog_info).setTitle(
					"未找到SD卡,升级可能失败,是否继续?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					downloadThread.start();
					updps.setVisibility(View.VISIBLE);
					okBtn.setEnabled(false);
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					if (downloadThread.isAlive()) {
						downloadThread.stop();
						cancel = true;
					}
					if (!optional) {
						Util.exit(mAct);
					}
					setResult(1);
					finish();
				}
			}).create();
			return sddialog;
		}

		}
		return super.onCreateDialog(id);
	}

	private void installApp() {
		Intent intent = new Intent();
		intent.putExtra("filePath", filePath);
		setResult(2,intent);
		finish();
	}

}
