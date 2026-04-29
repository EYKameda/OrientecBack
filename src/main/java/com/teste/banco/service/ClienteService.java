package com.teste.banco.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuditService auditService;

    public ClienteService(ClienteRepository clienteRepository, AuditService auditService) {
        this.clienteRepository = clienteRepository;
        this.auditService = auditService;
    }

    public ClienteDTO salvarCliente(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new CpfAlreadyExistsException("CPF já cadastrado");
        }
        try {
            cliente = clienteRepository.save(cliente);
            String user = getCurrentUser();
            auditService.logDataChange("CREATE", "Cliente", cliente.getId().toString(), user, "Cliente criado", null);
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
        Cliente existing = clienteRepository.findById(cliente.getId()).orElse(null);
        cliente = clienteRepository.save(cliente);
        String user = getCurrentUser();
        auditService.logDataChange("UPDATE", "Cliente", cliente.getId().toString(), user, "Cliente atualizado", null);
        return cliente;
    }

    private String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "unknown";
    }

}
