package br.gama.itau.demo.repository;

import org.springframework.data.repository.CrudRepository;

import br.gama.itau.demo.model.Cliente;

public interface ClienteRepo extends CrudRepository <Cliente, Long> {
    
}
