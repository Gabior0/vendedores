package com.casasbahia.vendedores.controller;

import com.casasbahia.vendedores.jsonHandler.JsonHandler;
import com.casasbahia.vendedores.model.Vendedor;
import com.casasbahia.vendedores.model.request.DadosAtualizacaoVendedor;
import com.casasbahia.vendedores.model.request.DadosCadastraisVendedor;
import com.casasbahia.vendedores.model.response.DadosFilialDetalhamento;
import com.casasbahia.vendedores.service.VendedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
        DadosCadastraisVendedor dadosVendedor = JsonHandler.readJsonFromFile("src/test/DadosCadastraisVendedor.json", DadosCadastraisVendedor.class);
        Vendedor vendedor = new Vendedor(dadosVendedor);
        DadosFilialDetalhamento filial = JsonHandler.readJsonFromFile("src/test/Filial.json", DadosFilialDetalhamento.class);

        when(vendedorService.cadastrar(any(DadosCadastraisVendedor.class))).thenReturn(vendedor);
        when(vendedorService.obtemFilial()).thenReturn(filial);

        mockMvc.perform(post("/vendedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dadosVendedor)))
                .andExpect(status().isCreated());

        verify(vendedorService, times(1)).cadastrar(any(DadosCadastraisVendedor.class));
        verify(vendedorService, times(1)).obtemFilial();
    }

    @Test
    public void deveListarVendedores() throws Exception {
        Vendedor vendedor = JsonHandler.readJsonFromFile("src/test/Vendedor.json", Vendedor.class);
        List<Vendedor> vendedores = new ArrayList<>();
        vendedores.add(vendedor);
        DadosFilialDetalhamento filial = JsonHandler.readJsonFromFile("src/test/Filial.json", DadosFilialDetalhamento.class);

        when(vendedorService.listar()).thenReturn(vendedores);
        when(vendedorService.obtemFilial()).thenReturn(filial);

        mockMvc.perform(get("/vendedores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendedorService, times(1)).listar();
        verify(vendedorService, times(1)).obtemFilial();
    }

    @Test
    public void deveAtualizarVendedor() throws Exception {
        DadosAtualizacaoVendedor dadosAtualizacao = JsonHandler.readJsonFromFile("src/test/DadosAtualizacaoVendedor.json", DadosAtualizacaoVendedor.class);
        Vendedor vendedor = JsonHandler.readJsonFromFile("src/test/Vendedor.json", Vendedor.class);
        vendedor.atualizarInformacoes(dadosAtualizacao);
        DadosFilialDetalhamento filial = JsonHandler.readJsonFromFile("src/test/Filial.json", DadosFilialDetalhamento.class);

        when(vendedorService.atualizar(any(DadosAtualizacaoVendedor.class))).thenReturn(vendedor);
        when(vendedorService.obtemFilial()).thenReturn(filial);

        mockMvc.perform(put("/vendedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dadosAtualizacao)))
                .andExpect(status().isOk());

        verify(vendedorService, times(1)).atualizar(any(DadosAtualizacaoVendedor.class));
        verify(vendedorService, times(1)).obtemFilial();
    }

    @Test
    public void testExcluirVendedor() throws Exception {
        String matricula = "12-CLT";

        doNothing().when(vendedorService).excluir(matricula);

        mockMvc.perform(delete("/vendedores/{matricula}", matricula))
                .andExpect(status().isNoContent());

        verify(vendedorService, times(1)).excluir(matricula);
    }
}