package com.teste.banco.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teste.banco.dto.TransacaoDTO;
import com.teste.banco.mapper.TransacaoMapper;
import com.teste.banco.model.Transacao;
import com.teste.banco.repository.TransacaoRepository;

@Service
public class ExtratoService {

    private final TransacaoRepository transacaoRepository;

    public ExtratoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public List<TransacaoDTO> findByNumeroConta(Long contaNumero, String tipo) {
        tipo = tipo.replaceAll("\\s+", "").toUpperCase();

        switch (tipo) {
            case "IDCONTA" -> {
                List<Transacao> listTran = transacaoRepository.findAllTransacaoByContaId(contaNumero);
                return TransacaoMapper.toDTO(listTran);
            }
            case "CONTANUMERO" -> {
                List<Transacao> tran = transacaoRepository.findByContaNumero(contaNumero);
                return TransacaoMapper.toDTO(tran);
            }
            default -> throw new IllegalArgumentException("Não existe esse tipo de busca");
        }
    }
}