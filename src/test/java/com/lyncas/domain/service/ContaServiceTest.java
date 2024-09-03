package com.lyncas.domain.service;

import com.lyncas.domain.entity.Conta;
import com.lyncas.domain.entity.Situacao;
import com.lyncas.domain.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)  // Usar MockitoExtension para testes unitários com Mockito
public class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    private Conta conta;

    @BeforeEach
    public void setup() {
        // Inicializar os mocks manualmente, ou você pode usar o @ExtendWith como foi feito acima
        MockitoAnnotations.openMocks(this);

        // Criando uma instância de Conta para ser usada nos testes
        conta = new Conta();
        conta.setDataVencimento(LocalDate.now());
        conta.setDataPagamento(LocalDate.now().minusDays(5));
        conta.setValor(new BigDecimal("250.75"));
        conta.setDescricao("Conta de energia elétrica");
        conta.setSituacao(Situacao.PENDENTE);
    }

    @Test
    public void testSaveConta() {
        when(contaRepository.save(conta)).thenReturn(conta);
        Conta savedConta = contaService.save(conta);
        assertEquals(conta, savedConta);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        Conta conta = new Conta();
        conta.setId(id);

        when(contaRepository.findById(id)).thenReturn(Optional.of(conta));

        Optional<Conta> foundConta = contaService.findById(id);

        assertTrue(foundConta.isPresent());
        assertEquals(id, foundConta.get().getId());
        Mockito.verify(contaRepository, Mockito.times(1)).findById(id);
    }
}