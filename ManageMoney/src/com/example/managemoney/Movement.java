package com.example.managemoney;

public class Movement {
	int idMovement;
	int idAccount;
	Double amount;
	String type;
	String date;
	public Movement(int idMovement, int idAccount, Double amount, String type,
			String date) {
		this.idMovement = idMovement;
		this.idAccount = idAccount;
		this.amount = amount;
		this.type = type;
		this.date = date;
	}
	
}
