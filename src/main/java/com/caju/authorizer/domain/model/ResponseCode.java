package com.caju.authorizer.domain.model;

import lombok.Getter;

@Getter
public enum ResponseCode {
	
	APROVADA("00", "Trasação aprovada"),
	REJEITADA("51", "Transação rejeitada, porque não tem saldo suficiente"),
	ERRO_GENERICO("07", "Ocorreu erro interno que impediu o processamento da transação");
	
	private String codigo;
	private String mensagem;
	
	private ResponseCode(String codigo, String mensagem) {
		this.codigo = codigo;
		this.mensagem = mensagem;
	}
	
}
