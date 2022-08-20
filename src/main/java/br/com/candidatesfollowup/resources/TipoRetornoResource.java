package br.com.candidatesfollowup.resources;

import br.com.candidatesfollowup.domain.TipoRetorno;
import br.com.candidatesfollowup.domain.dtos.TipoRetornoDTO;
import br.com.candidatesfollowup.services.TipoRetornoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin
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

    @PutMapping("/{idTipoRetorno}")
    public ResponseEntity<?> atualizarTipoDeRetorno(@PathVariable Integer idTipoRetorno,
            @Valid @RequestBody TipoRetornoDTO tipoRetornoDTO){
        TipoRetorno tipoRetorno = tipoRetornoService.fromDTO(tipoRetornoDTO);
        tipoRetorno.setId(idTipoRetorno);
        tipoRetornoService.salvarTipoDeRetorno(tipoRetorno);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("desabilitar/{idTipoRetorno}")
    public ResponseEntity<?> desabilitarTipoDeRetorno(@PathVariable Integer idTipoRetorno){
        tipoRetornoService.desabilitarTipoDeRetorno(idTipoRetorno);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("desabilitar")
    public ResponseEntity<?> desabilitarTodosTipoDeRetorno(){
        tipoRetornoService.desabilitarTodosTipoDeRetorno();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("habilitar/{idTipoRetorno}")
    public ResponseEntity<?> habilitarTipoDeRetorno(@PathVariable Integer idTipoRetorno){
        tipoRetornoService.habilitarTipoDeRetorno(idTipoRetorno);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("habilitar")
    public ResponseEntity<?> habilitarTodosTipoDeRetorno(){
        tipoRetornoService.habilitarTodosTipoDeRetorno();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idTipoRetorno}")
    public ResponseEntity<?> deletarTipoDeRetorno(@PathVariable Integer idTipoRetorno){
        tipoRetornoService.deletarTipoDeRetorno(idTipoRetorno);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/desabilitados")
    public ResponseEntity<?> deletarTiposDeRetornoDesabilitados(){
        tipoRetornoService.deletarTiposDeRetornoDesabilitados();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idTipoRetorno}")
    public ResponseEntity<?> buscarTipoDeRetornoPorId(@PathVariable Integer idTipoRetorno){
        return ResponseEntity.ok(tipoRetornoService.buscarTipoDeRetornoPorId(idTipoRetorno));
    }

    @GetMapping
    public ResponseEntity<?> buscarTodosTiposDeRetornoHabilitados(){
        List<TipoRetorno> tiposRetorno = tipoRetornoService.buscarTodosTiposDeRetornoHabilitados();
        return ResponseEntity.ok(tiposRetorno);
    }

    @GetMapping("/descricao")
    public ResponseEntity<?> buscarTipoDeRetornoPorDescricao(@RequestParam(value = "descricao", defaultValue = "") String descricaoTipoRetorno,
                                                                @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                @RequestParam(value = "linesPerPage", defaultValue = "10")  Integer linesPerPage){
        if (descricaoTipoRetorno.trim().isEmpty()) return buscarTodosTiposDeRetornoHabilitados();
        Page<TipoRetorno> tiposRetorno = tipoRetornoService.buscarTipoDeRetornoPorDescricao(descricaoTipoRetorno, page, linesPerPage);
        return ResponseEntity.ok(tiposRetorno);
    }

    @GetMapping("/desabilitados")
    public ResponseEntity<?> buscarTodosTiposDeRetornoDesabilitados(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "linesPerPage", defaultValue = "10")  Integer linesPerPage){
        Page<TipoRetorno> tiposRetorno = tipoRetornoService.buscarTodosTiposDeRetornoDesabilitados(page, linesPerPage);
        return ResponseEntity.ok(tiposRetorno);
    }

}
