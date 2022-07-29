package br.com.followupcandidatos.services;

import br.com.followupcandidatos.repositories.TipoRetornoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoRetornoService {

    @Autowired
    private TipoRetornoRepository tipoRetornoRepository;

}
