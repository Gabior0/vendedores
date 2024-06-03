package com.casasbahia.vendedores.service;

import com.casasbahia.vendedores.client.FilialClient;
import com.casasbahia.vendedores.model.Vendedor;
import com.casasbahia.vendedores.model.request.DadosAtualizacaoVendedor;
import com.casasbahia.vendedores.model.request.DadosCadastraisVendedor;
import com.casasbahia.vendedores.model.response.DadosFilialDetalhamento;
import com.casasbahia.vendedores.repository.VendedorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VendedorService {

    @Autowired
    VendedorRepository repository;

    @Autowired
    FilialClient client;

    @Transactional
    public Vendedor cadastrar(@Valid DadosCadastraisVendedor dadosVendedor) {
        Vendedor vendedor = new Vendedor(dadosVendedor);
        repository.save(vendedor);
        return vendedor;
    }


    public List<Vendedor> listar() {
        return repository.findAll();
    }

    @Transactional
    public Vendedor atualizar(@Valid DadosAtualizacaoVendedor dadosVendedor) {
        Vendedor vendedor = repository.getReferenceByMatricula(dadosVendedor.getMatricula());
        vendedor.atualizarInformacoes(dadosVendedor);
        return vendedor;
    }

    @Transactional
    public void excluir(String matricula) {
        repository.deleteByMatricula(matricula);
    }

    public DadosFilialDetalhamento obtemFilial() {
        return new DadosFilialDetalhamento(client.obtemfilial().getBody());
    }
}
