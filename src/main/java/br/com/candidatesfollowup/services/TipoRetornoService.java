package br.com.candidatesfollowup.services;

import br.com.candidatesfollowup.domain.TipoRetorno;
import br.com.candidatesfollowup.domain.dtos.TipoRetornoDTO;
import br.com.candidatesfollowup.exceptions.ObjectNotFoundException;
import br.com.candidatesfollowup.repositories.TipoRetornoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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

    public List<TipoRetorno> buscarTodosTiposDeRetornoHabilitados() {
        return tipoRetornoRepository.findAllByIsAtivoTrueOrderByIdAsc();
    }

    public Page<TipoRetorno> buscarTodosTiposDeRetornoDesabilitados(Integer page, Integer linesPerPage) {
        Pageable pageable = PageRequest.of(page, linesPerPage);
        return tipoRetornoRepository.findAllByIsAtivoFalse(pageable);
    }

    public List<TipoRetorno> buscarTipoDeRetornoPorDescricao(String descricao) {
        return tipoRetornoRepository.findByDescricaoContainingIgnoreCase(descricao);
    }

    public TipoRetorno fromDTO(TipoRetornoDTO tipoRetornoDTO) {
        return new TipoRetorno(null, tipoRetornoDTO.getDescricao().trim(), true);
    }
}
