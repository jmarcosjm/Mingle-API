package com.valj.mingleapi.rest;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;
import com.valj.mingleapi.model.document.IngredienteCadastrado;
import com.valj.mingleapi.service.IngredienteCadastradoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/ingrediente-cadastrado")
public class IngredienteCadastradoRest {
    private IngredienteCadastradoService service;

    @PostMapping
    public ResponseEntity<IngredienteCadastrado> adicionar(@RequestBody IngredienteCadastrado ingredienteCadastrado){
        try{
            return ResponseEntity.ok(service.adicionar(ingredienteCadastrado));
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.ACCEPTED, "Ingrediente ja cadastrado", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<IngredienteCadastrado>> getAll(@RequestHeader String idUsuario){
        return ResponseEntity.ok(service.getAll(idUsuario));
    }
}
