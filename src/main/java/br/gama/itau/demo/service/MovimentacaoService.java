package br.gama.itau.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.gama.itau.demo.model.Conta;
import br.gama.itau.demo.model.Movimentacao;
import br.gama.itau.demo.repository.ContaRepo;
import br.gama.itau.demo.repository.MovimentacaoRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepo repo;
    private final ContaRepo contaRepo;

    public Movimentacao newMovimentacao(Movimentacao movimentacao) {
        if (movimentacao.getNumeroSeq() > 0) {
            return null;
        }
        Movimentacao newMovimentacao = repo.save(movimentacao);
        return newMovimentacao;
    }

    public List<Movimentacao> getAll(long numeroConta) {
        Optional<Conta> conta = contaRepo.findById(numeroConta);
        if (conta.isPresent()) {
            List<Movimentacao> movimentacoes = conta.get().getMovimentacoes();
            return movimentacoes;
        }
        return null;
    }

}
