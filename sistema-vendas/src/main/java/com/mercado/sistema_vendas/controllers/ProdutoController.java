package com.mercado.sistema_vendas.controllers;

import com.mercado.sistema_vendas.models.Produto;
import com.mercado.sistema_vendas.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // Permite requisições do frontend
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Criar novo produto
    @PostMapping("/salvar")
    public ResponseEntity<?> salvarProduto(@RequestBody Produto produto) {
        try {
            Produto produtoSalvo = produtoService.salvar(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao salvar o produto: " + e.getMessage());
        }
    }

    // Atualizar produto existente
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        try {
            Produto produtoExistente = produtoService.buscarPorId(id);
            if (produtoExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado com ID: " + id);
            }

            produtoExistente.setCodigo(produtoAtualizado.getCodigo());
            produtoExistente.setNome(produtoAtualizado.getNome());
            produtoExistente.setDescricao(produtoAtualizado.getDescricao());
            produtoExistente.setPreco(produtoAtualizado.getPreco());
            produtoExistente.setQuantidade(produtoAtualizado.getQuantidade());

            Produto produtoSalvo = produtoService.salvar(produtoExistente);
            return ResponseEntity.ok(produtoSalvo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao atualizar o produto: " + e.getMessage());
        }
    }

    // Buscar produto por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarProdutoPorId(@PathVariable Long id) {
        try {
            Produto produto = produtoService.buscarPorId(id);
            if (produto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado com ID: " + id);
            }
            return ResponseEntity.ok(produto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao buscar o produto: " + e.getMessage());
        }
    }

    // Listar todos os produtos
    @GetMapping("/listar")
    public ResponseEntity<?> listarProdutos() {
        try {
            List<Produto> produtos = produtoService.listarTodos();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao listar os produtos: " + e.getMessage());
        }
    }

    // Excluir produto por ID
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirProduto(@PathVariable Long id) {
        try {
            produtoService.excluirPorId(id);
            return ResponseEntity.ok("Produto excluído com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao excluir o produto: " + e.getMessage());
        }
    }
}
