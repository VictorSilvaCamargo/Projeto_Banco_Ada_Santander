package com.banco.ProjetoBanco.service;

import com.banco.ProjetoBanco.model.Cliente;
import com.banco.ProjetoBanco.repository.ClienteRepository;
import com.banco.ProjetoBanco.exception.CPFExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvarCliente(String nome, String cpf, String email) {

        if (clienteRepository.findByCpf(cpf) != null) {
            throw new CPFExistenteException("CPF já cadastrado");
        }

        Cliente cliente = new Cliente(nome, cpf, email);
        clienteRepository.save(cliente);
        return cliente;
    }
}
