package com.valj.mingleapi.repository;

import com.valj.mingleapi.model.document.IngredienteCadastrado;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredienteCadastradoRepository extends MongoRepository<IngredienteCadastrado, String> {
    List<IngredienteCadastrado> findAllByIdUsuario(String idUsuario);
    IngredienteCadastrado findBy_id(String _id);
}
