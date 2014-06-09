package com.example.managemoney;

import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class ListAccountView extends ListActivity {

	private Speaker speaker;
	static final String[] ACCOUNTS = new String[] { "Debit Card",
			"Work payments", "School payments", "Home expenses" };
	Vibrator v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		speaker = new Speaker(this);
		// setContentView(R.layout.activity_list_account_view);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_account,
				ACCOUNTS));
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				// Toast.makeText(getApplicationContext(),
				// ((TextView)view).getText(), Toast.LENGTH_SHORT).show();
				speaker.speakText("Movements for "+ (String)((TextView)view).getText());
				v.vibrate(500);
				Intent i = new Intent(ListAccountView.this, MovementsList.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_account_view, menu);
		return super.onCreateOptionsMenu(menu);
		// return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_list_account_view, container, false);
			return rootView;
		}
	}

}
