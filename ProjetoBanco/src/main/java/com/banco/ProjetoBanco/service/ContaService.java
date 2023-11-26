package com.banco.ProjetoBanco.service;

import com.banco.ProjetoBanco.model.*;
import com.banco.ProjetoBanco.repository.ContaRepository;
import com.banco.ProjetoBanco.repository.HistoricoTransacoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private HistoricoTransacoesRepository historicoTransacoesRepository;

    public Conta criarConta(Cliente cliente) {
        TipoConta tipoConta = TipoConta.CORRENTE;
        Long proximoNumeroConta = contaRepository.findMaxNumeroConta() + 1;

        Conta conta = new Conta();
        conta.setNumero(proximoNumeroConta);
        conta.setTipo(tipoConta);
        conta.setSaldo(BigDecimal.ZERO);
        conta.setCliente(cliente);

        return contaRepository.save(conta);
    }

    public boolean sacar(Long numeroConta, BigDecimal valor) {
        Conta conta = contaRepository.findByNumero(numeroConta);
        if (conta != null && conta.sacar(valor)) {
            contaRepository.save(conta);

            HistoricoTransacoes historico = new HistoricoTransacoes();
            historico.setConta(conta);
            historico.setTipoTransacao(TipoTransacao.SAQUE);
            historico.setValor(valor);
            historicoTransacoesRepository.save(historico);

            return true;
        }
        return false;
    }

    public boolean depositar(Long numeroConta, BigDecimal valor) {
        Conta conta = contaRepository.findByNumero(numeroConta);
        if (conta != null) {
            conta.depositar(valor);
            contaRepository.save(conta);

            HistoricoTransacoes historico = new HistoricoTransacoes();
            historico.setConta(conta);
            historico.setTipoTransacao(TipoTransacao.DEPOSITO);
            historico.setValor(valor);
            historicoTransacoesRepository.save(historico);

            return true;
        }
        return false;
    }


    public boolean sacarSemHistorico(Long numeroConta, BigDecimal valor) {
        Conta conta = contaRepository.findByNumero(numeroConta);
        if (conta != null && conta.sacar(valor)) {
            contaRepository.save(conta);
            return true;
        }
        return false;
    }

    public boolean depositarSemHistorico(Long numeroConta, BigDecimal valor) {
        Conta conta = contaRepository.findByNumero(numeroConta);
        if (conta != null) {
            conta.depositar(valor);
            contaRepository.save(conta);
            return true;
        }
        return false;
    }

    public boolean realizarTransferencia(Long contaOrigem, Long contaDestino, BigDecimal valor) {
        Conta origem = contaRepository.findByNumero(contaOrigem);
        Conta destino = contaRepository.findByNumero(contaDestino);

        if (origem != null && destino != null) {
            if (sacarSemHistorico(contaOrigem, valor)) {
                depositarSemHistorico(contaDestino, valor);

                HistoricoTransacoes historico = new HistoricoTransacoes();
                historico.setConta(origem);
                historico.setTipoTransacao(TipoTransacao.TRANSFERENCIA);
                historico.setValor(valor);
                historico.setContaDestino(destino);
                historico.setDataHora(LocalDateTime.now());

                historicoTransacoesRepository.save(historico);

                return true;
            }
        }

        return false;
    }

}


