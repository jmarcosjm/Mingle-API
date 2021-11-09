package com.valj.mingleapi.rest;

import com.valj.mingleapi.model.document.Ingrediente;
import com.valj.mingleapi.service.IngredienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ingredientes")
@AllArgsConstructor
public class IngredienteRest {
    private IngredienteService service;

    @GetMapping
    public ResponseEntity<List<Ingrediente>> getAll(){return ResponseEntity.ok(service.getAll());}

    @PostMapping
    public ResponseEntity<Ingrediente> adicionar(@RequestBody Ingrediente ingrediente){
        ingrediente.setNome(ingrediente.getNome().toUpperCase());
        Optional<Ingrediente> optionalIngrediente = service.findByNome(ingrediente.getNome());
        return optionalIngrediente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(service.adicionar(ingrediente)));
    }

    @PostMapping(path = "/lista")
    public ResponseEntity<List<Ingrediente>> adicionarVarios(@RequestBody List<Ingrediente> ingredientes){
        List<Ingrediente> retorno = new ArrayList<>();
        List<Ingrediente> novosIngredientes = ingredientes.stream()
                .filter(ingrediente -> {
                    ingrediente.setNome(ingrediente.getNome().toUpperCase());
                    Optional<Ingrediente> ingredienteExistente = service.findByNome(ingrediente.getNome());
                    if(ingredienteExistente.isEmpty()) return true;
                    else{
                        retorno.add(ingredienteExistente.get());
                        return false;
                    }
                }).collect(Collectors.toList());
        retorno.addAll(service.adicionarVarios(novosIngredientes));
        return ResponseEntity.ok(retorno);
    }
}