package br.com.followupcandidatos.repositories;

import br.com.followupcandidatos.domain.TipoRetorno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRetornoRepository extends JpaRepository<TipoRetorno, Integer> {

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
}
