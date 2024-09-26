package com.mercado.sistema_vendas.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mercado.sistema_vendas.models.Venda;
import com.mercado.sistema_vendas.dto.VendaDTO;
import com.mercado.sistema_vendas.models.ItemVenda;
import com.mercado.sistema_vendas.dto.ItemVendaDTO;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);

        TypeMap<VendaDTO, Venda> vendaDTOToVendaTypeMap = modelMapper.createTypeMap(VendaDTO.class, Venda.class);
        vendaDTOToVendaTypeMap.addMappings(mapper -> mapper.skip(Venda::setItens));
        Converter<ItemVenda, ItemVendaDTO> itemVendaToItemVendaDTOConverter = context -> {
            ItemVenda source = context.getSource();
            ItemVendaDTO destination = new ItemVendaDTO();
            destination.setId(source.getId());
            destination.setProdutoCodigo(source.getProduto().getCodigo());
            destination.setProdutoNome(source.getProduto().getNome());
            destination.setQuantidade(source.getQuantidade());
            destination.setPrecoUnitario(source.getPrecoUnitario());
            return destination;
        };
        modelMapper.createTypeMap(ItemVenda.class, ItemVendaDTO.class)
                .setConverter(itemVendaToItemVendaDTOConverter);

        TypeMap<Venda, VendaDTO> vendaToVendaDTOTypeMap = modelMapper.createTypeMap(Venda.class, VendaDTO.class);
        vendaToVendaDTOTypeMap.addMappings(mapper -> mapper.skip(VendaDTO::setItens));
        vendaToVendaDTOTypeMap.addMappings(mapper -> mapper.using(ctx -> {
            List<ItemVenda> itens = ((Venda) ctx.getSource()).getItens();
            return itens.stream()
                    .map(item -> modelMapper.map(item, ItemVendaDTO.class))
                    .collect(Collectors.toList());
        }).map(Venda::getItens, VendaDTO::setItens));

        return modelMapper;
    }

}
