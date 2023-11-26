package com.banco.ProjetoBanco.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Historico_Transacoes")
public class HistoricoTransacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transacao", nullable = false)
    private TipoTransacao tipoTransacao;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @OneToOne
    @JoinColumn(name = "conta_destino_id")
    private Conta contaDestino;

    public HistoricoTransacoes() {
        this.dataHora = LocalDateTime.now();
    }

    public HistoricoTransacoes(Conta conta, TipoTransacao tipoTransacao, BigDecimal valor, LocalDateTime dataHora, Conta contaDestino) {
        this.conta = conta;
        this.tipoTransacao = tipoTransacao;
        this.valor = valor;
        this.dataHora = dataHora;
        this.contaDestino = contaDestino;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }
}
