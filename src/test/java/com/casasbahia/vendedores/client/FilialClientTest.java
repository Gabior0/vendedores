package com.casasbahia.vendedores.client;

import com.casasbahia.vendedores.model.response.DadosFilialDetalhamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class FilialClientTest {

    @Autowired
    private FilialClient filialClient;

    @MockBean
    private FilialClient mockFilialClient;

    @Test
    public void deveObtemFilial() {
        DadosFilialDetalhamento dadosFilial = new DadosFilialDetalhamento();
        given(mockFilialClient.obtemfilial()).willReturn(ResponseEntity.ok(dadosFilial));
        ResponseEntity<DadosFilialDetalhamento> response = filialClient.obtemfilial();
        assertEquals(dadosFilial, response.getBody());
    }
}
