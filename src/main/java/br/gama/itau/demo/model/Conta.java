package br.gama.itau.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    
    @Enumerated(EnumType.ORDINAL)
    private TipoConta tipoConta;
    
    private double saldo;

    @OneToMany(mappedBy = "conta")
    @JsonIgnoreProperties("conta")
    private List<Movimentacao> movimentacoes;

    @ManyToOne
    @JoinColumn(name = "id_cliente") // add coluna na tabela conta
    @JsonIgnoreProperties("contas")
    private Cliente cliente;
}
