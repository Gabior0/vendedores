package com.casasbahia.vendedores.service;

import com.casasbahia.vendedores.VendedoresApplication;
import com.casasbahia.vendedores.client.FilialClient;
import com.casasbahia.vendedores.jsonHandler.JsonHandler;
import com.casasbahia.vendedores.model.Vendedor;
import com.casasbahia.vendedores.model.request.DadosAtualizacaoVendedor;
import com.casasbahia.vendedores.model.request.DadosCadastraisVendedor;
import com.casasbahia.vendedores.model.response.DadosDetalhamentoVendedores;
import com.casasbahia.vendedores.model.response.DadosFilialDetalhamento;
import com.casasbahia.vendedores.repository.VendedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VendedorServiceTest {

    @Mock
    private VendedorRepository repository;

    @Mock
    private FilialClient client;

    @InjectMocks
    private VendedorService service;
    private Vendedor vendedor;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarVendedor() {
        DadosCadastraisVendedor dadosVendedor = new DadosCadastraisVendedor();
        Vendedor vendedor = new Vendedor(dadosVendedor);
        DadosFilialDetalhamento filialDetalhamento = new DadosFilialDetalhamento();

        when(repository.save(any(Vendedor.class))).thenReturn(vendedor);
        when(client.obtemfilial()).thenReturn(ResponseEntity.ok(new DadosFilialDetalhamento()));

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity response = service.cadastrar(dadosVendedor, uriBuilder);

        verify(repository, times(1)).save(any(Vendedor.class));
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void deveListarVendedores() throws IOException {
        Vendedor vendedor = JsonHandler.readJsonFromFile("src/test/Vendedor.json", Vendedor.class);
        List<Vendedor> vendedores = new ArrayList<>();
        vendedores.add(vendedor);

        when(repository.findAll()).thenReturn(vendedores);
        when(client.obtemfilial()).thenReturn(ResponseEntity.ok(new DadosFilialDetalhamento()));

        ResponseEntity<List<DadosDetalhamentoVendedores>> response = service.listar();

        verify(repository, times(1)).findAll();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void deveAtualizarVendedor() throws IOException {
        DadosAtualizacaoVendedor dadosVendedor = JsonHandler.readJsonFromFile("src/test/DadosAtualizacaoVendedor.json", DadosAtualizacaoVendedor.class);
        Vendedor vendedor = JsonHandler.readJsonFromFile("src/test/Vendedor.json", Vendedor.class);
        DadosFilialDetalhamento filialDetalhamento = new DadosFilialDetalhamento();

        when(repository.getReferenceByMatricula(anyString())).thenReturn(vendedor);
        when(client.obtemfilial()).thenReturn(ResponseEntity.ok(new DadosFilialDetalhamento()));

        ResponseEntity response = service.atualizar(dadosVendedor);

        verify(repository, times(1)).getReferenceByMatricula(anyString());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deveExcluirVendedor() {
        String matricula = "12345";

        doNothing().when(repository).deleteByMatricula(matricula);

        ResponseEntity response = service.excluir(matricula);

        verify(repository, times(1)).deleteByMatricula(matricula);
        assertEquals(204, response.getStatusCodeValue());
    }
}
