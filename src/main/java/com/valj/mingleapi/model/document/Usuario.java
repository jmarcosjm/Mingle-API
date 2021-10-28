package com.valj.mingleapi.model.document;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
@Data
@AllArgsConstructor
public class Usuario {
    @Id
    private String _id;
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String passwordHash;
}
