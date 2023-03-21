package br.gama.itau.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import br.gama.itau.demo.exceptions.NotFoundException;
import br.gama.itau.demo.model.Cliente;
import br.gama.itau.demo.model.Conta;
import br.gama.itau.demo.model.Movimentacao;
import br.gama.itau.demo.model.TipoConta;
import br.gama.itau.demo.model.TipoOperacao;
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
        Movimentacao movimentacaoRetorno = movimentacaoService.newMovimentacao(movimentacaoSemId);

        assertEquals(movimentacaoSemId.getConta().getNumeroConta(), movimentacaoRetorno.getConta().getNumeroConta());
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
        List<Movimentacao> listaMovimentacao = GenerateMovimentacao.listaMovimentacao();
        Optional<Conta> conta = Optional.of(Conta.builder()
                .agencia(1234)
                .numeroConta(1L)
                .tipoConta(TipoConta.PESSOA_FISICA)
                .saldo(10000)
                .cliente(Cliente.builder().build())
                .movimentacoes(listaMovimentacao)
                .build());

        BDDMockito.when(contaRepo.findById(ID)).thenReturn(conta);
        List<Movimentacao> movimentacaoRetorno = movimentacaoService.getAll(ID);

        assertEquals(3, movimentacaoRetorno.size());
        assertEquals(conta.get().getNumeroConta(), movimentacaoRetorno.get(0).getConta().getNumeroConta());
        assertEquals(conta.get().getNumeroConta(), movimentacaoRetorno.get(1).getConta().getNumeroConta());
        assertEquals(conta.get().getNumeroConta(), movimentacaoRetorno.get(2).getConta().getNumeroConta());
    }

    @Test
    void getAll_returnNull_whenIdContaNotExists() {
        final long ID = 1L;

        BDDMockito.when(contaRepo.findById(ID)).thenReturn(Optional.empty());
        List<Movimentacao> listaRetorno = movimentacaoService.getAll(ID);

        assertTrue(listaRetorno == null);

    }

    @Test
    void transferirValores_retornaVerdadeiro_quandoIdsContasExistentes() {
        Optional<Conta> contaOrigem = Optional.of(Conta.builder()
                .agencia(8622)
                .saldo(10000)
                .numeroConta(1L)
                .build());
        Optional<Conta> contaDestino = Optional.of(Conta.builder()
                .agencia(8622)
                .numeroConta(2L)
                .build());
        final double VALOR = 1000;

        Movimentacao mov1 = Movimentacao.builder()
                .conta(contaOrigem.get()).valor(VALOR).dataOperacao(LocalDate.now())
                .tipoOperacao(TipoOperacao.DEBITO).build();

        Movimentacao mov2 = Movimentacao.builder()
                .conta(contaDestino.get()).valor(VALOR).dataOperacao(LocalDate.now())
                .tipoOperacao(TipoOperacao.CREDITO).build();

        Movimentacao mov1ComId = Movimentacao.builder()
                .conta(contaOrigem.get()).numeroSeq(1L).valor(VALOR).dataOperacao(LocalDate.now())
                .tipoOperacao(TipoOperacao.DEBITO).build();

        Movimentacao mov2ComId = Movimentacao.builder()
                .conta(contaDestino.get()).numeroSeq(2L).valor(VALOR).dataOperacao(LocalDate.now())
                .tipoOperacao(TipoOperacao.CREDITO).build();

        BDDMockito.when(contaRepo.findById(1L)).thenReturn(contaOrigem);
        BDDMockito.when(contaRepo.findById(2L)).thenReturn(contaDestino);
        BDDMockito.when(contaRepo.save(contaOrigem.get())).thenReturn(contaOrigem.get());
        BDDMockito.when(contaRepo.save(contaDestino.get())).thenReturn(contaDestino.get());
        BDDMockito.when(movimentacaoRepo.save(mov1)).thenReturn(mov1ComId);
        BDDMockito.when(movimentacaoRepo.save(mov2)).thenReturn(mov2ComId);

        boolean retorno = movimentacaoService.transferirValores(1L, 2L, VALOR);

        assertTrue(retorno);

        assertEquals(9000, contaOrigem.get().getSaldo());
        assertEquals(1000, contaDestino.get().getSaldo());
        assertEquals(1000, mov1ComId.getValor());
        assertEquals(1000, mov2ComId.getValor());

        // verify(movimentacaoRepo, Mockito.times(1)).save(mov1);
        // verify(movimentacaoRepo, Mockito.times(1)).save(mov2);

    }

    @Test
    void transferirValores_retornaFalso_quandoSaldoInsuficiente() {
        Optional<Conta> contaOrigem = Optional.of(Conta.builder()
                .agencia(8622)
                .saldo(500)
                .numeroConta(1L)
                .build());
        Optional<Conta> contaDestino = Optional.of(Conta.builder()
                .agencia(8622)
                .numeroConta(2L)
                .build());
        final double VALOR = 1000;

        BDDMockito.when(contaRepo.findById(1L)).thenReturn(contaOrigem);
        BDDMockito.when(contaRepo.findById(2L)).thenReturn(contaDestino);

        boolean retorno = movimentacaoService.transferirValores(1L, 2L, VALOR);

        assertTrue(retorno == false);
        assertEquals(500, contaOrigem.get().getSaldo());
        assertEquals(0, contaDestino.get().getSaldo());

    }

    @Test
    void transferirValores_lancaExcecaoNotFoundException_quandoIdContaInexistente() {
        final double VALOR = 1000;

        Optional<Conta> contaOrigem = Optional.of(Conta.builder()
                .agencia(8622)
                .saldo(500)
                .numeroConta(1L)
                .build());
        Optional<Conta> contaDestino = Optional.empty();

        BDDMockito.when(contaRepo.findById(1L)).thenReturn(contaOrigem);
        BDDMockito.when(contaRepo.findById(2L)).thenReturn(contaDestino);

        assertThrows(NotFoundException.class, () -> {
            movimentacaoService.transferirValores(1L, 2L, VALOR);
        });
    }

}
