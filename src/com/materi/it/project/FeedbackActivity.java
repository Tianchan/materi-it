package com.materi.it.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.materi.it.app.R;
import com.materi.it.library.JSONParser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FeedbackActivity extends ListActivity {

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> productsList;

	// URL to get all Tamu list
	// private static String url_all_products = "http://10.0.2.2/android_connect/get_all_products.php";
	
	private static String url_all_products = "http://pakar-software.com/database/materi-it/get_all_products.php";
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "products";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";

	// Tabel Feedback JSONArray
	JSONArray products = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_feedback);

		// Hashmap for ListView
		productsList = new ArrayList<HashMap<String, String>>();

		// Loading Feedback in Background Thread
		new LoadAllProducts().execute();

		// Get listview
		ListView lv = getListView();

		// On seleting single Feedback
		// Launching Edit Feedback Screen
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Getting values from selected ListItem
				String pid = ((TextView) view.findViewById(R.id.pid)).getText()
						.toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						ViewFeedbackActivity.class);
				
				// Sending pid to next activity
				in.putExtra(TAG_PID, pid);
				
				// Starting new activity and expecting some response back
				startActivityForResult(in, 100);
			}
		});

	}

	// Response from Edit Feedback Activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		if (resultCode == 100) {
			// if result code 100 is received 
			// Means user edited/deleted feedback
			// Reload this screen again
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

	}

	/**
	 * Background Async Task to Load all Feedback by making HTTP Request
	 * */
	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FeedbackActivity.this);
			pDialog.setMessage("Loading Inbox. Please wait ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * Getting All Feedback from url
		 * */
		@Override
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// Getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// Products found
					// Getting Array of Tamu
					products = json.getJSONArray(TAG_PRODUCTS);

					// Looping through All Tamu
					for (int i = 0; i < products.length(); i++) {
						JSONObject c = products.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(TAG_PID);
						String name = c.getString(TAG_NAME);

						// Creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// Adding each child node to HashMap key => value
						map.put(TAG_PID, id);
						map.put(TAG_NAME, name);

						// Adding HashList to ArrayList
						productsList.add(map);
					}
				} else {
					// No Tamu found
					// Launch Add New product Activity
					Intent i = new Intent(getApplicationContext(),
							NewFeedbackActivity.class);
					// Closing all previous activities
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
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
			// Dismiss the dialog after getting all products
			pDialog.dismiss();
			// Updating UI from Background Thread
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							FeedbackActivity.this, productsList,
							R.layout.list_feedback, new String[] { TAG_PID,
									TAG_NAME},
							new int[] { R.id.pid, R.id.name });
					// Updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}