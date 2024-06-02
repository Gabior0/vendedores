package com.casasbahia.vendedores.client;

import com.casasbahia.vendedores.model.response.DadosFilialDetalhamento;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "filial", url = "http://localhost:8081")
public interface FilialClient {
    @GetMapping("/filial")
    ResponseEntity<DadosFilialDetalhamento> obtemfilial();
}
