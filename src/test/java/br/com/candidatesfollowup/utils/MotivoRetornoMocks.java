package br.com.candidatesfollowup.utils;

import br.com.candidatesfollowup.domain.MotivoRetorno;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.mock.web.MockHttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class MotivoRetornoMocks {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static MotivoRetorno gerarMockMotivoRetorno(){
        return new MotivoRetorno(1, "Remuneração", true);
    }

    public static List<MotivoRetorno> gerarMockMotivoRetornoListHabilitados(){
        return Arrays.asList(
                new MotivoRetorno(1, "Idioma", true),
                new MotivoRetorno(2, "Moeda de Pagamento", true),
                new MotivoRetorno(3, "Modalidade de Trabalho", true)
        );
    }

    public static List<MotivoRetorno> gerarMockMotivoRetornoListDesabilitados(){
        return Arrays.asList(
                new MotivoRetorno(4, "Remuneração", false),
                new MotivoRetorno(5, "Sem Retorno", false)
        );
    }

    public static MotivoRetorno convertMockHttpServletResponseToMotivoRetorno(MockHttpServletResponse response) throws Exception {
        return objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<MotivoRetorno>() {});
    }

    public static List<MotivoRetorno> convertMockHttpServletResponseToListMotivoRetorno(MockHttpServletResponse response) throws Exception {
        return objectMapper.readValue(new JSONObject(response.getContentAsString(StandardCharsets.UTF_8)).getString("content"),
                new TypeReference<List<MotivoRetorno>>() {});
    }
}
