package com.banco.ProjetoBanco.service;

import com.banco.ProjetoBanco.model.Cliente;
import com.banco.ProjetoBanco.repository.ClienteRepository;
import com.banco.ProjetoBanco.exception.CPFExistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    @Test
    public void deveCriarUmNovoCliente() {
        // Given
        String nome = "Victor";
        String cpf = "123.456.789-10";
        String email = "victor@gmail.com";

        Cliente clienteMock = new Cliente(nome, cpf, email);

        // When
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);
        Cliente cliente = clienteService.salvarCliente(nome, cpf, email);

        // Then
        assertNotNull(cliente);
        assertEquals(nome, cliente.getNome());
        assertEquals(cpf, cliente.getCpf());
        assertEquals(email, cliente.getEmail());
    }

    @Test
    public void deveLancarExcecaoAoSalvarClienteComCpfExistente() {
        // Given
        String nome = "Vinicius";
        String cpf = "123.456.789-10";
        String email = "vinicius@gmail.com";


        when(clienteRepository.findByCpf(cpf)).thenReturn(new Cliente());

        // When/Then
        assertThrows(CPFExistenteException.class, () -> {
            clienteService.salvarCliente(nome, cpf, email);
        });
    }




}
