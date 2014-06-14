package com.example.managemoney;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

@SuppressLint("NewApi") public class AddDetailPopup extends DialogFragment{
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		builder.setTitle("Add new detail");
		builder.setMessage("Please, fill the data");
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(inflater.inflate(R.layout.add_detail_view, null))
				// action buttons
				.setPositiveButton(R.string.add_button,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// send the name of the new account
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

}