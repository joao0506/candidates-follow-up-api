package br.com.candidatesfollowup.resources;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import br.com.candidatesfollowup.services.CandidatoRetornoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidato-retorno")
public class CandidatoRetornoResource {

    @Autowired
    private CandidatoRetornoService candidatoRetornoService;

}
