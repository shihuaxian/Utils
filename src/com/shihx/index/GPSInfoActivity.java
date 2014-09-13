package com.shihx.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * get gps location
 * info 
 * don't forget add permission
 * @author shihx1
 *
 */
public class GPSInfoActivity extends Activity {
	private String TAG = "GPSInfoActivity";
	private TextView infoTxt;
	LocationManager locationManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		infoTxt = new TextView(this);
		setContentView(infoTxt);
		/*LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		Criteria crit = new Criteria();
		crit.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = lm.getBestProvider(crit, true);
		Location loc = lm.getLastKnownLocation(provider);
		Toast.makeText(this, "loc is:"+loc, 1).show();*/
		openGPSSettings();
	}
	
	protected void onResume() {  
	    super.onResume();  
	    locationManager.requestLocationUpdates("gps", 60000, 1, locationListener);  
	}  
	  
	@Override  
	protected void onPause() {  
	    super.onPause();  
	    locationManager.removeUpdates(locationListener);  
	}  
	
	private void openGPSSettings(){
		LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		if(locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)){
			Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT).show();
			getLocation();
			return;
		}
		Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		startActivityForResult(intent, 12);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==12){
			getLocation();
		}
	}
	
	private void getLocation(){
		//LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		//查找服务信息
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria,true);// get info
		Log.i(TAG, "provider is:"+provider);
		Location location = locationManager.getLastKnownLocation(provider);//get location
		updateToNewLocation(location);
		locationManager.requestLocationUpdates(provider, 1* 1000, 500,locationListener);
	}
	
	//位置监听
    private LocationListener locationListener=new LocationListener() {
         
        /**
         * 位置信息变化时触发
         */
        public void onLocationChanged(Location location) {
        	updateToNewLocation(location);
            Log.i(TAG, "时间："+location.getTime());
            Log.i(TAG, "经度："+location.getLongitude());
            Log.i(TAG, "纬度："+location.getLatitude());
            Log.i(TAG, "海拔："+location.getAltitude());
        }
         
        /**
         * GPS状态变化时触发
         */
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
            //GPS状态为可见时
            case LocationProvider.AVAILABLE:
                Log.i(TAG, "当前GPS状态为可见状态");
                break;
            //GPS状态为服务区外时
            case LocationProvider.OUT_OF_SERVICE:
                Log.i(TAG, "当前GPS状态为服务区外状态");
                break;
            //GPS状态为暂停服务时
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.i(TAG, "当前GPS状态为暂停服务状态");
                break;
            }
        }
     
        /**
         * GPS开启时触发
         */
        public void onProviderEnabled(String provider) {
            Location location=locationManager.getLastKnownLocation(provider);
            updateToNewLocation(location);
        }
     
        /**
         * GPS禁用时触发
         */
        public void onProviderDisabled(String provider) {
        	updateToNewLocation(null);
        }
 
     
    };
	private void updateToNewLocation(Location location){
		if(location!=null){
			double latitude = location.getLatitude();
			double longTitude = location.getLongitude();
			infoTxt.setText("latitude："+latitude + "\n longtTitude:"+longTitude);
		}else{
			infoTxt.setText("can't get location");
		}
	}
}
