package com.valj.mingleapi.model;

import com.valj.mingleapi.model.document.Ingrediente;
import com.valj.mingleapi.model.document.Receita;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class ReceitaResponse {
    private Receita receita;
    private String ingredientesEmComum;
    private String quantidadePossuida;
    private List<IngredienteQuantidade> ingredientesQuantidades;

    public ReceitaResponse(Receita receita, List<IngredienteUtilizado> ingredientesUsuario){
        this.receita = receita;

        Map<Ingrediente,IngredienteUtilizado> ingredientesReceita = receita.getIngredientesUtilizados().stream().collect(Collectors.toMap(IngredienteUtilizado::getIngrediente, Function.identity()));
        Map<Ingrediente,IngredienteUtilizado> ingredientesUsuarioReceita = ingredientesUsuario.stream()
                .filter(ingredienteUtilizado -> ingredientesReceita.containsKey(ingredienteUtilizado.getIngrediente()))
                .collect(Collectors.toMap(IngredienteUtilizado::getIngrediente, Function.identity()));
        //FALTA CONSEGUIR A QUANTIDADE DE CADA INGREDIENTE POSSUIDA E A QUANTIDADE TOTAL POSSUIDA PELO USUARIO COMPARANDO OS 2 MAPS

        double numeroIngredientesReceita = ingredientesReceita.size();
        double numeroIngredientesComuns = ingredientesUsuarioReceita.size();
        ingredientesEmComum = "%" + (numeroIngredientesComuns/numeroIngredientesReceita) * 100;
    }


    public void getQuantidades(){

    }
}
