package br.com.followupcandidatos.services;

import br.com.followupcandidatos.domain.TipoRetorno;
import br.com.followupcandidatos.domain.dtos.TipoRetornoDTO;
import br.com.followupcandidatos.exceptions.ObjectNotFoundException;
import br.com.followupcandidatos.repositories.TipoRetornoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public void desabilitarTodosTipoDeRetorno() {
        tipoRetornoRepository.disableAllTipoRetorno();
    }

    @Transactional
    public void habilitarTipoDeRetorno(Integer idTipoRetorno) {
        tipoRetornoRepository.enableTipoRetorno(idTipoRetorno);
    }
    @Transactional
    public void habilitarTodosTipoDeRetorno() {
        tipoRetornoRepository.enableAllTipoRetorno();
    }

    @Transactional
    public void deletarTipoDeRetorno(Integer idTipoRetorno) {
        tipoRetornoRepository.delete(buscarTipoDeRetornoPorId(idTipoRetorno));
    }

    @Transactional
    public void deletarTiposDeRetornoDesabilitados() {
        tipoRetornoRepository.deleteAllByIsAtivoFalse();
    }

    public TipoRetorno buscarTipoDeRetornoPorId(Integer idTipoRetorno) throws ObjectNotFoundException {
        Optional<TipoRetorno> tipoRetorno = tipoRetornoRepository.findById(idTipoRetorno);
        return tipoRetorno.orElseThrow(() -> new ObjectNotFoundException("Tipo De Retorno não encontrado!"));
    }

    public Page<TipoRetorno> buscarTodosTiposDeRetornoHabilitados(Integer page, Integer linesPerPage) {
        Pageable pageable = PageRequest.of(page, linesPerPage);
        return tipoRetornoRepository.findAllByIsAtivoTrue(pageable);
    }

    public Page<TipoRetorno> buscarTipoDeRetornoPorDescricao(String descricao, Integer page, Integer linesPerPage) {
        Pageable pageable = PageRequest.of(page, linesPerPage);
        return tipoRetornoRepository.findByDescricaoContainingIgnoreCase(descricao, pageable);
    }

    public TipoRetorno fromDTO(TipoRetornoDTO tipoRetornoDTO) {
        return new TipoRetorno(null, tipoRetornoDTO.getDescricao().trim(), true);
    }
}
