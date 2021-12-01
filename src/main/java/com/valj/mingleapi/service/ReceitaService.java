package com.valj.mingleapi.service;

import com.valj.mingleapi.model.document.Receita;
import com.valj.mingleapi.repository.ReceitaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReceitaService {
    private ReceitaRepository repository;

    public List<Receita> getAll() {
        return repository.findAll();
    }

    public void adicionar(Receita receita) {
        repository.insert(receita);
    }

    public Optional<Receita> getById(String _id) {
        return repository.findById(_id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
