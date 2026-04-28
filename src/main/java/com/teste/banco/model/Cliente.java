package com.teste.banco.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @Min(value = 1, message = "Idade deve ser maior ou igual a 0")
    @Max(value = 300, message = "Idade deve ser menor ou igual a 300")
    private Integer idade;
    
    private String email;

    @Column(nullable = false, unique = true)
    private String cpf;

    @ManyToMany
    @JoinTable( name = "cliente_conta", 
        inverseJoinColumns = @JoinColumn(name = "conta_id")    )
    private List<Conta> contas = new ArrayList<>();


    public void addConta(Conta conta) {
        if (this.contas.size() < 5) {          
            this.contas.add(conta);
        } else {
            throw new IllegalStateException("Um cliente não pode ter mais de 5 contas");
        }
    }

    public void removeConta(Conta conta) {
        if (this.contas.size() > 1) {
            this.contas.remove(conta);
        } else {
            throw new IllegalStateException("Um cliente deve ter pelo menos 1 conta");
        }
    }
}