package com.mercado.sistema_vendas.services;

import com.mercado.sistema_vendas.models.ItemVenda;
import com.mercado.sistema_vendas.models.Produto;
import com.mercado.sistema_vendas.models.Venda;
import com.mercado.sistema_vendas.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoService produtoService;

    @Transactional
    public Venda salvar(Venda venda) throws Exception {
        for (ItemVenda item : venda.getItens()) {
            // Buscar o Produto pelo código vindo do frontend
            Produto produto = produtoService.buscarPorCodigo(item.getProdutoCodigo());

            if (produto == null) {
                throw new Exception("Produto não encontrado com código: " + item.getProdutoCodigo());
            }

            if (produto.getQuantidade() < item.getQuantidade()) {
                throw new Exception("Quantidade insuficiente em estoque para o produto: " + produto.getNome());
            }
            produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
            produtoService.salvar(produto);

            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        }

        venda.setData(LocalDateTime.now());
        venda.setValorTotal(calcularValorTotal(venda.getItens()));
        return vendaRepository.save(venda);
    }

    private Double calcularValorTotal(List<ItemVenda> itens) {
        return itens.stream()
                .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade()).sum();
    }

    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    public List<Venda> buscarPorVendedor(String vendedor) {
        return vendaRepository.findByVendedorContainingIgnoreCase(vendedor);
    }

    public Venda buscarPorId(Long id) {
        return vendaRepository.findById(id).orElse(null);
    }

    public void excluirPorId(Long id) {
        Venda venda = vendaRepository.findById(id).orElse(null);
        if (venda == null) {
            throw new RuntimeException("Venda não encontrada com ID: " + id);
        }
        vendaRepository.deleteById(id);
    }
}
