package br.com.candidatesfollowup.resources;

import br.com.candidatesfollowup.domain.MotivoRetorno;
import br.com.candidatesfollowup.services.MotivoRetornoService;
import br.com.candidatesfollowup.utils.MotivoRetornoMocks;
import br.com.candidatesfollowup.utils.RealizarRequisicao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MotivoRetornoResource.class)
@ContextConfiguration(classes= {MotivoRetornoResource.class})
@AutoConfigureMockMvc
public class MotivoRetornoResourceTest {

    @Autowired
    private MotivoRetornoResource motivoRetornoResource;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MotivoRetornoService motivoRetornoService;

    private RealizarRequisicao realizarRequisicao = new RealizarRequisicao();

    private static String path = "/motivo-retorno/";

    private static MotivoRetorno motivoRetorno = new MotivoRetorno();

    private static List<MotivoRetorno> motivosRetornoHabilitados, motivosRetornoDesabilitados;

    @Before
    public void setUp(){
        realizarRequisicao.setMockMvc(mockMvc);
        motivoRetorno = MotivoRetornoMocks.gerarMockMotivoRetorno();
        motivosRetornoHabilitados = MotivoRetornoMocks.gerarMockMotivoRetornoListHabilitados();
        motivosRetornoDesabilitados = MotivoRetornoMocks.gerarMockMotivoRetornoListDesabilitados();
    }

