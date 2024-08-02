package com.caju.authorizer.infrastructure.entity;

import java.util.Date;

import com.caju.authorizer.infrastructure.entity.pk.AmountPk;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "AMOUNT")
@IdClass(AmountPk.class)
@Entity
public class Amount {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Id 
	@Column(name = "ACCOUNTID")
	private int accountId;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "BALANCE")
	private double balance;
	
	@Column(name = "DATETIMEUPDATESALDO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeUpdateSaldo;
	
	public Amount() {
	}
	
	public Amount(int accountId, String category, double balance, Date dateTimeUpdateSaldo) {
		this.accountId = accountId;
		this.category = category;
		this.balance = balance;
		this.dateTimeUpdateSaldo = dateTimeUpdateSaldo;
	}
}

