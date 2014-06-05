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

public class MPLocationService extends Service {

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
		manager = (LocationManager) getSystemService(LOCATION_SERVICE);
		try {
			manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000,
					10, ll);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
					2000, 10, ll);
		} catch (Exception e) {
			// TODO: handle exception
		}

		Location location = getLocationPrivider(manager);
		addTo(location);

	}

	private void addTo(Location location) {
		
		 * mAMapLocManager = LocationManagerProxy.getInstance(this); if
		 * (location == null) { for (final String provider :
		 * mAMapLocManager.getProviders(true)) { if
		 * (LocationProviderProxy.AMapNetwork.equals(provider)) {
		 * mAMapLocManager.requestLocationUpdates(provider, 2000, 10, al); } }
		 * return; } if (location.getLatitude() == 0 || location.getLongitude()
		 * == 0) { for (final String provider :
		 * mAMapLocManager.getProviders(true)) { if
		 * (LocationProviderProxy.AMapNetwork.equals(provider)) {
		 * mAMapLocManager.requestLocationUpdates(provider, 2000, 10, al); } }
		 * return; } saveLocation(location.getLatitude(),
		 * location.getLongitude());
		 

		if (location == null) {
			mAMapLocManager = LocationManagerProxy.getInstance(this);
			mAMapLocManager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork, 1000, 10, al);
			return;
		}
		if (location.getLatitude() == 0 || location.getLongitude() == 0) {
			mAMapLocManager = LocationManagerProxy.getInstance(this);
			mAMapLocManager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork, 1000, 10, al);
			return;
		}
		//saveLocation(location);

	}

	private void saveLocation(AMapLocation location) {
		
		
		MyApplication.appContext.gpsInfo = new GPSInfo();		
		MyApplication.appContext.gpsInfo.accuracy = location.getAccuracy() + "";
		MyApplication.appContext.gpsInfo.adCode = location.getAdCode() + "";
		MyApplication.appContext.gpsInfo.city = location.getCity() + "";
		MyApplication.appContext.gpsInfo.cityCode = location.getCityCode() + "";
		MyApplication.appContext.gpsInfo.latitude = location.getLatitude() ;
		MyApplication.appContext.gpsInfo.longitude = location.getLongitude() ;
		
		

		stopSelf();
	}

	private Location getLocationPrivider(LocationManager lm) {
		Location retLocation = null;
		try {
			retLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (retLocation == null) {
				retLocation = lm
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retLocation;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (manager != null) {
			manager.removeUpdates(ll);
			manager = null;
		}
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

	};

	private final LocationListener ll = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				//saveLocation(location.getLatitude(), location.getLongitude());
			}

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Location loc = null;
			if (LocationManager.GPS_PROVIDER.equals(provider)) {
				loc = ((LocationManager) getSystemService(Context.LOCATION_SERVICE))
						.getLastKnownLocation(provider);
			}
			if (loc != null) {
				//saveLocation(loc.getLatitude(), loc.getLongitude());
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	};
*/
}
