package br.com.followupcandidatos.services;

import br.com.followupcandidatos.domain.MotivoRetorno;
import br.com.followupcandidatos.domain.dtos.MotivoRetornoDTO;
import br.com.followupcandidatos.exceptions.ObjectNotFoundException;
import br.com.followupcandidatos.repositories.MotivoRetornoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class MotivoRetornoService {

    @Autowired
    private MotivoRetornoRepository motivoRetornoRepository;

    @Transactional
    public MotivoRetorno salvarMotivoRetorno(MotivoRetorno motivoRetorno){
        return motivoRetornoRepository.save(motivoRetorno);
    }

    @Transactional
    public MotivoRetorno buscarMotivoDeRetornoPorId(Integer idMotivoRetorno)  {
        Optional<MotivoRetorno> motivoRetorno = motivoRetornoRepository.findById(idMotivoRetorno);
        return motivoRetorno.orElseThrow(() -> new ObjectNotFoundException("Motivo de Retorno não encontrado!"));
    }

    @Transactional
    public Page<MotivoRetorno> buscarMotivosDeRetornoHabilitados(Integer page, Integer linesPerPage) {
        Pageable pageRequest = PageRequest.of(page, linesPerPage);
        return motivoRetornoRepository.findAllByIsAtivoTrue(pageRequest);
    }

    @Transactional
    public Page<MotivoRetorno> buscarMotivoDeRetornoPorDescricao(String descricaoMotivoRetorno, Integer page, Integer linesPerPage) {
        Pageable pageRequest = PageRequest.of(page, linesPerPage);
        return  motivoRetornoRepository.findByDescricaoContainingIgnoreCase(descricaoMotivoRetorno, pageRequest);
    }

    @Transactional
    public Page<MotivoRetorno> buscarMotivosDeRetornoDesabilitados(Integer page, Integer linesPerPage) {
        Pageable pageRequest = PageRequest.of(page, linesPerPage);
        return motivoRetornoRepository.findAllMotivosDeRetornoDisabled(pageRequest);
    }

    @Transactional
    public void desabilitarMotivoDeRetorno(Integer idMotivoRetorno) {
        motivoRetornoRepository.disableMotivoRetorno(idMotivoRetorno);
    }

    @Transactional
    public void desabilitarTodosMotivoDeRetorno(){
        motivoRetornoRepository.disableAllMotivoRetorno();
    }

    @Transactional
    public void habilitarMotivoDeRetorno(Integer idMotivoRetorno) {
        motivoRetornoRepository.enableMotivoRetorno(idMotivoRetorno);
    }

    @Transactional
    public void habilitarTodosMotivoDeRetorno(){
        motivoRetornoRepository.enableAllMotivoRetorno();
    }

    @Transactional
    public void deletarMotivoDeRetorno(Integer idMotivoRetorno) {
        motivoRetornoRepository.delete(buscarMotivoDeRetornoPorId(idMotivoRetorno));
    }

    @Transactional
    public void deletarTodosMotivosDeRetornoDesabilitados() {
        motivoRetornoRepository.deleteAllMotivoRetornoDisabled();
    }

    public MotivoRetorno fromDTO(MotivoRetornoDTO motivoRetornoDTO){
        return new MotivoRetorno(null, motivoRetornoDTO.getDescricao().trim(), true);
    }
}
