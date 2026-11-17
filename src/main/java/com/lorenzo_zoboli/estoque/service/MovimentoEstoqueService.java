package com.lorenzo_zoboli.estoque.service;

import com.lorenzo_zoboli.estoque.dto.MovimentoEstoqueRequestDTO;
import com.lorenzo_zoboli.estoque.dto.MovimentoEstoqueResponseDTO;
import com.lorenzo_zoboli.estoque.exception.RegraNegocioException;
import com.lorenzo_zoboli.estoque.model.Produto;
import com.lorenzo_zoboli.estoque.model.enums.TipoMovimentacao;
import com.lorenzo_zoboli.estoque.repository.MovimentoEstoqueRepository;
import com.lorenzo_zoboli.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovimentoEstoqueService {

    private final MovimentoEstoqueRepository movimentoEstoqueRepository;
    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepository;

    public MovimentoEstoqueService(
            MovimentoEstoqueRepository movimentoEstoqueRepository,
            ProdutoService produtoService,
            ProdutoRepository produtorepository) {
        this.produtoRepository = produtorepository;
        this.produtoService = produtoService;
        this.movimentoEstoqueRepository = movimentoEstoqueRepository;
    }
/**
    @Transactional
    public MovimentoEstoqueResponseDTO registrar(MovimentoEstoqueRequestDTO dto) {
        // Busca o produto no banco, caso não exista o ProdutoService lança um erro 404
        Produto produto = produtoService.buscarEntidadePorId(dto.idProduto());

        // Verifica o tipo da operação
        if (dto.tipo() == TipoMovimentacao.ENTRADA) {
            produto.setQtEstoque(produto.getQtEstoque() + dto.quantidade());
        } else if (dto.tipo() == TipoMovimentacao.SAIDA) {
            if (produto.getQtEstoque() < dto.quantidade()) {
                throw new RegraNegocioException(
                        String.format("Estoque insuficiente para o produto '%s'. Disponível: %d, Solicitado: %d",
                                produto.getNome(), produto.getQtEstoque(), dto.quantidade())
                );
            }
            produto.setQtEstoque(produto.getQtEstoque() - dto.quantidade());
        }
        produtoReposit
    }
    **/
}
