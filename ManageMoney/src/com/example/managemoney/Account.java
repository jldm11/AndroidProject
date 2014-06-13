package com.example.managemoney;

public class Account {
	int idAccount;
	int idUser; 
	String status;
	String accountName;
	public Account(int idAccount, int idUser, String status,
			String accountName) {
		this.idAccount = idAccount;
		this.idUser = idUser;
		this.status = status;
		this.accountName = accountName;
	}
}
