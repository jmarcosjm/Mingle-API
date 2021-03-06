package com.valj.mingleapi.rest;

import com.valj.mingleapi.model.document.Ingrediente;
import com.valj.mingleapi.model.document.IngredienteCadastrado;
import com.valj.mingleapi.service.IngredienteCadastradoService;
import com.valj.mingleapi.service.IngredienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredientes")
@AllArgsConstructor
public class IngredienteRest {
    private IngredienteService service;
    private IngredienteCadastradoService ingredienteCadastradoService;

    @GetMapping
    public ResponseEntity<List<Ingrediente>> getAll(@RequestParam(required = false) String nome, @RequestParam(required = false) String user) {
        System.out.println(nome);
        if (nome != null) return ResponseEntity.ok(service.findAllByNome(nome));

        if (user != null) return ResponseEntity.ok(service.findAllByNome(user));

        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{_id}")
    public ResponseEntity<Ingrediente> get(@PathVariable("_id") String _id) {
        return service.getById(_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ingrediente> adicionar(@RequestBody Ingrediente ingrediente) {
        ingrediente.setNome(ingrediente.getNome().toUpperCase());
        Optional<Ingrediente> optionalIngrediente = service.findByNome(ingrediente.getNome());
        return optionalIngrediente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(service.adicionar(ingrediente)));
    }

    @GetMapping(value = "/cadastrar/{user}")
    public ResponseEntity<List<IngredienteCadastrado>> ingredientesCadastrados(@PathVariable("user") String user) {
        return ResponseEntity.ok(ingredienteCadastradoService.getAll(user));
    }

//    @PostMapping(path = "/lista")
//    public ResponseEntity<List<Ingrediente>> adicionarVarios(@RequestBody List<Ingrediente> ingredientes) {
//        List<Ingrediente> retorno = new ArrayList<>();
//        List<Ingrediente> novosIngredientes = ingredientes.stream()
//                .filter(ingrediente -> {
//                    ingrediente.setNome(ingrediente.getNome().toUpperCase());
//                    Optional<Ingrediente> ingredienteExistente = service.findByNome(ingrediente.getNome());
//                    if (ingredienteExistente.isEmpty()) return true;
//                    else {
//                        retorno.add(ingredienteExistente.get());
//                        return false;
//                    }
//                }).collect(Collectors.toList());
//        retorno.addAll(service.adicionarVarios(novosIngredientes));
//        return ResponseEntity.ok(retorno);
//    }

    @PostMapping(path = "/lista")
    public ResponseEntity<List<Ingrediente>> adicionarVarios(@RequestBody List<Ingrediente> ingredientes) {
        List<Ingrediente> retorno = new ArrayList<>();
        ingredientes.forEach(ingrediente -> {
            ingrediente.setNome(ingrediente.getNome().toUpperCase());
            Optional<Ingrediente> ingredienteExistente = service.findByNome(ingrediente.getNome());
            if (ingredienteExistente.isEmpty()) retorno.add(service.adicionar(ingrediente));
            else {
                retorno.add(ingredienteExistente.get());
            }
        });
        return ResponseEntity.ok(retorno);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
