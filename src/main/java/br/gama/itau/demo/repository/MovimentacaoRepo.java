package br.gama.itau.demo.repository;

import org.springframework.data.repository.CrudRepository;

import br.gama.itau.demo.model.Movimentacao;

public interface MovimentacaoRepo extends CrudRepository<Movimentacao, Long> {
    
}
