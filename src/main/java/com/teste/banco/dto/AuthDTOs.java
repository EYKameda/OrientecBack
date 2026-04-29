package com.teste.banco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String login;
    private String senha;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private long expiresIn;
    private FuncionarioDTO funcionario;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {
    private Long codigo;
    private String login;
    private String nome;
    private String apelido;
    private Integer nivel;
    private String funcao;
    private String sede;
    private String cartao;
    private Boolean ativo;
    private Boolean moduloBackup;
    private Boolean moduloCadastros;
    private Boolean moduloCaixa;
    private Boolean moduloEstoque;
    private Boolean moduloFinanceiro;
    private Boolean moduloGerencial;
    private Boolean moduloOs;
    private Boolean moduloPonto;
    private Boolean moduloTabelas;
    private Boolean moduloVendas;
    // ... adicione outros campos conforme necessário
}
