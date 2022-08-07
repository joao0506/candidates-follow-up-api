package br.com.candidatesfollowup.utils;

import br.com.candidatesfollowup.domain.TipoRetorno;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.mock.web.MockHttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class TipoRetornoMocks {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static TipoRetorno gerarMockTipoRetorno(){
        return new TipoRetorno(1, "InMail", true);
    }

    public static List<TipoRetorno> gerarMockTipoRetornoListHabilitados(){
        return Arrays.asList(
                new TipoRetorno(1, "InMail", true),
                new TipoRetorno(2, "InBox", true),
                new TipoRetorno(3, "LinkedIn", true)
        );
    }

    public static List<TipoRetorno> gerarMockTipoRetornoListDesabilitados(){
        return Arrays.asList(
                new TipoRetorno(1, "E-mail", false)
        );
    }

    public static TipoRetorno convertMockHttpServletResponseToTipoRetorno(MockHttpServletResponse response) throws Exception {
        return objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<TipoRetorno>() {});
    }

    public static List<TipoRetorno> convertMockHttpServletResponseToListTipoRetorno(MockHttpServletResponse response) throws Exception {
        return objectMapper.readValue(new JSONObject(response.getContentAsString(StandardCharsets.UTF_8)).getString("content"),
                new TypeReference<List<TipoRetorno>>() {});
    }
}
