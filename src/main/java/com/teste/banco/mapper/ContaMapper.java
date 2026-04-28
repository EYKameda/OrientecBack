package com.teste.banco.mapper;

import java.util.stream.Collectors;

import com.teste.banco.dto.ContaDTO;
import com.teste.banco.model.Cliente;
import com.teste.banco.model.Conta;

public class ContaMapper {

    public static ContaDTO toDTO(Conta conta) {
        ContaDTO dto = new ContaDTO();
        dto.setId(conta.getId());
        dto.setSaldo(conta.getSaldo());
        dto.setContaNumero(conta.getContaNumero());
        dto.setClientesIds(conta.getClientes().stream().map(Cliente::getId).collect(Collectors.toList()));
        return dto;
    }

    public static Conta toEntity(ContaDTO dto) {
        Conta conta = new Conta();
        conta.setId(dto.getId());
        conta.setSaldo(dto.getSaldo());
        conta.setContaNumero(dto.getContaNumero());
        return conta;
    }
}
