package com.mercado.sistema_vendas.services;
import com.mercado.sistema_vendas.models.ItemVenda;
import com.mercado.sistema_vendas.models.Produto;
import com.mercado.sistema_vendas.repository.ItemVendaRepository;
import com.mercado.sistema_vendas.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ItemVendaRepository itemVendaRepository;

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
    public Produto buscarPorCodigo(String codigo) {
        return produtoRepository.findByCodigo(codigo);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }
    public void excluirPorId(Long id) throws Exception {
        Produto produto = buscarPorId(id);
        if (produto == null) {
            throw new Exception("Produto nao foi encontrado");
        }

        List<ItemVenda> itensVenda = itemVendaRepository.findByProduto(produto);
        if (!itensVenda.isEmpty()) {
            throw new Exception("Esse produto esta ligado a uma venda, logo, nao pode ser deletado");
        }

        produtoRepository.deleteById(id);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
}
