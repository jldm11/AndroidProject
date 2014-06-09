package com.example.managemoney;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.managemoney.R.id;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private AssetsPropertyReader assetsPropertyReader;
	private Context context;
	private Properties properties;
	private Speaker speaker;
	private Vibrator v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		speaker = new Speaker(context);
		v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
		assetsPropertyReader = new AssetsPropertyReader(context);
		properties = assetsPropertyReader.getProperties("urls.properties");
		// check if you are connected or not
		if (!isConnected()) {
			Toast.makeText(getApplicationContext(), "You are NOT connected",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "You are connected",
					Toast.LENGTH_SHORT).show();
		}

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	// Hard coded
	public void login(View view) {
		Toast.makeText(getApplicationContext(), "Log in", Toast.LENGTH_SHORT)
				.show();
		v.vibrate(500);
		speaker.speakText("Logged in");
		// Intent i = new Intent(MainActivity.this, Accounts.class);
		Intent i = new Intent(MainActivity.this, ListAccountView.class);
		this.finish();
		startActivity(i);
	}

	// public void login(View view) {
	// EditText editTextEmail = (EditText) findViewById(R.id.dMail),
	// editTextPassword = (EditText) findViewById(R.id.dPass);
	// String email = editTextEmail.getText().toString(), password =
	// editTextPassword
	// .getText().toString();
	// // Verify data
	// //HttpAsyncTask getUserTask = new HttpAsyncTask();
	// String [] request = {"GET","user",properties.getProperty("getUser") +
	// email + "/"
	// + password,""};
	// WebServiceClient wsClient = new WebServiceClient();
	// wsClient.execute(request);
	// JSONObject userJSON;
	// try {
	// userJSON = wsClient.get();
	// String idUser = userJSON.get("idUser").toString();
	// if (idUser != "0") {
	// Toast.makeText(getApplicationContext(), "Loged in " + idUser,
	// Toast.LENGTH_SHORT).show();
	// speaker.speakText("Logged in");
	// // change activity
	// Intent i = new Intent(MainActivity.this, Register.class);
	// this.finish();
	// startActivity(i);
	// }
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ExecutionException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public void openSignUpActivity(View view) {
		Intent i = new Intent(MainActivity.this, Register.class);
		this.finish();
		startActivity(i);
		speakSignUp();
	}

	// Speak Actions
	public void speakEmail(View view) {
		v.vibrate(500);
		speaker.speakText("Type your email");
	}

	public void speakPassword(View view) {
		v.vibrate(500);
		speaker.speakText("Type your password");
	}

	public void speakSignUp() {
		v.vibrate(500);
		speaker.speakText("Sign Up");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// // if (id == R.id.action_settings) {
	// // return true;
	// // }
	// return super.onOptionsItemSelected(item);
	// }
	// private class HttpAsyncTask extends AsyncTask<String, Void, JSONObject> {
	// @Override
	// protected JSONObject doInBackground(String... urls) {
	//
	// return wsClient.getJSONFromURL(urls[0]);
	// }
	// }

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
