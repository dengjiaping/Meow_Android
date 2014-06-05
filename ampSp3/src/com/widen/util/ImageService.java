package com.widen.util;



import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

public class ImageService {
	
	public ExecutorService executors;
	
	public ImageService(){
		executors = Executors.newFixedThreadPool(10);
	}

	
	private Map<String, SoftReference<Bitmap>> imageCahce = new HashMap<String, SoftReference<Bitmap>>();
	
	public Bitmap getBitmap(final String str,final ImageCallback im,final int Size){
		
		
		
		if(imageCahce.containsKey(str)){
			
			SoftReference<Bitmap> softReference = imageCahce.get(str);
			if(softReference.get()!=null){
				Bitmap Bitmap = softReference.get();
				return Bitmap;
			}
		}
		
		
		final Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				
				im.imageLoaded((Bitmap)msg.obj, str);
				
			}
			
		};
		
		executors.execute(new Runnable() {
			
			@Override
			public void run() {
			
				Bitmap bitmap = getBitmap(str,Size);

				imageCahce.put(str, new SoftReference<Bitmap>(bitmap));
				Message message = handler.obtainMessage(0, bitmap);
				message.obj = bitmap;
				handler.sendMessage(message);
				
			}
		});
		
		
		return null;
	}
	
	
	
	public Bitmap getBitmap (String str,int Size){
		
		Bitmap bitmap = null;
		try{
		URL url = new URL(str);
	
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		InputStream in = conn.getInputStream();
	
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = Size;
		
		
		bitmap = BitmapFactory.decodeStream(in, null, opts);
		}catch(Exception e){
		
			e.printStackTrace();
		}
		
		
		
		
		
		 return bitmap;

	}
	
	
	public interface ImageCallback{
		public void imageLoaded(Bitmap imageBitmap ,String url);
	}

}
