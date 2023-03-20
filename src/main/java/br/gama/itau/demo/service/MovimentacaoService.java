package br.gama.itau.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.gama.itau.demo.exceptions.NotFoundException;
import br.gama.itau.demo.model.Conta;
import br.gama.itau.demo.model.Movimentacao;
import br.gama.itau.demo.model.TipoOperacao;
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

    public boolean transferirValores(long contaOrigem, long contaDestino, double valor) {
        Optional<Conta> contaDeOrigem = contaRepo.findById(contaOrigem);
        Optional<Conta> contaDeDestino = contaRepo.findById(contaDestino);
        if (contaDeDestino.isPresent() && contaDeOrigem.isPresent() && valor > 0) {
            if (contaDeOrigem.get().getSaldo() >= valor) {
                contaDeOrigem.get().setSaldo(contaDeOrigem.get().getSaldo() - valor);
                contaDeDestino.get().setSaldo(contaDeDestino.get().getSaldo() + valor);
                contaRepo.save(contaDeOrigem.get());
                contaRepo.save(contaDeDestino.get());

                Movimentacao mov1 = Movimentacao.builder()
                .conta(contaDeOrigem.get()).valor(valor).dataOperacao(LocalDate.now()).tipoOperacao(TipoOperacao.DEBITO).build();
                Movimentacao mov2 = Movimentacao.builder()
                .conta(contaDeDestino.get()).valor(valor).dataOperacao(LocalDate.now()).tipoOperacao(TipoOperacao.CREDITO).build();
                repo.save(newMovimentacao(mov1));
                repo.save(newMovimentacao(mov2));


                return true;
            } else {
                return false;
            }
        }
        throw new NotFoundException("Uma ou mais contas não foram encontradas");
    }

}
