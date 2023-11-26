package com.banco.ProjetoBanco.repository;

import com.banco.ProjetoBanco.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    Conta findByNumero(Long numero);

    @Query("SELECT COALESCE(MAX(c.numero), 0) FROM Conta c")
    Long findMaxNumeroConta();
}
