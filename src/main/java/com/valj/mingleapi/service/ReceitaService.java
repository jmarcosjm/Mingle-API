package com.valj.mingleapi.service;

import com.valj.mingleapi.model.IngredienteUtilizado;
import com.valj.mingleapi.model.ReceitaResponse;
import com.valj.mingleapi.model.document.Ingrediente;
import com.valj.mingleapi.model.document.IngredienteCadastrado;
import com.valj.mingleapi.model.document.Receita;
import com.valj.mingleapi.repository.ReceitaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReceitaService {

    private MongoTemplate mongoTemplate;

    private ReceitaRepository repository;

    public List<Receita> getAll() {
        return repository.findAll();
    }

    public void adicionar(Receita receita) {
        repository.insert(receita);
    }

    public Optional<Receita> getById(String _id) {
        return repository.findById(_id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<Receita> getReceitaByIngrediente(Ingrediente ingrediente){
        return repository.findDistinctAllByIngredientesUtilizados_Ingrediente__id(ingrediente.get_id());
    }

//    public List<Receita> getReceitasByIngredientes(List<String> ids){
//        return repository.findDistinctAllByIngredientesUtilizados_Ingrediente__idIn(ids);
//    }


    public List<ReceitaResponse> getReceitasByIngredientes(List<IngredienteUtilizado> ingredientesUtilizados){
        List<Receita> retorno = new ArrayList<>();

        ingredientesUtilizados.forEach(ingredienteUtilizado -> {
//            Criteria criterio = Criteria
//                    .where("ingredientesUtilizados.ingrediente._id").is(ingredienteUtilizado.getIngrediente().get_id())
//                    .and("ingredientesUtilizados.unidade").in(getUnidades(ingredienteUtilizado.getUnidade()))
//                    .and("ingredientesUtilizados.quantidade").gte(ingredienteUtilizado.getQuantidade());

            Criteria criterio = Criteria
                    .where("ingredientesUtilizados")
                    .elemMatch(Criteria
                            .where("ingrediente._id").is(ingredienteUtilizado.getIngrediente().get_id())
                            .and("unidade").in(ingredienteUtilizado.getUnidade()));

            Query query = new Query();
            query.addCriteria(criterio);
            retorno.addAll(mongoTemplate.find(query,Receita.class,"receitas"));
        });
        return retorno.stream()
                .distinct()
                .map(receita -> new ReceitaResponse(receita, ingredientesUtilizados))
                .collect(Collectors.toList());
    }

    public List<String> getUnidades(String unidade){
        List<String> unidades = new ArrayList<>();
        switch (unidade){
            case "Gramas":
                unidades.add("Gramas");
                unidades.add("kg");
                unidades.add("unidades");
                break;
            case "kg":
                unidades.add("kg");
                unidades.add("unidades");
                break;
            case "unidades":
                unidades.add("unidades");
                break;
        }
        return unidades;
    }
}
