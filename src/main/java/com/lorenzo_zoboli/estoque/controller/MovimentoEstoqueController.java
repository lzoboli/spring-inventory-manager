package com.lorenzo_zoboli.estoque.controller;

import com.lorenzo_zoboli.estoque.service.MovimentoEstoqueService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movimentos")
public class MovimentoEstoqueController {

    private final MovimentoEstoqueService movimentoEstoqueService;

    public MovimentoEstoqueController(MovimentoEstoqueService movimentoEstoqueService) {
        this.movimentoEstoqueService = movimentoEstoqueService;
    }
}
