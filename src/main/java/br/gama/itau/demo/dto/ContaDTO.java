package br.gama.itau.demo.dto;

import br.gama.itau.demo.model.Conta;
import br.gama.itau.demo.model.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContaDTO {
    
    private int agencia;
    private long numeroConta;
    private TipoConta tipoConta;
    
    public ContaDTO(Conta conta) {
        this.agencia = conta.getAgencia();
        this.numeroConta = conta.getNumeroConta();
        this.tipoConta = conta.getTipoConta();
    }

}
