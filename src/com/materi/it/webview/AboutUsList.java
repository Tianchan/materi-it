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

public class AboutUsList extends ListActivity {

	static final String[] MENU = new String[] { "Materi-IT", "Copyright", "Project Info" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_about_us, MENU));

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent menu1 = new Intent(getApplicationContext(),
						AboutUs.class);
				switch (position) {
				case 0:

					menu1.putExtra("halaman", "materi.html");
				    startActivity(menu1);
					break;
				case 1:

					menu1.putExtra("halaman", "copyright.html");
					startActivity(menu1);
					break;

				case 2:

					menu1.putExtra("halaman", "info.html");
					startActivity(menu1);
					break;

				}
			}
		});

	}
}