package com.caju.authorizer.infrastructure.entity;

import java.io.Serializable;
import java.sql.Date;

import com.caju.authorizer.infrastructure.entity.pk.ClientPk;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "CLIENT")
@Entity
public class Client implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "NAMEUSER", length = 60)
	private String nameUser;
	
	@Column(name = "CPF", length = 11)
	private long cpf;
	
	@Column(name = "DATENASC")
	private Date dateNasc;
	
	public Client() {
	}
	
	public Client(int id, String nameUser, long cpf, Date dateNasc) {
		this.id = id;
		this.nameUser = nameUser;
		this.cpf = cpf;
		this.dateNasc = dateNasc;
	}
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((this.accountId == null) ? 0 : this.accountId.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Client other = (Client) obj;
//		if (this.accountId == null) {
//			if (other.accountId != null)
//				return false;
//		} else if (!this.accountId.equals(other.accountId))
//			return false;
//		return true;
//	}

}
