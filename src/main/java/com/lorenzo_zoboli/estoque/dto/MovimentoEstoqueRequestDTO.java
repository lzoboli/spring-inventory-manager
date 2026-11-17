package com.lorenzo_zoboli.estoque.dto;

import com.lorenzo_zoboli.estoque.model.enums.TipoMovimentacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovimentoEstoqueRequestDTO(
        @NotNull(message = "O ID do produto é obrigatório")
        Long idProduto,
        @NotNull(message = "O tipo de movimentação (ENTRADA ou SAIDA) é obrigatório")
        TipoMovimentacao tipo,
        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade movimentada deve ser maior que zero")
        Integer quantidade,
        String observacao
) {}
