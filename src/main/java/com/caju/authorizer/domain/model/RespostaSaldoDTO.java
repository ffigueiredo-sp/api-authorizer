package com.caju.authorizer.domain.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaSaldoDTO<T>{
	
	@JsonInclude(Include.NON_EMPTY)
	private List<T> data;

	@JsonInclude(Include.NON_EMPTY)
	private String code;
	
	@JsonInclude(Include.NON_EMPTY)
	private String mensagem;
	
	public RespostaSaldoDTO() {
	}

	public RespostaSaldoDTO(final List<T> resultado) {
		this.data = resultado;
	}
	
	public String toString(){
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);
		builder.append("data", this.data).append("mensagem", this.mensagem).append("code", this.code);
		return builder.toString();
	}
}
