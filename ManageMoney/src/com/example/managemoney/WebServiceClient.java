package com.example.managemoney;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;
import android.os.AsyncTask;

public class WebServiceClient extends AsyncTask<String, Void, JSONObject> {

	public JSONObject getJSONFromURL(String url) {
		JSONObject resultJson = null;
		String resultString = getStringFromURL(url);
		try {
			resultJson = new JSONObject(resultString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultJson;
	}

	private String getStringFromURL(String url) {
		InputStream inputStream = null;
		String resultString = "";
		try {
			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// convert input stream to string
			if (inputStream != null) {
				resultString = convertInputStreamToString(inputStream);
			} else
				resultString = "";
		} catch (Exception e) {
			Log.d("InputStream", e.getMessage());
		}
		return resultString;
	}

	public void setRegisterToDBwithURL(String url,
			List<BasicNameValuePair> parameters) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			// Add your data
			httppost.setEntity(new UrlEncodedFormEntity(parameters));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;
	}

	// This function will receive a String like this
	// request = [http method, entity, url, request parameters]
	@Override
	protected JSONObject doInBackground(String... request) {

		if (request[0] == "GET") {
			return getJSONFromURL(request[2]);
		} else {
			List<BasicNameValuePair> parameters = addPostParameters(request[1],
					request[3]);
			setRegisterToDBwithURL(request[2], parameters);
			return null;
		}

	}

	private List<BasicNameValuePair> addPostParameters(String entity,
			String parameters) {
		String[] parametersArray = parameters.split(",");
		String[] attributes;
		if (entity.equals("user")) {
			attributes = "name,lastName,address,country,email,password"
					.split(",");
		} else if (entity.equals("account")) {
			attributes = "idUser,status,accountName"
					.split(",");
		} else if (entity.equals("movement")) {
			attributes = "idAccount,amount,type,date"
					.split(",");
		} else {
			attributes = "idMovement,description,amount"
					.split(",");
		}
		// Make url parameters
		List<BasicNameValuePair> parametersPOST = new ArrayList<BasicNameValuePair>();
		for (int i = 0; i < parametersArray.length; i++) {
			parametersPOST.add(new BasicNameValuePair(attributes[i],
					parametersArray[i]));
		}

		return parametersPOST;
	}

}
