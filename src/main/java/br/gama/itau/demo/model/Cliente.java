package br.gama.itau.demo.model;

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

@Entity // entidade que vai se tornar objeto no BD
@Getter // retornar o dado
@Setter // inserir ou alterar o dado
public class Cliente {
    
    @Id // mostrar que é a Primary key (PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GenerateValue gera automaticamente e sequencialmente o Id
    private long id;

    @Column(length = 100, nullable = false) // identificar que será uma coluna no Bd
    private String nome;

    @Column(length = 20, nullable = false, unique = true)
    private String cpf;

    @Column(length = 20, nullable = false, unique = true)
    private String telefone;

    @OneToMany(mappedBy = "cliente") // para ligar o cliente com a conta, esta sendo mapeado por meio atributo cliente
    @JsonIgnoreProperties("cliente")
    private List<Conta> contas;

}
