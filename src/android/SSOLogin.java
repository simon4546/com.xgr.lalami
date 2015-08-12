package com.xgr.lalami;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class SSOLogin extends CordovaPlugin {
	public CallbackContext callback;

	public boolean execute(String paramString, JSONArray paramJSONArray,
			final CallbackContext paramCallbackContext) throws JSONException {
		Log.i("travel", "forAndroid：" + paramString);
		callback = paramCallbackContext;
		Platform platform = ShareSDK.getPlatform(this.cordova.getActivity(),
				SinaWeibo.NAME);
		;
		if (paramString.equals("loginQQInfo")) {
			platform = ShareSDK
					.getPlatform(this.cordova.getActivity(), QQ.NAME);
		}
		if (paramString.equals("loginWeiXinInfo")) {
			platform = ShareSDK.getPlatform(this.cordova.getActivity(),
					Wechat.NAME);
		}
		// weibo.SSOSetting(true);
		SSOPlatformActionListener spa = new SSOPlatformActionListener();
		platform.setPlatformActionListener(spa);
		platform.showUser(null);// 执行登录，登录后在回调里面获取用户资料

		return false;
	}

	public class SSOPlatformActionListener implements PlatformActionListener {

		@Override
		public void onCancel(Platform arg0, int arg1) {
			callback.error(500);
		}

		@Override
		public void onComplete(Platform platform, int action,
				HashMap<String, Object> res) {
			// 解析部分用户资料字段
			String id, name, description, profile_image_url;
			id = res.get("id").toString();// ID
			name = res.get("name").toString();// 用户名
			description = res.get("description").toString();// 描述
			profile_image_url = res.get("profile_image_url").toString();// 头像链接
			JSONObject coords = new JSONObject();
			try {
				coords.put("id", id);
				coords.put("name", name);
				coords.put("description", description);
				coords.put("profile_image_url", profile_image_url);
				callback.success(coords);
			} catch (JSONException e) {
				callback.error(500);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void onError(Platform arg0, int arg1, Throwable arg2) {
			callback.error(500);
		}

	}
}
