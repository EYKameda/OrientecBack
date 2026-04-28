package com.teste.banco.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal saldo = BigDecimal.ZERO;

    @Min(value = 1, message = "Numero da conta deve ser maior que 1")
    @Column(unique = true)
    private Long contaNumero;

    @JsonBackReference
    @ManyToMany(mappedBy = "contas")
    private List<Cliente> clientes = new ArrayList<>();

    public void addCliente(Cliente cliente) {
        if (this.clientes.size() < 5) {
            this.clientes.add(cliente);
            cliente.getContas().add(this);
        } else {
            throw new IllegalStateException("Uma conta não pode ter mais de 5 clientes");
        }
    }

    public void removeCliente(Cliente cliente) {
        if (this.clientes.size() > 1) {
            this.clientes.remove(cliente);
            cliente.getContas().remove(this);
        } else {
            throw new IllegalStateException("Uma conta deve ter pelo menos 1 cliente");
        }
    }
        
    @JsonManagedReference
    @OneToMany(mappedBy = "conta")
    private List<Transacao> transacoes = new ArrayList<>();
    
    
}