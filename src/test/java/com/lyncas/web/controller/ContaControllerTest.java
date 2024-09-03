package com.lyncas.web.controller;

import com.lyncas.domain.entity.Conta;
import com.lyncas.domain.service.ContaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContaController.class)
public class ContaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContaService contaService;

    @Test
    void testGetById() throws Exception {
        Long id = 1L;
        Conta conta = new Conta();
        conta.setId(id);
        conta.setDescricao("Conta de teste");

        Mockito.when(contaService.findById(id)).thenReturn(Optional.of(conta));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.descricao").value("Conta de teste"));

        Mockito.verify(contaService, Mockito.times(1)).findById(id);
    }
}
