package com.widen.widget;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.widen.R;
import com.widen.http.HttpConfig;
import com.widen.util.ImageManager;
import com.widen.util.ImageManager.ImageCallback;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;




public class URLImageView extends ImageView implements ImageCallback {

	private String url;
	private boolean ok = true;
	private Bitmap bitmap;
	private Drawable loading;
	private int angle;
	private Map<String, Bitmap> temps = new HashMap<String, Bitmap>();
	private Drawable def;
	private int resId;
	private IBtnLister mIBtnLister; 
	
	/**
	 * 自动适应布宽高按最小比例拉伸
	 */
	private boolean autoFit;

	/**
	 * 点击查看原图
	 */
	private boolean clickShowSrc;
	

	public URLImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public URLImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		loading = getResources().getDrawable(R.drawable.loading);
		def = getDrawable();
		if (attrs != null) {
			autoFit = attrs.getAttributeBooleanValue(null, "auto_fit", false);
			clickShowSrc = attrs.getAttributeBooleanValue(null, "auto_click",
					false);
			/*
			 * if (clickShowSrc) { setOnClickListener(new View.OnClickListener()
			 * {
			 * 
			 * @Override public void onClick(View v) { Intent it = new
			 * Intent(getContext(), ImageActivity.class);
			 * it.putExtra(IntentExtras.EXTRA_URL, url);
			 * it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
			 * Intent.FLAG_ACTIVITY_SINGLE_TOP); getContext().startActivity(it);
			 * } }); }
			 */
		}
	}

	public void setImageURL(String url) {
		if (TextUtils.isEmpty(url)) {
			this.url = url;
			setImageDrawable(def);
			ok = true;
		} else if (!url.equals(this.url)
				|| (bitmap != null && bitmap.isRecycled())) {
			if (!TextUtils.isEmpty(this.url)) {
				// 将bitmap放入缓存 , 离开时候清空.
				synchronized (this) {
					temps.put(this.url, bitmap);
				}
			}
			bitmap = null;
			if(!url.contains("http")){
				url = HttpConfig.IMAGE_SERVER + url;
			}
			this.url = url;
			ok = false;
			ImageManager.getInstance().getImage(getContext(), url, this);
		}
	}

	public void setImageURL2(String url) {
		if (TextUtils.isEmpty(url)) {
			this.url = url;
			setImageDrawable(def);
			ok = true;
		} else if (!url.equals(this.url)
				|| (bitmap != null && bitmap.isRecycled())) {
			if (!TextUtils.isEmpty(this.url)) {
				// 将bitmap放入缓存 , 离开时候清空.
				synchronized (this) {
					temps.put(this.url, bitmap);
				}
			}
			bitmap = null;
			
			this.url = url;
			ok = false;
			ImageManager.getInstance().getImage2(getContext(), url, this);
		}
	}

	/* 在Gallery中充满屏幕宽度的方法 */
	public void fullOfWidth(int width, int height, int screenWidth) {

		LayoutParams layoutParams = getLayoutParams();
		layoutParams.width = screenWidth;
		float s = (float) width / layoutParams.width;
		float h = (height / s);
		layoutParams.height = (int) h;
		this.setLayoutParams(layoutParams);

	}

	public String getImageURL() {
		return url;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		int w = getWidth();
		int h = getHeight();
		int imgW = loading.getIntrinsicWidth();
		int imgH = loading.getIntrinsicHeight();
		if (w >= imgW && h >= imgH) {
			loading.setBounds((w - imgW) / 2, (h - imgH) / 2, (w + imgW) / 2,
					(h + imgH) / 2);
		}

		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (def != null) {
			def.setAlpha(myAlpha);
		}
		if (ok) {
			if (bitmap != null && bitmap.isRecycled()) {
				setImageURL(url);
				if (bitmap == null || bitmap.isRecycled()) {
					setImageDrawable(def);

					super.onDraw(canvas);
					return;
				}
			}
			if (autoFit && getDrawable() != null) {
				Drawable d = getDrawable();
				float screenXY = getWidth() / (float) getHeight();
				int dw = d.getIntrinsicWidth();
				int dh = d.getIntrinsicHeight();

				float drawableXY = dw / (float) dh;
				// if (screenXY > drawableXY) {
				// int scaleH = dh * getWidth() / dw;
				// int top = (scaleH - getHeight()) / 2;
				// d.setBounds(0, -top, getWidth(), getHeight() + top);
				// } else {
				// int scaleW = dw * getHeight() / dh;
				// int left = (scaleW - getWidth()) / 2;
				// d.setBounds(-left, 0, left + getWidth(), getHeight());
				// }
				if (screenXY > drawableXY) {
					float scale = (float) getHeight() / dh;
					int w = (int) (dw * scale);
					d.setBounds((getWidth() - w) / 2, 0, (getWidth() + w) / 2,
							getHeight());
				} else {
					float scale = (float) getWidth() / dw;
					int h = (int) (dh * scale);
					d.setBounds(0, (getHeight() - h) / 2, getWidth(),
							(getHeight() + h) / 2);
				}
				d.draw(canvas);
				// super.onDraw(canvas);
			} else {
				super.onDraw(canvas);
			}
		} else {
			long t = SystemClock.uptimeMillis();
			angle = (int) ((t - drawTime) / 2);
			canvas.rotate(angle, getWidth() / 2, getHeight() / 2);
			loading.draw(canvas);
			postInvalidateDelayed(50);
		}

	}

	long drawTime = SystemClock.uptimeMillis();

	@Override
	public void onGetImage(final Bitmap bm, final String url) {
		Object[] obj = new Object[] { bm, url };
		mHandler.sendMessage(mHandler.obtainMessage(0, obj));

	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Object[] o = (Object[]) msg.obj;
			onGetImageInUIThread((Bitmap) o[0], (String) o[1]);
		}
	};

	private void cleanTemps() {
		synchronized (this) {
			if (temps != null) {
				Set<String> str = temps.keySet();
				for (String s : str) {
					Bitmap bm = temps.get(s);
					if (bm != null && !bm.isRecycled()) {
						bm.recycle();
					}
				}
				temps.clear();
			}
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		cleanTemps();
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}

	}

	private int myAlpha = 255;

	@Override
	public void setAlpha(int alpha) {
		myAlpha = alpha;
		super.setAlpha(alpha);
	}

	@Override
	public void onGetImageInUIThread(Bitmap bm, String url) {
		Log.i("URLImageView ", "url" + url);
		if (url.equals(this.url)) {
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			bitmap = bm;
			if (bm == null) {
				setImageDrawable(def);
			} else {
				setImageBitmap(bm);
			}

			invalidate();
			cleanTemps();
			ok = true;
		} else {

			if (bm != null) {
				bm.recycle();
			}
		}
	}

	// public void setImageResource(int resId) {
	// super(resId);
	// }

	@Override
	public void setImageResource(int resId) {
		// TODO Auto-generated method stub
		this.resId = resId;
		super.setImageResource(resId);
	}

	public int getImageResouce() {
		return this.resId;
	}
	
	public interface IBtnLister{
		public void setViewVisable();
	}
	
	public void setBtnVisListener(IBtnLister listener){
		mIBtnLister = listener;
	}
}
