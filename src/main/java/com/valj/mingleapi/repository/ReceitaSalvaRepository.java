package com.valj.mingleapi.repository;

import com.valj.mingleapi.model.IdUsuarioReceita;
import com.valj.mingleapi.model.document.ReceitaSalva;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceitaSalvaRepository extends MongoRepository<ReceitaSalva, String> {

    Optional<ReceitaSalva> findByIdUsuarioReceita(IdUsuarioReceita idUsuarioReceita);

    void deleteByIdUsuarioReceita(IdUsuarioReceita idUsuarioReceita);

    List<ReceitaSalva> getAllByIdUsuarioReceita__idUsuario(String _idUsuario);
}
