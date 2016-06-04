package com.materi.it.bookmark;

import com.materi.it.app.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class W3Schools extends Activity {

	WebView wv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmark);

		wv = (WebView) findViewById(R.id.bookmark);
		WebSettings webSettings = wv.getSettings();
		webSettings.setJavaScriptEnabled(true);
		wv.setWebViewClient(new MyBrowser());
		wv.loadUrl("http://www.w3schools.com/");
	}
	
	 private class MyBrowser extends WebViewClient {
	       @Override
	        public  boolean shouldOverrideUrlLoading(WebView view, String url) {
	            view.loadUrl(url);
	            return true;
	        }
	 }
	 
	 public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		  if ((paramInt == 4) && (this.wv.canGoBack()))
		  	  {
			  this.wv.goBack();
			  return true;
		  	  }
		  finish();
		  return super.onKeyDown(paramInt, paramKeyEvent);
	  	  }
}