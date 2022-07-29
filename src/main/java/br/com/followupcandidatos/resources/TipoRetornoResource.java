package br.com.followupcandidatos.resources;

import br.com.followupcandidatos.domain.TipoRetorno;
import br.com.followupcandidatos.domain.dtos.TipoRetornoDTO;
import br.com.followupcandidatos.services.TipoRetornoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/tipo-retorno")
public class TipoRetornoResource {

    @Autowired
    private TipoRetornoService tipoRetornoService;

    @PostMapping
    public ResponseEntity<?> inserirTipoDeRetorno(@Valid @RequestBody TipoRetornoDTO tipoRetornoDTO){
        TipoRetorno tipoRetorno = tipoRetornoService.salvarTipoDeRetorno(tipoRetornoService.fromDTO(tipoRetornoDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(tipoRetorno.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
