package com.lorenzo_zoboli.estoque.controller;

import com.lorenzo_zoboli.estoque.dto.CategoriaRequestDTO;
import com.lorenzo_zoboli.estoque.dto.CategoriaResponseDTO;
import com.lorenzo_zoboli.estoque.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    // Injeção via construtor: mais seguro e evita @Autowired solto nos atributos
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    // @Valid dispara as regras do DTO (@NotBlank, etc.) antes mesmo de entrar no método
    // @RequestBody pega o JSON que veio no corpo da requisição e converte pro DTO
    public ResponseEntity<CategoriaResponseDTO> criar(@Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.registrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas()); // Devolve 200 OK
    }

    @GetMapping("/{id}")
    // @PathVariable captura o número que veio direto na URL (ex: /api/categorias/1)
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.ok(categoriaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build(); // Devolve 204 No Content (deu certo e não tem corpo pra retornar)
    }
}
