package com.casasbahia.vendedores.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.casasbahia.vendedores.jsonHandler.JsonHandler;
import com.casasbahia.vendedores.model.request.DadosAtualizacaoVendedor;
import com.casasbahia.vendedores.model.request.DadosCadastraisVendedor;
import com.casasbahia.vendedores.model.response.DadosDetalhamentoVendedores;
import com.casasbahia.vendedores.service.VendedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;


public class VendedorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VendedorService vendedorService;

    @InjectMocks
    private VendedorController vendedorController;


    private DadosCadastraisVendedor dadosVendedor;

    @BeforeEach
    public void setup() throws IOException {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendedorController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        dadosVendedor = JsonHandler.readJsonFromFile("src/test/DadosCadastraisVendedor.json", DadosCadastraisVendedor.class);

    }

    @Test
    public void deveCadastrarVendedor() throws Exception {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        when(vendedorService.cadastrar(any(DadosCadastraisVendedor.class), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/vendedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dadosVendedor)))
                .andExpect(status().isOk());
    }

    @Test
    public void deveListarVendedores() throws Exception {

        List<DadosDetalhamentoVendedores> dadosDetalhamentoVendedores = List.of(JsonHandler.readJsonFromFile("src/test/DadosDetalhamentoVendedor.json", DadosDetalhamentoVendedores.class));

        when(vendedorService.listar()).thenReturn(ResponseEntity.ok(dadosDetalhamentoVendedores));

        mockMvc.perform(get("/vendedores")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deveAtualizarVendedor() throws Exception {
        DadosAtualizacaoVendedor dadosVendedor = JsonHandler.readJsonFromFile("src/test/DadosAtualizacaoVendedor.json", DadosAtualizacaoVendedor.class);

        when(vendedorService.atualizar(any(DadosAtualizacaoVendedor.class))).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(put("/vendedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dadosVendedor)))
                .andExpect(status().isOk());
    }

    @Test
    public void deveExcluirVendedor() throws Exception {
        String matricula = "12345";

        when(vendedorService.excluir(anyString())).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/vendedores/{matricula}", matricula))
                .andExpect(status().isOk());
    }
}