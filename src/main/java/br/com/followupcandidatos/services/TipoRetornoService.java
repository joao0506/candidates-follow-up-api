package br.com.followupcandidatos.services;

import br.com.followupcandidatos.domain.TipoRetorno;
import br.com.followupcandidatos.domain.dtos.TipoRetornoDTO;
import br.com.followupcandidatos.exceptions.ObjectNotFoundException;
import br.com.followupcandidatos.repositories.TipoRetornoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TipoRetornoService {

    @Autowired
    private TipoRetornoRepository tipoRetornoRepository;

    @Transactional
    public TipoRetorno salvarTipoDeRetorno(TipoRetorno tipoRetorno) {
        return tipoRetornoRepository.save(tipoRetorno);
    }

    @Transactional
    public void desabilitarTipoDeRetorno(Integer idTipoRetorno) {
        tipoRetornoRepository.disableTipoDeRetorno(idTipoRetorno);
    }

    @Transactional
    public TipoRetorno buscarTipoDeRetornoPorId(Integer idTipoRetorno) throws ObjectNotFoundException {
        Optional<TipoRetorno> tipoRetorno = tipoRetornoRepository.findById(idTipoRetorno);
        return tipoRetorno.orElseThrow(() -> new ObjectNotFoundException("Tipo De Retorno não encontrado!"));
    }

    public TipoRetorno fromDTO(TipoRetornoDTO tipoRetornoDTO) {
        return new TipoRetorno(null, tipoRetornoDTO.getDescricao(), true);
    }
}
