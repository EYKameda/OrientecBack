package com.teste.banco.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.teste.banco.dto.ClienteDTO;
import com.teste.banco.exception.ClienteNotFoundException;
import com.teste.banco.exception.CpfAlreadyExistsException;
import com.teste.banco.mapper.ClienteMapper;
import com.teste.banco.model.Cliente;
import com.teste.banco.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
  
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteDTO salvarCliente(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new CpfAlreadyExistsException("CPF já cadastrado");
        }
        try {
            cliente = clienteRepository.save(cliente);
            return ClienteMapper.toDTO(cliente);
        } catch (DataAccessException e) {
            throw new InternalError("Erro ao salvar a cliente: " + e.getMessage(), e);
        }
    }

    public ClienteDTO buscarPorCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não localizado com CPF: " + cpf));
        return ClienteMapper.toDTO(cliente);
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado com id: " + id));
    }

    public Cliente updatCliente (Cliente cliente){
        return clienteRepository.save(cliente);
    }

}
