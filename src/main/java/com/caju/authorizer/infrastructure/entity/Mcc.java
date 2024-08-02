package com.caju.authorizer.infrastructure.entity;

import java.util.Date;

import com.caju.authorizer.infrastructure.entity.pk.MccPk;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "MCC")
@IdClass(MccPk.class)
@Entity
public class Mcc {
	
	@Id 
	@Column(name = "IDMCC")
	private int idMCC;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "NAMEMCC", length = 60)
	private String nameMcc;
	
	@Column(name = "DATELASTUPDATE")
	private Date dateLastUpdate;
	
	public Mcc() {
	}
	public Mcc(int idMCC, String idCategory, String nameMcc, Date dateLastUpdate) {
		this.idMCC = idMCC;
		this.category = idCategory;
		this.nameMcc = nameMcc;
		this.dateLastUpdate = dateLastUpdate;
	}
	
}


