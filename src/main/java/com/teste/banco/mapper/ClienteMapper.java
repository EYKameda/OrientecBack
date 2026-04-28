package com.teste.banco.mapper;

import com.teste.banco.dto.ClienteDTO;
import com.teste.banco.model.Cliente;

public class ClienteMapper{

    public static ClienteDTO toDTO(Cliente cliente){

        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setCpf(cliente.getCpf());
        dto.setEmail(cliente.getEmail());
        dto.setNome(cliente.getNome());
        dto.setIdade(cliente.getIdade());
        return dto;
    }

    public static Cliente toEntity(ClienteDTO clienteDTO){
        Cliente cliente = new Cliente();
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setNome(clienteDTO.getNome());
        cliente.setIdade(clienteDTO.getIdade());
        cliente.setId(clienteDTO.getId());
        return cliente;
    }    
}