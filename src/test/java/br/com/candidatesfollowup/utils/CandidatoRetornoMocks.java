package br.com.candidatesfollowup.utils;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import br.com.candidatesfollowup.domain.MotivoRetorno;
import br.com.candidatesfollowup.domain.TipoRetorno;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.mock.web.MockHttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CandidatoRetornoMocks {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static CandidatoRetorno gerarMockCandidatoRetorno(){
        return new CandidatoRetorno(1, "José da Silva", LocalDate.of(2022,8,8),
                true, false, Arrays.asList(MotivoRetornoMocks.gerarMockMotivoRetorno()), TipoRetornoMocks.gerarMockTipoRetorno());
    }

    public static List<CandidatoRetorno> gerarMockListCandidatoRetornoHabilitados(){
        return Arrays.asList(
                new CandidatoRetorno(1, "José da Silva", LocalDate.of(2022,8,8),
                true, true, null, null),
                new CandidatoRetorno(2, "Maria das Flores", LocalDate.of(2022,8,12),
                        true, false, null, null),
                new CandidatoRetorno(3, "Aroldo Oliveira", LocalDate.of(2022,8,11),
                        true, false, null, null)
                );
    }

    public static List<CandidatoRetorno> gerarMockListCandidatoRetornoDesabilitados(){
        return Arrays.asList(
                new CandidatoRetorno(4, "Vicente Nunes", LocalDate.of(2022,8,15),
                        false, true, null, null),
                new CandidatoRetorno(5, "Eduarda Ramos", LocalDate.of(2022,9,10),
                        false, false, null, null),
                new CandidatoRetorno(6, "Stephany Campos", LocalDate.of(2022,9,6),
                        false, false, null,null)
        );
    }

    public static JSONObject convertMockHttpServletResponseJsonObject(MockHttpServletResponse response) throws Exception {
        return new JSONObject(response.getContentAsString(StandardCharsets.UTF_8));
    }

    public static JSONArray convertMockHttpServletResponseJson(MockHttpServletResponse response) throws Exception {
        return new JSONArray(new JSONObject(response.getContentAsString(StandardCharsets.UTF_8)).getString("content"));
    }

}
