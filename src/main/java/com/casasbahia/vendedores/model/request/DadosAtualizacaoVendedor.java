package com.casasbahia.vendedores.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DadosAtualizacaoVendedor {

    @NotBlank
    private String matricula;
    private String nome;
    private LocalDate dataNascimento;
    private Long cpfCnpj;
    private String email;

}
