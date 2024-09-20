package com.mercado.sistema_vendas.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    private String descricao;

    @NotBlank(message = "Código é obrigatório")
    @Column(unique = true)
    private String codigo;

    @NotNull(message = "Valor é obrigatorio")
    @Positive(message = "Valor tem que ser +")
    private Double valor;
    @NotNull(message = "Qtd e obrigatoria")
    @Min(value = 0, message = "Qtd nao pode ser -")
    private Integer quantidade;

    public Produto() {

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;

    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {

        this.nome = nome;
    }
    public String getDescricao() {

        return descricao;

    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public Double getValor() {

        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public Integer getQuantidade() {
        return quantidade;

    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}


