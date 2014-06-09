package com.example.managemoney;

//import java.util.List;

import java.util.Properties;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;

public class Register extends ActionBarActivity {
	// private Spinner spinner;
	private AssetsPropertyReader assetsPropertyReader;
	private Properties properties;
	private Speaker speaker;
	private Context context;
	private Vibrator v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		// addItemsOnSpinner();
		context = this;
		speaker = new Speaker(context);
		v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
		assetsPropertyReader = new AssetsPropertyReader(getApplicationContext());
		properties = assetsPropertyReader.getProperties("urls.properties");
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

	}

	public void recorUser(View view){
		Toast.makeText(getApplicationContext(), "signup", Toast.LENGTH_SHORT)
		.show();
		v.vibrate(500);
		speakSignUp();
	}
//	public void recordUser(View view) {
//		Toast.makeText(getApplicationContext(), "signup", Toast.LENGTH_SHORT)
//				.show();
//		// Get user data
//		EditText editTextName = (EditText) findViewById(R.id.registerName), editTextLastName = (EditText) findViewById(R.id.registerLastName), editTextEmail = (EditText) findViewById(R.id.registerEmail), editTextPassword = (EditText) findViewById(R.id.registerPassword);
//		Spinner spinnerCountry = (Spinner) findViewById(R.id.countrySpinner);
//		String name = editTextName.getText().toString(), lastName = editTextLastName
//				.getText().toString(), country = spinnerCountry
//				.getSelectedItem().toString(), email = editTextEmail.getText()
//				.toString(), password = editTextPassword.getText().toString();
//		String[] request = {
//				"POST",
//				"user",
//				properties.getProperty("insertUser"),
//				name + "," + lastName + "," + "" + "," + country + "," + email
//						+ "," + password };
//		// Execute POST
//		WebServiceClient wsClient = new WebServiceClient();
//		wsClient.execute(request);
//		speakSignUp();
//	}

	// Speak Actions
	public void speakName(View view) {
		v.vibrate(500);
		speaker.speakText("Type your name");
	}

	public void speakLastName(View view) {
		v.vibrate(500);
		speaker.speakText("Type your last name");
	}

	public void speakCountry(View view) {
		v.vibrate(500);
		speaker.speakText("Select your country");
	}

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

	// private void addItemsOnSpinner() {
	// spinner = (Spinner) findViewById(R.id.countrySpinner);
	//
	// Countries country = new Countries();
	// List<String> coun = country.getCountries();
	//
	// ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
	// android.R.layout.simple_spinner_item, coun);
	// dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// spinner.setAdapter(dataAdapter);
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_register,
					container, false);
			return rootView;
		}
	}

}
