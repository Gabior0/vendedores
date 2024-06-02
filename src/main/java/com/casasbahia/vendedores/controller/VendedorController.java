package com.casasbahia.vendedores.controller;

import com.casasbahia.vendedores.model.request.DadosAtualizacaoVendedor;
import com.casasbahia.vendedores.model.request.DadosCadastraisVendedor;
import com.casasbahia.vendedores.model.response.DadosDetalhamentoVendedores;
import com.casasbahia.vendedores.service.VendedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    VendedorService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastraVendedor(@RequestBody @Valid DadosCadastraisVendedor dadosVendedor, UriComponentsBuilder uriBuilder) {
        return service.cadastrar(dadosVendedor, uriBuilder);
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<DadosDetalhamentoVendedores>> listarVendedores() {
        return service.listar();
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarVendedor(@RequestBody @Valid DadosAtualizacaoVendedor dadosVendedor) {
        return service.atualizar(dadosVendedor);
    }

    @DeleteMapping("/{matricula}")
    @Transactional
    public ResponseEntity excluirVendedor(@PathVariable String matricula) {
        return service.excluir(matricula);
    }

}
