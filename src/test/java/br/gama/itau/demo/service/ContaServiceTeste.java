package br.gama.itau.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import br.gama.itau.demo.exceptions.NotFoundException;
import br.gama.itau.demo.model.Cliente;
import br.gama.itau.demo.model.Conta;
import br.gama.itau.demo.repository.ClienteRepo;
import br.gama.itau.demo.repository.ContaRepo;

public class ContaServiceTeste {

    private final ContaRepo contaRepo = mock(ContaRepo.class);
    private final ClienteRepo clienteRepo = mock(ClienteRepo.class);
    private ContaService contaService;

    @BeforeEach
    public void init() {
        this.contaService = new ContaService(contaRepo, clienteRepo);
    }

    @Test
    void getById_returnConta_whenIdExists() {
        final long ID = 1L;
        Conta conta = new Conta();
        conta.setNumeroConta(ID);

        BDDMockito.when(contaRepo.findById(ID)).thenReturn(Optional.of(conta));

        Conta contaTest = contaService.getById(ID);

        assertEquals(1L, contaTest.getNumeroConta());
        assertTrue(contaTest != null);
    }

    @Test
    void getById_returnConta_whenIdNotExists() {
        Conta conta = new Conta();
        conta.setNumeroConta(2L);

        assertThrows(NotFoundException.class, () -> {
            contaService.getById(conta.getNumeroConta());
        });
    }

    @Test
    void newConta_returnNovaConta_whenContaValida() {
        Conta conta = Conta.builder().build();
        Conta contaRetorno = new Conta();
        contaRetorno.setAgencia(3221);
        contaRetorno.setNumeroConta(1L);
        BDDMockito.when(contaRepo.save(ArgumentMatchers.any(Conta.class)))
                .thenReturn(contaRetorno);

        Conta contaCriada = contaService.newConta(conta);

        assertTrue(contaCriada != null);
        assertTrue(contaCriada.getNumeroConta() > 0);

        verify(contaRepo, Mockito.times(1)).save(conta);
    }

    @Test
    void updateConta_returnContaAtualizada_whenContaValida() {
        Optional<Conta> contaOptional = Optional.of(new Conta());
        contaOptional.get().setNumeroConta(1L);
        Conta conta = new Conta();
        conta.setNumeroConta(1L);

        BDDMockito.when(contaRepo.findById(conta.getNumeroConta()))
                .thenReturn(contaOptional);
        BDDMockito.when(contaRepo.save(conta)).thenReturn(contaOptional.get());

        Conta updateConta = contaService.updateConta(conta);

        assertTrue(updateConta != null);
        assertEquals(1L, updateConta.getNumeroConta());

        verify(contaRepo, Mockito.times(1))
                .save(conta);
    }

    @Test
    void updateConta_returnNull_whenContaInvalida() {
        Conta conta = new Conta();
        conta.setNumeroConta(1L);

        BDDMockito.when(contaRepo.findById(conta.getNumeroConta()))
                .thenReturn(Optional.empty());

        Conta contaNula = contaService.updateConta(conta);

        assertTrue(contaNula == null);
    }

    @Test
    void getContasClientes_returnListaContas_whenClienteValido() {
        Conta conta = new Conta();
        Conta conta2 = new Conta();
        ArrayList<Conta> listaContas = new ArrayList<>();
        listaContas.add(conta);
        listaContas.add(conta2);
        Optional<Cliente> clienteOptional = Optional.of(new Cliente());
        clienteOptional.get().setContas(listaContas);
        clienteOptional.get().setId(1L);
        conta.setCliente(clienteOptional.get());
        conta2.setCliente(clienteOptional.get());

        BDDMockito.when(clienteRepo.findById(1L)).thenReturn(clienteOptional);
        List<Conta> contas = contaService.getContasClientes(1L);

        assertEquals(2, contas.size());
        assertFalse(contas.isEmpty());
        assertEquals(1L, contas.get(0).getCliente().getId());
    }

    @Test
    void getContasClientes_returnNull_whenClienteInvalido() {

        BDDMockito.when(clienteRepo.findById(1L)).thenReturn(Optional.empty());
        List<Conta> contaVazia = contaService.getContasClientes(1L);

        // assertEquals(0, contaVazia.size());
        assertTrue(contaVazia == null);
    }

}
