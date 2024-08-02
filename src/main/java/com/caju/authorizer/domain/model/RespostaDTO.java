package com.caju.authorizer.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaDTO<T>{
	
	@JsonInclude(Include.NON_EMPTY)
	private String code;
	
	@JsonInclude(Include.NON_EMPTY)
	private String mensagem;
	
	public RespostaDTO() {
	}

}
