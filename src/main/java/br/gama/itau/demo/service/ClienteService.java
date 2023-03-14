package br.gama.itau.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.gama.itau.demo.model.Cliente;
import br.gama.itau.demo.repository.ClienteRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ClienteService {
    
    private final ClienteRepo repo;

    public Cliente getById(long id) throws NotFoundException{
        Optional<Cliente> clienteOptional=repo.findById(id);

        if(clienteOptional.isEmpty()){
            throw new NotFoundException();
        }
        return clienteOptional.get();
    }

    public List<Cliente> getAll(){
       List<Cliente> clientes = (List<Cliente>) repo.findAll();
        return clientes;
    }

    public Cliente newCliente(Cliente novoCliente){
        if(novoCliente.getId() > 0){
            return null;
        }
        Cliente cliente = repo.save(novoCliente);
        return cliente;
    }

}
