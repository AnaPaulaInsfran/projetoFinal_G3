package br.gama.itau.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gama.itau.demo.exceptions.NotFoundException;
import br.gama.itau.demo.model.Cliente;
import br.gama.itau.demo.repository.ClienteRepo;
import br.gama.itau.demo.util.GenerateCliente;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    private final static ClienteRepo clienteRepo = mock(ClienteRepo.class);
    private static ClienteService clienteService;

    @BeforeAll
    static void init() {
        clienteService = new ClienteService(clienteRepo);
    }

    @Test
    void getById_returnClient_whenIdExists() {
        Optional<Cliente> cliente = Optional.of(GenerateCliente.novoCliente());

        BDDMockito.when(clienteRepo.findById(1L)).thenReturn(cliente);

        Cliente clienteRetorno = clienteService.getById(1L);
        assertThat(clienteRetorno).isNotNull();
        assertEquals(cliente.get().getId(), clienteRetorno.getId());
    }

    @Test
    void getById_returnException_whenIdInvalido() {
        Optional<Cliente> cliente = Optional.empty();

        BDDMockito.when(clienteRepo.findById(6L)).thenReturn(cliente);

        assertThrows(NotFoundException.class, () -> {
            clienteService.getById(6L);

        });
    }

    @Test
    void getAll_returnListClient_whenIdExists() {
        List<Cliente> listaCliente = GenerateCliente.listaCliente();
        BDDMockito.when(clienteRepo.findAll()).thenReturn(listaCliente);
        List<Cliente> listaRetorno = clienteService.getAll();

        assertEquals(3,listaRetorno.size());
    }

    @Test
    void newClient_returnNewClient_whenClientSemId() {
        Cliente clienteComId = GenerateCliente.novoClienteSemId();
        clienteComId.setId(9L);
        Cliente clienteSemId = GenerateCliente.novoClienteSemId();

        BDDMockito.when(clienteRepo.save(clienteSemId)).thenReturn(clienteComId);
        Cliente clienteRetorno = clienteService.newCliente(clienteSemId);

        assertEquals(clienteSemId.getNome(),clienteRetorno.getNome());
        assertEquals(clienteSemId.getTelefone(),clienteRetorno.getTelefone());
        assertEquals(clienteSemId.getCpf(),clienteRetorno.getCpf());
        assertEquals(clienteComId.getId(), clienteRetorno.getId());
   
        verify(clienteRepo,Mockito.times(1)).save(clienteSemId);
    }

    @Test
    void newClient_returnNull_whenClientIdExists() {
    Cliente cliente = GenerateCliente.novoCliente3();
    Cliente clienteRetorno = clienteService.newCliente(cliente);

    assertTrue(clienteRetorno == null);

    verify(clienteRepo,Mockito.times(0)).save(cliente);

    }
}