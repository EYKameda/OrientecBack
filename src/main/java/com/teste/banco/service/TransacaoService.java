package com.teste.banco.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.teste.banco.dto.TransacaoDTO;
import com.teste.banco.exception.ResourceNotFoundException;
import com.teste.banco.exception.TransacaoNotAcceptableException;
import com.teste.banco.mapper.TransacaoMapper;
import com.teste.banco.model.Conta;
import com.teste.banco.model.Transacao;
import com.teste.banco.repository.ContaRepository;
import com.teste.banco.repository.TransacaoRepository;

import jakarta.transaction.Transactional;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, ContaRepository contaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
    }

    public List<TransacaoDTO> getAllTransacao() {
        return transacaoRepository.findAll().stream()
                .map(TransacaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TransacaoDTO getTransacaoById(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacao not found with ID: " + id));
        return TransacaoMapper.toDTO(transacao);
       }


    @Transactional
    public void realizarTransacao(Long contaNumero, BigDecimal valor, String tipo) {
        Conta conta = contaRepository.findByContaNumero(contaNumero);
        switch (tipo) {
            case "DEBITO" -> {
                if (conta.getSaldo().compareTo(valor) < 0) {
                    throw new RuntimeException("Saldo insuficiente");
                }   conta.setSaldo(conta.getSaldo().subtract(valor));
            }
            case "CREDITO" -> conta.setSaldo(conta.getSaldo().add(valor));
            default -> throw new IllegalArgumentException("Tipo de transação inválido");
        }
        contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setValor(valor);
        transacao.setDataHora(LocalDateTime.now());
        transacao.setTipo(tipo);
        transacaoRepository.save(transacao);
    }
    
    public Transacao save(Transacao transacao) {
        setConta(transacao);
        updateValor(transacao);
        contaRepository.save(transacao.getConta());
        transacaoRepository.save(transacao);
        return (transacao);
    }

    private void setConta(Transacao transacao) {
        Conta conta = contaRepository.findByContaNumero(transacao.getConta().getContaNumero());
        transacao.getConta().setClientes(conta.getClientes());
        transacao.getConta().setId(conta.getId());
        transacao.getConta().setSaldo(conta.getSaldo());
        transacao.getConta().setTransacoes(conta.getTransacoes());
    }

    private void updateValor(Transacao transacao) {
        switch (transacao.getTipo().toUpperCase()) {
            case "CREDITO" ->
                transacao.getConta().setSaldo(transacao.getConta().getSaldo().add(transacao.getValor()));
            case "DEBITO" -> {
                if (0 >= (transacao.getValor().compareTo(transacao.getConta().getSaldo()))) {
                    throw new TransacaoNotAcceptableException("Saldo Insuficiente na Conta.");
                }
                transacao.getConta().setSaldo(transacao.getConta().getSaldo().subtract(transacao.getValor()));
            }
            default ->
                throw new TransacaoNotAcceptableException("Tipo de transacao Inválido.");
        }
    }

    public List<TransacaoDTO> getTransacaoByContaId(Long contaId) {
        return transacaoRepository.findAllTransacaoByContaId(contaId).stream()
            .map(TransacaoMapper::toDTO)
            .collect(Collectors.toList());
    }
}
