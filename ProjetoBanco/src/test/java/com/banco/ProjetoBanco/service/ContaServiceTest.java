package com.banco.ProjetoBanco.service;

import com.banco.ProjetoBanco.model.Cliente;
import com.banco.ProjetoBanco.model.Conta;
import com.banco.ProjetoBanco.model.TipoConta;
import com.banco.ProjetoBanco.repository.ContaRepository;
import com.banco.ProjetoBanco.repository.HistoricoTransacoesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private HistoricoTransacoesRepository historicoTransacoesRepository;


    @BeforeEach
    @Test
    public void deveCriarContaQuandoClienteEhCriado() {
        // Given
        Cliente cliente = new Cliente("Victor", "123.456.789-10", "victor@gmail.com");

        // When
        when(contaRepository.findMaxNumeroConta()).thenReturn(0L);
        when(contaRepository.save(any(Conta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Conta conta = contaService.criarConta(cliente);

        // Then
        assertNotNull(conta);
        assertEquals(TipoConta.CORRENTE, conta.getTipo());
        assertEquals(1L, conta.getNumero());
        assertEquals(cliente, conta.getCliente());
    }

    @Test
    public void deveDepositarNaContaComSucesso() {
        // Given
        Cliente cliente = new Cliente("Victor", "123.456.789-10", "victor@gmail.com");
        Conta conta = new Conta();
        conta.setNumero(1L);
        conta.setTipo(TipoConta.CORRENTE);
        conta.setSaldo(BigDecimal.ZERO);
        conta.setCliente(cliente);


        // When
        when(contaRepository.findByNumero(1L)).thenReturn(conta);

        BigDecimal valorDeposito = new BigDecimal("100.00");

        boolean depositoSucesso = contaService.depositar(1L, valorDeposito);

        // Then
        assertTrue(depositoSucesso);
        assertEquals(new BigDecimal("100.00"), conta.getSaldo());

    }

    @Test
    public void naoDeveDepositarEmContaInexistente() {
        // Given
        Cliente cliente = new Cliente("Victor", "123.456.789-10", "victor@gmail.com");
        Conta conta = new Conta();
        conta.setNumero(1L);
        conta.setTipo(TipoConta.CORRENTE);
        conta.setSaldo(BigDecimal.ZERO);
        conta.setCliente(cliente);

        // When
        when(contaRepository.findByNumero(2L)).thenReturn(null);

        BigDecimal valorDeposito = new BigDecimal("100.00");

        boolean depositoSucesso = contaService.depositar(2L, valorDeposito);

        // Then
        assertFalse(depositoSucesso);
    }

    @Test
    public void deveSacarDaContaComSucesso() {
        // Given
        Cliente cliente = new Cliente("Victor", "123.456.789-10", "victor@gmail.com");
        Conta conta = new Conta();
        conta.setNumero(1L);
        conta.setTipo(TipoConta.CORRENTE);
        conta.setSaldo(new BigDecimal("200.00"));
        conta.setCliente(cliente);


        // When
        when(contaRepository.findByNumero(1L)).thenReturn(conta);

        BigDecimal valorSaque = new BigDecimal("100.00");

        boolean saqueSucesso = contaService.sacar(1L, valorSaque);

        // Then
        assertTrue(saqueSucesso);
        assertEquals(new BigDecimal("100.00"), conta.getSaldo());

    }

    @Test
    public void naoDeveSacarQuandoSaldoInsuficiente() {
        // Given
        Cliente cliente = new Cliente("Victor", "123.456.789-10", "victor@gmail.com");
        Conta conta = new Conta();
        conta.setNumero(1L);
        conta.setTipo(TipoConta.CORRENTE);
        conta.setSaldo(BigDecimal.ZERO);
        conta.setCliente(cliente);


        // When
        when(contaRepository.findByNumero(1L)).thenReturn(conta);

        BigDecimal valorSaque = new BigDecimal("100.00");

        boolean saqueSucesso = contaService.sacar(1L, valorSaque);

        // Then
        assertFalse(saqueSucesso);
        assertEquals(BigDecimal.ZERO, conta.getSaldo());
    }


    @Test
    public void deveRealizarTransferenciaComSucesso() {
        // Given
        Cliente clienteOrigem = new Cliente("Victor", "123.456.789-10", "victor@gmail.com");
        Cliente clienteDestino = new Cliente("Vinicius", "987.654.321-00", "vinicius@gmail.com");

        Conta contaOrigem = new Conta();
        contaOrigem.setNumero(1L);
        contaOrigem.setTipo(TipoConta.CORRENTE);
        contaOrigem.setSaldo(new BigDecimal("200.00"));
        contaOrigem.setCliente(clienteOrigem);

        Conta contaDestino = new Conta();
        contaDestino.setNumero(2L);
        contaDestino.setTipo(TipoConta.CORRENTE);
        contaDestino.setSaldo(BigDecimal.ZERO);
        contaDestino.setCliente(clienteDestino);


        // When
        when(contaRepository.findByNumero(1L)).thenReturn(contaOrigem);
        when(contaRepository.findByNumero(2L)).thenReturn(contaDestino);

        BigDecimal valorTransferencia = new BigDecimal("100.00");

        boolean transferenciaSucesso = contaService.realizarTransferencia(1L, 2L, valorTransferencia);

        // Then
        assertTrue(transferenciaSucesso);

        assertEquals(new BigDecimal("100.00"), contaOrigem.getSaldo());
        assertEquals(new BigDecimal("100.00"), contaDestino.getSaldo());

    }

    @Test
    public void naoDeveRealizarTransferenciaSemSaldoSuficiente() {
        // Given
        Cliente clienteOrigem = new Cliente("Victor", "123.456.789-10", "victor@gmail.com");
        Cliente clienteDestino = new Cliente("Vinicius", "987.654.321-00", "vinicius@gmail.com");

        Conta contaOrigem = new Conta();
        contaOrigem.setNumero(1L);
        contaOrigem.setTipo(TipoConta.CORRENTE);
        contaOrigem.setSaldo(new BigDecimal("50.00"));
        contaOrigem.setCliente(clienteOrigem);

        Conta contaDestino = new Conta();
        contaDestino.setNumero(2L);
        contaDestino.setTipo(TipoConta.CORRENTE);
        contaDestino.setSaldo(BigDecimal.ZERO);
        contaDestino.setCliente(clienteDestino);


        // When
        when(contaRepository.findByNumero(1L)).thenReturn(contaOrigem);
        when(contaRepository.findByNumero(2L)).thenReturn(contaDestino);

        BigDecimal valorTransferencia = new BigDecimal("100.00");

        boolean transferenciaSucesso = contaService.realizarTransferencia(1L, 2L, valorTransferencia);

        // Then
        assertFalse(transferenciaSucesso);

        assertEquals(new BigDecimal("50.00"), contaOrigem.getSaldo());
        assertEquals(BigDecimal.ZERO, contaDestino.getSaldo());

    }

}
