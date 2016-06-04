package com.materi.it.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.materi.it.app.R;
import com.materi.it.bookmark.HomeBookmark;
import com.materi.it.library.UserFunctions;
import com.materi.it.webview.AboutUsList;
import com.materi.it.webview.HomeMateriIT;

public class DashboardActivity extends Activity {
	Button aboutus;
	Button WebMateriIT;
	Button bookmarklist;
	UserFunctions userFunctions;
	Button btnLogout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /**
         * Dashboard Screen for the application
         * */        
        
        // Check login status in database
        
        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){
        	setContentView(R.layout.member_area);
        	
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        	
        	WebMateriIT = (Button) findViewById(R.id.WebMateriIT);
        	bookmarklist = (Button) findViewById(R.id.bookmarklist);
            aboutus = (Button) findViewById(R.id.aboutus);
            
    		// View Feedback click event
            WebMateriIT.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View view) {
    				// Launching create new Feedback activity
    				Intent i = new Intent(getApplicationContext(), HomeMateriIT.class);
    				startActivity(i);
    				
    			}
    		});
            
         // View Feedback click event
            bookmarklist.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View view) {
    				// Launching create new Feedback activity
    				Intent i = new Intent(getApplicationContext(), HomeBookmark.class);
    				startActivity(i);
    				
    			}
    		});
            
    		// View Feedback click event
    		aboutus.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View view) {
    				// Launching create new Feedback activity
    				Intent i = new Intent(getApplicationContext(), AboutUsList.class);
    				startActivity(i);
    				
    			}
    		});
        	
        	
        	btnLogout = (Button) findViewById(R.id.btnLogout);
        	
        	btnLogout.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
				public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				userFunctions.logoutUser(getApplicationContext());
    				Intent login = new Intent(getApplicationContext(), LoginActivity.class);
    	        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	        	startActivity(login);
    	        	// Closing dashboard screen
    	        	finish();
    			}
    		});
        	
        }else{
        	// User is not logged in show login screen
        	Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(login);
        	// Closing dashboard screen
        	finish();
        }
        
    }
}