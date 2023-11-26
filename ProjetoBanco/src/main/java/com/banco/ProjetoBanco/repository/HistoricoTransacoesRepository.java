package com.banco.ProjetoBanco.repository;

import com.banco.ProjetoBanco.model.HistoricoTransacoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoTransacoesRepository extends JpaRepository<HistoricoTransacoes, Long> {

}
