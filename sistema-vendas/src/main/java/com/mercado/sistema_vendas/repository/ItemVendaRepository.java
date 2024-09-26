package com.mercado.sistema_vendas.repository;

import com.mercado.sistema_vendas.models.ItemVenda;
import com.mercado.sistema_vendas.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    List<ItemVenda> findByProduto(Produto produto);
}
