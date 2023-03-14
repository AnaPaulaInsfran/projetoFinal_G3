package br.gama.itau.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gama.itau.demo.model.Conta;
import br.gama.itau.demo.service.ContaService;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired

    private ContaService service;

    @GetMapping("/{id}")
    public ResponseEntity<Conta> getById(@PathVariable long id) throws NotFoundException {
        Conta conta = service.getById(id);

        return ResponseEntity.ok(conta);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<Conta>> getAllByCustomer(@PathVariable long id) {
        List<Conta> contas = service.getContasClientes(id);
        if (contas.size() == 0) {
            return ResponseEntity.notFound().build();
            
        }
        return ResponseEntity.ok(contas);
    }

    @PostMapping
    public ResponseEntity<Conta> newConta(@RequestBody Conta novaConta){
        Conta contaInserida = service.newConta(novaConta);

        if(contaInserida == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(contaInserida);

    } 
}
