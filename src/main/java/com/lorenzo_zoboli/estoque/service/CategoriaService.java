package com.lorenzo_zoboli.estoque.service;

import com.lorenzo_zoboli.estoque.dto.CategoriaRequestDTO;
import com.lorenzo_zoboli.estoque.dto.CategoriaResponseDTO;
import com.lorenzo_zoboli.estoque.exception.RegraNegocioException;
import com.lorenzo_zoboli.estoque.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service //essa classe guarda regras de negócio e pode ser injetada em outros lugares
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    // Injeção via construtor: jeito mais limpo e facilita posteriormente na criação dos testes.
    public CategoriaService(CategoriaRepository  categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional // Garante que as alterações sejam commitadas somente caso todas as operações sejam executadas com sucesso, caso contrário dá rollback
    public CategoriaResponseDTO criar(CategoriaRequestDTO dto) {
        // Verifica se já existe uma classe de mesmo nome antes de criar (ignorando maiúsculas e minúsculas)
        if (categoriaRepository.existsByNomeIgnoreCase(dto.nome())) {
            throw new RegraNegocioException("Já existe uma categoria cadastrada com o nome: " + dto.nome());
        }

        // Traz os dados do DTO (trazidos da web)
    }
}
