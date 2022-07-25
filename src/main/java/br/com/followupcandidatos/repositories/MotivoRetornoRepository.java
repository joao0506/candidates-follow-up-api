package br.com.followupcandidatos.repositories;

import br.com.followupcandidatos.domain.MotivoRetorno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotivoRetornoRepository extends JpaRepository<MotivoRetorno, Integer> {

    Optional<MotivoRetorno> findById(Integer idMotivoDeRetorno);

    Page<MotivoRetorno> findAll(Pageable pageable);

    Page<MotivoRetorno> findByDescricao(String descricaoMotivoRetorno, Pageable pageable);

    @Query(value = "SELECT m FROM MotivoRetorno m WHERE m.isAtivo IS FALSE")
    Page<MotivoRetorno> findAllMotivosDeRetornoDisabled(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE MotivoRetorno m SET m.isAtivo = false WHERE m.id = ?1")
    void disableMotivoRetorno(Integer idMotivoRetorno);

    @Modifying
    @Query(value = "UPDATE MotivoRetorno m SET m.isAtivo = true WHERE m.id = ?1")
    void enableMotivoRetorno(Integer idMotivoRetorno);

}
