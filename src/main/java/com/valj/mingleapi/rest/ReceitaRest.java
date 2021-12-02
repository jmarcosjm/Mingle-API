package com.valj.mingleapi.rest;

import com.valj.mingleapi.model.document.Ingrediente;
import com.valj.mingleapi.model.document.IngredienteCadastrado;
import com.valj.mingleapi.model.document.Receita;
import com.valj.mingleapi.model.document.ReceitaSalva;
import com.valj.mingleapi.service.IngredienteCadastradoService;
import com.valj.mingleapi.service.ReceitaSalvaService;
import com.valj.mingleapi.service.ReceitaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping(path = "/salvas")
    public ResponseEntity<List<Receita>> getAllReceitasSalvas(@RequestHeader String _idUsuario) {
        List<ReceitaSalva> receitasSalvas = receitaSalvaService.encontrarTodosPorIdUsuario(_idUsuario);
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

    @GetMapping(path = "/receitas-ingrediente-cadastrado")
    ResponseEntity<List<Receita>> getReceitasPorIngredienteCadastrado(@RequestHeader String idUsuario,
                                                                      @RequestHeader String idIngredienteCadastrado){
        IngredienteCadastrado ingredienteCadastrado = ingredienteCadastradoService.getByIdUsuarioReceita(idUsuario,idIngredienteCadastrado);
        return ResponseEntity.ok(service.getReceitaByIngrediente(ingredienteCadastrado.getIngredienteUtilizado().getIngrediente()));
    }

    @GetMapping(path = "/receitas-todos-ingrediente-cadastrado")
    ResponseEntity<List<Receita>> getReceitasPorIngredientesCadastrados(@RequestHeader String idUsuario){
        List<String> ids = ingredienteCadastradoService.getAll(idUsuario).stream()
                .map(ingredienteCadastrado -> ingredienteCadastrado.getIngredienteUtilizado().getIngrediente().get_id())
                .collect(Collectors.toList());
        return ResponseEntity.ok(service.getReceitasByIngredientes(ids));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
