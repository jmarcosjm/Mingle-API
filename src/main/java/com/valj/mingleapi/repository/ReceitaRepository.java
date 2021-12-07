package com.valj.mingleapi.repository;

import com.valj.mingleapi.model.document.Ingrediente;
import com.valj.mingleapi.model.document.Receita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends MongoRepository<Receita, String> {
    Receita getReceitaBy_id(String _id);
    List<Receita> findAllByIngredientesUtilizados_Ingrediente__id(String _id);
}
