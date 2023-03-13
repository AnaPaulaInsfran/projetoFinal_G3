package br.gama.itau.demo.model;

public enum TipoConta {
    
    PESSOA_FISICA(1), 
    ESTUDANTIL(2),
    SALARIO(3);

    private int valor;
    
    private TipoConta(int valor) {
        this.valor = valor;
    }

    
}
