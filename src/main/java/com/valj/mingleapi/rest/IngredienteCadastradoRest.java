package com.valj.mingleapi.rest;

import com.valj.mingleapi.model.document.IngredienteCadastrado;
import com.valj.mingleapi.service.IngredienteCadastradoService;
import com.valj.mingleapi.service.IngredienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/ingrediente-cadastrado")
public class IngredienteCadastradoRest {
    private IngredienteCadastradoService service;
    private IngredienteService ingredienteService;

    @PostMapping
    public ResponseEntity<IngredienteCadastrado> adicionar(@RequestBody IngredienteCadastrado ingredienteCadastrado) {
        try {
            var ingrediente = ingredienteCadastrado
                    .getIngredienteUtilizado()
                    .getIngrediente();

            if (ingredienteService.findByNome(ingrediente.getNome()).isEmpty()) {
                ingrediente.setNome(ingrediente.getNome().toUpperCase());
                ingredienteCadastrado.getIngredienteUtilizado().setIngrediente(ingredienteService.adicionar(ingrediente));
            } else {
                ingredienteCadastrado.getIngredienteUtilizado()
                        .setIngrediente(ingredienteService
                                .findByNome(ingrediente.getNome())
                                .orElse(ingrediente));
            }

            return ResponseEntity.ok(service.adicionar(ingredienteCadastrado));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.ACCEPTED, "Ingrediente ja cadastrado", e);
        }
    }

    @GetMapping(value = "/{idUsuario}")
    public ResponseEntity<List<IngredienteCadastrado>> getAll(@PathVariable("idUsuario") String idUsuario) {
        return ResponseEntity.ok(service.getAll(idUsuario));
    }
}
