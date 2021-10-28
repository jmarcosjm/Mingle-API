package com.valj.mingleapi.rest;

import com.valj.mingleapi.model.IdUsuarioReceita;
import com.valj.mingleapi.model.document.ReceitaSalva;
import com.valj.mingleapi.service.ReceitaSalvaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/salvar-receita")
public class ReceitaSalvaRest {
    ReceitaSalvaService service;


}
