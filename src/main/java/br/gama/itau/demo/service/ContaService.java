package br.gama.itau.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.gama.itau.demo.model.Cliente;
import br.gama.itau.demo.model.Conta;
import br.gama.itau.demo.repository.ClienteRepo;
import br.gama.itau.demo.repository.ContaRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepo repo;
    private final ClienteRepo clienteRepo;

    public Conta newConta(Conta conta) {
        if (conta.getNumeroConta() > 0) {
            return null;
        }
        Conta newConta = repo.save(conta);
        return newConta;
    }

    public Conta getById(long id) throws NotFoundException {
        Optional<Conta> contaOptional = repo.findById(id);
        if (contaOptional.isEmpty()) {
            throw new NotFoundException();
        }
        return contaOptional.get();
    }

    public Conta updateConta(Conta conta) {
        Optional<Conta> contaOptional = repo.findById(conta.getNumeroConta());

        if (contaOptional.isPresent()) {
            conta.setNumeroConta(conta.getNumeroConta());
            Conta updateConta = repo.save(conta);
            return updateConta;
        }
        return null;
    }

    public List<Conta> getContasClientes(long idCliente) {
        Optional<Cliente> clienteOptional = clienteRepo.findById(idCliente);

        if (clienteOptional.isPresent()) {
            return clienteOptional.get().getContas();
        }
        return null;
    }

}