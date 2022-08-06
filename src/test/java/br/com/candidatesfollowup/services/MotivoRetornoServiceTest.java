package br.com.candidatesfollowup.services;

import br.com.candidatesfollowup.domain.MotivoRetorno;
import br.com.candidatesfollowup.domain.dtos.MotivoRetornoDTO;
import br.com.candidatesfollowup.exceptions.ObjectNotFoundException;
import br.com.candidatesfollowup.repositories.MotivoRetornoRepository;
import br.com.candidatesfollowup.utils.MotivoRetornoMocks;
import org.junit.Assert;
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
public class MotivoRetornoServiceTest {

    @InjectMocks
    MotivoRetornoService motivoRetornoService;

    @Mock
    MotivoRetornoRepository motivoRetornoRepository;

    /*
    * Dado um motivo de retorno válido, deve inserir o motivo de retorno com sucesso.
    * */
    @Test
    public void deveSalvarMotivoDeRetorno(){
        when(motivoRetornoRepository.save(any())).thenReturn(MotivoRetornoMocks.gerarMockMotivoRetorno());
        MotivoRetorno motivoRetorno = motivoRetornoService.salvarMotivoRetorno(MotivoRetornoMocks.gerarMockMotivoRetorno());
        Assert.assertEquals(motivoRetorno.getId(), MotivoRetornoMocks.gerarMockMotivoRetorno().getId());
    }

    /*
    * Dado um código válido, deve retornar um motivo de retorno válido.
    * */
    @Test
    public void deveRetornarMotivoDeRetornoPorId(){
        Optional<MotivoRetorno> motivoRetornoOptional = Optional.of(MotivoRetornoMocks.gerarMockListMotivosRetorno().get(3));
        when(motivoRetornoRepository.findById(4)).thenReturn(motivoRetornoOptional);
        MotivoRetorno motivoRetorno = motivoRetornoService.buscarMotivoDeRetornoPorId(4);
        Assert.assertEquals(motivoRetorno.getDescricao(), "Modalidade de Trabalho");
    }

    /*
    * Dado um código inexistente deve retornar a exceção de ObjectNotFoundException.
    * */
    @Test(expected = ObjectNotFoundException.class)
    public void deveRetornarExceptionQuandoNaoEncontrarObjeto(){
        motivoRetornoService.buscarMotivoDeRetornoPorId(4);
    }

    /*
    * Dado a chamada abaixo, deve listar todos os motivos de retorno habilitados.
    * */
    @Test
    public void deveRetornarTodosMotivosDeRetornoHabilitados(){
        List<MotivoRetorno> motivosDeRetornoList = Arrays.asList(
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(1),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(2),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(3));
        Page<MotivoRetorno> motivosRetorno = new PageImpl<>(motivosDeRetornoList);
        when(motivoRetornoRepository.findAllByIsAtivoTrue(PageRequest.of(0, 5))).thenReturn(motivosRetorno);

        Page<MotivoRetorno> motivosRetornoPage = motivoRetornoService.buscarMotivosDeRetornoHabilitados(0, 5);

        Assert.assertEquals(motivosRetornoPage.getContent().get(1).getDescricao(), "Moeda de Pagamento");
        Assert.assertTrue(motivosRetornoPage.getContent().get(2).getIsAtivo());
    }

    /*
    * Dado a descrição abaixo, deve retornar todos os motivos de retorno que contenham a descrição
    * informada.
    * */
    @Test
    public void deveRetornarMotivoDeRetornoQuandoBuscarPorDescricao() {
        List<MotivoRetorno> motivosDeRetorno = Arrays.asList(
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(2),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(3));
        Page<MotivoRetorno> motivosRetorno = new PageImpl<>(motivosDeRetorno);

        when(motivoRetornoRepository.findByDescricaoContainingIgnoreCase("mo",
                PageRequest.of(0, 5))).thenReturn(motivosRetorno);

        Page<MotivoRetorno> motivosRetornoPorDescricao = motivoRetornoService
                .buscarMotivoDeRetornoPorDescricao("mo", 0, 5);

        Assert.assertEquals(motivosRetornoPorDescricao.getContent().size(), 2L);
        Assert.assertEquals(motivosRetornoPorDescricao.getContent().get(0).getDescricao(), "Moeda de Pagamento");
        Assert.assertEquals(motivosRetornoPorDescricao.getContent().get(1).getId().toString(), "4");
    }

