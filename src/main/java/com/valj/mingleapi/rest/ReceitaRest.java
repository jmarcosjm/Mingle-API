package com.valj.mingleapi.rest;

import com.valj.mingleapi.model.document.Receita;
import com.valj.mingleapi.model.document.ReceitaSalva;
import com.valj.mingleapi.service.ReceitaSalvaService;
import com.valj.mingleapi.service.ReceitaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receitas")
@AllArgsConstructor
public class ReceitaRest {
    private ReceitaService service;
    private ReceitaSalvaService receitaSalvaService;

    @GetMapping
    public ResponseEntity<List<Receita>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{_id}")
    public ResponseEntity<Receita> get(@PathVariable("_id") String _id) {
        return service.getById(_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Receita> adicionar(@RequestBody Receita receita) {
        service.adicionar(receita);
        return ResponseEntity.ok(receita);
    }

    @GetMapping(path = "/salvas")
    public ResponseEntity<List<Receita>> getAllReceitasSalvas(@RequestHeader String _idUsuario) {
        List<ReceitaSalva> receitasSalvas = receitaSalvaService.encontrarTodosPorIdUsuario(_idUsuario);
        List<Receita> receitas = receitasSalvas.stream()
                .map(receitaSalva -> service.getById(receitaSalva.getIdUsuarioReceita().get_idReceita()).get())
                .collect(Collectors.toList());
        return ResponseEntity.ok(receitas);
    }

    @PostMapping(path = "/salvar")
    ResponseEntity<String> alternarSalvarReceita(@RequestBody ReceitaSalva receitaSalva) {
        String resposta;
        Optional<ReceitaSalva> receitaSalvaExistente = receitaSalvaService.encontrarPorIdUsuarioReceita(receitaSalva.getIdUsuarioReceita());

        if (receitaSalvaExistente.isPresent()) {
            receitaSalvaService.apagarReceitaSalva(receitaSalvaExistente.get().getIdUsuarioReceita());
            resposta = "Receita removida dos favoritos";
        } else {
            receitaSalvaService.salvarReceita(receitaSalva);
            resposta = "Receita adicionada aos favoritos";
        }

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
