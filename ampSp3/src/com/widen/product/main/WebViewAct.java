package com.widen.product.main;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat.ShareParams;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.application.MyApplication;
import com.widen.http.HttpConfig;
import com.widen.product.BaseActivity;
import com.widen.util.Util;
@EActivity(R.layout.webview)
public class WebViewAct extends BaseActivity{
	
	@ViewById
	WebView webview;	
	String url = HttpConfig.CURRENT_HOST + "app/share.cshtml";
	@Click
	public void back(){
		finish();
	}
	
	
	@AfterViews
	public void afterViews() {	
		/*if(MyApplication.appContext.dm.widthPixels <=480){
			jinyinmao_lay.setBackgroundResource(R.drawable.jinyinmao_bg);
			jinyinmao_icon.setBackgroundResource(R.drawable.jinyinmao_icon);
		}else{
			jinyinmao_lay.setBackgroundResource(R.drawable.jinyinmao_bg_100);
			jinyinmao_icon.setBackgroundResource(R.drawable.jinyinmao_icon_100);
		}*/
		
System.out.println("dpi   " + MyApplication.appContext.dm.densityDpi /2.54);		

DisplayMetrics dm = new DisplayMetrics();  
dm = getResources().getDisplayMetrics();  
  
float density  = dm.density;        // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）  
int densityDPI = dm.densityDpi;     

System.out.println("density   " + density);
System.out.println("densityDPI   " + densityDPI);

		MobclickAgent.onEvent(WebViewAct.this, "茶叶蛋页面");
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setDrawingCacheEnabled(true); 
		synCookies(this,url);
		webview.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, final String urlString) {
				// TODO Auto-generated method stub
				try {
					if(urlString.startsWith("jym://share?")){
						String string = urlString.substring(urlString.indexOf("?")+1,urlString.length());
						String data = URLDecoder.decode(string, "utf8");
						JSONObject jsonObject = new JSONObject(data);						
						String type = Util.getJsonString(jsonObject, "type");
						String title = 	Util.getJsonString(jsonObject, "title");	
						if(type.equals("weibo")){								
							MobclickAgent.onEvent(WebViewAct.this, "茶叶蛋分享");
							shareSina(title);								
						}else if(type.equals("weixin")){							
							MobclickAgent.onEvent(WebViewAct.this, "茶叶蛋分享");
							shareWechat(title);
						}else if(type.equals("pengyou")){						
							MobclickAgent.onEvent(WebViewAct.this, "茶叶蛋分享");
							shareWechatMoments(title);
							
						
							
							
							
						/*	Platform plat = null;
							ShareParams sp = new ShareParams();
							plat = ShareSDK.getPlatform(WebViewAct.this, "WechatMoments");		
							sp.setShareType(Platform.SHARE_IMAGE);	
							sp.setText(title);
							sp.setImageData(addWatermark(bmp,bitmap2,bitmap4));
							plat.share(sp);*/
							
						}						
					}
				} catch (Exception e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				return super.shouldOverrideUrlLoading(view, url);
			}
			
		});
		webview.loadUrl(url);
	}
	
	
	private void shareSina(String text){
		ShareParams sp = new ShareParams();		
		Platform plat = ShareSDK.getPlatform(this, "SinaWeibo");	
		sp.setText(text);		
		String url = "";
		try {
			url = saveMyBitmap("share_picture",captureWebView());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//showToast("分享失败");
		}
		
		if(TextUtils.isEmpty(url)){
			showToast("无法保存图片,分享失败");
			return;
		}
		
		
		sp.setImagePath(url);		
		plat.setPlatformActionListener(new PlatformActionListener() {
			
			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(-1);			
			}
			
			@Override
			public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(1);			
			}
			
			@Override
			public void onCancel(Platform arg0, int arg1) {
				// TODO Auto-generated method stub				
			}
		});		
		plat.share(sp);		
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			if(msg.what == 1){
				showToast("分享成功");
			}else{
				showToast("分享失败    ");
			}
		}
		
	};
	
	public String saveMyBitmap(String bitName,Bitmap mBitmap) throws Exception{
		 /* File f = new File("/sdcard/" + bitName + ".png");
		  if(f.exists()){
			  f.delete();
		  }
		  try {
		   f.createNewFile();
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   
		  }
		  FileOutputStream fOut = null;
		  try {
		   fOut = new FileOutputStream(f);
		  } catch (FileNotFoundException e) {
		   e.printStackTrace();
		  }
		  mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		  try {
		   fOut.flush();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  try {
		   fOut.close();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }*/
		
		File f = new File("/sdcard/" + bitName + ".png");
		  if(f.exists()){
			  f.delete();
		  }
		
		   f.createNewFile();
		  
		  FileOutputStream fOut = null;
		
		   fOut = new FileOutputStream(f);
		 
		  mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		 
		   fOut.flush();
		 
		
		   fOut.close();
		  
		  
		  
		  return f.getPath();
		 }
	
	private void shareWechat(String text){
		Platform plat = null;
		ShareParams sp = new ShareParams();
		plat = ShareSDK.getPlatform(this, "Wechat");		
		sp.setShareType(Platform.SHARE_IMAGE);	
		sp.setText(text);
		sp.setImageData(captureWebView());
		plat.share(sp);
	}
	
	public static float getDensity(double d,Context context) {
		float density = 0;//像素值
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int y = dm.densityDpi;
		density = (float) (d*(y/2.54));
		return density;
	}

	
	private void shareWechatMoments(String text){
		Platform plat = null;
		ShareParams sp = new ShareParams();
		plat = ShareSDK.getPlatform(this, "WechatMoments");		
		sp.setShareType(Platform.SHARE_IMAGE);	
		sp.setText(text);
		sp.setImageData(captureWebView());
		plat.share(sp);
	}
	

	
	private Bitmap captureWebView(){	
		Picture snapShot = webview.capturePicture();
		Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(),snapShot.getHeight(), Bitmap.Config.ARGB_8888);
		
		Canvas canvas = new Canvas(bmp);
		snapShot.draw(canvas);
		Bitmap  bitmap = BitmapFactory.decodeResource(WebViewAct.this.getResources(), R.drawable.jinyinmao_bg);
		Bitmap  bitmap3 = BitmapFactory.decodeResource(WebViewAct.this.getResources(), R.drawable.jinyinmao_icon);
		Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, bmp.getWidth(),  (int)(getDensity(0.9,WebViewAct.this)/(MyApplication.appContext.dm.widthPixels/bmp.getWidth())), true);
		Bitmap  bitmap4 =  Bitmap.createScaledBitmap(bitmap3, 320, 52, true);
		return addWatermark(bmp,bitmap2,bitmap4);
			
		/*Bitmap  bitmap = BitmapFactory.decodeResource(WebViewAct.this.getResources(), R.drawable.jinyinmao_bg);
		
System.out.println("w1   " + bmp.getWidth());	
System.out.println("dip   " + Dip2SpUtil.dip2px(WebViewAct.this, 23));	
				
				
		Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, bmp.getWidth(), Dip2SpUtil.dip2px(WebViewAct.this, 23)52(int)(bitmap.getHeight() * (bitmap.getWidth()/(float)bmp.getWidth())) , true);
		Bitmap  bitmap3 = BitmapFactory.decodeResource(WebViewAct.this.getResources(), R.drawable.jinyinmao_icon);
		Bitmap bitmap4 = Bitmap.createScaledBitmap(bitmap3, 320, 52 , true);*/
		
	/*	jinyinmao_lay.setDrawingCacheEnabled(true);     
		jinyinmao_lay.measure(     
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY),     
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY)); 
		
		jinyinmao_lay.layout(0, 0, jinyinmao_lay.getMeasuredWidth(),     
				Dip2SpUtil.dip2px(WebViewAct.this, 52));     
System.out.println("1   " + jinyinmao_lay.getWidth());
System.out.println("2   " + jinyinmao_lay.getHeight());
System.out.println("w1   " + webview.getHeight());
System.out.println("w2   " + webview.getWidth());
System.out.println("aaa   " + (webview.getWidth()/jinyinmao_lay.getWidth()));		
System.out.println("aaa   " + ((float)webview.getWidth()/jinyinmao_lay.getWidth()));
		jinyinmao_lay.buildDrawingCache();  
		Bitmap bitmap = jinyinmao_lay.getDrawingCache();	
		Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, webview.getWidth(), jinyinmao_lay.getHeight() * (webview.getWidth()/jinyinmao_lay.getWidth()) , true);
		*/
		//return addWatermark(bmp,bitmap2);

	}
	
	
    private Bitmap addWatermark(Bitmap src, Bitmap watermark,Bitmap watermark2) {  
        if (src == null || watermark  == null) {       	
            return src;  
        }  
          
        int sWid = src.getWidth();  
        int sHei = src.getHeight();  
        int wWid = watermark.getWidth();  
        int wHei = watermark.getHeight();  
        int wWid2 = watermark2.getWidth();  
        int wHei2 = watermark2.getHeight(); 


        
        if (sWid == 0 || sHei == 0) {  
            return null;  
        }  
        Bitmap bitmap = Bitmap.createBitmap(sWid, sHei, Config.ARGB_8888);  
        try {  
            Canvas cv = new Canvas(bitmap); 
            cv.drawBitmap(src, 0, 0, null);    
            cv.drawBitmap(watermark, 0, sHei-wHei, null);
/*System.out.println("qqq   " + (sHei-wHei + (wHei2)));  
System.out.println("oo   " + sHei);
System.out.println("rr   " + wHei);
System.out.println("ee   " + wHei2);*/
            cv.drawBitmap(watermark2, (sWid-wWid2)/2, (sHei- wHei2), null);
            cv.save(Canvas.ALL_SAVE_FLAG);  
            cv.restore();  
        } catch (Exception e) {  
            bitmap = null;  
            e.getStackTrace();  
        }  
        return bitmap;  
    }  
	
/*	private Bitmap captureWebView(View view){		
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        view.setDrawingCacheEnabled(false);
System.out.println("bitmap   " + bitmap);        
		
		view.setDrawingCacheEnabled(true);     
		view.measure(     
               MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),     
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));     
		view.layout(0, 0, view.getMeasuredWidth(),     
				view.getMeasuredHeight());     
   
		view.buildDrawingCache();  
		Bitmap bitmap = view.getDrawingCache();
System.out.println("bitmap   " + bitmap);		
        return bitmap;	
	}*/
	
	 public static void synCookies(Context context, String url) {  
		    CookieSyncManager.createInstance(context);  
		    CookieManager cookieManager = CookieManager.getInstance();  
		    cookieManager.setAcceptCookie(true);  
		   // cookieManager.removeSessionCookie();//移除  
		    cookieManager.setCookie(url, MyApplication.appContext.ampAuthToken);//cookies是在HttpClient中获得的cookie  
		    CookieSyncManager.getInstance().sync();  
		}  
	 
	 
	 @Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			MobclickAgent.onPageEnd("分享页");
			MobclickAgent.onPause(this);
		}

		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			
			MobclickAgent.onPageStart("分享页");
		    MobclickAgent.onResume(this);
		}
	 
}
