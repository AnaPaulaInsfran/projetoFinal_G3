package br.gama.itau.projetoFinal.model;

public enum TipoConta {
    
    PESSOA_FISICA(1), 
    ESTUDANTIL(2),
    SALARIO(3);

    private int valor;
    
    private TipoConta(int valor) {
        this.valor = valor;
    }

    
}
