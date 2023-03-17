package br.gama.itau.demo.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gama.itau.demo.repository.ContaRepo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ContaControllerITTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ContaRepo contaRepo;

    @BeforeEach
    public void setup() {
        contaRepo.deleteAll();
    }

    @Test
    void getById_returnConta_whenIdExists () {

    }

    @Test
    void getAllByCustomer_returnListContas_whenIdExists () {

    }

    @Test
    void getAllByCustomer_returnNotFound_whenIdNotExists () {

    }

    @Test
    void newConta_returnConta_whenIdNotExists () {

    }
}
