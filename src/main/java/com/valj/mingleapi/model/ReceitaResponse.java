package com.valj.mingleapi.model;

import com.valj.mingleapi.model.document.Receita;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.valj.mingleapi.constants.ConstantsUnidadeQuantidade.*;

@Data
public class ReceitaResponse {
    private Receita receita;
    private String ingredientesEmComum;
    private double quantidadeTotalPossuida;
    private List<IngredienteQuantidade> ingredientesQuantidades;

    public ReceitaResponse(Receita receita, List<IngredienteUtilizado> ingredientesUsuario) {
        this.receita = receita;
        ingredientesQuantidades = new ArrayList<>();
        Map<String, IngredienteUtilizado> ingredientesReceita = receita.getIngredientesUtilizados().stream()
                .collect(Collectors.toMap(IngredienteUtilizado::getIngredienteId, Function.identity()));
        Map<String, IngredienteUtilizado> ingredientesUsuarioReceita = ingredientesUsuario.stream()
                .filter(ingredienteUtilizado -> ingredientesReceita.containsKey(ingredienteUtilizado.getIngredienteId()))
                .collect(Collectors.toMap(IngredienteUtilizado::getIngredienteId, Function.identity()));

        int numeroIngredientesReceita = ingredientesReceita.size();
        int numeroIngredientesComuns = ingredientesUsuarioReceita.size();
        ingredientesEmComum = numeroIngredientesComuns + "/" + numeroIngredientesReceita;
        setQuantidades(ingredientesReceita, ingredientesUsuarioReceita);
    }

    public void setQuantidades(Map<String, IngredienteUtilizado> ingredientesReceita, Map<String, IngredienteUtilizado> ingredientesUsuarioReceita) {
        List<Double> quantidadesTotais = Arrays.asList(0.0, 0.0);
        for (Map.Entry<String, IngredienteUtilizado> entry : ingredientesReceita.entrySet()) {
            List<IngredienteUtilizado> ingredienteUtilizadosTratamento = new ArrayList<>();
            ingredienteUtilizadosTratamento.add(entry.getValue());
            if (ingredientesUsuarioReceita.containsKey(entry.getKey()))
                ingredienteUtilizadosTratamento.add(ingredientesUsuarioReceita.get(entry.getKey()));
            boolean isMesmaClasseUnidade = isMesmaUnidade(ingredienteUtilizadosTratamento);
            List<Double> quantidades = tratarUnidades(ingredienteUtilizadosTratamento, isMesmaClasseUnidade);
            double porcentagemQuantidade = -1.0;
            if (isMesmaClasseUnidade) {
                porcentagemQuantidade = (quantidades.get(POSICAO_USUARIO) / quantidades.get(POSICAO_RECEITA));
                if (porcentagemQuantidade > 1.0) porcentagemQuantidade = 1.0;
                if (quantidades.get(POSICAO_USUARIO) > quantidades.get(POSICAO_RECEITA))
                    quantidades.set(POSICAO_USUARIO, quantidades.get(POSICAO_RECEITA));
                quantidadesTotais.set(POSICAO_USUARIO, quantidadesTotais.get(POSICAO_USUARIO) + quantidades.get(POSICAO_USUARIO));
            }
            ingredientesQuantidades.add(new IngredienteQuantidade(ingredienteUtilizadosTratamento.get(0).getIngrediente(), porcentagemQuantidade));
            quantidadesTotais.set(POSICAO_RECEITA, quantidadesTotais.get(POSICAO_RECEITA) + quantidades.get(POSICAO_RECEITA));
        }
        quantidadeTotalPossuida = quantidadesTotais.get(POSICAO_USUARIO) / quantidadesTotais.get(POSICAO_RECEITA);
    }

    public List<Double> tratarUnidades(List<IngredienteUtilizado> ingredienteUtilizadosTratamento, boolean isMesmaClasseUnidade) {
        List<Double> quantidades = new ArrayList<>();
        int limite = 0;
        if (isMesmaClasseUnidade) limite = 1;
        for (int i = 0; i <= limite; i++) {
            IngredienteUtilizado ingredienteUtilizado = ingredienteUtilizadosTratamento.get(i);
            switch (ingredienteUtilizado.getUnidade()) {
                case LITROS:
                case QUILOS:
                    quantidades.add(ingredienteUtilizado.getQuantidade() * 1000);
                    break;
                case MILILITROS:
                case UNIDADES:
                case GRAMAS:
                    quantidades.add(ingredienteUtilizado.getQuantidade());
                    break;
                case COLHERES_DE_SOPA:
                    quantidades.add(ingredienteUtilizado.getQuantidade() * 15);
                    break;
                case COLHERES_DE_CHA:
                    quantidades.add(ingredienteUtilizado.getQuantidade() * 5);
                    break;
            }
        }
        return quantidades;
    }

    public boolean isMesmaUnidade(List<IngredienteUtilizado> ingredienteUtilizadosTratamento) {
        String[] unidadeMassa = {QUILOS, GRAMAS};
        String[] unidadeVolume = {LITROS, MILILITROS, COLHERES_DE_CHA, COLHERES_DE_SOPA};
        List<String> volumes = Arrays.asList(unidadeMassa);
        boolean isMassa = volumes.containsAll(
                ingredienteUtilizadosTratamento.stream()
                        .map(IngredienteUtilizado::getUnidade)
                        .collect(Collectors.toList())
        );
        volumes = Arrays.asList(unidadeVolume);
        boolean isVolume = volumes.containsAll(
                ingredienteUtilizadosTratamento.stream()
                        .map(IngredienteUtilizado::getUnidade)
                        .collect(Collectors.toList())
        );
        boolean isUnidade = ingredienteUtilizadosTratamento.stream()
                .map(IngredienteUtilizado::getUnidade)
                .allMatch(unidade -> unidade.equals(UNIDADES));
        return (isUnidade || isMassa || isVolume) && ingredienteUtilizadosTratamento.size() == 2;
    }
}