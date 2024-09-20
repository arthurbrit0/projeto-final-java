package com.mercado.sistema_vendas.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class ItemVenda {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private String produtoCodigo;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @NotNull(message = "Qtd é obrigatoria")
    @Positive(message = "Qtd tem que ser +")

    private Integer quantidade;


    private Double precoUnitario;
    public ItemVenda() {
    }

    public Long getId() {
        return id;
    }

    public String getProdutoCodigo() {
        return produtoCodigo;
    }
    public void setProdutoCodigo(String produtoCodigo) {
        this.produtoCodigo = produtoCodigo;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public Integer getQuantidade() {
        return quantidade;

    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    public Double getPrecoUnitario() {
        return precoUnitario;
    }
    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}



