package com.teste.banco.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.banco.dto.ClienteDTO;
import com.teste.banco.dto.ContaDTO;
import com.teste.banco.exception.ContaNotFoundException;
import com.teste.banco.exception.ResourceNotFoundException;
import com.teste.banco.service.ContaService;

@RestController
@RequestMapping("/api/conta")
@CrossOrigin(origins = "http://localhost:4200")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping
    public ResponseEntity<List<ContaDTO>> getAllContas() {
        try {
            return ResponseEntity.ok(contaService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{clienteId}/{numeroConta}")
    public ResponseEntity<ContaDTO> criarConta(@PathVariable Long clienteId, @PathVariable Long numeroConta) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(contaService.criarConta1(clienteId, numeroConta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/cliente/{contaId}")
    public ResponseEntity<ClienteDTO> buscarClientePorIdConta(@PathVariable Long contaId) {
        return ResponseEntity.ok(contaService.getClientIdConta(contaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> getContaById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(contaService.findById(id));
        } catch (ContaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/byNumero/{numero}")
    public ResponseEntity<ContaDTO> getContaByNumero(@PathVariable Long numero) {
        try {
            return ResponseEntity.ok(contaService.findByNumero(numero));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
