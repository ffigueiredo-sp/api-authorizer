package com.caju.authorizer.application.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.caju.authorizer.domain.model.RequestCompraDto;
import com.caju.authorizer.domain.model.RespostaDTO;
import com.caju.authorizer.domain.model.RespostaSaldoDTO;
import com.caju.authorizer.infrastructure.entity.Amount;
import com.caju.authorizer.infrastructure.repository.AmountRepository;
import com.caju.authorizer.infrastructure.repository.ClientRepository;
import com.caju.authorizer.infrastructure.repository.HistoricoTransactionRepository;
import com.caju.authorizer.infrastructure.repository.MccRepository;

@SpringBootTest
public class CompraControllerTest {
	
	@Mock
	private ClientRepository client;
	
	@Mock
	private HistoricoTransactionRepository transaction;
	
	@Mock
	private AmountRepository amountClient;
	
	@Mock
	private MccRepository mcc;
	
	@InjectMocks
	private CompraController compraController;
	
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2})
	void testGetSaldo(final int accountId) {
		RespostaSaldoDTO respostaSaldo = thenRespostaSaldoDTO();
		List<Amount> listAmountClient = thenListAmountClient();
		respostaSaldo.setData(listAmountClient);
		
		when(amountClient.findAllAmoutByAccountId(accountId)).thenReturn(listAmountClient);
		
		ResponseEntity<RespostaSaldoDTO> response = compraController.getSaldo(accountId);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2})
	void testUpdate(final int accountId) {
		
		RequestCompraDto request = thenRequestCompraDto();
		List<Amount> listAmountClient = thenListAmountClient();
		
		when(amountClient.findAllAmoutByAccountId(accountId)).thenReturn(listAmountClient);
		ResponseEntity<RespostaDTO> response = compraController.updateSaldo(request);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	private RequestCompraDto thenRequestCompraDto() {
		RequestCompraDto request = new RequestCompraDto();
		request.setAccount("1");
		request.setMcc("5411");
		request.setMerchant("TESTE");
		request.setTotalAmount(100.00);
		return request;
	}

	private List<Amount> thenListAmountClient() {
		
		Amount amount1 = new Amount();
		
		amount1.setAccountId(1);
		amount1.setBalance(10.00);
		amount1.setCategory("FOOD");
		amount1.setDateTimeUpdateSaldo(new Date());
		amount1.setId(1);
		
		List<Amount> listAmountClient = new ArrayList<Amount>();
		
		listAmountClient.add(amount1);
		
		return listAmountClient;
	}

	private RespostaSaldoDTO thenRespostaSaldoDTO() {
		RespostaSaldoDTO respostaSaldo = new RespostaSaldoDTO();
		respostaSaldo.setCode("00");
		respostaSaldo.setData(new ArrayList<>());
		respostaSaldo.setMensagem("XX");
		return respostaSaldo;
	}
	


}
