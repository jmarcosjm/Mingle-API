package com.valj.mingleapi.service;

import com.valj.mingleapi.model.document.IngredienteCadastrado;
import com.valj.mingleapi.repository.IngredienteCadastradoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IngredienteCadastradoService {
    private IngredienteCadastradoRepository repository;

    public IngredienteCadastrado adicionar(IngredienteCadastrado ingredienteCadastrado) {
        return repository.insert(ingredienteCadastrado);
    }

    public List<IngredienteCadastrado> getAll() {
        return repository.findAll();
    }

    public List<IngredienteCadastrado> getAll(String idUsuario) {
        return repository.findAllByIdUsuario(idUsuario);
    }

    public List<IngredienteCadastrado> getAllNotFrom(String idUsuario) {
        return repository.findAllByIdUsuarioNot(idUsuario);
    }

    public IngredienteCadastrado getByIdUsuario(String _id) {
        return repository.findBy_id(_id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
