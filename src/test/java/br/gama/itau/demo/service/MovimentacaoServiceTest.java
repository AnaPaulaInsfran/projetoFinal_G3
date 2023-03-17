package br.gama.itau.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import br.gama.itau.demo.model.Cliente;
import br.gama.itau.demo.model.Conta;
import br.gama.itau.demo.model.Movimentacao;
import br.gama.itau.demo.model.TipoConta;
import br.gama.itau.demo.repository.ContaRepo;
import br.gama.itau.demo.repository.MovimentacaoRepo;
import br.gama.itau.demo.util.GenerateMovimentacao;

public class MovimentacaoServiceTest {

    private static final MovimentacaoRepo movimentacaoRepo = mock(MovimentacaoRepo.class);
    private static final ContaRepo contaRepo = mock(ContaRepo.class);

    private static MovimentacaoService movimentacaoService;

    @BeforeAll
    static void init() {
        movimentacaoService = new MovimentacaoService(movimentacaoRepo, contaRepo);
    }

    @Test
    void newMovimentacao_returnNewMovimentacao_whenIdNotExists() {
        Movimentacao movimentacaoSemId = GenerateMovimentacao.novaMovimentacaoSemId();
        Movimentacao movimentacaoComId = GenerateMovimentacao.novaMovimentacaoSemId();
        movimentacaoComId.setNumeroSeq(1L);

        BDDMockito.when(movimentacaoRepo.save(movimentacaoSemId)).thenReturn(movimentacaoComId);
        Movimentacao movimentacaoRetorno = movimentacaoService.newMovimentacao(movimentacaoComId);

        assertEquals(movimentacaoSemId.getConta(), movimentacaoRetorno.getConta());
        assertEquals(movimentacaoSemId.getDataOperacao(), movimentacaoRetorno.getDataOperacao());
        assertEquals(movimentacaoSemId.getTipoOperacao(), movimentacaoRetorno.getTipoOperacao());
        assertEquals(movimentacaoSemId.getValor(), movimentacaoRetorno.getValor());
        assertTrue(movimentacaoRetorno.getNumeroSeq() != 0);

        verify(movimentacaoRepo, Mockito.times(1)).save(movimentacaoSemId);
    }

    @Test
    void newMovimentacao_returnNull_whenIdExists() {
        Movimentacao movimentacaoComId = GenerateMovimentacao.novaMovimentacao();
        
        Movimentacao movimentacaoRetorno = movimentacaoService.newMovimentacao(movimentacaoComId);

        assertTrue(movimentacaoRetorno == null);
    }

    @Test
    void getAll_returnListMovimentacao_whenIdContaExists() {
        final long ID = 1L;
        List <Movimentacao> listaMovimentacao = GenerateMovimentacao.listaMovimentacao();
        Optional <Conta> conta = Optional.of(Conta.builder()
            .agencia(1234)
            .numeroConta(1L)
            .tipoConta(TipoConta.PESSOA_FISICA)
            .saldo(10000)
            .cliente(Cliente.builder().build())
            .movimentacoes(listaMovimentacao)
            .build());

        BDDMockito.when(contaRepo.findById(ID)).thenReturn(conta);    
        List <Movimentacao> movimentacaoRetorno = movimentacaoService.getAll(ID);

        assertEquals(3, movimentacaoRetorno.size());
        assertEquals(conta.get().getNumeroConta(), movimentacaoRetorno.get(0).getConta().getNumeroConta());
        assertEquals(conta.get().getNumeroConta(), movimentacaoRetorno.get(1).getConta().getNumeroConta());
        assertEquals(conta.get().getNumeroConta(), movimentacaoRetorno.get(2).getConta().getNumeroConta());
    }

    @Test
    void getAll_returnNull_whenIdContaNotExists() {
        final long ID = 1L;

        BDDMockito.when(contaRepo.findById(ID)).thenReturn(Optional.empty());
        List <Movimentacao> listaRetorno = movimentacaoService.getAll(ID);

        assertTrue(listaRetorno == null);

    }

}
