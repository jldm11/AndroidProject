package com.example.managemoney;

public class Detail {
	int idDetail;
	int idMovement;
	String description;
	Double amount;
	public Detail(int idDetail, int idMovement, String description,
			Double amount) {
		this.idDetail = idDetail;
		this.idMovement = idMovement;
		this.description = description;
		this.amount = amount;
	}
	
}
