package com.valj.mingleapi.model;

import com.valj.mingleapi.model.document.Ingrediente;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredienteQuantidade {
    private Ingrediente ingrediente;
    private double quantidadePossuida;
}
