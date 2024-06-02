package com.casasbahia.vendedores.model.response;

import com.casasbahia.vendedores.model.Vendedor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DadosDetalhamentoVendedores {


    private String matricula;
    private String nome;
    private LocalDate dataNascimento;
    private Long cpfCnpj;
    private String email;
    private String tipoContratacao;
    private DadosFilialDetalhamento filial;


    public DadosDetalhamentoVendedores(Vendedor vendedor, DadosFilialDetalhamento filial) {
        this.matricula = vendedor.getMatricula();
        this.nome = vendedor.getNome();
        this.dataNascimento = vendedor.getDataNascimento();
        this.cpfCnpj = vendedor.getCpfCnpj();
        this.email = vendedor.getEmail();
        this.tipoContratacao = vendedor.getTipoContratacao();
        this.filial = new DadosFilialDetalhamento(filial);
    }

}
