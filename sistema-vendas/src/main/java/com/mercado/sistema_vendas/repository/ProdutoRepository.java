package com.mercado.sistema_vendas.repository;

import com.mercado.sistema_vendas.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Produto findByCodigo(String codigo);
}
