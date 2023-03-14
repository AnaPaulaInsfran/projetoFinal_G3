package br.gama.itau.demo.repository;

import org.springframework.data.repository.CrudRepository;

import br.gama.itau.demo.model.Conta;

public interface ContaRepo extends CrudRepository <Conta, Long> {
    
}
