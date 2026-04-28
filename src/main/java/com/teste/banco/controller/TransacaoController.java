package com.teste.banco.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teste.banco.dto.TransacaoDTO;
import com.teste.banco.exception.ResourceNotFoundException;
import com.teste.banco.service.TransacaoService;

@RestController
@RequestMapping("/api/transacao")
@CrossOrigin(origins = "http://localhost:4200")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }


    @PostMapping
    public void debitar(@RequestParam Long contaId, @RequestParam BigDecimal valor, String tipo) {
        transacaoService.realizarTransacao(contaId, valor, tipo);
    }

    @GetMapping
    public ResponseEntity<List<TransacaoDTO>> getAllTransacao() {
        try {
            List<TransacaoDTO> transactionDTOs = transacaoService.getAllTransacao();
            return ResponseEntity.ok(transactionDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDTO> getTransactionById(@PathVariable Long id) {
        try {
            TransacaoDTO transactionDTO = transacaoService.getTransacaoById(id);
            return ResponseEntity.ok(transactionDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<TransacaoDTO>> getTransacaoByContaId(@PathVariable Long contaId) {
        try {
            List<TransacaoDTO> transacao = transacaoService.getTransacaoByContaId(contaId);
            return ResponseEntity.ok(transacao);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
