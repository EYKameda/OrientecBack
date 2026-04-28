package com.teste.banco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.teste.banco.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    
    List<Transacao> findAllTransacaoByContaId(Long contaId);
    
    @Query("SELECT t FROM Transacao t WHERE t.conta.contaNumero = :contaNumero")
    List<Transacao> findByContaNumero(@Param("contaNumero") Long contaNumero);
}