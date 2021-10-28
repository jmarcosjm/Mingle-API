package com.valj.mingleapi.rest;

import com.valj.mingleapi.model.document.Usuario;
import com.valj.mingleapi.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioRest {
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }


    @PostMapping
    public ResponseEntity<List<String>> novoUsuario(@RequestBody Usuario usuario){
        boolean usernameCadastrado = service.verificarExistenciaUsuario(usuario.getUsername());
        boolean emailCadastrado = service.verificarExistenciaEmail(usuario.getEmail());
        List<String> respostas = new ArrayList<>();
        if(usernameCadastrado || emailCadastrado){
            if(usernameCadastrado) respostas.add("Username já cadastrtado");
            if(emailCadastrado) respostas.add("Email já cadastrado");
        }
        else{
            usuario.setPasswordHash(BCrypt.hashpw(usuario.getPasswordHash(), BCrypt.gensalt()));
            service.adicionar(usuario);
            respostas.add("Usuario criado com sucesso");
        }
        return ResponseEntity.ok(respostas);
    }

    @PostMapping("/login")
    public ResponseEntity<List<String>> logar(@RequestBody Usuario usuario){
        Optional<Usuario> usuarioCadastrado = service.econtraPorEmail(usuario.getEmail());
        List<String> respostas = new ArrayList<>();

        if(usuarioCadastrado.isEmpty())
        {
            respostas.add("Usuario não cadastrado");
            return ResponseEntity.ok(respostas);
        }

        String passwordHash = usuarioCadastrado.get().getPasswordHash();
        String senhaDigitada = usuario.getPasswordHash();

        if(BCrypt.checkpw(senhaDigitada,passwordHash)) respostas.add("Credenciais corretas");
        else respostas.add("Credenciais incorretas");

        return ResponseEntity.ok(respostas);
    }

}
