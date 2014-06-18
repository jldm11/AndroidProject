package com.example.managemoney;

import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class AddAccountPopup extends DialogFragment {

	private int idUser = 0;
	private Properties properties;
	private ListAccountView list;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View v = inflater.inflate(R.layout.add_account_view, null);
		final View name = v.findViewById(R.id.new_account_name);

		builder.setTitle("Add new account");
		builder.setMessage("Please, fill the data");
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(v)
				// action buttons
				.setPositiveButton(R.string.add_button,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								String text = ((TextView) name).getText()
										.toString();
								recordAccount(getUserId(), text, "A");
								list.updateAccountList(getUserId());
								Toast.makeText(getActivity().getBaseContext(),
										"Account added", Toast.LENGTH_SHORT)
										.show();

							}
						})
				.setNegativeButton(R.string.cancel_button,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								AddAccountPopup.this.getDialog().cancel();
							}
						});

		return builder.create();

	}

	private int getUserId() {
		return this.idUser;
	}

	public void setIdUser(int id) {
		this.idUser = id;
	}

	public void setList(ListAccountView list) {
		this.list = list;
	}

	public void recordAccount(int idUser, String accountName, String status) {

		String[] request = { "POST", "account",
				properties.getProperty("insertAccount"),
				idUser + "," + status + "," + accountName };
		// Execute POST
		WebServiceClient wsClient = new WebServiceClient();
		wsClient.execute(request);
		// }
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}