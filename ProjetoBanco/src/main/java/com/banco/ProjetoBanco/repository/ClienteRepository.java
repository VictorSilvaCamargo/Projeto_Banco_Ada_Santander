package com.banco.ProjetoBanco.repository;

import com.banco.ProjetoBanco.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByCpf(String cpf);
}
