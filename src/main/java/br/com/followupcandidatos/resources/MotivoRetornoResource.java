package br.com.followupcandidatos.resources;

import br.com.followupcandidatos.domain.MotivoRetorno;
import br.com.followupcandidatos.domain.dtos.MotivoRetornoDTO;
import br.com.followupcandidatos.services.MotivoRetornoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping
    public ResponseEntity<?> buscarMotivosDeRetorno(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "linesPerPage", defaultValue = "24")  Integer linesPerPage
                                                    ){
        Page<MotivoRetorno> motivosDeRetorno = motivoRetornoService.buscarMotivosDeRetorno(page, linesPerPage);
        return ResponseEntity.ok(motivosDeRetorno);
    }

    @GetMapping("/{idMotivoRetorno}")
    public ResponseEntity<?> buscarMotivoDeRetornoPorId(@PathVariable Integer idMotivoRetorno){
        return ResponseEntity.ok(motivoRetornoService.buscarMotivoDeRetornoPorId(idMotivoRetorno));
    }

}
