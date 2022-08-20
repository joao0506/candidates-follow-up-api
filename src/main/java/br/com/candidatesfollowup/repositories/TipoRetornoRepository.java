package br.com.candidatesfollowup.repositories;

import br.com.candidatesfollowup.domain.TipoRetorno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoRetornoRepository extends JpaRepository<TipoRetorno, Integer> {

    List<TipoRetorno> findAllByIsAtivoTrue();

    Page<TipoRetorno> findAllByIsAtivoFalse(Pageable pageable);

    Page<TipoRetorno> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);

    @Modifying
    @Query("UPDATE TipoRetorno t SET t.isAtivo = false WHERE t.id = ?1")
    void disableTipoDeRetorno(Integer idTipoRetorno);

    @Modifying
    @Query("UPDATE TipoRetorno t SET t.isAtivo = true WHERE t.id = ?1")
    void enableTipoRetorno(Integer idTipoRetorno);

    @Modifying
    @Query("UPDATE TipoRetorno t SET t.isAtivo = false WHERE t.isAtivo IS TRUE")
    void disableAllTipoRetorno();

    @Modifying
    @Query("UPDATE TipoRetorno t SET t.isAtivo = true WHERE t.isAtivo IS FALSE")
    void enableAllTipoRetorno();

    @Modifying
    @Query("DELETE FROM TipoRetorno t WHERE t.isAtivo IS FALSE")
    void deleteAllByIsAtivoFalse();

}
