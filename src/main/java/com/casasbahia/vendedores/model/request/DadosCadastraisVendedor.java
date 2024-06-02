package com.casasbahia.vendedores.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DadosCadastraisVendedor {

    @NotBlank
    private String nome;
    private LocalDate dataNascimento;
    @NotNull
    private Long cpfCnpj;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String tipoContratacao;

}
