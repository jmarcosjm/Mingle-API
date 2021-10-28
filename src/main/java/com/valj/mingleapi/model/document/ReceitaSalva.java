package com.valj.mingleapi.model.document;

import com.valj.mingleapi.model.IdUsuarioReceita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "receitasSalvas")
@Data
@AllArgsConstructor
public class ReceitaSalva {
    @Id
    private String _id;
    @NonNull
    @Indexed(unique = true)
    private IdUsuarioReceita idUsuarioReceita;
}
