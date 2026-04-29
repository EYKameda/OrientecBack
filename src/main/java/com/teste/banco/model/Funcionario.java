package com.teste.banco.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "funcionarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    private String apelido;
    private Integer nivel;
    private String funcao;
    private String sede;
    private String cartao;

    @Column(name = "ferias_inicio")
    private LocalDate feriasInicio;

    @Column(name = "ferias_termino")
    private LocalDate feriasTermino;

    @Column(name = "entrada_data")
    private LocalDateTime entradaData;

    @Column(name = "saida_data")
    private LocalDateTime saidaData;

    private String entrada;
    private String saida;
    private String atraso;
    private String minimo;
    private String situacao;
    private String dentro;

    @Column(name = "modulo_backup")
    private Boolean moduloBackup = false;

    @Column(name = "modulo_cadastros")
    private Boolean moduloCadastros = false;

    @Column(name = "modulo_caixa")
    private Boolean moduloCaixa = false;

    @Column(name = "modulo_estoque")
    private Boolean moduloEstoque = false;

    @Column(name = "modulo_financeiro")
    private Boolean moduloFinanceiro = false;

    @Column(name = "modulo_gerencial")
    private Boolean moduloGerencial = false;

    @Column(name = "modulo_os")
    private Boolean moduloOs = false;

    @Column(name = "modulo_ponto")
    private Boolean moduloPonto = false;

    @Column(name = "modulo_tabelas")
    private Boolean moduloTabelas = false;

    @Column(name = "modulo_vendas")
    private Boolean moduloVendas = false;

    @Column(name = "bate_ponto")
    private Boolean batePonto = false;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_exclusao")
    private LocalDateTime dataExclusao;

    @Column(name = "usuario_exclusao")
    private String usuarioExclusao;

    // Horários (t11, t12, t13, ... t76)
    private String t11, t12, t13, t14, t15, t16;
    private String t21, t22, t23, t24, t25, t26;
    private String t31, t32, t33, t34, t35, t36;
    private String t41, t42, t43, t44, t45, t46;
    private String t51, t52, t53, t54, t55, t56;
    private String t61, t62, t63, t64, t65, t66;
    private String t71, t72, t73, t74, t75, t76;
}
