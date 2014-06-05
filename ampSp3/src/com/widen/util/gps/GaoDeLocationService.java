package com.widen.util.gps;


import com.widen.application.MyApplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GaoDeLocationService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/*private LocationManagerProxy mAMapLocManager;
	private LocationManager manager;
	private AMapLocation location;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		mAMapLocManager = LocationManagerProxy.getInstance(this);	
		mAMapLocManager.requestLocationUpdates(
				 LocationProviderProxy.AMapNetwork, 5000, 10, al);

	}

	private void saveLocation(AMapLocation location) {

		MyApplication.appContext.gpsInfo = new GPSInfo();
		MyApplication.appContext.gpsInfo.accuracy = location.getAccuracy() + "";
		MyApplication.appContext.gpsInfo.adCode = location.getAdCode() + "";
		MyApplication.appContext.gpsInfo.city = location.getCity() + "";
		MyApplication.appContext.gpsInfo.cityCode = location.getCityCode();
		MyApplication.appContext.gpsInfo.latitude = location.getLatitude();
		MyApplication.appContext.gpsInfo.longitude = location.getLongitude();


		stopSelf();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(al);
			mAMapLocManager.destory();
			mAMapLocManager = null;
		}
		
	
		
		super.onDestroy();
	}

	private final AMapLocationListener al = new AMapLocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLocationChanged(AMapLocation location) {
			if (location != null) {
				saveLocation(location);
			}

		}

	};*/

	

}
