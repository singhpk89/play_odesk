package com.singhpk.odeskmobileapp;


import com.google.ads.Ad;

import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;

import com.google.ads.AdView;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class OdeskActivity extends Activity implements AdListener{

	private WebView wView ;
	private boolean appLaunch = true;
	private TextView blankView;
	private AdView adView;
	private ProgressBar bar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_score_board);
		bar = (ProgressBar)findViewById(R.id.progressBar1);
		wView = (WebView)findViewById(R.id.webview);
		wView.loadUrl(AppConstant.STARTUP_LINK);
		wView.getSettings().setJavaScriptEnabled(true);
		wView.getSettings().setBuiltInZoomControls(true);
		wView.getSettings().setAllowFileAccess(true);
		wView.requestFocus(View.FOCUS_DOWN);
		wView.getSettings().setDatabaseEnabled(true);
		wView.getSettings().setDomStorageEnabled(true);
		wView.getSettings().setRenderPriority(RenderPriority.HIGH);
		wView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		blankView = (TextView)findViewById(R.id.blankview);
		wView.setWebViewClient(new SSLTolerentWebViewClient());

		adView = (AdView)findViewById(R.id.adView);
		AdRequest request = new AdRequest();
		adView.loadAd(request);


	}



	private class SSLTolerentWebViewClient extends WebViewClient {

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
			//setBlankViewHidden(false);

		}
		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed(); // Ignore SSL certificate errors
		}
		@Override
		public void onPageFinished(WebView view, String url) {
			//Progresss.stop();
			bar.setVisibility(View.GONE);
		}
		public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
			//setBlankViewHidden(false);
			//if(appLaunch){Progresss.start(OdeskActivity.this);appLaunch = false;}
			bar.setVisibility(View.VISIBLE);
			if(!isNetworkAvailable(OdeskActivity.this))
			{
				Toast.makeText(getApplicationContext(), "Oops!Check your network connection", Toast.LENGTH_LONG);
			}

		};



	}
	//checkNetwork Connection
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	public void onBackPressed() {
		if(wView.canGoBack()){
			wView.goBack();
		}else{
			finish();
		}
	}


	@Override
	protected void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveAd(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

}
