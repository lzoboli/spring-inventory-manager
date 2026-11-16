package com.lorenzo_zoboli.estoque.dto;

import com.lorenzo_zoboli.estoque.model.Produto;

import java.math.BigDecimal;

public record ProdutoResponseDTO (
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        int qtEstoque,
        Long idCategoria,
        String nomeCategoria
) {
    public static ProdutoResponseDTO fromEntity(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQtEstoque(),
                produto.getCategoria().getId(),
                produto.getCategoria().getNome()
        );
    }
}