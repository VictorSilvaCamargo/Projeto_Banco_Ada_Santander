package com.banco.ProjetoBanco.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id", nullable = false)
    private Conta origem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id", nullable = false)
    private Conta destino;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    public Transacao(Conta origem, Conta destino, BigDecimal valor) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
    }

    public Transacao() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conta getOrigem() {
        return origem;
    }

    public void setOrigem(Conta origem) {
        this.origem = origem;
    }

    public Conta getDestino() {
        return destino;
    }

    public void setDestino(Conta destino) {
        this.destino = destino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }


}
