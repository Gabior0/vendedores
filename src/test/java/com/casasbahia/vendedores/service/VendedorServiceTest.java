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
    public void deveCadastrarVendedor() throws IOException {
        DadosCadastraisVendedor dadosVendedor = JsonHandler.readJsonFromFile("src/test/DadosCadastraisVendedor.json", DadosCadastraisVendedor.class);

        Vendedor vendedor = new Vendedor(dadosVendedor);

        when(repository.save(any(Vendedor.class))).thenReturn(vendedor);

        Vendedor result = service.cadastrar(dadosVendedor);

        assertNotNull(result);
        assertEquals("Gabriel Teste", result.getNome());
        verify(repository, times(1)).save(any(Vendedor.class));
    }

    @Test
    public void deveListarVendedores() throws IOException {
        Vendedor vendedor = JsonHandler.readJsonFromFile("src/test/Vendedor.json", Vendedor.class);
        List<Vendedor> vendedores = new ArrayList<>();
        vendedores.add(vendedor);

        when(repository.findAll()).thenReturn(vendedores);

        List<Vendedor> result = service.listar();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void deveAtualizarVendedor() throws IOException {
        DadosAtualizacaoVendedor dadosAtualizacao = JsonHandler.readJsonFromFile("src/test/DadosAtualizacaoVendedor.json", DadosAtualizacaoVendedor.class);
        Vendedor vendedor = JsonHandler.readJsonFromFile("src/test/Vendedor.json", Vendedor.class);

        when(repository.getReferenceByMatricula("12-CLT")).thenReturn(vendedor);

        Vendedor result = service.atualizar(dadosAtualizacao);

        assertNotNull(result);
        verify(repository, times(1)).getReferenceByMatricula("12-CLT");
    }

    @Test
    public void deveExcluirVendedor() {
        String matricula = "12-CLT";

        doNothing().when(repository).deleteByMatricula(matricula);

        service.excluir(matricula);

        verify(repository, times(1)).deleteByMatricula(matricula);
    }
}
