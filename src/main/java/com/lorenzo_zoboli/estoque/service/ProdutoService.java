package com.lorenzo_zoboli.estoque.service;

import com.lorenzo_zoboli.estoque.dto.ProdutoRequestDTO;
import com.lorenzo_zoboli.estoque.dto.ProdutoResponseDTO;
import com.lorenzo_zoboli.estoque.exception.RecursoNaoEncontradoException;
import com.lorenzo_zoboli.estoque.model.Categoria;
import com.lorenzo_zoboli.estoque.model.Produto;
import com.lorenzo_zoboli.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    public ProdutoService (ProdutoRepository produtoRepository, CategoriaService categoriaService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
    }

    @Transactional
    public ProdutoResponseDTO registrar(ProdutoRequestDTO dto) {
        // Busca a categoria; se o ID não existir, o próprio CategoriaService já estoura um erro 404
        Categoria categoria = categoriaService.buscarEntidadePorId(dto.idCategoria());

        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setQtEstoque(0); // Os produtos começam com saldo zerado, deve ser injetado posteriormente via movimentação
        produto.setCategoria(categoria);

        return ProdutoResponseDTO.fromEntity(produtoRepository.save(produto));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id) {
        return ProdutoResponseDTO.fromEntity(buscarEntidadePorId(id));
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = buscarEntidadePorId(id);
        Categoria categoria = categoriaService.buscarEntidadePorId(dto.idCategoria());

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setCategoria(categoria);
        // O qtEstoque não é alterado, respeitando a regra de negócio

        return ProdutoResponseDTO.fromEntity(produtoRepository.save(produto));
    }

    @Transactional
    public void deletar(Long id) {
        Produto produto = buscarEntidadePorId(id);
        produtoRepository.delete(produto);
    }

    // É reutilizado pelo MovimentoEstoqueService para buscar o produto ou lança um 404
    public Produto buscarEntidadePorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com id: " + id));
    }
}
