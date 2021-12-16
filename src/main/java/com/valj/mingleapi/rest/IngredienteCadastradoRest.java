package com.valj.mingleapi.rest;

import com.valj.mingleapi.model.IngredienteUtilizado;
import com.valj.mingleapi.model.document.Ingrediente;
import com.valj.mingleapi.model.document.IngredienteCadastrado;
import com.valj.mingleapi.service.IngredienteCadastradoService;
import com.valj.mingleapi.service.IngredienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/ingrediente-cadastrado")
public class IngredienteCadastradoRest {
    private IngredienteCadastradoService service;
    private IngredienteService ingredienteService;

    @PostMapping
    public ResponseEntity<IngredienteCadastrado> adicionar(@RequestBody IngredienteCadastrado ingredienteCadastrado) {
        try {
            if(ingredienteCadastrado.getIngredienteUtilizado().getIngrediente() == null || ingredienteCadastrado.getIngredienteUtilizado().getIngrediente().getNome() == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não especificado");
            var ingrediente = ingredienteCadastrado
                    .getIngredienteUtilizado()
                    .getIngrediente();
            ingrediente.setNome(ingrediente.getNome().toUpperCase());
            Optional<Ingrediente> optionalIngrediente = ingredienteService.findByNome(ingrediente.getNome());
            if (optionalIngrediente.isEmpty()) {
                ingredienteCadastrado.getIngredienteUtilizado().setIngrediente(ingredienteService.adicionar(ingrediente));
            } else {
                ingredienteCadastrado.getIngredienteUtilizado()
                        .setIngrediente(optionalIngrediente.orElse(ingrediente));
            }

            return ResponseEntity.ok(service.adicionar(ingredienteCadastrado));
        }catch (ResponseStatusException e){
            throw e;
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.ACCEPTED, "Ingrediente ja cadastrado", e);
        }
    }

    @GetMapping(value = "/{idUsuario}")
    public ResponseEntity<List<IngredienteUtilizado>> getAll(@PathVariable("idUsuario") String idUsuario) {
        return ResponseEntity.ok(service.getAll(idUsuario).stream()
                .map(IngredienteCadastrado::getIngredienteUtilizado)
                .collect(Collectors.toList()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
