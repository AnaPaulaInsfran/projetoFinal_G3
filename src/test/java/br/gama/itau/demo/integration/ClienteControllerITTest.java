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
import br.gama.itau.demo.repository.ClienteRepo;
import br.gama.itau.demo.util.GenerateCliente;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ClienteControllerITTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ClienteRepo clienteRepo;

  @BeforeEach
  public void setup() {
    clienteRepo.deleteAll();
  }

  @Test
  void getAll_returnListCLient_whenClienteValido() throws Exception {
    // List <Cliente> lista = GenerateCliente.listaClienteSemId();
    // clienteRepo.saveAll(lista);
    List<Cliente> lista = new ArrayList<>();
    lista.add(GenerateCliente.novoClienteSemId());
    lista.add(GenerateCliente.novoClienteSemId2());

    List<Cliente> clientesRetorno = (List<Cliente>) clienteRepo.saveAll(lista);

    ResultActions resposta = mockMvc.perform(get("/cliente")
        .contentType(MediaType.APPLICATION_JSON));

    resposta.andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", CoreMatchers.is(clientesRetorno.size())))
        .andExpect(jsonPath("$[0].nome", CoreMatchers.is(clientesRetorno.get(0).getNome())))
        .andExpect(jsonPath("$[0].telefone", CoreMatchers.is(clientesRetorno.get(0).getTelefone())));
  }

  // @Test
  // void getAll_throwsNotFoundException_whenClienteInvalido() throws Exception {

  // ResultActions resposta = mockMvc.perform(get("/cliente")
  // .contentType(MediaType.APPLICATION_JSON));

  // resposta.andExpect(status().isNotFound());
  // }

  @Test
  void getById_returnCLientDTO_whenIdClienteValido() throws Exception {
    Cliente novoCliente = GenerateCliente.novoClienteSemId();
    Cliente clienteCriado = clienteRepo.save(novoCliente);

    ResultActions resultado = mockMvc.perform(get("/cliente/{id}", clienteCriado.getId())
        .contentType(MediaType.APPLICATION_JSON));

    resultado.andExpect(status().isOk())
        .andExpect(jsonPath("$.cpf", CoreMatchers.is(clienteCriado.getCpf())));
  }

  @Test
  void getById_throwsNotFoundException_whenIdClienteInvalido() throws Exception {

    ResultActions resultado = mockMvc.perform(get("/cliente/100")
        .contentType(MediaType.APPLICATION_JSON));

    resultado.andExpect(status().isNotFound());
  }

  @Test
  void cadastrarCliente_returnNewCLient_whenCLientNotExists() throws Exception {
    Cliente novoCliente = GenerateCliente.novoClienteSemId();

    ResultActions resultado = mockMvc.perform(post("/cliente")
        .content(objectMapper.writeValueAsString(novoCliente))
        .contentType(MediaType.APPLICATION_JSON));

    resultado.andExpect(status().isCreated())
        .andExpect(jsonPath("$.telefone", CoreMatchers.is(novoCliente.getTelefone())));
  }

  @Test
  void cadastrarCliente_throwsBadRquest_whenCLientExists() throws Exception {
    Cliente novoCliente = GenerateCliente.novoCliente();

    ResultActions resultado = mockMvc.perform(post("/cliente")
        .content(objectMapper.writeValueAsString(novoCliente))
        .contentType(MediaType.APPLICATION_JSON));

    resultado.andExpect(status().isBadRequest());
  }

}
