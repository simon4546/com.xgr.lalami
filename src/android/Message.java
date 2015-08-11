package com.xgr.lalami;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by simon on 14-10-17.
 */
public class Message extends CordovaPlugin {
    public boolean execute(String paramString, JSONArray paramJSONArray,final CallbackContext paramCallbackContext)
            throws JSONException {


        Log.i("travel", "forAndroid：" + paramString);


        if (paramString.equals("alipay")) {
            Context context = cordova.getActivity()
                    .getApplicationContext();
            Intent intent = new Intent(context, SimonWebViewActivity.class);
            intent.putExtra("url", paramJSONArray.getString(0));
            intent.putExtra("ordid", paramJSONArray.getString(1));
            intent.putExtra("ordfee", paramJSONArray.getString(2));
            intent.putExtra("dealtitle", paramJSONArray.getString(3));

            
            cordova.getActivity().startActivity(intent);
            paramCallbackContext.success();
            return true;
        }
       
        if (paramString.equals("toast")) {
            final String message = paramJSONArray.getString(0);
            final String duration = paramJSONArray.getString(1);
            final String position = paramJSONArray.getString(2);

            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    android.widget.Toast toast = android.widget.Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT);
                    if ("top".equals(position)) {
                        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 20);
                    } else  if ("bottom".equals(position)) {
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 20);
                    } else if ("center".equals(position)) {
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    } else {
                        paramCallbackContext.error("invalid position. valid options are 'top', 'center' and 'bottom'");
                        return;
                    }

                    if ("short".equals(duration)) {
                        toast.setDuration(android.widget.Toast.LENGTH_SHORT);
                    } else if ("long".equals(duration)) {
                        toast.setDuration(android.widget.Toast.LENGTH_LONG);
                    } else {
                        paramCallbackContext.error("invalid duration. valid options are 'short' and 'long'");
                        return;
                    }
                    toast.show();
                    paramCallbackContext.success();
                }
            });
        }

        return false;
    }
}
