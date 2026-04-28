package com.teste.banco.mapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.teste.banco.dto.TransacaoDTO;
import com.teste.banco.model.Transacao;

public class TransacaoMapper {

    private TransacaoMapper() {
    }

    public static TransacaoDTO toDTO(Transacao transacao) {
        TransacaoDTO dto = new TransacaoDTO();
        dto.setId(transacao.getId());
        dto.setContaId(transacao.getConta().getId());
        dto.setTipo(transacao.getTipo());
        dto.setValor(transacao.getValor());
        dto.setDataHora(transacao.getDataHora());
        return dto;
    }

    public static Transacao toEntity(TransacaoDTO dto) {
        Transacao transacao = new Transacao();
        transacao.setId(dto.getId());
        transacao.setTipo(dto.getTipo());
        transacao.setValor(dto.getValor());
        transacao.setDataHora(dto.getDataHora());
        return transacao;
    }

    public static List<TransacaoDTO> toDTO(Iterable<Transacao> transacoes) {
        return StreamSupport.stream(transacoes.spliterator(), false)
                .map(transacao -> new TransacaoDTO(
                        transacao.getId(),
                        transacao.getValor(),
                        transacao.getConta().getContaNumero(), 
                        transacao.getTipo()
                ))
                .collect(Collectors.toList());
    }

    public static List<TransacaoDTO> toDTO(List<Transacao> transacoes) {
        return transacoes.stream()
                .map(transacao -> new TransacaoDTO(
                        transacao.getId(),
                        transacao.getValor(),
                        transacao.getConta().getContaNumero(),
                        transacao.getTipo()
                ))
                .collect(Collectors.toList());
    }
}
