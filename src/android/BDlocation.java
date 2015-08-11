package com.xgr.lalami;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import java.util.HashMap;
import java.util.Map;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class BDlocation extends CordovaPlugin {
	public JSONObject jsonObj = new JSONObject();
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	public CallbackContext callback;
	public boolean result = false;
	private static final Map<Integer, String> ERROR_MESSAGE_MAP = new HashMap<Integer, String>();
	private static final String DEFAULT_ERROR_MESSAGE = "服务端定位失败";
	static {
		ERROR_MESSAGE_MAP.put(61, "GPS定位结果");
		ERROR_MESSAGE_MAP.put(62, "扫描整合定位依据失败。此时定位结果无效");
		ERROR_MESSAGE_MAP.put(63, "网络异常，没有成功向服务器发起请求。此时定位结果无效");
		ERROR_MESSAGE_MAP.put(65, "定位缓存的结果");
		ERROR_MESSAGE_MAP.put(66, "离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果");
		ERROR_MESSAGE_MAP.put(67, "离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果");
		ERROR_MESSAGE_MAP.put(68, "网络连接失败时，查找本地离线定位时对应的返回结果。");
		ERROR_MESSAGE_MAP.put(161, "表示网络定位结果");
	};
	@Override
	public boolean execute(String paramString, JSONArray paramJSONArray,final CallbackContext paramCallbackContext)
        throws JSONException {

		callback=paramCallbackContext;
		mLocationClient = new LocationClient(cordova.getActivity().getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(50000);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        mLocationClient.requestLocation();
        //paramCallbackContext.success("fuck");
        return true;
    }
	class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
		            return ;
			
			try {
				JSONObject coords = new JSONObject();
				coords.put("latitude", location.getLatitude());
				coords.put("longitude", location.getLongitude());
				coords.put("radius", location.getRadius());
				coords.put("city", location.getCity());
				jsonObj.put("coords", coords);

				int locationType = location.getLocType();

				jsonObj.put("locationType", locationType);
				jsonObj.put("code", locationType);
				jsonObj.put("message", getErrorMessage(locationType));
				
				switch (location.getLocType()) {
					case BDLocation.TypeGpsLocation:
						coords.put("speed", location.getSpeed());
						coords.put("altitude", location.getAltitude());
						jsonObj.put("SatelliteNumber",
								location.getSatelliteNumber());
						break;
	
					case BDLocation.TypeNetWorkLocation:
						jsonObj.put("addr", location.getAddrStr());
						break;
				}

				Log.d("BaiduLocationPlugin", "run: " + jsonObj.toString());
				callback.success(jsonObj);
				result = true;
			} catch (JSONException e) {
				callback.error(e.getMessage());
				result = true;
			}finally{
				if (mLocationClient != null && mLocationClient.isStarted()) {
					mLocationClient.stop();
					mLocationClient = null;
				}
			}
		}
	}
	@Override
	public void onDestroy() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
			mLocationClient = null;
		}
		super.onDestroy();
	}

	private void logMsg(String s) {
		System.out.println(s);
	}

	public CallbackContext getCallbackContext() {
		return callback;
	}

	public void setCallbackContext(CallbackContext callbackContext) {
		callback = callbackContext;
	}
	public String getErrorMessage(int locationType) {
		String result = ERROR_MESSAGE_MAP.get(locationType);
		if (result == null) {
			result = DEFAULT_ERROR_MESSAGE;
		}
		return result;
	}
	
}

