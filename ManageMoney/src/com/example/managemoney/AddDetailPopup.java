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
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class AddDetailPopup extends DialogFragment {

	private int idMove = 0;
	private Properties properties;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View v = inflater.inflate(R.layout.add_detail_view, null);
		final View description = v.findViewById(R.id.new_detail_name);
		final View amount = v.findViewById(R.id.amount);
		builder.setTitle("Add new detail");
		builder.setMessage("Please, fill the data");
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(v)
				// action buttons
				.setPositiveButton(R.string.add_button,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// send the name of the new account
								String descp = ((TextView) description)
										.getText().toString();
								String am = ((TextView) amount).getText()
										.toString();
								double amount = Double.parseDouble(am);
								recordDetail(getIdMove(), descp, amount);
								Toast.makeText(getActivity().getBaseContext(),
										descp + " " + amount,
										Toast.LENGTH_SHORT).show();
							}
						})
				.setNegativeButton(R.string.cancel_button,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								AddDetailPopup.this.getDialog().cancel();
							}
						});

		return builder.create();

	}

	public void recordDetail(int idMovement, String description, double amount) {
		String[] request = { "POST", "detail",
				properties.getProperty("insertDetail"),
				idMovement + "," + description + "," + amount };
		// Execute POST
		WebServiceClient wsClient = new WebServiceClient();
		wsClient.execute(request);
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setIdMove(int id) {
		this.idMove = id;
	}

	public int getIdMove() {
		return this.idMove;
	}
}
