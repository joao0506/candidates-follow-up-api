package br.com.candidatesfollowup.services;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import br.com.candidatesfollowup.repositories.CandidatoRetornoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidatoRetornoService {

    @Autowired
    private CandidatoRetornoRepository candidatoRetornoRepository;

}
