package com.example.managemoney;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.webkit.WebView.FindListener;
import android.widget.Toast;

@SuppressLint("NewApi") public class AddAccountPopup extends DialogFragment{
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		
		builder.setTitle("Add new account");
		builder.setMessage("Please, fill the data");
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(inflater.inflate(R.layout.add_account_view, null))
				// action buttons
				.setPositiveButton(R.string.add_button,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
//								toma el nombre de la cuenta a agregar
//								final String accountName = (String) AddAccountPopup.this.getText(R.id.new_account_name);
//								 Toast.makeText(getActivity().getBaseContext(),
//								 accountName.toString(), Toast.LENGTH_SHORT).show();
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

}