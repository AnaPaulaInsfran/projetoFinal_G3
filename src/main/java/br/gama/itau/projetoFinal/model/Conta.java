package br.gama.itau.projetoFinal.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long numeroConta;

    @Column(length = 10, nullable = false)
    private int agencia;
    
    @Column
    private TipoConta tipoConta;

    @Column
    private double saldo;

    @OneToMany(mappedBy = "numeroConta")
    @JsonIgnoreProperties("numeroConta")
    private List<Movimentacao> movimentacoes;
}
