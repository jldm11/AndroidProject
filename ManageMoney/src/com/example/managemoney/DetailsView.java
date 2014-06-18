package com.example.managemoney;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
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

public class DetailsView extends ListActivity {
	private AssetsPropertyReader assetsPropertyReader;
	private Context context;
	private Properties properties;
	private Speaker speaker;
	Vibrator v;
	private List<Detail> detailsList;
	private int idMovement;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		speaker = new Speaker(this);
		context = this;
		assetsPropertyReader = new AssetsPropertyReader(context);
		properties = assetsPropertyReader.getProperties("urls.properties");
		Bundle bundle = getIntent().getExtras();
		idMovement = bundle.getInt("idMovement");
		updateDetails();
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				speaker.speakText((String) ((TextView) view).getText());
				v.vibrate(500);
				Toast.makeText(getApplicationContext(),
						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void updateDetails() {
		setListAdapter(new ArrayAdapter<String>(this, R.layout.detail_list,
				setDetails(idMovement)));
	}

	private String[] setDetails(int idMovement) {
		detailsList = new ArrayList<Detail>();
		String[] details = {};
		String[] request = { "GET", "detail",
				properties.getProperty("getDetail") + idMovement, "" };
		WebServiceClient wsClient = new WebServiceClient();
		wsClient.execute(request);
		try {
			JSONObject detailsJSON;
			detailsJSON = wsClient.get();
			JSONArray detailsJSONList = detailsJSON.getJSONArray("details");
			int numberOfdetails = detailsJSONList.length();
			details = new String[numberOfdetails];
			for (int i = 0; i < numberOfdetails; i++) {
				JSONObject tempJSONobj = detailsJSONList.getJSONObject(i);
				Detail tempDetail = new Detail(tempJSONobj.getInt("idDetail"),
						tempJSONobj.getInt("idMovement"),
						tempJSONobj.getString("description"),
						tempJSONobj.getDouble("amount"));
				detailsList.add(tempDetail);
				details[i] = tempDetail.description + ": $" + tempDetail.amount;
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
		return details;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details_view, menu);
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == R.id.addAccount) {
			AddDetailPopup popup = new AddDetailPopup();
			popup.setProperties(properties);
			popup.setIdMove(idMovement);
			popup.setList(this);
			popup.show(getFragmentManager(), "Add Detail");
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
			View rootView = inflater.inflate(R.layout.fragment_details_view,
					container, false);
			return rootView;
		}
	}

}
