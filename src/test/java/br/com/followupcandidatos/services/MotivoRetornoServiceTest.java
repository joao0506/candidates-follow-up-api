package br.com.followupcandidatos.services;

import br.com.followupcandidatos.domain.MotivoRetorno;
import br.com.followupcandidatos.domain.dtos.MotivoRetornoDTO;
import br.com.followupcandidatos.exceptions.ObjectNotFoundException;
import br.com.followupcandidatos.repositories.MotivoRetornoRepository;
import br.com.followupcandidatos.utils.MotivoRetornoMocks;
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

    @Test
    public void deveSalvarMotivoDeRetorno(){
        when(motivoRetornoRepository.save(any())).thenReturn(MotivoRetornoMocks.gerarMockMotivoRetorno());
        MotivoRetorno motivoRetorno = motivoRetornoService.salvarMotivoRetorno(MotivoRetornoMocks.gerarMockMotivoRetorno());
        Assert.assertEquals(motivoRetorno.getId(), MotivoRetornoMocks.gerarMockMotivoRetorno().getId());
    }

    @Test
    public void deveRetornarMotivoDeRetornoPorId(){
        Optional<MotivoRetorno> motivoRetornoOptional = Optional.of(MotivoRetornoMocks.gerarMockListMotivosRetorno().get(3));
        when(motivoRetornoRepository.findById(4)).thenReturn(motivoRetornoOptional);
        MotivoRetorno motivoRetorno = motivoRetornoService.buscarMotivoDeRetornoPorId(4);
        Assert.assertEquals(motivoRetorno.getDescricao(), "Modalidade de Trabalho");
    }

    @Test(expected = ObjectNotFoundException.class)
    public void deveRetornarExceptionQuandoNaoEncontrarObjeto(){
        motivoRetornoService.buscarMotivoDeRetornoPorId(4);
    }

    @Test
    public void deveRetornarTodosMotivosDeRetorno(){
        Page<MotivoRetorno> motivosRetorno = new PageImpl<>(MotivoRetornoMocks.gerarMockListMotivosRetorno());
        when(motivoRetornoRepository.findAll(PageRequest.of(0, 5))).thenReturn(motivosRetorno);

        Page<MotivoRetorno> motivosRetornoPage = motivoRetornoService.buscarMotivosDeRetorno(0, 5);
        Assert.assertEquals(motivosRetornoPage.getContent().size(), 5L);
        Assert.assertEquals(motivosRetornoPage.getContent().get(0).getDescricao(), "Remuneração");
    }

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

    @Test
    public void deveRetornarMotivoDeRetornoDesabilitados() {
        List<MotivoRetorno> motivosDeRetorno = Arrays.asList(
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(0),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(4));
        Page<MotivoRetorno> motivosRetorno = new PageImpl<>(motivosDeRetorno);

        when(motivoRetornoRepository.findAllMotivosDeRetornoDisabled(
                PageRequest.of(0, 5))).thenReturn(motivosRetorno);

        Page<MotivoRetorno> motivosRetornoDesabilitados = motivoRetornoService.buscarMotivosDeRetornoDesabilitados(0, 5);

        Assert.assertEquals(motivosRetornoDesabilitados.getContent().size(), 2L);
        Assert.assertEquals(motivosRetornoDesabilitados.getContent().get(0).getDescricao(), "Remuneração");
        Assert.assertEquals(motivosRetornoDesabilitados.getContent().get(1).getId().toString(), "5");
    }

    @Test
    public void deveDesabilitarMotivoDeRetorno(){
        motivoRetornoService.desabilitarMotivoDeRetorno(1);
        verify(motivoRetornoRepository, times(1)).disableMotivoRetorno(1);
    }

    @Test
    public void deveDesabilitarTodosMotivosDeRetorno(){
        motivoRetornoService.desabilitarTodosMotivoDeRetorno();
        verify(motivoRetornoRepository, times(1)).disableAllMotivoRetorno();
    }

    @Test
    public void deveHabilitarMotivoDeRetorno(){
        motivoRetornoService.habilitarMotivoDeRetorno(1);
        verify(motivoRetornoRepository, times(1)).enableMotivoRetorno(1);
    }

    @Test
    public void deveHabilitarTodosMotivosDeRetorno(){
        motivoRetornoService.habilitarTodosMotivoDeRetorno();
        verify(motivoRetornoRepository, times(1)).enableAllMotivoRetorno();
    }

    @Test
    public void deveDeletarMotivoDeRetorno(){
        when(motivoRetornoRepository.findById(1)).thenReturn(Optional.of(MotivoRetornoMocks.gerarMockMotivoRetorno()));
        motivoRetornoService.deletarMotivoDeRetorno(1);
        verify(motivoRetornoRepository, times(1)).delete(any());
    }

    @Test
    public void deveConverterMotivoRetornoDTOEmMotivoRetorno(){
        MotivoRetornoDTO motivoRetornoDTO = new MotivoRetornoDTO();
        motivoRetornoDTO.setDescricao("Idioma");

        MotivoRetorno motivoRetorno = motivoRetornoService.fromDTO(motivoRetornoDTO);
        Assert.assertTrue(motivoRetorno.getIsAtivo());
    }

}
