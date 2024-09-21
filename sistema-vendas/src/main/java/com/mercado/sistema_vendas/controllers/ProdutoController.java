package com.mercado.sistema_vendas.controllers;

import com.mercado.sistema_vendas.models.Produto;
import com.mercado.sistema_vendas.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Listar todos os produtos
    @GetMapping("/listar")
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    // Salvar novo produto
    @PostMapping("/salvar")
    public ResponseEntity<?> salvarProduto(@Valid @RequestBody Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getFieldErrors());
        }

        try {
            produtoService.salvar(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produto);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Código já existente. Por favor, escolha outro código.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao salvar o produto.");
        }
    }

    // Atualizar produto
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @Valid @RequestBody Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getFieldErrors());
        }

        produto.setId(id);
        produtoService.salvar(produto);
        return ResponseEntity.ok(produto);
    }

    // Excluir produto
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirProduto(@PathVariable Long id) {
        try {
            produtoService.excluirPorId(id);
            return ResponseEntity.ok("Produto excluído com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o produto.");
        }
    }

    // Buscar produto por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        if (produto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(produto);
    }
}
