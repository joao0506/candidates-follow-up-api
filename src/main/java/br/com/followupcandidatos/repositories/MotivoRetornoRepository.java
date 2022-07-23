package br.com.followupcandidatos.repositories;

import br.com.followupcandidatos.domain.MotivoRetorno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotivoRetornoRepository extends JpaRepository<MotivoRetorno, Integer> {
}
