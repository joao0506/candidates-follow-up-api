package br.com.candidatesfollowup.services;

import br.com.candidatesfollowup.domain.TipoRetorno;
import br.com.candidatesfollowup.domain.dtos.TipoRetornoDTO;
import br.com.candidatesfollowup.exceptions.ObjectNotFoundException;
import br.com.candidatesfollowup.repositories.TipoRetornoRepository;
import br.com.candidatesfollowup.utils.TipoRetornoMocks;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TipoRetornoServiceTest {

    @InjectMocks
    private TipoRetornoService tipoRetornoService;

    @Mock
    private TipoRetornoRepository tipoRetornoRepository;

    private static TipoRetorno tipoRetorno;

    private static List<TipoRetorno> tipoRetornoHabilitados, tipoRetornoDesabilitados;

    @Before
    public void setUp(){
        tipoRetorno = TipoRetornoMocks.gerarMockTipoRetorno();
        tipoRetornoHabilitados = TipoRetornoMocks.gerarMockTipoRetornoListHabilitados();
        tipoRetornoDesabilitados = TipoRetornoMocks.gerarMockTipoRetornoListDesabilitados();
    }

    /*
    * Deve realizar a chamada ao método de inserção de um tipo de retorno.
    * */
    @Test
    public void deveInserirTipoDeRetorno(){
        when(tipoRetornoRepository.save(tipoRetorno)).thenReturn(tipoRetorno);

        TipoRetorno tipoRetornoResponse = tipoRetornoService.salvarTipoDeRetorno(tipoRetorno);

        verify(tipoRetornoRepository, times(1)).save(tipoRetorno);
        Assert.assertEquals(tipoRetorno.getId(), tipoRetornoResponse.getId());
    }

    /*
     * Deve realizar a chamada ao método para desabilitar um tipo de retorno
     * */
    @Test
    public void deveDesabilitarTipoDeRetorno(){
        tipoRetornoService.desabilitarTipoDeRetorno(1);

        verify(tipoRetornoRepository, times(1)).disableTipoDeRetorno(1);
    }

    /*
     * Deve realizar a chamada ao método para desabilitar todos os tipos de retorno
     * */
    @Test
    public void deveDesabilitarTodosTipoDeRetorno(){
        tipoRetornoService.desabilitarTodosTipoDeRetorno();

        verify(tipoRetornoRepository, times(1)).disableAllTipoRetorno();
    }

    /*
     * Deve realizar a chamada ao método para habilitar um tipo de retorno
     * */
    @Test
    public void deveHabilitarTipoDeRetorno(){
        tipoRetornoService.habilitarTipoDeRetorno(1);

        verify(tipoRetornoRepository, times(1)).enableTipoRetorno(1);
    }

    /*
     * Deve realizar a chamada ao método para habilitar todos os tipos de retorno
     * */
    @Test
    public void deveHabilitarTodosTipoDeRetorno(){
        tipoRetornoService.habilitarTodosTipoDeRetorno();

        verify(tipoRetornoRepository, times(1)).enableAllTipoRetorno();
    }

    /*
     * Deve realizar a chamada ao método para deletar um tipo de retorno
     * */
    @Test
    public void deveDeletarTipoDeRetorno(){
        when(tipoRetornoRepository.findById(1)).thenReturn(Optional.of(tipoRetorno));

        tipoRetornoService.deletarTipoDeRetorno(1);

        verify(tipoRetornoRepository, times(1)).delete(tipoRetorno);
    }

    /*
     * Deve realizar a chamada ao método para deletar todos os tipos de retorno
     * */
    @Test
    public void deveDeletarTodosTiposDeRetornoDesabilitados(){
        tipoRetornoService.deletarTiposDeRetornoDesabilitados();

        verify(tipoRetornoRepository, times(1)).deleteAllByIsAtivoFalse();
    }

    /*
    * Dado um id válido, deve retornar o tipo de retorno correspondente
    * ao código informado.
    * */
    @Test
    public void deveRetornarTipoDeRetornoPorId(){
        when(tipoRetornoRepository.findById(1)).thenReturn(Optional.of(tipoRetorno));

        TipoRetorno tipoRetornoResponse = tipoRetornoService.buscarTipoDeRetornoPorId(1);

        Assert.assertEquals(tipoRetornoResponse.getId(), tipoRetorno.getId());
        Assert.assertTrue(tipoRetornoResponse.getIsAtivo());
    }

    /*
     * Dado um id inexistente, deve lançar a exceção de ObjectNotFoundException
     * */
    @Test(expected = ObjectNotFoundException.class)
    public void deveLancarExceptionQuandoNaoRetornarTipoDeRetornoPorId(){
        tipoRetornoService.buscarTipoDeRetornoPorId(1);
    }

    /*
    * Deve buscar todos os tipos de retorno habilitados
    * */
    @Test
    public void deveRetornarTodosOsTiposDeRetornoHabilitados(){
        when(tipoRetornoRepository.findAllByIsAtivoTrue())
                .thenReturn(tipoRetornoHabilitados);

        List<TipoRetorno> tipoRetornoList = tipoRetornoService.buscarTodosTiposDeRetornoHabilitados();

        Assert.assertEquals(tipoRetornoHabilitados.size(), tipoRetornoList.size());
    }

    /*
     * Deve buscar todos os tipos de retorno desabilitados
     * */
    @Test
    public void deveRetornarTodosOsTiposDeRetornoDesabilitados(){
        when(tipoRetornoRepository.findAllByIsAtivoFalse(PageRequest.of(0, 5)))
                .thenReturn(new PageImpl<>(tipoRetornoDesabilitados));

        Page<TipoRetorno> tipoRetornoList = tipoRetornoService.buscarTodosTiposDeRetornoDesabilitados(0, 5);

        Assert.assertEquals(tipoRetornoDesabilitados.size(), tipoRetornoList.getSize());
    }

    /*
     * Deve buscar todos os tipos de retorno que contenham os caracteres
     * informados na descricao.
     * */
    @Test
    public void deveRetornarTodosOsTiposDeRetornoPorDescricao(){
        List<TipoRetorno> tipoRetornoListbyDescricao = Arrays.asList(
                new TipoRetorno(1, "InMail", true),
                new TipoRetorno(4, "E-mail", false)
        );

        when(tipoRetornoRepository.findByDescricaoContainingIgnoreCase("ma", PageRequest.of(0, 5)))
                .thenReturn(new PageImpl<>(tipoRetornoListbyDescricao));

        Page<TipoRetorno> tipoRetornoList = tipoRetornoService.buscarTipoDeRetornoPorDescricao("ma",0, 5);

        Assert.assertEquals(tipoRetornoListbyDescricao.size(), tipoRetornoList.getSize());
    }


    /*
    * Deve converter objeto TipoRetornoDTO em objeto TipoRetorno
    * */
    @Test
    public void deveConverterTipoRetornoDTOEmTipoRetorno(){
        TipoRetornoDTO tipoRetornoDTO = new TipoRetornoDTO();
        tipoRetornoDTO.setDescricao("E-mail");

        TipoRetorno tipoRetorno = tipoRetornoService.fromDTO(tipoRetornoDTO);

        Assert.assertNull(tipoRetorno.getId());
        Assert.assertTrue(tipoRetorno.getIsAtivo());
    }


}