    /*
     * Dado a chamada abaixo, deve listar todos os motivos de retorno desabilitados.
     * */
    @Test
    public void deveRetornarMotivoDeRetornoDesabilitados() {
        List<MotivoRetorno> motivosDeRetorno = Arrays.asList(
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(0),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(4));
        Page<MotivoRetorno> motivosRetorno = new PageImpl<>(motivosDeRetorno);

        when(motivoRetornoRepository.findAllByIsAtivoFalse(
                PageRequest.of(0, 5))).thenReturn(motivosRetorno);

        Page<MotivoRetorno> motivosRetornoDesabilitados = motivoRetornoService.buscarMotivosDeRetornoDesabilitados(0, 5);

        Assert.assertEquals(motivosRetornoDesabilitados.getContent().get(0).getDescricao(), "Remuneração");
        Assert.assertFalse(motivosRetornoDesabilitados.getContent().get(1).getIsAtivo());
    }

    /*
    * Dado um código de motivo de retorno válido, deve ser executado 1 vez a chamada ao método para desabilitar
    * o motivo de retorno correspondente ao código.
    * */
    @Test
    public void deveDesabilitarMotivoDeRetorno(){
        motivoRetornoService.desabilitarMotivoDeRetorno(1);
        verify(motivoRetornoRepository, times(1)).disableMotivoRetorno(1);
    }

    /*
    * Dada a chamada abaixo, deve ser desabilitados todos os motivos de retorno habilitados
    * chamando uma vez o metodo que desabilita dos motivos de retorno.
    * */
    @Test
    public void deveDesabilitarTodosMotivosDeRetorno(){
        motivoRetornoService.desabilitarTodosMotivoDeRetorno();
        verify(motivoRetornoRepository, times(1)).disableAllMotivoRetorno();
    }

    /*
     * Dado um código de motivo de retorno válido, deve ser executado 1 vez a chamada ao método para habilitar
     * o motivo de retorno correspondente ao código.
     * */
    @Test
    public void deveHabilitarMotivoDeRetorno(){
        motivoRetornoService.habilitarMotivoDeRetorno(1);
        verify(motivoRetornoRepository, times(1)).enableMotivoRetorno(1);
    }

    /*
     * Dada a chamada abaixo, deve ser habilitados todos os motivos de retorno desabilitados
     * chamando uma vez o metodo que habilita dos motivos de retorno.
     * */
    @Test
    public void deveHabilitarTodosMotivosDeRetorno(){
        motivoRetornoService.habilitarTodosMotivoDeRetorno();
        verify(motivoRetornoRepository, times(1)).enableAllMotivoRetorno();
    }

    /*
     * Dado um código de motivo de retorno válido, deve ser executado 1 vez a chamada ao método para deletar
     * o motivo de retorno correspondente ao código.
     * */
    @Test
    public void deveDeletarMotivoDeRetorno(){
        when(motivoRetornoRepository.findById(1)).thenReturn(Optional.of(MotivoRetornoMocks.gerarMockMotivoRetorno()));
        motivoRetornoService.deletarMotivoDeRetorno(1);
        verify(motivoRetornoRepository, times(1)).delete(any());
    }

    /*
    * Dado a chamada abaixo, deve ser deletados todos os motivos de retorno desabilitados.
    * */
    @Test
    public void deveDeletarTodosMotivosDeRetornoDesabilitados(){
        motivoRetornoService.deletarTodosMotivosDeRetornoDesabilitados();
        verify(motivoRetornoRepository, times(1)).deleteAllMotivoRetornoDisabled();
    }

    /*
     * Dado a chamada abaixo, deve ser convertido um objeto do tipo MotivoRetornoDTO para MotivoRetorno.
     * */
    @Test
    public void deveConverterMotivoRetornoDTOEmMotivoRetorno(){
        MotivoRetornoDTO motivoRetornoDTO = new MotivoRetornoDTO();
        motivoRetornoDTO.setDescricao("Idioma");

        MotivoRetorno motivoRetorno = motivoRetornoService.fromDTO(motivoRetornoDTO);
        Assert.assertTrue(motivoRetorno.getIsAtivo());
    }
}
