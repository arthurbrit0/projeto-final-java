package com.mercado.sistema_vendas.dto;

public class ItemVendaDTO {
    private Long id;
    private String produtoCodigo;
    private String produtoNome;
    private Integer quantidade;
    private Double precoUnitario;

    public ItemVendaDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getProdutoCodigo() {
        return produtoCodigo;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProdutoCodigo(String produtoCodigo) {
        this.produtoCodigo = produtoCodigo;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
