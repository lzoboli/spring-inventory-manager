package com.lorenzo_zoboli.estoque.service;

import com.lorenzo_zoboli.estoque.dto.MovimentoEstoqueRequestDTO;
import com.lorenzo_zoboli.estoque.dto.MovimentoEstoqueResponseDTO;
import com.lorenzo_zoboli.estoque.exception.RegraNegocioException;
import com.lorenzo_zoboli.estoque.model.MovimentoEstoque;
import com.lorenzo_zoboli.estoque.model.Produto;
import com.lorenzo_zoboli.estoque.model.enums.TipoMovimentacao;
import com.lorenzo_zoboli.estoque.repository.MovimentoEstoqueRepository;
import com.lorenzo_zoboli.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    @Transactional
    public MovimentoEstoqueResponseDTO registrar(MovimentoEstoqueRequestDTO dto) {
        // Busca o produto no banco, caso não exista o ProdutoService lança um erro 404
        Produto produto = produtoService.buscarEntidadePorId(dto.idProduto());

        // Verifica o tipo da operação
        if (dto.tipo() == TipoMovimentacao.ENTRADA) {
            produto.setQtEstoque(produto.getQtEstoque() + dto.quantidade());
        } else if (dto.tipo() == TipoMovimentacao.SAIDA) {
            // Não permite estoque negativo
            if (produto.getQtEstoque() < dto.quantidade()) {
                throw new RegraNegocioException(
                        String.format("Estoque insuficiente para o produto '%s'. Disponível: %d, Solicitado: %d",
                                produto.getNome(), produto.getQtEstoque(), dto.quantidade())
                );
            }
            produto.setQtEstoque(produto.getQtEstoque() - dto.quantidade());
        }
        produtoRepository.save(produto);

        MovimentoEstoque movimento = new MovimentoEstoque();
        movimento.setProduto(produto);
        movimento.setTipo(dto.tipo());
        movimento.setQuantidade(dto.quantidade());
        movimento.setDdataHora(LocalDateTime.now());
        movimento.setObservacao(dto.observacao());

        MovimentoEstoque salvo = movimentoEstoqueRepository.save(movimento);
        return MovimentoEstoqueResponseDTO.fromEntity(salvo);
    }

    @Transactional(readOnly = true)
    public List<MovimentoEstoqueResponseDTO> listarTodos() {
        return movimentoEstoqueRepository.findAll()
                .stream()
                .map(MovimentoEstoqueResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MovimentoEstoqueResponseDTO> listarPorProduto(Long idProduto) {
        // Verifica se o produto existe antes de buscar, devolve 404 caso não
        produtoService.buscarEntidadePorId(idProduto);

        return movimentoEstoqueRepository.findByProdutoIdOrderByDataHoraDesc(idProduto)
                .stream()
                .map(MovimentoEstoqueResponseDTO::fromEntity)
                .toList();
    }
}
