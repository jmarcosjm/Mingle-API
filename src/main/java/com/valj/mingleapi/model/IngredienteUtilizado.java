package com.valj.mingleapi.model;

import com.valj.mingleapi.model.document.Ingrediente;
import lombok.Data;

@Data
public class IngredienteUtilizado {
    private Ingrediente ingrediente;
    private int quantidade;
    private String unidade;
}
