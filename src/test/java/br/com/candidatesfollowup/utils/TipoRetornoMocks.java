package br.com.candidatesfollowup.utils;

import br.com.candidatesfollowup.domain.TipoRetorno;

public class TipoRetornoMocks {

    public static TipoRetorno gerarMockTipoRetorno(){
        return new TipoRetorno(1, "InMail", true);
    }
}
