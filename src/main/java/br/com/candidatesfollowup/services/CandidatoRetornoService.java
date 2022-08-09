package br.com.candidatesfollowup.services;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import br.com.candidatesfollowup.domain.dtos.CandidatoRetornoDTO;
import br.com.candidatesfollowup.exceptions.ObjectNotFoundException;
import br.com.candidatesfollowup.repositories.CandidatoRetornoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CandidatoRetornoService {

    @Autowired
    private CandidatoRetornoRepository candidatoRetornoRepository;

    @Transactional
    public CandidatoRetorno salvarCandidatoRetorno(CandidatoRetorno candidatoRetorno) {
        return candidatoRetornoRepository.save(candidatoRetorno);
    }

    public CandidatoRetorno fromDTO(CandidatoRetornoDTO candidatoRetornoDTO){
        return new CandidatoRetorno(null, candidatoRetornoDTO.getNomeCandidato().trim(), candidatoRetornoDTO.getDataRetorno(),
                true, false, candidatoRetornoDTO.getMotivoRetorno(), candidatoRetornoDTO.getTipoRetorno());
    }
}
