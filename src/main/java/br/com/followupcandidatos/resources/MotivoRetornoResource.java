package br.com.followupcandidatos.resources;

import br.com.followupcandidatos.domain.MotivoRetorno;
import br.com.followupcandidatos.domain.dtos.MotivoRetornoDTO;
import br.com.followupcandidatos.services.MotivoRetornoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/motivo-retorno")
public class MotivoRetornoResource {

    @Autowired
    private MotivoRetornoService motivoRetornoService;

    @PostMapping
    public ResponseEntity<?> inserirMotivoRetorno(@Valid @RequestBody MotivoRetornoDTO motivoRetornoDTO){
        MotivoRetorno motivoRetorno = motivoRetornoService.inserirMotivoRetorno(motivoRetornoService.fromDTO(motivoRetornoDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(motivoRetorno.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
