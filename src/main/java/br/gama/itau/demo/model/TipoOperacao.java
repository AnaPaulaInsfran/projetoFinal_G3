package br.gama.itau.demo.model;

public enum TipoOperacao {
    
    CREDITO(1),
    DEBITO(2);

    private int valor;

    private TipoOperacao(int valor) {
        this.valor = valor;
    }
}
