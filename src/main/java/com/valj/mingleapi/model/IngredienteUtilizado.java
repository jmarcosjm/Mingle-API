package com.valj.mingleapi.model;

import com.valj.mingleapi.model.document.Ingrediente;
import lombok.Data;

import java.util.Objects;

@Data
public class IngredienteUtilizado {
    private Ingrediente ingrediente;
    private double quantidade;
    private String unidade;

    public String getIngredienteId(){
        return getIngrediente().get_id();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredienteUtilizado that = (IngredienteUtilizado) o;
        return ingrediente.getNome().equals(that.ingrediente.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingrediente);
    }
}
