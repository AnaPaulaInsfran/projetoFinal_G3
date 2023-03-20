package br.gama.itau.demo.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.gama.itau.demo.model.Conta;
import br.gama.itau.demo.model.Movimentacao;
import br.gama.itau.demo.model.TipoOperacao;

public class GenerateMovimentacao {
    public static Movimentacao novaMovimentacao(){
        return Movimentacao.builder()
        .conta(Conta.builder().numeroConta(1L).build())
        .numeroSeq(1L)
        .dataOperacao(LocalDate.now())
        .valor(10000)
        .tipoOperacao(TipoOperacao.CREDITO)
        .build();
    }
   
    public static Movimentacao novaMovimentacao2(){
        return Movimentacao.builder()
        .conta(Conta.builder().numeroConta(1L).build())
        .numeroSeq(2L)
        .dataOperacao(LocalDate.now())
        .valor(5000)
        .tipoOperacao(TipoOperacao.CREDITO)
        .build();
    }
    
    public static Movimentacao novaMovimentacao3(){
        return Movimentacao.builder()
        .conta(Conta.builder().numeroConta(1L).build())
        .numeroSeq(3L)
        .dataOperacao(LocalDate.now())
        .valor(100)
        .tipoOperacao(TipoOperacao.DEBITO)
        .build();
    }
    
    public static Movimentacao novaMovimentacaoSemId(){
        return Movimentacao.builder()
        .conta(Conta.builder().build())
        .dataOperacao(LocalDate.now())
        .valor(650)
        .tipoOperacao(TipoOperacao.CREDITO)
        .build();
    }

    public static Movimentacao novaMovimentacaoSemId2(){
        return Movimentacao.builder()
        .conta(Conta.builder().build())
        .dataOperacao(LocalDate.now())
        .valor(650)
        .tipoOperacao(TipoOperacao.CREDITO)
        .build();
    }

    
    public static List <Movimentacao> listaMovimentacao() {
        ArrayList <Movimentacao> listaMovimentacoes = new ArrayList<>();
        listaMovimentacoes.add(novaMovimentacao());
        listaMovimentacoes.add(novaMovimentacao2());
        listaMovimentacoes.add(novaMovimentacao3());

        return listaMovimentacoes;
    }



    public static List <Movimentacao> listaMovimentacaoSemId() {
        ArrayList <Movimentacao> listaMovimentacoes = new ArrayList<>();
        listaMovimentacoes.add(novaMovimentacaoSemId());
        listaMovimentacoes.add(novaMovimentacaoSemId2());

        return listaMovimentacoes;
    }

}
