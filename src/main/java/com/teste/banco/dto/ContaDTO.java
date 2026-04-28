package com.teste.banco.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaDTO {
    private Long id;
    private BigDecimal saldo = BigDecimal.ZERO;
    private Long contaNumero;
    private List<Long> clientesIds;    
}
