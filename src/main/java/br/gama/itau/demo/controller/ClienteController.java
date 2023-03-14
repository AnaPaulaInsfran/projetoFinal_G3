package br.gama.itau.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gama.itau.demo.model.Cliente;
import br.gama.itau.demo.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    
@Autowired
private ClienteService service;

@GetMapping
public ResponseEntity<List<Cliente>> getAll(){
    List<Cliente> clientes = service.getAll();
    return ResponseEntity.ok(clientes);
}

}
