package com.xgr.lalami;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpreferences extends CordovaPlugin {
	public static final String PUT_STRING = "put";
	public static final String GET_STRING = "get";
	public static final String REMOVE = "remove";
	public static final String CLEAR = "clear";
	public static String PREF_FILE = "";
	SharedPreferences SharedPref;
	SharedPreferences.Editor editor;

	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		SharedPref = this.cordova.getActivity().getApplicationContext().getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
		if(PUT_STRING.equals(action)){
			editor = SharedPref.edit();
			try{
				editor.putString(args.getString(0), args.getString(1));
				editor.commit();
			}catch (Exception e){
				callbackContext.error("Error editing Key " + args.getString(0) + " with value " + args.getString(1) + e.getMessage());
				return false;
			}
			callbackContext.success("Added Value " + args.getString(1) + " to Preferences key " + args.getString(0));
			return true;		
		}
		if(GET_STRING.equals(action)){									
			String KeyVal;			
			try{				
				if(SharedPref.contains(args.getString(0))){
					KeyVal = SharedPref.getString(args.getString(0), "");
					callbackContext.success(KeyVal);
					return true;
				}else{
					callbackContext.error("No data");
					return false;
				}
			}catch (Exception e){
				callbackContext.error("Could Not Retreive " + args.getString(0) + e.getMessage());
				return false;
			}
		}if(REMOVE.equals(action)){
			editor = SharedPref.edit();
			try{
				editor.remove(args.getString(0));
				editor.commit();
			}catch (Exception e){
				callbackContext.error("Error editing Key " + args.getString(0) + " with value " + args.getLong(1) + e.getMessage());
				return false;
			}
			callbackContext.success("Removed Value from Key " + args.getString(0));
			return true;		
		}
		if(CLEAR.equals(action)){
			editor = SharedPref.edit();
			try{				
				editor.clear();
				editor.commit();
			}catch (Exception e){
				callbackContext.error("Could Not Clear Shared preference File " + e.getMessage());
				return false;
			}
			callbackContext.success("Cleared preference File ");
			
			return true;
		}else{
			callbackContext.error("Invalid Action");
			return false;
		}
    }
}