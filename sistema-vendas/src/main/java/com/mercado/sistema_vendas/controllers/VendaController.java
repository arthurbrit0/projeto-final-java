package com.mercado.sistema_vendas.controllers;

import com.mercado.sistema_vendas.models.Venda;
import com.mercado.sistema_vendas.models.ItemVenda;
import com.mercado.sistema_vendas.dto.VendaDTO;
import com.mercado.sistema_vendas.dto.ItemVendaDTO;
import com.mercado.sistema_vendas.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping("/salvar")
    public ResponseEntity<?> salvarVenda(@Valid @RequestBody VendaDTO vendaDTO) {
        try {
            Venda venda = convertToEntity(vendaDTO);

            Venda vendaSalva = vendaService.salvar(venda);

            VendaDTO vendaSalvaDTO = convertToDTO(vendaSalva);

            return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalvaDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarVendas(
            @RequestParam(value = "vendedor", required = false) String vendedor,
            @RequestParam(value = "data", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVenda) {

        try {
            List<Venda> vendas;

            if (vendedor != null && !vendedor.isEmpty()) {
                vendas = vendaService.buscarPorVendedor(vendedor);
            } else if (dataVenda != null) {
                vendas = vendaService.buscarPorData(dataVenda);
            } else {
                vendas = vendaService.listarTodas();
            }

            List<VendaDTO> vendasDTO = vendas.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(vendasDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro: " + e.getMessage());
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirVenda(@PathVariable Long id) {
        try {
            vendaService.excluirPorId(id);
            return ResponseEntity.ok("Venda excluída com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao excluir a venda: " + e.getMessage());
        }
    }

    private Venda convertToEntity(VendaDTO vendaDTO) {
        Venda venda = new Venda();
        venda.setVendedor(vendaDTO.getVendedor());

        List<ItemVenda> itens = vendaDTO.getItens().stream().map(itemDTO -> {
            ItemVenda item = new ItemVenda();
            item.setQuantidade(itemDTO.getQuantidade());
            item.setProdutoCodigo(itemDTO.getProdutoCodigo());
            return item;
        }).collect(Collectors.toList());

        venda.setItens(itens);
        return venda;
    }

    private VendaDTO convertToDTO(Venda venda) {
        VendaDTO vendaDTO = new VendaDTO();
        vendaDTO.setId(venda.getId());
        vendaDTO.setData(venda.getData());
        vendaDTO.setVendedor(venda.getVendedor());
        vendaDTO.setValorTotal(venda.getValorTotal());
        List<ItemVendaDTO> itensDTO = venda.getItens().stream()
                .map(item -> {
                    ItemVendaDTO itemDTO = new ItemVendaDTO();
                    itemDTO.setId(item.getId());
                    itemDTO.setProdutoCodigo(item.getProduto().getCodigo());
                    itemDTO.setProdutoNome(item.getProduto().getNome());
                    itemDTO.setQuantidade(item.getQuantidade());
                    itemDTO.setPrecoUnitario(item.getPrecoUnitario());
                    return itemDTO;
                })
                .collect(Collectors.toList());

        vendaDTO.setItens(itensDTO);

        return vendaDTO;

    }
}
