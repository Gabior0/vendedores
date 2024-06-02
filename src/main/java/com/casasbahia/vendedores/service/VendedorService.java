package com.casasbahia.vendedores.service;

import com.casasbahia.vendedores.client.FilialClient;
import com.casasbahia.vendedores.model.Vendedor;
import com.casasbahia.vendedores.model.request.DadosAtualizacaoVendedor;
import com.casasbahia.vendedores.model.request.DadosCadastraisVendedor;
import com.casasbahia.vendedores.model.response.DadosDetalhamentoVendedores;
import com.casasbahia.vendedores.model.response.DadosFilialDetalhamento;
import com.casasbahia.vendedores.repository.VendedorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class VendedorService {

    @Autowired
    VendedorRepository repository;

    @Autowired
    FilialClient client;

    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastraisVendedor dadosVendedor, UriComponentsBuilder uriBuilder) {
        Vendedor vendedor = new Vendedor(dadosVendedor);
        repository.save(vendedor);
        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(vendedor.getMatricula()).toUri();
        DadosFilialDetalhamento filial = new DadosFilialDetalhamento(client.obtemfilial().getBody());
        return ResponseEntity.created(uri).body(new DadosDetalhamentoVendedores(vendedor, filial));
    }
    

    public ResponseEntity<List<DadosDetalhamentoVendedores>> listar() {
        DadosFilialDetalhamento filial = new DadosFilialDetalhamento(client.obtemfilial().getBody());
        List<DadosDetalhamentoVendedores> vendedor = repository.findAll().stream().map(dados -> new DadosDetalhamentoVendedores(dados, filial)).toList();
        return ResponseEntity.ok(vendedor);
    }

    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoVendedor dadosVendedor) {
        Vendedor vendedor = repository.getReferenceByMatricula(dadosVendedor.getMatricula());
        vendedor.atualizarInformacoes(dadosVendedor);
        DadosFilialDetalhamento filial = new DadosFilialDetalhamento(client.obtemfilial().getBody());
        return ResponseEntity.ok(new DadosDetalhamentoVendedores(vendedor, filial));
    }

    public ResponseEntity excluir(@PathVariable String matricula) {
        repository.deleteByMatricula(matricula);
        return ResponseEntity.noContent().build();
    }
}
