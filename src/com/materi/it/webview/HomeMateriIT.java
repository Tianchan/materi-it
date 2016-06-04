package com.materi.it.webview;

import com.materi.it.app.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeMateriIT extends ListActivity {

	static final String[] MENU = new String[] { "Home",
		"Sitemap","Skripsi IT" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_materi_it, MENU));

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch (position) {
				case 0:
					Intent loadurl0 = new Intent(getApplicationContext(),
							UtamaMateriIT.class);
					
					startActivity(loadurl0);
					break;
				case 1:
					Intent loadurl1 = new Intent(getApplicationContext(),
							SitemapMateriIT.class);
					
					startActivity(loadurl1);
					break;
				case 2:
					Intent loadurl2 = new Intent(getApplicationContext(),
							SkripsiMateriIT.class);
					
					startActivity(loadurl2);
					break;


				}
			}
		});

	}
}