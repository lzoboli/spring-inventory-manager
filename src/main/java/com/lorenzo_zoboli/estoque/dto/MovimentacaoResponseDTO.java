package com.lorenzo_zoboli.estoque.dto;

import com.lorenzo_zoboli.estoque.model.MovimentacaoEstoque;
import com.lorenzo_zoboli.estoque.model.enums.TipoMovimentacao;

import java.time.LocalDateTime;

public record MovimentacaoResponseDTO (
        Long id,
        Long idProduto,
        String nomeProduto,
        TipoMovimentacao tipo,
        Integer quantidade,
        int saldoAtualProduto,
        LocalDateTime dtHora,
        String observacao
) {
    public static MovimentacaoResponseDTO fromEntity(MovimentacaoEstoque mov) {
        return new MovimentacaoResponseDTO(
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