package com.valj.mingleapi.service;

import com.valj.mingleapi.model.document.Usuario;
import com.valj.mingleapi.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    UsuarioRepository repository;

    public boolean verificarExistenciaEmail(String email) {
        return encontraPorEmail(email).isPresent();
    }

    public boolean verificarExistenciaUsuario(String username) {
        return repository.findByUsername(username).isPresent();
    }

    public Optional<Usuario> encontraPorEmail(String email) {
        return repository.findByEmail(email);
    }

    public void adicionar(Usuario usuario) {
        repository.insert(usuario);
    }

    public List<Usuario> getAll() {
        return repository.findAll();
    }
}
