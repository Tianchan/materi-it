package com.materi.it.project;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.materi.it.app.R;
import com.materi.it.library.JSONParser;
import com.materi.it.library.Validation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

public class NewFeedbackActivity extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;
	
    private EditText NamaText;
    private EditText HpText;
    private EditText FeedbackText;
    private Button btnSubmit;

	JSONParser jsonParser = new JSONParser();
	EditText inputName;
	EditText inputPrice;
	EditText inputDesc;

	// URL to create new Feedback
	// private static String url_create_product = "http://10.0.2.2/android_connect/create_product.php";
	private static String url_create_product = "http://pakar-software.com/database/materi-it/create_product.php";
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_feedback);

		// Edit Text
		inputName = (EditText) findViewById(R.id.inputName);
		inputPrice = (EditText) findViewById(R.id.inputPrice);
		inputDesc = (EditText) findViewById(R.id.inputDesc);
		
        registerViews();
	}
	
	private void registerViews() {
		NamaText = (EditText) findViewById(R.id.inputName);
        // TextWatcher would let us check validation error on the fly
		NamaText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(NamaText);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
		
		HpText = (EditText) findViewById(R.id.inputPrice);
        // TextWatcher would let us check validation error on the fly
		HpText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(HpText);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
		
		FeedbackText = (EditText) findViewById(R.id.inputDesc);
        // TextWatcher would let us check validation error on the fly
		FeedbackText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(FeedbackText);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
 
        btnSubmit = (Button) findViewById(R.id.btnCreateProduct);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Validation class will check the error and display the error on respective fields
                but it won't resist the form submission, so we need to check again before submit
                 */
                if (checkValidation())
                    submitForm();
                else
                    Toast.makeText(NewFeedbackActivity.this, "Periksa kembali formnya, jangan ada yang kosong.", Toast.LENGTH_LONG).show();
            }
        });
    }
 
    private void submitForm() {
    	
    	Toast.makeText(this, "Form sudah valid, silahkan klik kirim lagi.", Toast.LENGTH_LONG).show();
		
    	// Create button
		Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);
    	
		// Button click event
		btnCreateProduct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new Feedback in background thread
				new CreateNewProduct().execute();
			}
		});
    }
 
    private boolean checkValidation() {
        boolean ret = true;
 
        if (!Validation.hasText(NamaText)) ret = false;
        if (!Validation.hasText(HpText)) ret = false;
        if (!Validation.hasText(FeedbackText)) ret = false;
        
        return ret;
    }
	
	/**
	 * Background Async Task to Create new Feedback
	 * */
	class CreateNewProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NewFeedbackActivity.this);
			pDialog.setMessage("Mengirim Feedback ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating Feedback
		 * */
		protected String doInBackground(String... args) {
			String name = inputName.getText().toString();
			String price = inputPrice.getText().toString();
			String description = inputDesc.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("price", price));
			params.add(new BasicNameValuePair("description", description));

			// Getting JSON Object
			// Note that create Tamu url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);
			
			// Check log cat for response
			Log.d("Create Response", json.toString());

			// Check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created Tamu
					Intent i = new Intent(getApplicationContext(), FeedbackActivity.class);
					startActivity(i);
					
					// Closing this screen
					finish();
				} else {
					// Failed to create Tamu
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// Dismiss the dialog once done
			pDialog.dismiss();
		}

	}
}