package com.valj.mingleapi.model.document;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ingredientes_possuidos")
@Data
@AllArgsConstructor
public class IngredientePossuido {
    @Id
    private String _id;
    @NonNull
    private String user;
    @NonNull
    private String ingrediente;
}