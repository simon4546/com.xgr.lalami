package com.xgr.lalami;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
//import org.apache.cordova.CordovaWebViewClient;

import com.bbxvip.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

public class SimonWebViewActivity extends Activity  implements CordovaInterface{
	private final String TAG = "ShopWebView";
	private CordovaWebView mWebView;
	private LinearLayout llLoading;
	private Button backView;
	private Button closeView;
	private String backActivity = "Main";
	private Context context;
	
	// Instance for CordovaInterface
	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	private boolean mAlternateTitle = false;
	private boolean bound;
	private boolean volumeupBound;
	private boolean volumedownBound;
	private CordovaPlugin activityResultCallback;
	private Object activityResultKeepRunning;
	private Object keepRunning;

	
	@SuppressWarnings("deprecation")
	@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_qq);
		context=this;
        
		Intent intent = this.getIntent();
		String urlStr = intent.getExtras().getString("url");
		String ordid = intent.getExtras().getString("ordid");
		String ordfee = intent.getExtras().getString("ordfee");
		String dealtitle = intent.getExtras().getString("dealtitle");
		try {
			urlStr+="?ordid=" + ordid + "&ordfee=" + ordfee + "&dealtitle=" + URLEncoder.encode(dealtitle, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		mWebView = (CordovaWebView) findViewById(R.id.qq_mywebview);
		
		Config.init(this);
		//mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		llLoading = (LinearLayout) findViewById(R.id.qq_myview);
		backActivity = getIntent().getStringExtra("backActivity");
		//mWebView.setVerticalScrollBarEnabled(true);
		//mWebView.setHorizontalScrollBarEnabled(false);
		
//		WebSettings webSettings = mWebView.getSettings();
//		webSettings.setJavaScriptEnabled(true);
//		webSettings.setDomStorageEnabled(true);
//		webSettings.setAppCacheMaxSize(1024*1024*8);  
//		String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();  
//		webSettings.setAppCachePath(appCachePath);  
//		webSettings.setAllowFileAccess(true);  
//		webSettings.setAppCacheEnabled(true); 
//		webSettings.setSupportZoom(false);
		//mWebView.requestFocus();
		if (savedInstanceState == null) {
			mWebView.loadUrl(urlStr);
	    } else {
	    	//mWebView.restoreState(savedInstanceState);
	    	//mWebView.loadUrlIntoView(savedInstanceState.getString("url"));
	    }
		System.out.println(urlStr.toString());
		Log.i(TAG, "WebView Starting....");
		llLoading.setVisibility(View.VISIBLE);
//		CordovaWebViewClient client = new CordovaWebViewClient(this,mWebView) {
//			@Override
//			public void onPageFinished(android.webkit.WebView view,
//					java.lang.String url) {
//				llLoading.setVisibility(View.GONE);
//				super.onPageFinished(view, url);
//			}
//
//			@Override
//			public void onPageStarted(WebView view, String url, Bitmap favicon) {
//				Log.i(TAG, "WebView onPageStarted...");
//				Log.i(TAG, "URL = " + url);
//
//				super.onPageStarted(view, url, favicon);
//			}
//		};
		//mWebView.setWebViewClient(client);
		// 返回按钮
		backView = (Button) findViewById(R.id.goback);
		backView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 关闭按钮
		closeView = (Button) findViewById(R.id.webclose);
		closeView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	@Override
	public Activity getActivity() {
		return this;
	}
	@Deprecated
	public Context getContext() {
	    return this;
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    mWebView.handleDestroy();
	}
	
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
	    super.onActivityResult(requestCode, resultCode, intent);
	    final CordovaPlugin callback = this.activityResultCallback;
	    if (callback != null) {
	        // Need to use background thread
	        this.getThreadPool().execute(new Runnable() {
	            public void run() {
	                callback.onActivityResult(requestCode, resultCode, intent);
	            }
	        });
	    }
	}
	
	@Override
	public void startActivityForResult(CordovaPlugin command, Intent intent,
			int requestCode) {
		this.activityResultCallback = command;
	    this.activityResultKeepRunning = this.keepRunning;

	    // If multitasking turned on, then disable it for activities that return
	    // results
	    if (command != null) {
	        this.keepRunning = false;
	    }

	    // Start activity
	    super.startActivityForResult(intent, requestCode);		
	}
	@Override
	public void setActivityResultCallback(CordovaPlugin plugin) {
		this.activityResultCallback = plugin;
	}

	@Override
	public Object onMessage(String id, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ExecutorService getThreadPool() {
		return threadPool;
	}
}
