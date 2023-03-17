package br.gama.itau.demo.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gama.itau.demo.repository.MovimentacaoRepo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MovimentacaoControllerITTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private MovimentacaoRepo movimentacaoRepo;

    @BeforeEach
    public void setup() {
        movimentacaoRepo.deleteAll();
    }

    @Test
    void mostrarMovimentacoes_retornaListaMovimentacoes_quandoIdContaExistir () {

    }

    @Test
    void cadastrarMovimentacoes_retornaNovaMovimentacao_quandoIdNaoExistir () {

    }

    @Test
    void cadastrarMovimentacoes_retornaBadRequest_quandoIdExistir () {

    }
}
