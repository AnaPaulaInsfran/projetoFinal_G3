package br.gama.itau.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private MovimentacaoService service;

  @GetMapping("/{id}")
  public List<Movimentacao> mostrarMovimentacoes(@PathVariable long id) {
    return service.getAll(id);
  }

  @PostMapping
  public ResponseEntity<Movimentacao> cadastrarMovimentacoes(@RequestBody Movimentacao novaMovimentacao) {
    Movimentacao movimentacao = service.newMovimentacao(novaMovimentacao);
    if (movimentacao == null) {
      return ResponseEntity.badRequest().build();

    }
    return ResponseEntity.status(HttpStatus.CREATED).body(movimentacao);
  }

  @PostMapping("/{idOrigem}/{idDestino}/{valor}")
  public ResponseEntity<Movimentacao> transferirValor(@PathVariable long idOrigem, @PathVariable long idDestino,
      @PathVariable double valor) {
    boolean sucesso = service.transferirValores(idOrigem, idDestino, valor);

    if (sucesso) {
      return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    return ResponseEntity.badRequest().build();
  }

}
