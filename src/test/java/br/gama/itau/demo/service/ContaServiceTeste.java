package br.gama.itau.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import br.gama.itau.demo.exceptions.NotFoundException;
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

        assertThrows (NotFoundException.class, ()-> {
            contaService.getById(conta.getNumeroConta()); } );
    }

    @Test
    void newConta_returnNovaConta_whenContaValida() {
        Conta conta = new Conta();
        BDDMockito.when(contaRepo.save(ArgumentMatchers.any(Conta.class)))
        .thenReturn(conta);
        
        Conta contaCriada = contaService.newConta(conta);

        assertTrue(contaCriada != null);
        assertTrue(contaCriada.getNumeroConta() >= 0);

        verify(contaRepo, Mockito.times(1)).save(conta);
    }
}
