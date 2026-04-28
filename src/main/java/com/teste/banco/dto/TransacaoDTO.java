package com.teste.banco.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDTO {

    private Long id;
    private Long contaId;
    private String tipo;
    private BigDecimal valor;
    private LocalDateTime dataHora;

    public TransacaoDTO(Long id, BigDecimal valor, Long contaId, String tipo) {
        this.id = id;
        this.valor = valor;
        this.contaId = contaId;
        this.tipo = tipo;
    }
}
