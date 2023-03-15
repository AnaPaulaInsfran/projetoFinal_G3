package br.gama.itau.demo.dto;

import java.util.ArrayList;
import java.util.List;

import br.gama.itau.demo.model.Cliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClienteIdDTO {
    
    private String nome;
    private String cpf;
    private String telefone;
    private List<ContaDTO> contas;

    public ClienteIdDTO(Cliente cliente) {
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.telefone = cliente.getTelefone();

        ArrayList<ContaDTO> contasDTO = new ArrayList<>();

        for (int i = 0; i < cliente.getContas().size(); i++) {
            ContaDTO contaDTO = new ContaDTO(cliente.getContas().get(i)); 
            contasDTO.add(contaDTO);
        }
        this.contas = contasDTO;
    }
}
