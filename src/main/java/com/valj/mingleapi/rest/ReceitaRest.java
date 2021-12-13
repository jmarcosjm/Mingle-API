package com.valj.mingleapi.rest;

import com.valj.mingleapi.model.IngredienteUtilizado;
import com.valj.mingleapi.model.ReceitaResponse;
import com.valj.mingleapi.model.document.IngredienteCadastrado;
import com.valj.mingleapi.model.document.Receita;
import com.valj.mingleapi.model.document.ReceitaSalva;
import com.valj.mingleapi.service.IngredienteCadastradoService;
import com.valj.mingleapi.service.ReceitaSalvaService;
import com.valj.mingleapi.service.ReceitaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

//    @GetMapping(path = "/receitas-ingrediente-cadastrado")
//    ResponseEntity<List<Receita>> getReceitasPorIngredienteCadastrado(@RequestHeader String idUsuario){
//        List<Receita> retorno = new ArrayList<>();
//        List<Ingrediente> ingredientes = ingredienteCadastradoService.getAll(idUsuario).stream()
//                .map(ingredienteCadastrado -> ingredienteCadastrado.getIngredienteUtilizado().getIngrediente())
//                .collect(Collectors.toList());
//        ingredientes.forEach(ingrediente -> retorno.addAll(service.getReceitaByIngrediente(ingrediente)));
//        return ResponseEntity.ok(retorno);
//    }

    @GetMapping(path = "/ingrediente-cadastrado")
    ResponseEntity<List<ReceitaResponse>> getReceitasPorIngredienteCadastrado(@RequestHeader String idIngredienteCadastrado, @RequestHeader String idUsuario) {
        IngredienteCadastrado ingredienteCadastrado = ingredienteCadastradoService.getByIdUsuarioReceita(idIngredienteCadastrado);
        List<IngredienteUtilizado> ingredientesUsuario = ingredienteCadastradoService.getAll(idUsuario).stream()
                .map(IngredienteCadastrado::getIngredienteUtilizado)
                .collect(Collectors.toList());
        return ResponseEntity.ok(service.getReceitaByIngrediente(ingredienteCadastrado.getIngredienteUtilizado(), ingredientesUsuario));
    }

    @GetMapping(path = "/ingredientes-cadastrados/{idUsuario}")
    ResponseEntity<List<ReceitaResponse>> getReceitasPorIngredientesCadastrados(@PathVariable(value = "idUsuario") String idUsuario) {
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
