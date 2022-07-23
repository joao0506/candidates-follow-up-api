package br.com.followupcandidatos.repositories;

import br.com.followupcandidatos.domain.MotivoRetorno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotivoRetornoRepository extends JpaRepository<MotivoRetorno, Integer> {

    Page<MotivoRetorno> findAll(Pageable pageable);

    Optional<MotivoRetorno> findById(Integer idMotivoDeRetorno);

}
