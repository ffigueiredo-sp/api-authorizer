package com.caju.authorizer.application.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.caju.authorizer.application.rest.controller.interfaces.CompraRest;
import com.caju.authorizer.domain.model.RequestCompraDto;
import com.caju.authorizer.domain.model.ResponseCode;

import static com.caju.authorizer.domain.model.ResponseCode.APROVADA;
import static com.caju.authorizer.domain.model.ResponseCode.ERRO_GENERICO;
import static com.caju.authorizer.domain.model.ResponseCode.REJEITADA;
import static com.caju.authorizer.domain.model.TipoMCC.CASH;
import static com.caju.authorizer.domain.model.TipoMCC.MEAL;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.caju.authorizer.domain.model.TipoMCC.FOOD;

import com.caju.authorizer.domain.model.RespostaDTO;
import com.caju.authorizer.domain.model.RespostaSaldoDTO;
import com.caju.authorizer.domain.model.TipoMCC;
import com.caju.authorizer.domain.model.TransactionDTO;
import com.caju.authorizer.infrastructure.entity.Amount;
import com.caju.authorizer.infrastructure.entity.HistoricoTransaction;
import com.caju.authorizer.infrastructure.entity.Mcc;
import com.caju.authorizer.infrastructure.repository.AmountRepository;
import com.caju.authorizer.infrastructure.repository.ClientRepository;
import com.caju.authorizer.infrastructure.repository.HistoricoTransactionRepository;
import com.caju.authorizer.infrastructure.repository.MccRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller responsável por atender as requisições da API Authorizer
 * 
 * @author Felipe Figueiredo
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class CompraController implements CompraRest {

	private final ClientRepository client;

	private final HistoricoTransactionRepository transaction;

	private final AmountRepository amountClient;
	
	private final MccRepository mcc;
	
    private	RespostaDTO respostaDTO;
    
    @Override
	public ResponseEntity<RespostaSaldoDTO> getSaldo(int accountId) {

		List<Amount> listAmountClient = this.amountClient.findAllAmoutByAccountId(accountId);
		RespostaSaldoDTO respostaSaldo = new RespostaSaldoDTO();

		respostaSaldo.setData(listAmountClient);

		return ResponseEntity.status(HttpStatus.OK).body(respostaSaldo);
	}

	@Override
	public ResponseEntity<RespostaDTO> updateSaldo(final RequestCompraDto requestCompraDTO) {

		try {

			if (!validaUsuario(requestCompraDTO.getAccount())) {
				log.info("O portador consta não possui cadastro");
				throw new IllegalArgumentException ("Usuario inexistente");
			} 
			
			double valorCompra = requestCompraDTO.getTotalAmount();
			
			if (valorCompra <= 0.0) {
				log.info("O portador enviou transação com valor de compra invalido");
				throw new IllegalArgumentException ("Valor da compra invalido");
			}
			
			TipoMCC tipoMcc = getTipoMcc(Integer.parseInt(requestCompraDTO.getMcc()));

			if (tipoMcc.equals(MEAL)) {
				this.respostaDTO = fallbackMccNaoMapeado(requestCompraDTO, tipoMcc) ? preencheResposta(APROVADA)
						: preencheResposta(REJEITADA);
			} else {
				this.respostaDTO = validaSaldo(requestCompraDTO, tipoMcc) ? preencheResposta(APROVADA)
						: preencheResposta(REJEITADA);
			}

			log.info("O portador consta com a transação status={}", respostaDTO.getMensagem());

			TransactionDTO transactionDTO = atualizaTransactionDto(requestCompraDTO);

			HistoricoTransaction transaction = converterDtoTransaction(transactionDTO);
			
			this.transaction.save(transaction);
				
		} catch (Exception e) {
			log.error("Erro geral API ex={}", e);
			respostaDTO = preencheResposta(ERRO_GENERICO);
		}

		return ResponseEntity.status(HttpStatus.OK).body(respostaDTO);

	}

	private TransactionDTO atualizaTransactionDto(final RequestCompraDto requestCompraDTO) {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setAccount(Integer.parseInt(requestCompraDTO.getAccount()));
		transactionDTO.setTotalAmount(requestCompraDTO.getTotalAmount());
		transactionDTO.setMcc(Integer.parseInt(requestCompraDTO.getMcc()));
		transactionDTO.setMerchant(requestCompraDTO.getMerchant());
		transactionDTO.setStatus(respostaDTO.getCode());
		transactionDTO.setDateTimeBuy(new Date());
		return transactionDTO;
	}

	private Mcc convertDtoMcc(RequestCompraDto transactionDTO, String category) {
		Mcc mccNew = new Mcc();
		
		mccNew.setCategory(category);
		mccNew.setDateLastUpdate(new Date());
		mccNew.setIdMCC(Integer.parseInt(transactionDTO.getMcc()));
		mccNew.setNameMcc(transactionDTO.getMerchant());
		
		return mccNew;
	}

	private HistoricoTransaction converterDtoTransaction(TransactionDTO transactionDTO) {

		HistoricoTransaction transaction = new HistoricoTransaction();
		transaction.setAccountId(transactionDTO.getAccount());
		transaction.setIdMcc(transactionDTO.getMcc());
		transaction.setTotalAmount(transactionDTO.getTotalAmount());
		transaction.setDateTimeTran(transactionDTO.getDateTimeBuy());
		transaction.setStatus(transactionDTO.getStatus());
		return transaction;
	}

	private RespostaDTO preencheResposta(ResponseCode response) {
		RespostaDTO respostaDTO = new RespostaDTO();
		respostaDTO.setCode(response.getCodigo());
		respostaDTO.setMensagem(response.getMensagem());
		return respostaDTO;
	}

	protected boolean validaSaldo(RequestCompraDto requestCompraDTO, TipoMCC tipoMcc) {

		log.info("O portador consta com MCC={} valido", tipoMcc.name());
		List<Amount> listAmountClient = this.amountClient
				.findAllAmoutByAccountId(Integer.parseInt(requestCompraDTO.getAccount()));

		Optional<Amount> saldoCliente = getSaldoCliente(tipoMcc, listAmountClient);

		double saldo = saldoCliente.get().getBalance();
		double valorPedidoCompra = requestCompraDTO.getTotalAmount();
		Mcc mccAtualizado = convertDtoMcc(requestCompraDTO, saldoCliente.get().getCategory());
		// Se o saldo foi maior que o valor da compra e se o debito não ficar negativo,
		// segue
		if ((saldo >= valorPedidoCompra) && ((saldo - valorPedidoCompra) >= 0)) {
			saldo -= valorPedidoCompra;
			saldoCliente.get().setBalance(saldo);
			this.amountClient.save(saldoCliente.get());
			log.info("O portador consta com saldo, a transação foi completada");
			
			this.mcc.save(mccAtualizado);
			log.info("Mcc atualizado");
			
			return true;
		}

		log.info("O portador consta com saldo insuficiente, sera validado cash");

		return fallbackParteSaldoCash(tipoMcc, listAmountClient, saldoCliente, saldo, valorPedidoCompra, mccAtualizado);

	}

	protected boolean fallbackMccNaoMapeado(RequestCompraDto requestCompraDTO, TipoMCC tipoMcc) {

		log.info("O portador consta com transação de MCC não mapeado");

		List<Amount> listAmountClient = this.amountClient
				.findAllAmoutByAccountId(Integer.parseInt(requestCompraDTO.getAccount()));

		Optional<Amount> saldoCliente = getSaldoCliente(tipoMcc, listAmountClient);

		double saldo = saldoCliente.get().getBalance();
		double valorPedidoCompra = requestCompraDTO.getTotalAmount();

		// Busca saldo Cash
		Optional<Amount> saldoCashCliente = getSaldoCliente(tipoMcc.CASH, listAmountClient);
		double saldoCash = saldoCashCliente.get().getBalance();
		if ((saldoCash >= valorPedidoCompra) && ((saldoCash - valorPedidoCompra) >= 0)) {
			saldo -= valorPedidoCompra;
			saldoCliente.get().setBalance(saldo);
			this.amountClient.save(saldoCliente.get());
			log.info("O portador consta com saldo de cash, a transação foi completada");
			
			this.mcc.save(convertDtoMcc(requestCompraDTO, saldoCliente.get().getCategory()));
			log.info("Mcc atualizado");
			
			return true;
		}
		log.info("O portador consta com saldo insuficiente para completar a transação");
		return false;
	}

	protected boolean fallbackParteSaldoCash(TipoMCC tipoMcc, List<Amount> listAmountClient,
			Optional<Amount> saldoCliente, double saldo, double valorPedidoCompra, Mcc mccAtualizado) {
		// Busca saldo Cash
		Optional<Amount> saldoCashCliente = getSaldoCliente(tipoMcc.CASH, listAmountClient);

		// Caso o portador contenha saldo de cash para completar a transação, sera
		// efetuado
		double valorDebitadoCash = (saldo - valorPedidoCompra) + saldoCashCliente.get().getBalance();
		if (valorDebitadoCash >= 0) {
			saldoCliente.get().setBalance(0);
			saldoCashCliente.get().setBalance(valorDebitadoCash);
			this.amountClient.save(saldoCliente.get());
			this.amountClient.save(saldoCashCliente.get());
			log.info("O portador consta com saldo de cash, a transação foi completada");
			
			this.mcc.save(mccAtualizado);
			log.info("Mcc atualizado");
			
			return true;
		}

		log.info("O portador consta com saldo insuficiente para completar a transação");
		return false;
	}

	private Optional<Amount> getSaldoCliente(TipoMCC tipoMcc, List<Amount> listAmountClient) {
		return listAmountClient.stream()
				.filter(saldoClientePorTipoMcc -> saldoClientePorTipoMcc.getCategory().contains(tipoMcc.toString()))
				.findFirst();
	}

	private TipoMCC getTipoMcc(int mcc) {

		switch (mcc) {
		case 5411:
		case 5412: {
			return FOOD;
		}
		case 5811:
		case 5812: {
			return MEAL;
		}
		default:
			return CASH;
		}

	}

	private boolean validaUsuario(final String id) {

		int searchId = Integer.parseInt(id); 
		
		return this.client.existsById(searchId);

	}

	

}
