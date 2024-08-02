package com.caju.authorizer.domain.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {

	private int account;
	private double totalAmount;
	private int mcc;
	private String merchant;
	private Date dateTimeBuy;
	private String status;
	
	public TransactionDTO() {
		
	}

	public TransactionDTO(int account, double totalAmount, int mcc, String merchant, Date dateTimeBuy,
			String status) {
		this.account = account;
		this.totalAmount = totalAmount;
		this.mcc = mcc;
		this.merchant = merchant;
		this.dateTimeBuy = dateTimeBuy;
		this.status = status;
	}
}
