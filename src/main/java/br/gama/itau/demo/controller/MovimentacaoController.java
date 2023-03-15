package br.gama.itau.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gama.itau.demo.model.Movimentacao;
import br.gama.itau.demo.service.MovimentacaoService;

@RestController
@RequestMapping("/movimentacao")
public class MovimentacaoController {
    private MovimentacaoService service;
    
    @GetMapping("/{id}")
    public List<Movimentacao> mostrarMovimentacoes(@PathVariable long id){
        return service.getAll(id);
                  
        
    }  


    @PostMapping
    public ResponseEntity<Movimentacao> cadastrarMovimentacoes(@RequestBody Movimentacao novaMovimentacao ){
        Movimentacao movimentacao = service.newMovimentacao(novaMovimentacao);
        if (movimentacao == null) {
            return ResponseEntity.badRequest().build();
            
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(movimentacao);
    }
    
    
}