package com.valj.mingleapi.model;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class IdUsuarioReceita {
    @NonNull
    String _idUsuario;
    @NonNull
    String _idReceita;
}
