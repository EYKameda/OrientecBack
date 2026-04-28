package com.teste.banco.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.banco.dto.ClienteDTO;
import com.teste.banco.mapper.ClienteMapper;
import com.teste.banco.model.Cliente;
import com.teste.banco.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id);
        return ResponseEntity.ok(ClienteMapper.toDTO(cliente));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody Cliente cliente) {
        ClienteDTO novoClienteDTO = clienteService.salvarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoClienteDTO);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteDTO> buscarPorCpf(@PathVariable String cpf) {
        ClienteDTO clienteDTO = clienteService.buscarPorCpf(cpf);
        return clienteDTO != null ? ResponseEntity.ok(clienteDTO) : ResponseEntity.notFound().build();
    }
}
