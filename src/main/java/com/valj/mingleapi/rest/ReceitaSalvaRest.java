package com.valj.mingleapi.rest;

import com.valj.mingleapi.service.ReceitaSalvaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/salvar-receita")
public class ReceitaSalvaRest {
    ReceitaSalvaService service;

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