    /*
    * Dado um Motivo de Retorno válido, o status http deve ser 201 CREATED ao inserir com sucesso.
    * */
    @Test
    public void deveRetornar201CreatedAoInserirMotivoDeRetorno() throws Exception{
        when(motivoRetornoService.salvarMotivoRetorno(Mockito.any())).thenReturn(motivoRetorno);
        String motivoDeRetorno = "{\"descricao\": \"Remuneração\"}";

        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    /*
    * Dado um Motivo de Retorno com valor nulo, o status http deve ser 400 BAD REQUEST
    * ao tentar inserir um Motivo de Retorno null.
    * */
    @Test
    public void deveRetornar400BadRequestAoInserirMotivoDeRetornoComDescricaoNula() throws Exception{
        String motivoDeRetorno = "{\"descricao\": null}";
        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /*
     * Dado um Motivo de Retorno com valor vazio, o status http deve ser 400 BAD REQUEST
     * ao tentar inserir um Motivo de Retorno vazio.
     * */
    @Test
    public void deveRetornar400BadRequestAoInserirMotivoDeRetornoComDescricaoVazia() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"\"}";
        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /*
     * Dado um Motivo de Retorno com tamanho menor que 2 caracteres, o status http deve ser 400 BAD REQUEST
     * ao tentar inserir um Motivo de Retorno com tamanho menor que o permitido.
     * */
    @Test
    public void deveRetornar400BadRequestAoInserirMotivoDeRetornoComDescricaoMenorQueOValido() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"a\"}";
        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /*
     * Dado um Motivo de Retorno com tamanho maior que 60 caracteres, o status http deve ser 400 BAD REQUEST
     * ao tentar inserir um Motivo de Retorno com tamanho maior que o permitido.
     * */
    @Test
    public void deveRetornar400BadRequestAoInserirMotivoDeRetornoComDescricaoMaiorQueOValido() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";

        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);

        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /*
     * Dado um Motivo de Retorno válido, o status http deve ser 204 NO_CONTENT ao atualizar com sucesso.
     * */
    @Test
    public void deveRetornar204NoContentAoAtualizarMotivoDeRetorno() throws Exception {
        when(motivoRetornoService.fromDTO(any())).thenReturn(motivoRetorno);

        String motivoDeRetorno = "{\"descricao\": \"Teste Update\"}";
        MockHttpServletResponse response = realizarRequisicao.Put(path + "1",motivoDeRetorno);
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado um código de Motivo de Retorno válido, o status http deve ser 204 NO_CONTENT ao desabilitar com sucesso.
     * */
    @Test
    public void deveRetornar204NoContentAoDesabilitarMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path + "desabilitar/1", "");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado a chamada para desabilitar todos os motivos de retorno habilitados,
     * o status http deve ser 204 NO_CONTENT ao desabilitar todos com sucesso.
     * */
    @Test
    public void deveRetornar204NoContentAoDesabilitarTodosMotivosDeRetornoHabilitados() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path+"desabilitar/", "");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado um codigo de Motivo de Retorno válido, o status http deve ser 204 NO_CONTENT ao habilitar com sucesso.
     * */
    @Test
    public void deveRetornar204NoContentAoHabilitarMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path + "habilitar/2", "");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado a chamada para habilitar todos os motivos de retorno desabilitados,
     * o status http deve ser 204 NO_CONTENT ao habilitar todos com sucesso.
     * */
    @Test
    public void deveRetornar204NoContentAoHabilitarTodosMotivoDeRetornoDesabilitados() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path + "habilitar/", "");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado um codigo de Motivo de Retorno válido, o status http deve ser 204 NO_CONTENT ao deletar com sucesso.
     * */
    @Test
    public void deveRetornar204NoContentAoDeletarMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Delete(path+"2");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dada a requisição abaixo, o status http deve ser 204 NO_CONTENT ao deletar todos os motivos de retorno desabilitados.
     * */
    @Test
    public void deveRetornar204NoContentAoDeletarTodosMotivosDeRetornoDesabilitados() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Delete(path+"desabilitados");

        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado um codigo de Motivo de Retorno válido, o retorno deve ser o motivo de retorno correspondente
     * ao código informado (mock).
     * */
    @Test
    public void deveRetornarMotivoDeRetornoPorId() throws Exception{
        when(motivoRetornoService.buscarMotivoDeRetornoPorId(1)).thenReturn(motivoRetorno);

        MockHttpServletResponse response = realizarRequisicao.Get(path+"1");

        MotivoRetorno motivoRetornoResponse = MotivoRetornoMocks.convertMockHttpServletResponseToMotivoRetorno(response);

        Assert.assertEquals(motivoRetorno.getId(), motivoRetornoResponse.getId());
        Assert.assertEquals(motivoRetorno.getDescricao(), motivoRetornoResponse.getDescricao());
    }

    /*
    * Dada a chamada abaixo, deve retornar todos os motivos de retorno habilitados.
    * */
    @Test
    public void deveRetornarTodosMotivosDeRetornoHabilitados() throws Exception{
        when(motivoRetornoService.buscarMotivosDeRetornoHabilitados(0,5)).thenReturn(new PageImpl<>(motivosRetornoHabilitados));

        MockHttpServletResponse response = realizarRequisicao.GetPaginado(path+"", "0", "5");

        List<MotivoRetorno> motivosDeRetorno = MotivoRetornoMocks.convertMockHttpServletResponseToListMotivoRetorno(response);

        Assert.assertEquals(
                motivosDeRetorno.get(1).getDescricao(),
                motivosRetornoHabilitados.get(1).getDescricao());
        Assert.assertTrue(motivosDeRetorno.get(1).getIsAtivo());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    /*
    * Dado uma descrição com os caracteres abaixo, deve ser listado os motivos de retorno contendo
    * os caracteres presentes na requisição.
    * */
    @Test
    public void deveRetornarMotivosDeRetornoPorDescricao() throws Exception{
        List<MotivoRetorno> motivosRetornoPaged = Arrays.asList(
                                                        motivosRetornoHabilitados.get(1),
                                                        motivosRetornoHabilitados.get(2));
        Page<MotivoRetorno> motivosRetorno = new PageImpl<>(motivosRetornoPaged);
        when(motivoRetornoService.buscarMotivoDeRetornoPorDescricao("mo",0,5))
                .thenReturn(motivosRetorno);

        MockHttpServletResponse response = realizarRequisicao.GetPaginadoByDescricao(path+"descricao", "mo",
                "0", "5");

        List<MotivoRetorno> motivosDeRetorno = MotivoRetornoMocks.convertMockHttpServletResponseToListMotivoRetorno(response);

        Assert.assertEquals(motivosRetornoHabilitados.get(1).getDescricao(), motivosDeRetorno.get(0).getDescricao());
        Assert.assertEquals(motivosRetornoHabilitados.get(2).getDescricao(), motivosDeRetorno.get(1).getDescricao());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    /*
     * Dado uma descrição vazia, deve ser chamado o método para listar todos os motivos
     * de retorno habilitados.
     * */
    @Test
    public void deveRetornarMotivosHabilitadosQuandoDescricaoEhVazia() throws Exception{
        when(motivoRetornoService.buscarMotivosDeRetornoHabilitados(0,5)).thenReturn(new PageImpl<>(motivosRetornoHabilitados));

        MockHttpServletResponse response = realizarRequisicao.GetPaginadoByDescricao(path+"descricao", "",
                "0", "5");
        List<MotivoRetorno> motivosDeRetorno = MotivoRetornoMocks.convertMockHttpServletResponseToListMotivoRetorno(response);

        Assert.assertEquals(motivosDeRetorno.size(), 3L);
        Assert.assertEquals(motivosDeRetorno.get(0).getDescricao(), motivosRetornoHabilitados.get(0).getDescricao());
        Assert.assertTrue(motivosDeRetorno.get(2).getIsAtivo());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    /*
    * Dada a requisição abaixo, deve retornar todos os motivos de retorno desabilitados.
    * */
    @Test
    public void deveRetornarMotivosDeRetornoDesabilitados() throws Exception{
        when(motivoRetornoService.buscarMotivosDeRetornoDesabilitados(0,5))
                .thenReturn(new PageImpl<>(motivosRetornoDesabilitados));

        MockHttpServletResponse response = realizarRequisicao.GetPaginado(path+"/desabilitados", "0", "5");
        List<MotivoRetorno> motivosDeRetorno = MotivoRetornoMocks.convertMockHttpServletResponseToListMotivoRetorno(response);

        Assert.assertEquals(
                motivosDeRetorno.get(0).getDescricao(),
                motivosRetornoDesabilitados.get(0).getDescricao());
        Assert.assertFalse(motivosDeRetorno.get(0).getIsAtivo());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}