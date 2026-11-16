package com.lorenzo_zoboli.estoque.exception;

import java.time.LocalDateTime;
import java.util.List;


/**
 * DTO imutável responsável por padronizar o corpo das respostas de erro da API.
 * <p>
 * Utiliza um {@code record} do Java para garantir imutabilidade e gerar
 * automaticamente construtor, métodos de acesso (sem prefixo 'get'),
 * {@code equals()}, {@code hashCode()} e {@code toString()}.
 *
 * @param timestamp Data e hora exatas em que o erro ocorreu.
 * @param status    Código de status HTTP retornado (ex: 400, 404, 500).
 * @param erro      Descrição resumida do tipo de erro.
 * @param detalhes  Lista de mensagens específicas (útil para erros de validação de campos).
 */
public record ErroResponseDTO(
        LocalDateTime timestamp,
        int status,
        String erro,
        List<String> detalhes
) {
}
