package com.valj.mingleapi.service;

import com.valj.mingleapi.model.document.Ingrediente;
import com.valj.mingleapi.model.document.Receita;
import com.valj.mingleapi.repository.ReceitaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReceitaService {
    private ReceitaRepository repository;

    public List<Receita> getAll() {
        return repository.findAll();
    }

    public void adicionar(Receita receita){repository.insert(receita);}

    public Receita getById(String _idReceita){return repository.getReceitaBy_id(_idReceita);}
}
