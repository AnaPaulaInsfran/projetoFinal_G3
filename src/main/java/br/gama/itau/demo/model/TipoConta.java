package br.gama.itau.demo.model;

public enum TipoConta {
    
    PESSOA_FISICA(0), 
    ESTUDANTIL(1),
    SALARIO(2);

    private int valor;
    
    private TipoConta(int valor) {
        this.valor = valor;
    }
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }



    
}
