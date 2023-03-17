package br.gama.itau.demo.util;
import java.util.List;
import java.util.ArrayList;

import br.gama.itau.demo.model.Cliente;

public class GenerateCliente {
    
    public static Cliente novoCliente(){
        return Cliente.builder()
        .id(1L)
        .cpf("34698765468")
        .telefone("11955663321")
        .nome("Gabriel Sr. BigBoss")
        .build();
    }

    public static Cliente novoCliente2(){
        return Cliente.builder()
        .id(2L)
        .cpf("34698765478")
        .telefone("11955663521")
        .nome("Ana MegaBoss")
        .build();
    }

    public static Cliente novoCliente3(){
        return Cliente.builder()
        .id(3L)
        .cpf("34698785468")
        .telefone("11945663321")
        .nome("Wesley SuperBoss")
        .build();
    }

    public static Cliente novoClienteSemId(){
        return Cliente.builder()
        .cpf("34694785468")
        .telefone("11945668321")
        .nome("Edersson HiperBoss")
        .build();
    }

    public static Cliente novoClienteComLista(){
        return Cliente.builder()
        .id(1L)
        .cpf("34698765468")
        .telefone("11955663321")
        .nome("Gabriel Sr. BigBoss")
        .build();
    }

    public static List <Cliente> listaCliente(){
        ArrayList<Cliente> listaCliente = new ArrayList<>();
        listaCliente.add(novoCliente());
        listaCliente.add(novoCliente2());
        listaCliente.add(novoCliente3());
        return listaCliente();
    }

}
