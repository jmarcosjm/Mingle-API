package com.valj.mingleapi.repository;

import com.valj.mingleapi.model.document.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUsername(String username);
}
