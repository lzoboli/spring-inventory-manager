package com.lorenzo_zoboli.estoque.service;

import com.lorenzo_zoboli.estoque.dto.CategoriaRequestDTO;
import com.lorenzo_zoboli.estoque.dto.CategoriaResponseDTO;
import com.lorenzo_zoboli.estoque.exception.RecursoNaoEncontradoException;
import com.lorenzo_zoboli.estoque.exception.RegraNegocioException;
import com.lorenzo_zoboli.estoque.model.Categoria;
import com.lorenzo_zoboli.estoque.repository.CategoriaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service //essa classe guarda regras de negócio e pode ser injetada em outros lugares
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    // Injeção via construtor: jeito mais limpo e facilita posteriormente na criação dos testes.
    public CategoriaService(CategoriaRepository  categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional // Garante que as alterações sejam commitadas somente caso todas as operações sejam executadas com sucesso, caso contrário dá rollback
    public CategoriaResponseDTO registrar(CategoriaRequestDTO dto) {
        // Verifica se já existe uma classe de mesmo nome antes de criar (ignorando maiúsculas e minúsculas)
        if (categoriaRepository.existsByNomeIgnoreCase(dto.nome())) {
            throw new RegraNegocioException("Já existe uma categoria cadastrada com o nome: " + dto.nome());
        }

        // Traz os dados do DTO (trazidos da web)
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());

        // Salva no banco e já devolve convertido no DTO de resposta
        return CategoriaResponseDTO.fromEntity(categoriaRepository.save(categoria));
    }

    @Transactional(readOnly = true) // Avisa ao banco que é apenas leitura, tornando a consulta mais leve
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaResponseDTO::fromEntity) //Converte as categorias da lista em DTO
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = buscarEntidadePorId(id);
        return CategoriaResponseDTO.fromEntity(categoria);
    }

    @Transactional
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = buscarEntidadePorId(id);
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        return CategoriaResponseDTO.fromEntity(categoriaRepository.save(categoria));
    }

    @Transactional
    public void deletar(Long id) {
        Categoria categoria = buscarEntidadePorId(id);
        categoriaRepository.delete(categoria);
    }

    // Método auxiliar público para outros services utilizarem na hora de buscar a entidade real da categoria.
    public Categoria buscarEntidadePorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada com id: " + id));
    }
}
