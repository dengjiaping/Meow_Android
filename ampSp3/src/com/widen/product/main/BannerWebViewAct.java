package com.widen.product.main;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.webkit.WebSettings;
import android.webkit.WebView;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.product.BaseActivity;
@EActivity(R.layout.banner_webview)
public class BannerWebViewAct extends BaseActivity{
	
	@Click
	public void back() {
		finish();
	}
	@ViewById
	WebView webview;
	
	@Extra("url")
	String url;
	
	@AfterViews
	public void afterViews() {	
	    
	        WebSettings webSettings = webview.getSettings();       
	        webSettings.setJavaScriptEnabled(true);           
	        webview.loadUrl(url);   
		
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("banner");
		MobclickAgent.onResume(this);		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("banner");
		MobclickAgent.onPause(this);
	}
	
}
