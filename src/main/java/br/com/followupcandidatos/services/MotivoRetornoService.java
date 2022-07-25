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
    public Page<MotivoRetorno> buscarMotivosDeRetorno(Integer page, Integer linesPerPage) {
        Pageable pageRequest = PageRequest.of(page, linesPerPage);
        return motivoRetornoRepository.findAll(pageRequest);
    }

    @Transactional
    public Page<MotivoRetorno> buscarMotivoDeRetornoPorDescricao(String descricaoMotivoRetorno, Integer page, Integer linesPerPage) {
        Pageable pageRequest = PageRequest.of(page, linesPerPage);
        Page<MotivoRetorno> motivosRetorno = motivoRetornoRepository.findByDescricao(descricaoMotivoRetorno, pageRequest);
        return motivosRetorno;
    }

    @Transactional
    public void desabilitarMotivoDeRetorno(Integer idMotivoRetorno) {
        motivoRetornoRepository.disableMotivoRetorno(idMotivoRetorno);
    }

    public MotivoRetorno fromDTO(MotivoRetornoDTO motivoRetornoDTO){
        return new MotivoRetorno(null, motivoRetornoDTO.getDescricao().trim(), true);
    }
}
