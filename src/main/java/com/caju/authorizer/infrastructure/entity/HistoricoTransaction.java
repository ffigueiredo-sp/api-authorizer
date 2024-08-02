package com.caju.authorizer.infrastructure.entity;

import java.util.Date;

import com.caju.authorizer.infrastructure.entity.pk.TransactionPk;

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
@Table(name = "HISTORICO_TRANSACTION")
@IdClass(TransactionPk.class)
@Entity
public class HistoricoTransaction {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IDTRAN")
	private Integer idTran;

	@Id 
	@Column(name = "ACCOUNTID")
	private Integer accountId;
	
	@Id 
	@Column(name = "IDMCC")
	private Integer IdMcc;
	
	@Column(name = "TOTALAMOUNT")
	private double totalAmount;
	
	@Column(name = "DATETIMETRAN")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeTran;
	
	@Column(name = "STATUS")
	private String status;
	
	public HistoricoTransaction() {
	}
	
	public HistoricoTransaction(int accountId, int idMcc, double totalAmount, Date dateTimeTran, String status) {
		this.accountId = accountId;
		this.IdMcc = idMcc;
		this.totalAmount = totalAmount;
		this.dateTimeTran = dateTimeTran;
		this.status = status;
	}

	
}


