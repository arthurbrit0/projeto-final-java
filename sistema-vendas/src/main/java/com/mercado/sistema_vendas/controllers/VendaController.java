package com.mercado.sistema_vendas.controllers;
import com.mercado.sistema_vendas.models.ItemVenda;
import com.mercado.sistema_vendas.models.Produto;
import com.mercado.sistema_vendas.models.Venda;
import com.mercado.sistema_vendas.services.ProdutoService;
import com.mercado.sistema_vendas.services.VendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;
    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/nova")
    public String mostrarFormNovaVenda(Model model) {
        Venda venda = new Venda();
        venda.getItens().add(new ItemVenda()); // Adiciona um item vazio para o formulário

        model.addAttribute("venda", venda);
        model.addAttribute("produtos", produtoService.listarTodos());
        return "venda_form";
    }

    @PostMapping("/salvar")

    public String salvarVenda(@Valid @ModelAttribute Venda venda, BindingResult result, Model model) {
        venda.getItens().removeIf(item -> item.getProdutoCodigo() == null || item.getProdutoCodigo().isEmpty());

        for (int i=0; i < venda.getItens().size(); i++) {
            ItemVenda item = venda.getItens().get(i);
            if (item.getProdutoCodigo() != null && !item.getProdutoCodigo().isEmpty()) {
                Produto produto = produtoService.buscarPorCodigo(item.getProdutoCodigo());
                if (produto != null) {
                    item.setProduto(produto);
                    item.setPrecoUnitario(produto.getValor());
                } else {

                    result.rejectValue("itens[" + i + "].produtoCodigo", "error.itemVenda", "Produto não encontrado.");
                }
            } else {
                result.rejectValue("itens[" + i + "].produtoCodigo", "error.itemVenda", "Selecione um produto.");
            }

            if (item.getQuantidade() == null || item.getQuantidade() <= 0) {
                result.rejectValue("itens[" + i + "].quantidade", "error.itemVenda", "Quantidade deve ser maior que zero.");

            }
        }



        if (result.hasErrors()) {
            model.addAttribute("venda", venda);
            model.addAttribute("produtos", produtoService.listarTodos());
            return "venda_form";
        }

        try {

            vendaService.salvar(venda);
            return "redirect:/vendas/listar";

        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("venda", venda);
            model.addAttribute("produtos", produtoService.listarTodos());
            return "venda_form";

        }
    }

    @GetMapping("/listar")

    public String listarVendas(@RequestParam(value = "vendedor", required = false) String vendedor, Model model) {

        List<Venda> vendas;

        if (vendedor != null && !vendedor.isEmpty()) {
            vendas = vendaService.buscarPorVendedor(vendedor);
        } else {
            vendas = vendaService.listarTodas();
        }
        model.addAttribute("vendas", vendas);
        return "venda_lista";
    }
}


