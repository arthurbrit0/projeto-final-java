package com.mercado.sistema_vendas.repository;
import com.mercado.sistema_vendas.models.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByVendedorContainingIgnoreCase(String vendedor);
}
