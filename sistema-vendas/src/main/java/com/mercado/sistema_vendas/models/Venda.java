package com.mercado.sistema_vendas.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    @NotBlank(message = "Vendedor é obrigatório")
    private String vendedor;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "venda_id")
    private List<ItemVenda> itens;

    private Double valorTotal;

    public Venda() {
        this.itens = new ArrayList<>();
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) { // Adicionado o método setId
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public List<ItemVenda> getItens() {
        if (this.itens == null) {
            this.itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
