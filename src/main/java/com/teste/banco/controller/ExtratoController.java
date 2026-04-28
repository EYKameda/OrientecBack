package com.teste.banco.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teste.banco.dto.TransacaoDTO;
import com.teste.banco.service.ExtratoService;

@RestController
@RequestMapping("/api/extrato")
@CrossOrigin(origins = "http://localhost:4200")
public class ExtratoController {

    private final ExtratoService extratoService;

    public ExtratoController(ExtratoService extratoService) {
        this.extratoService = extratoService;
    }

@GetMapping
    public List<TransacaoDTO> getContaByNumeroConta(
            @RequestParam("contaNumero") Long contaNumero,
            @RequestParam("tipo") String tipo) {
        return extratoService.findByNumeroConta(contaNumero, tipo);
    }
}
