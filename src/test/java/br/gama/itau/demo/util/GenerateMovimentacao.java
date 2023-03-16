package br.gama.itau.demo.util;

import java.time.LocalDate;

import br.gama.itau.demo.model.Movimentacao;
import br.gama.itau.demo.model.TipoOperacao;

public class GenerateMovimentacao {
    public static Movimentacao novaMovimentacao(){
        return Movimentacao.builder()
        .numeroSeq(1L)
        .dataOperacao(LocalDate.now())
        .valor(10000)
        .tipoOperacao(TipoOperacao.CREDITO)
        .build();
    }
   
    public static Movimentacao novaMovimentacao2(){
        return Movimentacao.builder()
        .numeroSeq(2L)
        .dataOperacao(LocalDate.now())
        .valor(5000)
        .tipoOperacao(TipoOperacao.CREDITO)
        .build();
    }
    
    public static Movimentacao novaMovimentacao3(){
        return Movimentacao.builder()
        .numeroSeq(3L)
        .dataOperacao(LocalDate.now())
        .valor(100)
        .tipoOperacao(TipoOperacao.DEBITO)
        .build();
    }
    
    public static Movimentacao novaMovimentacaoSemId(){
        return Movimentacao.builder()
        .dataOperacao(LocalDate.now())
        .valor(650)
        .tipoOperacao(TipoOperacao.CREDITO)
        .build();
    }
    
}
