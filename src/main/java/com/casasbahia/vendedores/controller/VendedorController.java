package com.casasbahia.vendedores.controller;

import com.casasbahia.vendedores.model.Vendedor;
import com.casasbahia.vendedores.model.request.DadosAtualizacaoVendedor;
import com.casasbahia.vendedores.model.request.DadosCadastraisVendedor;
import com.casasbahia.vendedores.model.response.DadosDetalhamentoVendedores;
import com.casasbahia.vendedores.model.response.DadosFilialDetalhamento;
import com.casasbahia.vendedores.service.VendedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    VendedorService service;

    @PostMapping
    public ResponseEntity cadastraVendedor(@RequestBody @Valid DadosCadastraisVendedor dadosVendedor, UriComponentsBuilder uriBuilder) {
        Vendedor vendedor = service.cadastrar(dadosVendedor);
        URI uri = uriBuilder.path("/vendedores/{id}").buildAndExpand(vendedor.getMatricula()).toUri();
        DadosFilialDetalhamento filial = service.obtemFilial();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoVendedores(vendedor, filial));
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<DadosDetalhamentoVendedores>> listarVendedores() {
        List<Vendedor> vendedores = service.listar();
        DadosFilialDetalhamento filial = service.obtemFilial();
        List<DadosDetalhamentoVendedores> detalhes = vendedores.stream()
                .map(v -> new DadosDetalhamentoVendedores(v, filial))
                .collect(Collectors.toList());
        return ResponseEntity.ok(detalhes);
    }

    @PutMapping
    public ResponseEntity atualizarVendedor(@RequestBody @Valid DadosAtualizacaoVendedor dadosVendedor) {
        Vendedor vendedor = service.atualizar(dadosVendedor);
        DadosFilialDetalhamento filial = service.obtemFilial();
        return ResponseEntity.ok(new DadosDetalhamentoVendedores(vendedor, filial));
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity excluirVendedor(@PathVariable String matricula) {
        service.excluir(matricula);
        return ResponseEntity.noContent().build();
    }

}
