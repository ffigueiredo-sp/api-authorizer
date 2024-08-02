package com.caju.authorizer.application.rest.controller.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caju.authorizer.domain.model.RequestCompraDto;
import com.caju.authorizer.domain.model.RespostaDTO;
import com.caju.authorizer.domain.model.RespostaSaldoDTO;
import com.caju.authorizer.domain.model.TransactionDTO;
import com.caju.authorizer.infrastructure.entity.Amount;
import com.caju.authorizer.infrastructure.entity.HistoricoTransaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/authorizer/v1/caju")
@Tag(name = "MCC", description = "API para gerenciamento de requisições do Authorizer Caju")
public interface CompraRest {

	@Operation(summary = "Obtém saldo portador ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso")
	})
	
	@GetMapping("/saldo/accountId/{accountId}")
	public ResponseEntity<RespostaSaldoDTO> getSaldo(
		@PathVariable("accountId") @Parameter(description = "accountId", required = true, example = "123") final int accountId);
		
	@Operation(summary = "Efetua compra portador")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso")
	})
	
	@PutMapping("/compra")
	public ResponseEntity<RespostaDTO> updateSaldo(@Valid @RequestBody final RequestCompraDto requestCompraDto);
}
