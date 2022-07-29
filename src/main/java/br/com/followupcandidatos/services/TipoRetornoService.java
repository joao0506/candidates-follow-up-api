package br.com.followupcandidatos.services;

import br.com.followupcandidatos.domain.TipoRetorno;
import br.com.followupcandidatos.domain.dtos.TipoRetornoDTO;
import br.com.followupcandidatos.repositories.TipoRetornoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TipoRetornoService {

    @Autowired
    private TipoRetornoRepository tipoRetornoRepository;

    @Transactional
    public TipoRetorno salvarTipoDeRetorno(TipoRetorno tipoRetorno) {
        return tipoRetornoRepository.save(tipoRetorno);
    }

    public TipoRetorno fromDTO(TipoRetornoDTO tipoRetornoDTO) {
        return new TipoRetorno(null, tipoRetornoDTO.getDescricao(), true);
    }
}
