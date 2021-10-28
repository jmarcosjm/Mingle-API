package com.valj.mingleapi.model.document;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ingredientes")
@Data
@Builder
@AllArgsConstructor
public class Ingrediente {
    @Id
    private String _id;
    @NonNull
    private String nome;
}
