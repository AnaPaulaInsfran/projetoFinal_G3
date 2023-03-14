package br.gama.itau.demo.service;

import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.gama.itau.demo.model.Conta;
import br.gama.itau.demo.repository.ContaRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepo repo;

    public Conta newConta(Conta conta) {
        if (conta.getNumeroConta() > 0) {
            return null;
        }
        Conta newConta = repo.save(conta);
        return newConta;
    }

    public Conta getById(long id){
        Optional<Conta> contaOptional = repo.findById(id);
        if(contaOptional.isEmpty()){
            throw new NotFoundException();
        }
        return contaOptional.get();
        


}
}