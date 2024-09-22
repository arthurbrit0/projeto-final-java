package com.mercado.sistema_vendas.controllers;

import com.mercado.sistema_vendas.models.Venda;
import com.mercado.sistema_vendas.services.VendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;  // Import para DateTimeFormat
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;  // Import para LocalDate
import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    // Listar todas as vendas ou buscar por vendedor ou data
    @GetMapping("/listar")
    public ResponseEntity<List<Venda>> listarVendas(
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

            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    // Salvar nova venda
    @PostMapping("/salvar")
    public ResponseEntity<Venda> salvarVenda(@Valid @RequestBody Venda venda) throws Exception {
        Venda vendaSalva = vendaService.salvar(venda);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
    }



    // Excluir venda
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirVenda(@PathVariable Long id) {
        vendaService.excluirPorId(id);
        return ResponseEntity.ok("Venda excluída com sucesso.");
    }

    // Buscar venda por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Venda> buscarVendaPorId(@PathVariable Long id) {
        Venda venda = vendaService.buscarPorId(id);
        if (venda == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(venda);
    }
}
