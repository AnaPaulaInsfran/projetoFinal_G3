package br.gama.itau.demo.controller;

import java.util.ArrayList;
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

import br.gama.itau.demo.dto.ClienteDTO;
import br.gama.itau.demo.dto.ClienteIdDTO;
import br.gama.itau.demo.model.Cliente;
import br.gama.itau.demo.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAll() {
        List<Cliente> clientes = service.getAll();
        ArrayList<ClienteDTO> clientesDTO = new ArrayList<>();

        for (int i = 0; i < clientes.size(); i++) {
            ClienteDTO clienteDTO = new ClienteDTO(clientes.get(i));
            clientesDTO.add(clienteDTO);
        }
        return ResponseEntity.ok(clientesDTO);

    }

    @GetMapping("/{id}")
    public ResponseEntity <ClienteIdDTO> getById(@PathVariable long id) {
        Cliente cliente = service.getById(id);
        ClienteIdDTO clienteIdDTO = new ClienteIdDTO(cliente);
        return ResponseEntity.ok(clienteIdDTO);
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente novoCliente){
        Cliente clienteInserido = service.newCliente(novoCliente);
        if(clienteInserido == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteInserido);
    }
    

}
