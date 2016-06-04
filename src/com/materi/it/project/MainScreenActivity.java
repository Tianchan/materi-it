package com.materi.it.project;

import com.materi.it.app.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainScreenActivity extends Activity{
	
	Button btnViewProducts;
	Button btnNewProduct;
	Button login;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        
        ImageView img = (ImageView)findViewById(R.id.imageView1);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.materi-it.com/"));
                startActivity(intent);
            }
        });
		
		// Buttons
		btnViewProducts = (Button) findViewById(R.id.btnViewProducts);
		btnNewProduct = (Button) findViewById(R.id.btnCreateProduct);
		login = (Button) findViewById(R.id.login);
		
		// View Feedback click event
		btnViewProducts.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching All Feedback Activity
				Intent i = new Intent(getApplicationContext(), FeedbackActivity.class);
				startActivity(i);
				
			}
		});
		
		// View Feedback click event
		btnNewProduct.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching create new Feedback activity
				Intent i = new Intent(getApplicationContext(), NewFeedbackActivity.class);
				startActivity(i);
				
			}
		});
		
	// View Feedback click event
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching create new Feedback activity
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);
				
			}
		});
		
	}
}