package com.valj.mingleapi.repository;

import com.valj.mingleapi.model.document.Ingrediente;
import com.valj.mingleapi.model.document.Receita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends MongoRepository<Receita, String> {
    Receita getReceitaBy_id(String _id);

    List<Receita> findDistinctAllByIngredientesUtilizados_Ingrediente__idIn(List<String> ids);

    List<Receita> findDistinctAllByIngredientesUtilizados_Ingrediente__id(String _id);
}
