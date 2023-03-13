package br.gama.itau.projetoFinal.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long numeroSeq;

    @Column(length = 10, nullable = false)
    private LocalDate dataOperacao;

    @Column(length = 20, nullable = false)
    private double valor;

    @Column(length = 1, nullable = false)
    private TipoOperacao tipoOperacao;

    @ManyToOne
    @JoinColumn(name = "numero_conta")
    @JsonIgnoreProperties("movimentacoes")
    private Conta conta;
    
}
