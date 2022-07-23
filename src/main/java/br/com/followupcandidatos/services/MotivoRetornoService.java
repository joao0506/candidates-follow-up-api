package br.com.followupcandidatos.services;

import br.com.followupcandidatos.domain.MotivoRetorno;
import br.com.followupcandidatos.domain.dtos.MotivoRetornoDTO;
import br.com.followupcandidatos.repositories.MotivoRetornoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MotivoRetornoService {

    @Autowired
    private MotivoRetornoRepository motivoRetornoRepository;

    @Transactional
    public MotivoRetorno inserirMotivoRetorno(MotivoRetorno motivoRetorno){
        return motivoRetornoRepository.save(motivoRetorno);
    }

    @Transactional
    public Page<MotivoRetorno> buscarMotivosDeRetorno(Integer page, Integer linesPerPage) {
        Pageable pageRequest = PageRequest.of(page, linesPerPage);
        return motivoRetornoRepository.findAll(pageRequest);
    }

    public MotivoRetorno fromDTO(MotivoRetornoDTO motivoRetornoDTO){
        return new MotivoRetorno(null, motivoRetornoDTO.getDescricao().trim(), true);
    }
}
