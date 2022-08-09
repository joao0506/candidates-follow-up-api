package br.com.candidatesfollowup.repositories;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoRetornoRepository extends JpaRepository<CandidatoRetorno, Integer> {

    @Modifying
    @Query("UPDATE CandidatoRetorno c SET c.isAtivo = FALSE WHERE c.isAtivo IS TRUE")
    void disableAllCandidatoRetornoByIsAtivoTrue();

    @Modifying
    @Query("UPDATE CandidatoRetorno c SET c.isAtivo = FALSE WHERE c.id = ?1")
    void disableCandidatoRetorno(Integer idCandidatoRetorno);

    @Modifying
    @Query("UPDATE CandidatoRetorno c SET c.isAtivo = TRUE WHERE c.isAtivo IS FALSE")
    void enableAllCandidatoRetornoByIsAtivoFalse();

    @Modifying
    @Query("UPDATE CandidatoRetorno c SET c.isAtivo = TRUE WHERE c.id = ?1")
    void enableCandidatoRetorno(Integer idCandidatoRetorno);

}
