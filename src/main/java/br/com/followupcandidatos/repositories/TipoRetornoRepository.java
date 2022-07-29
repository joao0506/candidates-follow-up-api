package br.com.followupcandidatos.repositories;

import br.com.followupcandidatos.domain.TipoRetorno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRetornoRepository extends JpaRepository<TipoRetorno, Integer> {
}
