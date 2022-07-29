package br.com.followupcandidatos.resources;

import br.com.followupcandidatos.services.TipoRetornoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipo-retorno")
public class TipoRetornoResource {

    @Autowired
    private TipoRetornoService tipoRetornoService;

}
