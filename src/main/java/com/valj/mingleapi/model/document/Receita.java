package com.valj.mingleapi.model.document;

import com.mongodb.lang.NonNull;
import com.valj.mingleapi.model.IngredientesUtilizados;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "receitas")
@Data
@AllArgsConstructor
public class Receita {
    @Id
    private String _id;
    @NonNull
    private String nome;
    @NonNull
    private List<IngredientesUtilizados> ingredientesUtilizados;
    @NonNull
    private List<String> preparo;
    private String _idCriador;
    private String imagem;
}
