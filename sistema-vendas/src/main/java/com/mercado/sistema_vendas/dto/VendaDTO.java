package com.mercado.sistema_vendas.dto;

import java.time.LocalDateTime;
import java.util.List;

public class VendaDTO {
    private Long id;
    private LocalDateTime data;
    private String vendedor;
    private List<ItemVendaDTO> itens;
    private Double valorTotal;

    public VendaDTO() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getVendedor() {
        return vendedor;
    }

    public List<ItemVendaDTO> getItens() {
        return itens;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public void setItens(List<ItemVendaDTO> itens) {
        this.itens = itens;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
