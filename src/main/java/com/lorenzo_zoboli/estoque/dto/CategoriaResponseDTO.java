package com.lorenzo_zoboli.estoque.dto;

import com.lorenzo_zoboli.estoque.model.Categoria;

public record CategoriaResponseDTO (
    Long id,
    String nome,
    String descricao
) {
    public static CategoriaResponseDTO fromEntity(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }
}