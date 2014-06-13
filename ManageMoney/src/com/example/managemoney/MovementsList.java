package com.example.managemoney;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class MovementsList extends ListActivity {

	private AssetsPropertyReader assetsPropertyReader;
	private Context context;
	private Properties properties;
	private Speaker speaker;
	private List<Movement> movementsList;
	static final String[] MOVEMENTS = new String[] {
			"Income from others: $300", "Monthly payment: $1500",
			"February payment: $800" };
	Vibrator v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		speaker = new Speaker(this);
		context = this;
		assetsPropertyReader = new AssetsPropertyReader(context);
		properties = assetsPropertyReader.getProperties("urls.properties");
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_movement,
				setMovements("5")));// cambiar por idAccount
		v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				// Toast.makeText(getApplicationContext(),
				// ((TextView)view).getText(), Toast.LENGTH_SHORT).show();
				speaker.speakText("Details for "
						+ (String) ((TextView) view).getText());
				v.vibrate(500);
				Intent i = new Intent(MovementsList.this, DetailsView.class);
				startActivity(i);
			}
		});

	}

	private String[] setMovements(String idAccount) {
		movementsList = new ArrayList<Movement>();
		String[] movements = {};
		String[] request = { "GET", "movement",
				properties.getProperty("getMovement") + idAccount, "" };
		WebServiceClient wsClient = new WebServiceClient();
		wsClient.execute(request);
		try {
			JSONObject movementsJSON;
			movementsJSON = wsClient.get();
			JSONArray movementsJSONList = movementsJSON.getJSONArray("movements");
			int numberOfMovement = movementsJSONList.length();
			movements = new String[numberOfMovement];
			for (int i = 0; i < numberOfMovement; i++) {
				JSONObject tempJSONobj = movementsJSONList.getJSONObject(i);
				Movement tempMovement = new Movement(
						tempJSONobj.getInt("idMovement"),
						tempJSONobj.getInt("idAccount"),
						tempJSONobj.getDouble("amount"),
						tempJSONobj.getString("type"),
						tempJSONobj.getString("date"));
				movementsList.add(tempMovement);
				if(tempMovement.type == "I")
					movements[i] = "Entry: $" + tempMovement.amount.toString();
				else
					movements[i] = "Expense: $" + tempMovement.amount.toString();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movements;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movements_list, menu);
		return true;
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
			View rootView = inflater.inflate(R.layout.fragment_movements_list,
					container, false);
			return rootView;
		}
	}

}
