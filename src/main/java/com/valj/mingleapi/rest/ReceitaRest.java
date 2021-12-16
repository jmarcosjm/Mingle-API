package com.valj.mingleapi.rest;

import com.valj.mingleapi.model.IngredienteUtilizado;
import com.valj.mingleapi.model.document.IngredienteCadastrado;
import com.valj.mingleapi.model.document.Receita;
import com.valj.mingleapi.model.document.ReceitaSalva;
import com.valj.mingleapi.service.IngredienteCadastradoService;
import com.valj.mingleapi.service.IngredienteService;
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
    private IngredienteCadastradoService ingredienteCadastradoService;
    private IngredienteService ingredienteService;


    @GetMapping
    public ResponseEntity<List<Receita>> getAll(@RequestParam(required = false) String user, @RequestParam(required = false) String nome, @RequestBody(required = false) List<IngredienteUtilizado> ingredientes) {
        if (user == null) {
            if(nome != null) return ResponseEntity.ok(service.getAllByNome(nome));

            if(ingredientes == null) return ResponseEntity.ok(service.getAll());

            return ResponseEntity.ok(service.getReceitasByIngredientes(ingredientes));
        }

        return ResponseEntity.ok(service.encontrarTodosPorIdUsuario(user));
    }

    @GetMapping(value = "/{_id}")
    public ResponseEntity<Receita> get(@PathVariable("_id") String _id) {
        return service.getById(_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Receita> adicionar(@RequestBody Receita receita) {
        receita.getIngredientesUtilizados().forEach(ingredienteUtilizado -> {
            var ingrediente = ingredienteUtilizado.getIngrediente();

            if (ingredienteService.findByNome(ingrediente.getNome()).isEmpty()) {
                ingrediente.setNome(ingrediente.getNome().toUpperCase());
                ingredienteUtilizado.setIngrediente(ingredienteService.adicionar(ingrediente));
            } else {
                ingredienteUtilizado
                        .setIngrediente(ingredienteService
                                .findByNome(ingrediente.getNome())
                                .orElse(ingrediente));
            }
        });

        service.adicionar(receita);
        return ResponseEntity.ok(receita);
    }

    @GetMapping(path = "/salvas/{idUsuario}")
    public ResponseEntity<List<Receita>> getAllReceitasSalvas(@PathVariable("idUsuario") String idUsuario) {
        List<ReceitaSalva> receitasSalvas = receitaSalvaService.encontrarTodosPorIdUsuario(idUsuario);
        List<Receita> receitas = receitasSalvas.stream()
                .map(receitaSalva -> service.getById(receitaSalva.getIdUsuarioReceita().get_idReceita()).orElse(null))
                .collect(Collectors.toList());
        return ResponseEntity.ok(receitas);
    }

    @DeleteMapping(path = "/salvas")
    public ResponseEntity<Void> deleteAllReceitasSalvas() {
        receitaSalvaService.deleteAll();
        return ResponseEntity.ok().build();
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

    @GetMapping(path = "/ingrediente-cadastrado")
    ResponseEntity<List<Receita>> getReceitasPorIngredienteCadastrado(@RequestHeader String idIngredienteCadastrado) {
        IngredienteCadastrado ingredienteCadastrado = ingredienteCadastradoService.getByIdUsuario(idIngredienteCadastrado);
        return ResponseEntity.ok(service.getReceitaByIngrediente(ingredienteCadastrado.getIngredienteUtilizado()));
    }

    @GetMapping(path = "/ingredientes-cadastrados/{idUsuario}")
    ResponseEntity<List<Receita>> getReceitasPorIngredientesCadastrados(@PathVariable(value = "idUsuario") String idUsuario) {
        List<IngredienteUtilizado> ingredientesUsuario = ingredienteCadastradoService.getAll(idUsuario).stream()
                .map(IngredienteCadastrado::getIngredienteUtilizado)
                .collect(Collectors.toList());
        return ResponseEntity.ok(service.getReceitasByIngredientes(ingredientesUsuario));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
