package com.valj.mingleapi.service;

import com.valj.mingleapi.model.IngredienteUtilizado;
import com.valj.mingleapi.model.document.Receita;
import com.valj.mingleapi.repository.ReceitaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReceitaService {

    private ReceitaRepository repository;

    public List<Receita> getAll() {
        return repository.findAll();
    }

    public List<Receita> getAllByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
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

    public List<Receita> encontrarTodosPorIdUsuario(String idUsuario) {
        return repository.findAllBy_idCriador(idUsuario);
    }

    public List<Receita> getReceitaByIngrediente(IngredienteUtilizado ingredienteBusca, List<IngredienteUtilizado> ingredientesUsuario) {
        return repository.findAllByIngredientesUtilizados_Ingrediente__id(ingredienteBusca.getIngrediente().get_id());
    }


    public List<Receita> getReceitasByIngredientes(List<IngredienteUtilizado> ingredientesUsuario) {
        List<Receita> retorno = new ArrayList<>();

        ingredientesUsuario.forEach(ingredienteUtilizado -> retorno.addAll(repository.findAllByIngredientesUtilizados_Ingrediente_nomeIgnoreCase(ingredienteUtilizado.getIngrediente().getNome())));
        return retorno;
    }
}
