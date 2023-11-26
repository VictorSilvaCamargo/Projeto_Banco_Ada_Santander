package com.banco.ProjetoBanco.controller;


import com.banco.ProjetoBanco.model.Cliente;
import com.banco.ProjetoBanco.model.Conta;
import com.banco.ProjetoBanco.service.ClienteService;
import com.banco.ProjetoBanco.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ContaService contaService;

    @PostMapping("/criar")
    public ResponseEntity<String> criarCliente(
            @RequestParam String nome,
            @RequestParam String cpf,
            @RequestParam String email) {

        Cliente cliente = clienteService.salvarCliente(nome, cpf, email);


        Conta conta = contaService.criarConta(cliente);

        return new ResponseEntity<>("Cliente e conta criados com sucesso. Número da Conta: " + conta.getNumero(), HttpStatus.CREATED);
    }
}


