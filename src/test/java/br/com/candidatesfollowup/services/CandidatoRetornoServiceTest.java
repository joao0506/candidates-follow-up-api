package br.com.candidatesfollowup.services;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import br.com.candidatesfollowup.domain.TipoRetorno;
import br.com.candidatesfollowup.domain.dtos.CandidatoRetornoDTO;
import br.com.candidatesfollowup.exceptions.ObjectNotFoundException;
import br.com.candidatesfollowup.repositories.CandidatoRetornoRepository;
import br.com.candidatesfollowup.utils.CandidatoRetornoMocks;
import br.com.candidatesfollowup.utils.TipoRetornoMocks;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CandidatoRetornoServiceTest {

    @InjectMocks
    private CandidatoRetornoService candidatoRetornoService;

    @Mock
    private CandidatoRetornoRepository candidatoRetornoRepository;

    private static CandidatoRetorno candidatoRetorno;
    private static List<CandidatoRetorno> candidatosRetornoHabilitados, candidatosRetornoDesabilitados;

    @Before
    public void setUp(){
        candidatoRetorno = CandidatoRetornoMocks.gerarMockCandidatoRetorno();
        candidatosRetornoHabilitados = CandidatoRetornoMocks.gerarMockListCandidatoRetornoHabilitados();
        candidatosRetornoDesabilitados = CandidatoRetornoMocks.gerarMockListCandidatoRetornoDesabilitados();
    }

    /*
    * Dado um retorno de candidato válido,
    * deve ser chamado o método de salvar do objeto.
    * */
    @Test
    public void deveSalvarRetornoDeCandidato(){
        candidatoRetornoService.salvarCandidatoRetorno(candidatoRetorno);

        verify(candidatoRetornoRepository, times(1)).save(candidatoRetorno);
    }

    /*
     * Dado a chamada abaixo,
     * deve ser chamado o método de desabilitar todos os retornos de candidato.
     * */
    @Test
    public void deveDesabilitarTodosRetornosDeCandidato(){
        candidatoRetornoService.desabilitarTodosRetornosCandidatosHabilitados();

        verify(candidatoRetornoRepository, times(1)).disableAllCandidatoRetornoByIsAtivoTrue();
    }

    /*
     * Dado um id válido,
     * deve ser chamado o método de desabilitar o retorno de candidato correspondente ao código.
     * */
    @Test
    public void deveDesabilitarRetornoDeCandidato(){
        when(candidatoRetornoRepository.findById(1)).thenReturn(Optional.of(candidatoRetorno));

        candidatoRetornoService.desabilitarRetornoCandidato(1);

        verify(candidatoRetornoRepository, times(1)).disableCandidatoRetorno(1);
    }


    /*
     * Dado a chamada abaixo,
     * deve ser chamado o método de habilitar todos os retornos de candidato.
     * */
    @Test
    public void deveHabilitarTodosRetornosDeCandidato(){
        candidatoRetornoService.habilitarTodosRetornosCandidatosDesabilitados();

        verify(candidatoRetornoRepository, times(1)).enableAllCandidatoRetornoByIsAtivoFalse();
    }

    /*
     * Dado um id válido,
     * deve ser chamado o método de habilitar o retorno de candidato correspondente ao código.
     * */
    @Test
    public void deveHabilitarRetornoDeCandidato(){
        when(candidatoRetornoRepository.findById(1)).thenReturn(Optional.of(candidatoRetorno));

        candidatoRetornoService.habilitarRetornoCandidato(1);

        verify(candidatoRetornoRepository, times(1)).enableCandidatoRetorno(1);
    }

    /*
     * Dado um id válido,
     * deve ser chamado o método de marcar retorno candidato como contatado correspondente ao código.
     * */
    @Test
    public void deveMarcarRetornoDeCandidatoComoContatado(){
        candidatoRetornoService.marcarRetornoCandidatoComoContatado(1);

        verify(candidatoRetornoRepository, times(1)).candidatoContatado(1);
    }

    /*
     * Dado um id válido,
     * deve ser chamado o método de marcar retorno candidato como não contatado correspondente ao código.
     * */
    @Test
    public void deveMarcarRetornoDeCandidatoComoNaoContatado(){
        candidatoRetornoService.marcarRetornoCandidatoComoNaoContatado(1);

        verify(candidatoRetornoRepository, times(1)).candidatoNaoContatado(1);
    }

    /*
     * Dado um id válido,
     * deve ser chamado o método de deletar retorno candidato correspondente ao código.
     * */
    @Test
    public void deveDeletarRetornoDeCandidato(){
        when(candidatoRetornoRepository.findById(1)).thenReturn(Optional.of(candidatoRetorno));

        candidatoRetornoService.deletarRetornoCandidato(1);

        verify(candidatoRetornoRepository, times(1)).delete(candidatoRetorno);
    }

    /*
     * Dado a chamada abaixo,
     * deve ser chamado o método de deletar todos os retornos de candidato desabilitados.
     * */
    @Test
    public void deveDeletarTodosOsRetornosDeCandidatoDesabilitados(){
        candidatoRetornoService.deletarRetornoCandidatoDesabilitados();

        verify(candidatoRetornoRepository, times(1)).deleteAllByIsAtivoFalse();
    }

    /*
     * Dado a chamada abaixo,
     * deve ser chamado listado todos os retornos de candidato habilitados
     * */
    @Test
    public void deveListarTodosOsRetornosDeCandidatoHabilitados(){
        candidatoRetornoService.buscarTodosRetornoCandidatoHabilitados();

        verify(candidatoRetornoRepository, times(1)).findAllByIsAtivoTrueOrderByIdAsc();
    }

    /*
     * Dado a chamada abaixo,
     * deve ser chamado listado todos os retornos de candidato desabilitados
     * */
    @Test
    public void deveListarTodosOsRetornosDeCandidatoDesabilitados(){
        candidatoRetornoService.buscarTodosRetornoCandidatoDesabilitados(0, 5);

        verify(candidatoRetornoRepository, times(1)).findAllByIsAtivoFalse(any());
    }

    /*
     * Dado um id válido,
     * deve ser chamado o método de listar retorno candidato correspondente ao código.
     * */
    @Test
    public void deveListarRetornoDeCandidato(){
        when(candidatoRetornoRepository.findById(1)).thenReturn(Optional.of(candidatoRetorno));

        candidatoRetornoService.buscarCandidatoRetornoPorId(1);

        verify(candidatoRetornoRepository, times(1)).findById(1);
    }

    /*
    * Dado um id inexistente,
    * deve ser lançado a exceção de ObjectNotFoundException
    * */
    @Test(expected = ObjectNotFoundException.class)
    public void deveLancarExcecaoAonaoEncontrarRetornoDeCandidato(){
        candidatoRetornoService.buscarCandidatoRetornoPorId(1);
    }

    /*
    * Dado um CandidatoRetornoDTO válido,
    * deve ser convertido em CandidatoRetorno
     * */
    @Test
    public void deveConverterCandidatoRetornoDTOEmCandidatoRetorno(){
        TipoRetorno tipoRetorno = TipoRetornoMocks.gerarMockTipoRetorno();
        CandidatoRetornoDTO candidatoRetornoDTO = new CandidatoRetornoDTO();
        candidatoRetornoDTO.setNomeCandidato("José da Silva");
        candidatoRetornoDTO.setDataRetorno(LocalDate.of(2022, 8, 13));
        candidatoRetornoDTO.setCanalDeRetorno(tipoRetorno);

        CandidatoRetorno candidatoRetorno = candidatoRetornoService.fromDTO(candidatoRetornoDTO);

        Assert.assertNull(candidatoRetorno.getId());
        Assert.assertTrue(candidatoRetorno.getIsAtivo());
        Assert.assertFalse(candidatoRetorno.getIsCandidatoContatado());
    }
}
