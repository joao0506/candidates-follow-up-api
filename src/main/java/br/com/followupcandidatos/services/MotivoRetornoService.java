package br.com.followupcandidatos.services;

import br.com.followupcandidatos.domain.MotivoRetorno;
import br.com.followupcandidatos.domain.dtos.MotivoRetornoDTO;
import br.com.followupcandidatos.repositories.MotivoRetornoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotivoRetornoService {

    @Autowired
    private MotivoRetornoRepository motivoRetornoRepository;

    public MotivoRetorno inserirMotivoRetorno(MotivoRetorno motivoRetorno){
        return motivoRetornoRepository.save(motivoRetorno);
    }

    public MotivoRetorno fromDTO(MotivoRetornoDTO motivoRetornoDTO){
        return new MotivoRetorno(null, motivoRetornoDTO.getDescricao().trim(), true);
    }

}
