package br.gama.itau.demo.service;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.gama.itau.demo.repository.ContaRepo;
import br.gama.itau.demo.repository.MovimentacaoRepo;

public class MovimentacaoServiceTeste {

    private final MovimentacaoRepo movimentacaoRepo = mock(MovimentacaoRepo.class);
    private final ContaRepo contaRepo = mock(ContaRepo.class);

    private MovimentacaoService movimentacaoService;

    @BeforeAll
    void init() {
        movimentacaoService = new MovimentacaoService(movimentacaoRepo, contaRepo);
    }

    @Test
    void newMovimentacao_returnNewMovimentacao_whenIdNotExists() {

    }

    @Test
    void newMovimentacao_returnNull_whenIdExists() {

    }

    @Test
    void getAll_returnListMovimentacao_whenIdContaExists(){
        
    }

    @Test
    void getAll_returnNull_whenIdContaNotExists(){
        
    }

}
