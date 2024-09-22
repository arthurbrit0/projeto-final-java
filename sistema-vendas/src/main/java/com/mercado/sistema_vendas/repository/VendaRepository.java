package com.mercado.sistema_vendas.repository;

import com.mercado.sistema_vendas.models.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDateTime;


public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByVendedorContainingIgnoreCase(String vendedor);

    // Atualize para buscar por LocalDateTime
    List<Venda> findByDataBetween(LocalDateTime startDate, LocalDateTime endDate);
}


