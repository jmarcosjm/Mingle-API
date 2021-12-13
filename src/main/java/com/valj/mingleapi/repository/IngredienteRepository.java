package com.valj.mingleapi.repository;

import com.valj.mingleapi.model.document.Ingrediente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredienteRepository extends MongoRepository<Ingrediente, String> {
    Optional<Ingrediente> findIngredienteByNome(String nome);

    Optional<Ingrediente> findIngredienteByNomeIgnoreCase(String nome);

    List<Ingrediente> findByNomeContainingIgnoreCase(String nome);
}
