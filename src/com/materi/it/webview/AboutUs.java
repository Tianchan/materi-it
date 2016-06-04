package com.materi.it.webview;

import com.materi.it.app.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class AboutUs extends Activity {
	String content;
	String namafile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
	
		Intent intent = getIntent();

		namafile = intent.getStringExtra("halaman");
		namafile.lastIndexOf('.');

		content = "file:///android_asset/" + namafile;
		WebView wv;

		wv = (WebView) findViewById(R.id.webView1);
		WebSettings webSettings = wv.getSettings();
		webSettings.setJavaScriptEnabled(true);

		// wv.addJavascriptInterface(new WebAppInterface(this), "Android");

		wv.loadUrl(content);
	}

}
