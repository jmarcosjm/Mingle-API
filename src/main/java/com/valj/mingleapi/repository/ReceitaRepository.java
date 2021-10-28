package com.valj.mingleapi.repository;

import com.valj.mingleapi.model.document.Receita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaRepository extends MongoRepository<Receita, String> {
    Receita getReceitaBy_id(String _id);
}
