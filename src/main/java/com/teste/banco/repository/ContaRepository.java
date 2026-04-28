package com.teste.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste.banco.model.Conta;


@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    
    public boolean existsByContaNumero(Long numeroConta);

    public Conta findByContaNumero(Long numero);

}
