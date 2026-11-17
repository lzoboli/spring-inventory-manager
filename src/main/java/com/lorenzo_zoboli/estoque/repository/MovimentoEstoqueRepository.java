package com.lorenzo_zoboli.estoque.repository;

import com.lorenzo_zoboli.estoque.model.MovimentoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long> {
    List<MovimentoEstoque> findByProdutoIdOrderByDataHoraDesc(Long produtoId);
}