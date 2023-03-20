package br.gama.itau.demo.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import br.gama.itau.demo.model.Conta;
import br.gama.itau.demo.model.Movimentacao;
import br.gama.itau.demo.repository.ClienteRepo;
import br.gama.itau.demo.repository.ContaRepo;
import br.gama.itau.demo.repository.MovimentacaoRepo;
import br.gama.itau.demo.util.GenerateCliente;
import br.gama.itau.demo.util.GenerateMovimentacao;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MovimentacaoControllerITTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MovimentacaoRepo movimentacaoRepo;

  @Autowired
  private ContaRepo contaRepo;

  @Autowired
  private ClienteRepo clienteRepo;

  @BeforeEach
  public void setup() {
    movimentacaoRepo.deleteAll();
  }

  @Test
  void mostrarMovimentacoes_retornaListaMovimentacoes_quandoIdContaExistir() throws Exception {
    List<Movimentacao> movimentacoes = GenerateMovimentacao.listaMovimentacao();
    clienteRepo.save(GenerateCliente.novoClienteSemId());
    contaRepo.save(Conta.builder()
        .agencia(8622)
        .movimentacoes(movimentacoes)
        .build());

    List<Movimentacao> movimentacoesRetorno = (List<Movimentacao>) movimentacaoRepo.saveAll(movimentacoes);

    ResultActions resultado = mockMvc.perform(get("/movimentacao/1").contentType(MediaType.APPLICATION_JSON));

    resultado.andExpect(status().isOk())
    // .andExpect(jsonPath("$[0].numeroSeq", CoreMatchers.is(movimentacoesRetorno.get(0).getNumeroSeq())));
    .andExpect(jsonPath("$[0].valor", CoreMatchers.is(movimentacoesRetorno.get(0).getValor())));

  }

  @Test
  void cadastrarMovimentacoes_retornaNovaMovimentacao_quandoIdNaoExistir() throws Exception {
    Movimentacao movimentacao = GenerateMovimentacao.novaMovimentacaoSemId();

    contaRepo.save(Conta.builder()
    .agencia(8622)
    .build());


    ResultActions resultado = mockMvc.perform(post("/movimentacao")
        .content(objectMapper.writeValueAsString(movimentacao))
        .contentType(MediaType.APPLICATION_JSON));

    resultado.andExpect(status().isCreated())
        .andExpect(jsonPath("$.valor", CoreMatchers.is(movimentacao.getValor())));
  }

  @Test
  void cadastrarMovimentacoes_retornaBadRequest_quandoIdExistir() {

  }
}
