package br.com.candidatesfollowup.repositories;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoRetornoRepository extends JpaRepository<CandidatoRetorno, Integer> {
}
