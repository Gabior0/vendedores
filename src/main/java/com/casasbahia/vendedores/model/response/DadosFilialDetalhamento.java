package com.casasbahia.vendedores.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DadosFilialDetalhamento {
    private Long id;
    private String nome;
    private Long cnpj;
    private String cidade;
    private String uf;
    private String tipo;
    private String ativo;
    private LocalDate dataCadastro;
    private String ultimaAtualizacao;

    public DadosFilialDetalhamento(DadosFilialDetalhamento dadosFilialDetalhamento) {
        this(dadosFilialDetalhamento.getId(),
                dadosFilialDetalhamento.getNome(),
                dadosFilialDetalhamento.getCnpj(),
                dadosFilialDetalhamento.getCidade(),
                dadosFilialDetalhamento.getUf(),
                dadosFilialDetalhamento.getTipo(),
                dadosFilialDetalhamento.getAtivo(),
                dadosFilialDetalhamento.getDataCadastro(),
                dadosFilialDetalhamento.getUltimaAtualizacao());
    }
}
