package br.gama.itau.demo.integration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gama.itau.demo.model.Cliente;
import br.gama.itau.demo.model.Conta;
import br.gama.itau.demo.model.TipoConta;
import br.gama.itau.demo.repository.ClienteRepo;
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

    @Autowired
  private ClienteRepo clienteRepo;

    @BeforeEach
    public void setup() {
        contaRepo.deleteAll();
    }

    @Test
    void getById_returnConta_whenIdExists() throws Exception {

        Conta conta = Conta.builder().tipoConta(TipoConta.ESTUDANTIL).saldo(5642).agencia(0101).build();
        Conta novaConta = contaRepo.save(conta);

        ResultActions resultado = mockMvc.perform(get("/contas/{id}", novaConta.getNumeroConta())
                .contentType(MediaType.APPLICATION_JSON));

        resultado.andExpect(status().isOk())
                .andExpect(jsonPath("$.agencia", CoreMatchers.is(novaConta.getAgencia())))
                .andExpect(jsonPath("$.saldo", CoreMatchers.is(novaConta.getSaldo())));

    }

    @Test
  void getAllByCustomer_returnListContas_whenIdExists() throws Exception {
    List<Conta> lista = new ArrayList<>();
    Cliente cliente = Cliente.builder().nome("Gabriel").telefone("3546456994").cpf("546864766").contas(lista).build();
    lista.add(Conta.builder().cliente(cliente).agencia(0101).build());
    lista.add(Conta.builder().cliente(cliente).agencia(0101).build());
    lista.add(Conta.builder().cliente(cliente).agencia(0101).build());

    clienteRepo.save(cliente);
    List<Conta> listaRetorno = (List<Conta>) contaRepo.saveAll(lista);

    ResultActions resposta = mockMvc.perform(get("/contas/cliente/1")
        .contentType(MediaType.APPLICATION_JSON));

    resposta.andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", CoreMatchers.is(listaRetorno.size())))
        .andExpect(jsonPath("$[0].tipoConta", CoreMatchers.is(listaRetorno.get(0).getTipoConta())))
        .andExpect(jsonPath("$[0].agencia", CoreMatchers.is(listaRetorno.get(0).getAgencia())));

  }
    @Test
    void getAllByCustomer_returnNotFound_whenIdNotExists() {

    }

    @Test
  void newConta_returnConta_whenIdNotExists() throws Exception {
    Conta conta = Conta.builder().tipoConta(TipoConta.ESTUDANTIL).saldo(5642).agencia(0101).build();

    ResultActions resultado = mockMvc.perform(post("/contas")
        .content(objectMapper.writeValueAsString(conta))
        .contentType(MediaType.APPLICATION_JSON));

        resultado.andExpect(status().isCreated())
                .andExpect(jsonPath("$.agencia", CoreMatchers.is(conta.getAgencia())));
  }
}
