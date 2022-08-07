package br.com.candidatesfollowup.utils;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import br.com.candidatesfollowup.domain.TipoRetorno;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.mock.web.MockHttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;

public class CandidatoRetornoMocks {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static CandidatoRetorno gerarMockCandidatoRetorno(){
        return new CandidatoRetorno(1, "José da Silva", LocalDate.of(2022,8,8),
                true, false, Arrays.asList(MotivoRetornoMocks.gerarMockMotivoRetorno()), TipoRetornoMocks.gerarMockTipoRetorno());
    }

    public static CandidatoRetorno convertMockHttpServletResponseToCandidatoRetorno(MockHttpServletResponse response) throws Exception {
        return objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<CandidatoRetorno>() {});
    }

}
