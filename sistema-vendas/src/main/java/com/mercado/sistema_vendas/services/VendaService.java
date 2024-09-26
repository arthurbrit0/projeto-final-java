package com.mercado.sistema_vendas.services;

import com.mercado.sistema_vendas.models.ItemVenda;
import com.mercado.sistema_vendas.models.Produto;
import com.mercado.sistema_vendas.models.Venda;
import com.mercado.sistema_vendas.repository.VendaRepository;
import com.mercado.sistema_vendas.services.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Transactional(readOnly = true)
    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Venda> buscarPorVendedor(String vendedor) {
        return vendaRepository.findByVendedorContainingIgnoreCase(vendedor);
    }

    @Transactional(readOnly = true)
    public Venda buscarPorId(Long id) {
        Venda venda = vendaRepository.findById(id).orElse(null);

        if (venda != null) {
            for (ItemVenda item : venda.getItens()) {
                if (item.getProduto() != null) {
                    item.setProdutoCodigo(item.getProduto().getCodigo());
                }
            }
        }

        return venda;
    }

    @Transactional
    public void excluirPorId(Long id) throws Exception {
        Venda venda = vendaRepository.findById(id).orElse(null);
        if (venda == null) {
            throw new Exception("Venda não encontrada com ID: " + id);
        }
        vendaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Venda> buscarPorData(LocalDate dataVenda) {
        LocalDateTime startOfDay = dataVenda.atStartOfDay();
        LocalDateTime endOfDay = dataVenda.atTime(LocalTime.MAX);

        return vendaRepository.findByDataBetween(startOfDay, endOfDay);
    }
}
