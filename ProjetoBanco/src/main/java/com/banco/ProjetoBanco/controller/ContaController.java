package com.banco.ProjetoBanco.controller;


import com.banco.ProjetoBanco.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/{contaNumero}/depositar")
    public ResponseEntity<String> depositar(@PathVariable(name = "contaNumero") Long numero, @RequestParam BigDecimal valor) {
        if (contaService.depositar(numero, valor)) {
            return new ResponseEntity<>("Depósito realizado com sucesso.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Conta não encontrada.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{contaNumero}/sacar")
    public ResponseEntity<String> sacar(@PathVariable(name = "contaNumero") Long numero, @RequestParam BigDecimal valor) {
        if (contaService.sacar(numero, valor)) {
            return new ResponseEntity<>("Saque realizado com sucesso.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Saque não autorizado. Saldo insuficiente ou conta não encontrada.", HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/transferir")
    public ResponseEntity<String> transferir(@RequestParam Long contaOrigem, @RequestParam Long contaDestino, @RequestParam BigDecimal valor) {
        if (contaService.realizarTransferencia(contaOrigem, contaDestino, valor)) {
            return new ResponseEntity<>("Transferência realizada com sucesso.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Transferência não autorizada. Verifique as contas e o saldo.", HttpStatus.BAD_REQUEST);
        }
    }
}
