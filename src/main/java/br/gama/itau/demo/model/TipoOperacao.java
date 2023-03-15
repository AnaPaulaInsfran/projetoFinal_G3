package br.gama.itau.demo.model;

public enum TipoOperacao {
    
    CREDITO(0),
    DEBITO(1);

    private int valor;

    private TipoOperacao(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    
}
