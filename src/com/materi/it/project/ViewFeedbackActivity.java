package com.materi.it.project;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.materi.it.app.R;
import com.materi.it.library.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ViewFeedbackActivity extends Activity {

	EditText txtName;
	EditText txtPrice;
	EditText txtDesc;
	EditText txtCreatedAt;
	Button btnSave;

	String pid;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// Single Feedback url
	// private static final String url_product_detials = "http://10.0.2.2/android_connect/get_product_details.php";
	private static final String url_product_detials = "http://pakar-software.com/database/materi-it/get_product_details.php";
	
	// URL to update Feedback
	// private static final String url_update_product = "http://10.0.2.2/android_connect/update_product.php";
	private static final String url_update_product = "http://pakar-software.com/database/materi-it/update_product.php";
	
	// URL to delete Feedback
	// private static final String url_delete_product = "http://10.0.2.2/android_connect/delete_product.php";
	private static final String url_delete_product = "http://pakar-software.com/database/materi-it/delete_product.php";
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCT = "product";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";
	private static final String TAG_PRICE = "price";
	private static final String TAG_DESCRIPTION = "description";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_feedback);
		
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

		// Save button
		btnSave = (Button) findViewById(R.id.btnSave);

		// Getting Feedback details from intent
		Intent i = getIntent();
		
		// Getting Feedback id (pid) from intent
		pid = i.getStringExtra(TAG_PID);

		// Getting complete Feedback details in background thread
		new GetProductDetails().execute();

		// Save button click event
		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Starting background task to update Feedback
				new SaveProductDetails().execute();
			}
		});
	}

	/**
	 * Background Async Task to Get complete Feedback details
	 * */
	class GetProductDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewFeedbackActivity.this);
			pDialog.setMessage("Loading Inbox. Please wait ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting Feedback details in background thread
		 * */
		@Override
		protected String doInBackground(String... params) {

			// Updating UI from Background Thread
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// Check for success tag
					int success;
					try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("pid", pid));

						// Getting Feedback details by making HTTP request
						// Note that Feedback details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_product_detials, "GET", params);

						// Check your log for json response
						Log.d("Single Product Details", json.toString());
						
						// JSON success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// Successfully received Feedback details
							JSONArray productObj = json
									.getJSONArray(TAG_PRODUCT); // JSON Array
							
							// Get first Feedback object from JSON Array
							JSONObject product = productObj.getJSONObject(0);

							// Feedback with this pid found
							// Edit Text
							txtName = (EditText) findViewById(R.id.inputName);
							txtPrice = (EditText) findViewById(R.id.inputPrice);
							txtDesc = (EditText) findViewById(R.id.inputDesc);

							// Display Tanu data in EditText
							txtName.setText(product.getString(TAG_NAME));
							txtPrice.setText(product.getString(TAG_PRICE));
							txtDesc.setText(product.getString(TAG_DESCRIPTION));

						}else{
							// Feedback with pid not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(String file_url) {
			// Dismiss the dialog once got all details
			pDialog.dismiss();
		}
	}

	/**
	 * Background Async Task to Save Tamu Details
	 * */
	class SaveProductDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewFeedbackActivity.this);
			pDialog.setMessage("Update Inbox ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving Feedback
		 * */
		@Override
		protected String doInBackground(String... args) {

			// Getting updated data from EditTexts
			String name = txtName.getText().toString();
			String price = txtPrice.getText().toString();
			String description = txtDesc.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_PID, pid));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_PRICE, price));
			params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));

			// Sending modified data through http request
			// Notice that update product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_product,
					"POST", params);

			// Check JSON success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// Successfully updated
					Intent i = getIntent();
					// Send result code 100 to notify about feedback update
					setResult(100, i);
					finish();
				} else {
					// Failed to update Feedback
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(String file_url) {
			// Dismiss the dialog once Feedback updated
			pDialog.dismiss();
		}
	}

	/**
	 * Background Async Task to Delete Feedback
	 * */
	class DeleteProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewFeedbackActivity.this);
			pDialog.setMessage("Delete Inbox ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Deleting Feedback
		 * */
		@Override
		protected String doInBackground(String... args) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("pid", pid));

				// Getting Feedback details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(
						url_delete_product, "POST", params);

				// Check your log for JSON response
				Log.d("Delete Product", json.toString());
				
				// JSON success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					// Feedback successfully deleted
					// Notify previous activity by sending code 100
					Intent i = getIntent();
					// Send result code 100 to notify about feedback deletion
					setResult(100, i);
					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(String file_url) {
			// Dismiss the dialog once Feedback deleted
			pDialog.dismiss();
		}
	}
}
