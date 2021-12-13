package com.valj.mingleapi.service;

import com.valj.mingleapi.model.document.Ingrediente;
import com.valj.mingleapi.repository.IngredienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IngredienteService {
    private IngredienteRepository repository;

    public Optional<Ingrediente> getById(String _id) {
        return repository.findById(_id);
    }

    public Optional<Ingrediente> findByNome(String nome) {
        return repository.findIngredienteByNomeIgnoreCase(nome);
    }

    public List<Ingrediente> findAllByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Ingrediente> getAll() {
        return repository.findAll();
    }

    public Ingrediente adicionar(Ingrediente ingrediente) {
        return repository.insert(ingrediente);
    }

    public List<Ingrediente> adicionarVarios(List<Ingrediente> ingredientes) {
        return repository.insert(ingredientes);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
