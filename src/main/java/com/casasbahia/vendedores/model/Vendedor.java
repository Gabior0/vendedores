package com.casasbahia.vendedores.model;

import com.casasbahia.vendedores.model.request.DadosAtualizacaoVendedor;
import com.casasbahia.vendedores.model.request.DadosCadastraisVendedor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "vendedores")
@Entity(name = "Vendedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matricula;
    private String nome;
    private LocalDate dataNascimento;
    private Long cpfCnpj;
    private String email;
    private String tipoContratacao;

    public Vendedor(DadosCadastraisVendedor dadosVendedor) {
        this.matricula = "";
        this.nome = dadosVendedor.getNome();
        this.dataNascimento = dadosVendedor.getDataNascimento();
        this.cpfCnpj = dadosVendedor.getCpfCnpj();
        this.email = dadosVendedor.getEmail();
        this.tipoContratacao = dadosVendedor.getTipoContratacao();
    }


    @PostPersist
    public void preencheMatricula() {

        switch (tipoContratacao.toUpperCase()) {
            case "CLT":
                this.matricula = id + "-CLT";
                break;
            case "PESSOA JURÍDICA":
                this.matricula = id + "-PJ";
                break;
            case "OUTSOURCING":
                this.matricula = id + "-OUT";
                break;
        }
    }

    public void atualizarInformacoes(DadosAtualizacaoVendedor vendedor) {

        if (!vendedor.getNome().isBlank()) {
            this.nome = vendedor.getNome();
        }

        if (vendedor.getDataNascimento() != null) {
            this.dataNascimento = vendedor.getDataNascimento();
        }

        if (vendedor.getCpfCnpj() != null) {
            this.cpfCnpj = vendedor.getCpfCnpj();
        }

        if (vendedor.getEmail() != null) {
            this.email = vendedor.getEmail();
        }

    }
}
