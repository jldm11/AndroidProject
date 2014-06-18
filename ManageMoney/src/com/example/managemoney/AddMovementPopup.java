package com.example.managemoney;

import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class AddMovementPopup extends DialogFragment {

	private Properties properties;
	private int idAccount;
	private MovementsList list;


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View v = inflater.inflate(R.layout.add_movement_view, null);

		final View movAmount = v.findViewById(R.id.new_movement_amount);
		final Spinner movType = (Spinner)v.findViewById(R.id.movement_type);

		builder.setTitle("Add new movement");
		builder.setMessage("Please, fill the data");
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(v)
				// action buttons
				.setPositiveButton(R.string.add_button,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// send the name of the new account
								String am = ((TextView) movAmount).getText()
										.toString();
								String type = movType.getSelectedItem().toString();
								String t = type.equals("Expense") ? "E" : "I";
								double amount = Double.parseDouble(am);
								
								recordMovement(getIdAccount(), t, amount,
										"2014-06-23");
								list.updateMovements();
								Toast.makeText(getActivity().getBaseContext(),
										"Movement added", Toast.LENGTH_SHORT)
										.show();
							}
						})
				.setNegativeButton(R.string.cancel_button,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								AddMovementPopup.this.getDialog().cancel();
							}
						});

		return builder.create();

	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}
	
	public void setList(MovementsList list) {
		this.list = list;
	}

	public int getIdAccount() {
		return this.idAccount;
	}

	public void recordMovement(int idAccount, String type, Double amount,
			String date) {
		
		String[] request = { "POST", "movement",
				properties.getProperty("insertMovement"),
				idAccount + "," + amount + "," + type + "," + date };
		// Execute POST
		WebServiceClient wsClient = new WebServiceClient();
		wsClient.execute(request);
	}

}