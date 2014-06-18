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
	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		session = new SessionManager(context);
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

	public void speakRegisterScreen() {
		v.vibrate(500);
		speaker.speakText("Sign Up, Please entry your data to register you as a new user");
	}

	public void login(View view) {
		EditText editTextEmail = (EditText) findViewById(R.id.dMail), editTextPassword = (EditText) findViewById(R.id.dPass);
		String email = editTextEmail.getText().toString(), password = editTextPassword
				.getText().toString();
		// Verify data
		// HttpAsyncTask getUserTask = new HttpAsyncTask();
		String[] request = { "GET", "user",
				properties.getProperty("getUser") + email + "/" + password, "" };
		WebServiceClient wsClient = new WebServiceClient();
		wsClient.execute(request);
		JSONObject userJSON;
		try {
			userJSON = wsClient.get();
			String idUser = userJSON.get("idUser").toString();
			if (!idUser.equals("0")) {
				Toast.makeText(getApplicationContext(), "Loged in " + idUser,
						Toast.LENGTH_SHORT).show();
				v.vibrate(500);
				speaker.speakText("Logged in");
				session.createLoginSession(Integer.parseInt(idUser),
						userJSON.getString("name"), userJSON.getString("email"));
				// change activity
				Intent i = new Intent(MainActivity.this, ListAccountView.class);
				startActivity(i);
				this.finish();
			} else {
				editTextEmail.setText("");
				editTextPassword.setText("");
				findViewById(R.id.dMail).requestFocus();
				Toast.makeText(getApplicationContext(), "Wrong data",
						Toast.LENGTH_SHORT).show();
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
	}

	public void openSignUpActivity(View view) {
		Intent i = new Intent(MainActivity.this, Register.class);
		this.finish();
		startActivity(i);
		speakRegisterScreen();
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
