package com.lorenzo_zoboli.estoque.dto;

import com.lorenzo_zoboli.estoque.model.MovimentoEstoque;
import com.lorenzo_zoboli.estoque.model.enums.TipoMovimentacao;

import java.time.LocalDateTime;

public record MovimentoEstoqueResponseDTO(
        Long id,
        Long idProduto,
        String nomeProduto,
        TipoMovimentacao tipo,
        Integer quantidade,
        int saldoAtualProduto,
        LocalDateTime dtHora,
        String observacao
) {
    public static MovimentoEstoqueResponseDTO fromEntity(MovimentoEstoque mov) {
        return new MovimentoEstoqueResponseDTO(
                mov.getId(),
                mov.getProduto().getId(),
                mov.getProduto().getNome(),
                mov.getTipo(),
                mov.getQuantidade(),
                mov.getProduto().getQtEstoque(),
                mov.getDataHora(),
                mov.getObservacao()
        );
    }
}