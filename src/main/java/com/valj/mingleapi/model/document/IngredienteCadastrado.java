package com.valj.mingleapi.model.document;

import com.valj.mingleapi.model.IngredienteUtilizado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@Data
public class IngredienteCadastrado {
    @Id
    private String _id;
    @NonNull
    private String idUsuario;
    @NonNull
    @Indexed(unique = true)
    private IngredienteUtilizado ingredienteUtilizado;
}
