package br.com.candidatesfollowup.repositories;

import br.com.candidatesfollowup.domain.MotivoRetorno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MotivoRetornoRepository extends JpaRepository<MotivoRetorno, Integer> {

    Page<MotivoRetorno> findAllByIsAtivoTrue(Pageable pageable);

    Page<MotivoRetorno> findAllByIsAtivoFalse(Pageable pageable);

    Page<MotivoRetorno> findByDescricaoContainingIgnoreCase(String descricaoMotivoRetorno, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE MotivoRetorno m SET m.isAtivo = false WHERE m.id = ?1")
    void disableMotivoRetorno(Integer idMotivoRetorno);

    @Modifying
    @Query(value = "UPDATE MotivoRetorno m SET m.isAtivo = false WHERE m.isAtivo IS TRUE")
    void disableAllMotivoRetorno();

    @Modifying
    @Query(value = "UPDATE MotivoRetorno m SET m.isAtivo = true WHERE m.id = ?1")
    void enableMotivoRetorno(Integer idMotivoRetorno);

    @Modifying
    @Query(value = "UPDATE MotivoRetorno m SET m.isAtivo = true WHERE m.isAtivo IS FALSE")
    void enableAllMotivoRetorno();

    @Modifying
    @Query(value = "DELETE FROM MotivoRetorno m WHERE m.isAtivo IS FALSE")
    void deleteAllMotivoRetornoDisabled();

}
