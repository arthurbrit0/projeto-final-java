package com.mercado.sistema_vendas.services;

import com.mercado.sistema_vendas.models.Produto;
import com.mercado.sistema_vendas.repository.ProdutoRepository;
import com.mercado.sistema_vendas.repository.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Transactional(readOnly = true)
    public Produto buscarPorCodigo(String codigo) {
        return produtoRepository.findByCodigo(codigo);
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void excluirPorId(Long id) throws Exception {
        Produto produto = buscarPorId(id);
        if (produto == null) {
            throw new Exception("Produto não encontrado com ID: " + id);
        }

        if (!itemVendaRepository.findByProduto(produto).isEmpty()) {
            throw new Exception("Esse produto está ligado a uma venda e não pode ser deletado.");
        }
        produtoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
}
