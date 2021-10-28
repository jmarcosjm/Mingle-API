package com.valj.mingleapi.service;

import com.valj.mingleapi.model.IdUsuarioReceita;
import com.valj.mingleapi.model.document.Receita;
import com.valj.mingleapi.model.document.ReceitaSalva;
import com.valj.mingleapi.repository.ReceitaSalvaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReceitaSalvaService {
    private ReceitaSalvaRepository repository;

    public Optional<ReceitaSalva> encontrarPorIdUsuarioReceita(IdUsuarioReceita idUsuarioReceita){
        return repository.findByIdUsuarioReceita(idUsuarioReceita);
    }

    public void salvarReceita(ReceitaSalva receitaSalva){
        repository.insert(receitaSalva);
    }

    public void apagarReceitaSalva(IdUsuarioReceita idUsuarioReceita){
        repository.deleteByIdUsuarioReceita(idUsuarioReceita);
    }

    public List<ReceitaSalva> encontrarTodosPorIdUsuario(String _idUsuario){
        return repository.getAllByIdUsuarioReceita__idUsuario(_idUsuario);
    }
}
