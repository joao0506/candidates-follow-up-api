package br.com.candidatesfollowup.repositories;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import br.com.candidatesfollowup.domain.TipoRetorno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

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

    @Modifying
    @Query("UPDATE CandidatoRetorno c SET c.isCandidatoContatado = TRUE WHERE c.id = ?1")
    void candidatoContatado(Integer idCandidatoRetorno);

    @Modifying
    @Query("UPDATE CandidatoRetorno c SET c.isCandidatoContatado = FALSE WHERE c.id = ?1")
    void candidatoNaoContatado(Integer idCandidatoRetorno);

    void deleteAllByIsAtivoFalse();

    List<CandidatoRetorno> findAllByIsAtivoTrueOrderByIdAsc();

    Page<CandidatoRetorno> findAllByIsAtivoFalse(Pageable pageRequest);

    List<CandidatoRetorno> findAllByIsAtivoTrueAndDataRetorno(LocalDate data);

    List<CandidatoRetorno> findAllByIsAtivoTrueAndNomeCandidatoContainingIgnoreCaseAndDataRetorno(String nomeCandidato, LocalDate data);

    List<CandidatoRetorno> findAllByIsAtivoTrueAndNomeCandidatoContainingIgnoreCase(String nomeCandidato);

    List<CandidatoRetorno> findAllByIsAtivoTrueAndTipoRetorno(TipoRetorno tipoRetorno);
}
