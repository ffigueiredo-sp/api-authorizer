package com.caju.authorizer.infrastructure.entity.pk;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionPk implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer idTran;
	private Integer accountId;	
	private Integer IdMcc;

}
