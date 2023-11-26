package com.banco.ProjetoBanco.model;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "Contas")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", nullable = false, unique = true)
    private Long numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoConta tipo;

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

    @OneToOne
    @JoinColumn(name = "cliente_id", unique = true)
    private Cliente cliente;



    public Conta(Long numero, TipoConta tipo, BigDecimal saldo, Cliente cliente) {
        this.numero = numero;
        this.tipo = tipo;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    public Conta() {

    }

    public void depositar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
    }

    public boolean sacar(BigDecimal valor) {
        if (valor.compareTo(this.saldo) <= 0) {
            this.saldo = this.saldo.subtract(valor);
            return true;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
