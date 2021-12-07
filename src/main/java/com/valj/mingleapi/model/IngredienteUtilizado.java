package com.valj.mingleapi.model;

import com.valj.mingleapi.model.document.Ingrediente;
import lombok.Data;

@Data
public class IngredienteUtilizado {
    private Ingrediente ingrediente;
    private double quantidade;
    private String unidade;

    public String getIngredienteId(){
        return getIngrediente().get_id();
    }
}
