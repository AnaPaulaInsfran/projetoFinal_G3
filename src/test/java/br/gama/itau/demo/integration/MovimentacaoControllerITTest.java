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

import br.gama.itau.demo.model.Cliente;
import br.gama.itau.demo.model.Conta;
import br.gama.itau.demo.model.Movimentacao;
import br.gama.itau.demo.model.TipoConta;
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
    contaRepo.deleteAll();
    clienteRepo.deleteAll();
  }

  @Test
  void mostrarMovimentacoes_retornaListaMovimentacoes_quandoIdContaExistir() throws Exception {
    Cliente clienteCriado = clienteRepo.save(GenerateCliente.novoClienteSemId());

    Conta conta = contaRepo.save(Conta.builder()
        .cliente(clienteCriado)
        .agencia(8622)
        .build());

    List<Movimentacao> movimentacoes = GenerateMovimentacao.listaMovimentacaoSemIdConta();

    movimentacoes.get(0).setConta(conta);
    List<Movimentacao> movimentacoesRetorno = (List<Movimentacao>) movimentacaoRepo.saveAll(movimentacoes);

    ResultActions resultado = mockMvc
        .perform(get("/movimentacao/{id}", conta.getNumeroConta()).contentType(MediaType.APPLICATION_JSON));

    resultado.andExpect(status().isOk())
        // .andExpect(jsonPath("$[0].numeroSeq",
        // CoreMatchers.is(movimentacoesRetorno.get(0).getNumeroSeq())));
        .andExpect(jsonPath("$[0].valor", CoreMatchers.is(movimentacoesRetorno.get(0).getValor())));

  }

  @Test
  void cadastrarMovimentacoes_retornaNovaMovimentacao_quandoIdNaoExistir() throws Exception {
    Cliente clienteSalvo = clienteRepo.save(GenerateCliente.novoClienteSemId());

    Conta contaRetorno = contaRepo.save(Conta.builder()
        .cliente(clienteSalvo)
        .agencia(8622)
        .build());
    Movimentacao movimentacao = GenerateMovimentacao.novaMovimentacaoSemId();
    movimentacao.setConta(contaRetorno);

    ResultActions resultado = mockMvc.perform(post("/movimentacao")
        .content(objectMapper.writeValueAsString(movimentacao))
        .contentType(MediaType.APPLICATION_JSON));

    resultado.andExpect(status().isCreated())
        .andExpect(jsonPath("$.valor", CoreMatchers.is(movimentacao.getValor())));
  }

  // @Test
  // void cadastrarMovimentacoes_retornaBadRequest_quandoIdExistir() {
  // Movimentacao movimentacao = GenerateMovimentacao.novaMovimentacaoSemId();
  // movimentacaoRepo.save(movimentacao);

  // // ResultActions resultado = mockMvc.perform(post("/movimentacao/1")

  // // .contentType(MediaType.APPLICATION_JSON));

  // // resultado.andExpect(status().isBadRequest());
  // }

  @Test
  void transferirValor_retornaStatusOk_quandoContasEValorValidos() throws Exception {
    Cliente clienteSalvo = clienteRepo.save(GenerateCliente.novoClienteSemId());
    Conta conta1 = contaRepo.save(Conta.builder()
        .agencia(8622)
        .cliente(clienteSalvo)
        .tipoConta(TipoConta.PESSOA_FISICA)
        .saldo(10000)
        .build());
    Conta conta2 = contaRepo.save(Conta.builder()
        .agencia(8622)
        .tipoConta(TipoConta.ESTUDANTIL)
        .cliente(clienteSalvo)
        .build());

    ResultActions resultado = mockMvc
        .perform(post("/movimentacao/{origem}/{destino}/1000", conta1.getNumeroConta(), conta2.getNumeroConta())
            .contentType(MediaType.APPLICATION_JSON));

    resultado.andExpect(status().isCreated());

  }

  @Test
  void transferirValor_retornaNotFound_quandoContaInvalida() throws Exception {
    Cliente clienteSalvo = clienteRepo.save(GenerateCliente.novoClienteSemId());
    Conta conta = contaRepo.save(Conta.builder()
        .agencia(8622)
        .cliente(clienteSalvo)
        .tipoConta(TipoConta.PESSOA_FISICA)
        .saldo(10000)
        .build());

    ResultActions resultado = mockMvc.perform(post("/movimentacao/{origem}/2/1000", conta.getNumeroConta())
        .contentType(MediaType.APPLICATION_JSON));

    resultado.andExpect(status().isNotFound());

  }

  @Test
  void transferirValor_retornaBadRequest_quandoSaldoInsuficiente() throws Exception {
    Cliente clienteSalvo = clienteRepo.save(GenerateCliente.novoClienteSemId());
    Conta conta1 = contaRepo.save(Conta.builder()
        .agencia(8622)
        .cliente(clienteSalvo)
        .tipoConta(TipoConta.PESSOA_FISICA)
        .saldo(500)
        .build());
    Conta conta2 = contaRepo.save(Conta.builder()
        .agencia(8622)
        .tipoConta(TipoConta.ESTUDANTIL)
        .cliente(clienteSalvo)
        .build());

    ResultActions resultado = mockMvc
        .perform(post("/movimentacao/{origem}/{destino}/1000", conta1.getNumeroConta(), conta2.getNumeroConta())
            .contentType(MediaType.APPLICATION_JSON));

    resultado.andExpect(status().isBadRequest());

  }

}