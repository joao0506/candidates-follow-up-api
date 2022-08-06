package br.com.candidatesfollowup.utils;

import br.com.candidatesfollowup.domain.MotivoRetorno;

import java.util.Arrays;
import java.util.List;

public class MotivoRetornoMocks {

    public static MotivoRetorno gerarMockMotivoRetorno(){
        return new MotivoRetorno(1, "Remuneração", true);
    }

    public static List<MotivoRetorno> gerarMockListMotivosRetorno(){
        return Arrays.asList(
                new MotivoRetorno(1, "Remuneração", false),
                new MotivoRetorno(2, "Idioma", true),
                new MotivoRetorno(3, "Moeda de Pagamento", true),
                new MotivoRetorno(4, "Modalidade de Trabalho", true),
                new MotivoRetorno(5, "Sem Retorno", false)
        );
    }

}