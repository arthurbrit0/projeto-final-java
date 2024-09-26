package com.mercado.sistema_vendas.repository;

import com.mercado.sistema_vendas.models.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByVendedorContainingIgnoreCase(String vendedor);

    List<Venda> findByDataBetween(LocalDateTime start, LocalDateTime end);
}
