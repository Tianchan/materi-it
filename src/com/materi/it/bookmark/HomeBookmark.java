package com.materi.it.bookmark;

import com.materi.it.app.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeBookmark extends ListActivity {

	static final String[] MENU = new String[] { "Google Search",
			"W3Schools", "AndroidHIVE", "Jago Coding", "Tutorial Republic", "Elislab", "PHP"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_bookmark, MENU));

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch (position) {				
					case 0:
						Intent loadurl3 = new Intent(getApplicationContext(),
								Google.class);
						
						startActivity(loadurl3);
						break;
					case 1:
						Intent loadurl4 = new Intent(getApplicationContext(),
								W3Schools.class);
						
						startActivity(loadurl4);
						break;
					case 2:
						Intent loadurl5 = new Intent(getApplicationContext(),
								AndroidHIVE.class);
						
						startActivity(loadurl5);
						break;
					case 3:
						Intent loadurl6 = new Intent(getApplicationContext(),
								JagoCoding.class);
						
						startActivity(loadurl6);
						break;
					case 4:
						Intent loadurl7 = new Intent(getApplicationContext(),
								TutorialRepublic.class);
						
						startActivity(loadurl7);
						break;
					case 5:
						Intent loadurl8 = new Intent(getApplicationContext(),
								ElisLab.class);
						
						startActivity(loadurl8);
						break;
					case 6:
						Intent loadurl9 = new Intent(getApplicationContext(),
								PHP.class);
						
						startActivity(loadurl9);
						break;

				}
			}
		});

	}
}