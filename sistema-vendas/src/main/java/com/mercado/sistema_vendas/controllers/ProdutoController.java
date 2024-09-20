package com.mercado.sistema_vendas.controllers;
import com.mercado.sistema_vendas.models.Produto;
import com.mercado.sistema_vendas.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;
    @GetMapping("/novo")
    public String mostrarFormNovoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("formAction", "/produtos/salvar");
        return "produto_form";
    }

    @PostMapping("/salvar")
    public String salvarProduto(@Valid @ModelAttribute Produto produto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/produtos/salvar");
            return "produto_form";
        }
        try {

            produtoService.salvar(produto);
            return "redirect:/produtos/listar";
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("codigo", "error.produto",
                    "Código já existente. Por favor, escolha outro código.");
            model.addAttribute("formAction", "/produtos/salvar");
            return "produto_form";
        } catch (Exception e) {
            result.rejectValue(null, "error.produto", "Ocorreu um erro ao salvar o produto.");
            model.addAttribute("formAction", "/produtos/salvar");
            return "produto_form";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormEditarProduto(@PathVariable Long id, Model model) {
        Produto produto = produtoService.buscarPorId(id);
        if (produto == null) {
            return "redirect:/produtos/listar";
        }
        model.addAttribute("produto", produto);
        model.addAttribute("formAction", "/produtos/atualizar/" + id);
        return "produto_form";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarProduto(@PathVariable Long id, @Valid @ModelAttribute Produto produto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/produtos/atualizar/" + id);
            return "produto_form";

        }

        produto.setId(id);
        produtoService.salvar(produto);
        return "redirect:/produtos/listar";
    }

    @GetMapping("/excluir/{id}")

    public String excluirProduto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            produtoService.excluirPorId(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto excluído com sucesso.");
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        }
        return "redirect:/produtos/listar";
    }

    @GetMapping("/listar")

    public String listarProdutos(Model model) {

        model.addAttribute("produtos", produtoService.listarTodos());
        return "produto_lista";
    }
}
