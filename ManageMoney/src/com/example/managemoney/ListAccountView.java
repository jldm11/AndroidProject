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

@SuppressLint("NewApi")
public class ListAccountView extends ListActivity {

	private AssetsPropertyReader assetsPropertyReader;
	private Context context;
	private Properties properties;
	private Speaker speaker;
	private List<Account> accountsList;
	private SessionManager session;
	Vibrator v;
	private int idUser = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		speaker = new Speaker(this);
		context = this;
		session = new SessionManager(context);
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		assetsPropertyReader = new AssetsPropertyReader(context);
		properties = assetsPropertyReader.getProperties("urls.properties");
		idUser = session.getUserDetails().idUser;
		updateAccountList(idUser);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				speaker.speakText("Movements for "
						+ (String) ((TextView) view).getText());
				v.vibrate(500);
				Intent i = new Intent(ListAccountView.this, MovementsList.class);
				// Get the idAccount
				int idAccount = searchAccount((String) ((TextView) view)
						.getText());
				i.putExtra("idAccount", idAccount);
				startActivity(i);
			}
		});
	}
	
	public void updateAccountList(int idUser){
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_account,
				setAccounts(idUser)));
	}

	private String[] setAccounts(int idUser) {
		accountsList = new ArrayList<Account>();
		String[] accounts = {};
		String[] request = { "GET", "account",
				properties.getProperty("getAccount") + idUser, "" };
		WebServiceClient wsClient = new WebServiceClient();
		wsClient.execute(request);
		try {
			JSONObject accountsJSON;
			accountsJSON = wsClient.get();
			JSONArray accountsJSONList = accountsJSON.getJSONArray("accounts");
			int numberOfAccounts = accountsJSONList.length();
			accounts = new String[numberOfAccounts];
			for (int i = 0; i < numberOfAccounts; i++) {
				JSONObject tempJSONobj = accountsJSONList.getJSONObject(i);
				Account tempAccount = new Account(
						tempJSONobj.getInt("idAccount"),
						tempJSONobj.getInt("idUser"),
						tempJSONobj.getString("status"),
						tempJSONobj.getString("accountName"));
				accountsList.add(tempAccount);
				accounts[i] = tempAccount.accountName;
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
		return accounts;
	}

	private int searchAccount(String name) {
		int idAccount = 0;
		for (Account account : accountsList) {
			if (account.accountName.equals(name)) {
				idAccount = account.idAccount;
				break;
			}
		}
		return idAccount;
	}

//	public void recordAccount(int idUser, String accountName, String status) {
//		String[] request = { "POST", "account",
//				properties.getProperty("insertAccount"),
//				idUser + "," + status + "," + accountName };
//		// Execute POST
//		WebServiceClient wsClient = new WebServiceClient();
//		wsClient.execute(request);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_account_view, menu);
		return super.onCreateOptionsMenu(menu);
		// return true;
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
			AddAccountPopup popup = new AddAccountPopup();
			popup.setIdUser(idUser);
			popup.setProperties(properties);
			popup.show(getFragmentManager(), "Add Account");
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
