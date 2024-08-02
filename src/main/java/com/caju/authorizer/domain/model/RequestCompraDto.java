package com.caju.authorizer.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCompraDto {

	private String account;
	private double totalAmount;
	private String mcc;
	private String merchant;
	
	public RequestCompraDto() {
		
	}
	public RequestCompraDto(String account, double totalAmount, String mcc, String merchant) {
		this.account = account;
		this.totalAmount = totalAmount;
		this.mcc = mcc;
		this.merchant = merchant;
	}
	
}
