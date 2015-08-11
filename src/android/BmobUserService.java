package com.xgr.lalami;

import java.util.logging.Logger;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class BmobUserService extends CordovaPlugin {
	public boolean execute(String paramString, JSONArray paramJSONArray,
			final CallbackContext paramCallbackContext) throws JSONException {
		if (paramString.equals("sendMsg")) {
			/*RongIM.getInstance().sendTextMessage(
					ConversationType.PRIVATE, 
					paramJSONArray.getString(0), 
					"我挑逗了你一下", 
					new RongIMClient.SendMessageCallback() {
	            @Override
	            public void onSuccess(int messageId) {
	                Log.d("Send:", "success: " + messageId);
	            }

	            @Override
	            public void onError(int messageId, ErrorCode errorCode) {
	                Log.d("Send:", "error: " + messageId);
	            }

	            @Override
	            public void onProgress(int messageId, int percent) {
	                Log.d("Send:", "on progress: " + messageId + ", " + percent + "%");
	            }
	        });*/
			paramCallbackContext.success();
			return true;
		}
		/*if (paramString.equals("login")) {
			Log.d("BmobUserService", paramString);
			final BmobUserManager manager = ((CordovaApp) cordova.getActivity()).userManager;
			manager.login(paramJSONArray.getString(0),
					paramJSONArray.getString(1), new SaveListener() {
						@Override
						public void onSuccess() {
							Log.d("BmobUserService", "login success");
							paramCallbackContext.success(manager
									.getCurrentUserObjectId());
							return;
						}

						@Override
						public void onFailure(int errorcode, String arg0) {
							paramCallbackContext.error("login error");
							return;
						}
					});
		}
		
		
		if (paramString.equals("loginOut")) {
			BmobUserManager.getInstance(
					cordova.getActivity().getApplicationContext()).logout();
			paramCallbackContext.success();
			return true;
		}
		if (paramString.equals("reg")) {
			Log.d("BmobUserService", paramString);
			final BmobUserManager manager = ((CordovaApp) cordova.getActivity()).userManager;

			final BmobChatUser bu = new BmobChatUser();
			bu.setUsername(paramJSONArray.getString(0));
			bu.setPassword(paramJSONArray.getString(1));
			bu.setAvatar(paramJSONArray.getString(2));
			// 注册
			bu.signUp(cordova.getActivity(), new SaveListener() {

				@Override
				public void onSuccess() {
					if (manager != null)
						manager.bindInstallationForRegister(bu.getObjectId());
					paramCallbackContext.success(bu.getObjectId());
					return;
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					paramCallbackContext.error("register error");
					return;
				}
			});
		}*/

		return true;
	}
}
