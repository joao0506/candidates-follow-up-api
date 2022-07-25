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
    public ResponseEntity<?> inserirMotivoDeRetorno(@Valid @RequestBody MotivoRetornoDTO motivoRetornoDTO){
        MotivoRetorno motivoRetorno = motivoRetornoService.salvarMotivoRetorno(motivoRetornoService.fromDTO(motivoRetornoDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(motivoRetorno.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{idMotivoRetorno}")
    public ResponseEntity<?> atualizarMotivoDeRetorno(@PathVariable Integer idMotivoRetorno,
                                                      @Valid @RequestBody MotivoRetornoDTO motivoRetornoDTO){
        MotivoRetorno motivoRetorno = motivoRetornoService.fromDTO(motivoRetornoDTO);
        motivoRetorno.setId(idMotivoRetorno);
        motivoRetornoService.salvarMotivoRetorno(motivoRetorno);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/desabilitar/{idMotivoRetorno}")
    public ResponseEntity<?> desabilitarMotivoDeRetorno(@PathVariable Integer idMotivoRetorno){
        motivoRetornoService.desabilitarMotivoDeRetorno(idMotivoRetorno);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idMotivoRetorno}")
    public ResponseEntity<?> buscarMotivoDeRetornoPorId(@PathVariable Integer idMotivoRetorno){
        return ResponseEntity.ok(motivoRetornoService.buscarMotivoDeRetornoPorId(idMotivoRetorno));
    }

    @GetMapping
    public ResponseEntity<?> buscarMotivosDeRetorno(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "linesPerPage", defaultValue = "24")  Integer linesPerPage){
        Page<MotivoRetorno> motivosRetorno = motivoRetornoService.buscarMotivosDeRetorno(page, linesPerPage);
        return ResponseEntity.ok(motivosRetorno);
    }

    @GetMapping("/descricao/{descricaoMotivoRetorno}")
    public ResponseEntity<?> buscarMotivoDeRetornoPorDescricao(@PathVariable String descricaoMotivoRetorno,
                                                               @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                               @RequestParam(value = "linesPerPage", defaultValue = "24")  Integer linesPerPage){
        Page<MotivoRetorno> motivosRetorno = motivoRetornoService.buscarMotivoDeRetornoPorDescricao(descricaoMotivoRetorno, page, linesPerPage);
        return ResponseEntity.ok(motivosRetorno);
    }

    @GetMapping("/desabilitados")
    public ResponseEntity<?> buscarMotivosDeRetornoDesabilitados(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(value = "linesPerPage", defaultValue = "24")  Integer linesPerPage){
        Page<MotivoRetorno> motivosRetornoDesabilitados = motivoRetornoService.buscarMotivosDeRetornoDesabilitados(page, linesPerPage);
        return ResponseEntity.ok(motivosRetornoDesabilitados);
    }

}
