package br.com.candidatesfollowup.resources;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import br.com.candidatesfollowup.domain.dtos.CandidatoRetornoDTO;
import br.com.candidatesfollowup.services.CandidatoRetornoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/candidato-retorno")
public class CandidatoRetornoResource {

    @Autowired
    private CandidatoRetornoService candidatoRetornoService;

    @PostMapping
    public ResponseEntity<?> inserirRetornoCandidato(@Valid @RequestBody CandidatoRetornoDTO candidatoRetornoDTO){
        CandidatoRetorno candidatoRetorno = candidatoRetornoService.fromDTO(candidatoRetornoDTO);
        candidatoRetorno = candidatoRetornoService.salvarCandidatoRetorno(candidatoRetorno);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(candidatoRetorno.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{idRetornoCandidato}")
    public ResponseEntity<?> atualizarRetornoCandidato(@PathVariable Integer idRetornoCandidato,
                                                       @Valid @RequestBody CandidatoRetornoDTO candidatoRetornoDTO){
        CandidatoRetorno candidatoRetorno = candidatoRetornoService.fromDTO(candidatoRetornoDTO);
        candidatoRetorno.setId(idRetornoCandidato);
        candidatoRetornoService.salvarCandidatoRetorno(candidatoRetorno);
        return ResponseEntity.noContent().build();
    }
}
