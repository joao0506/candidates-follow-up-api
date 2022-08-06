package br.com.candidatesfollowup.resources;

import br.com.candidatesfollowup.domain.MotivoRetorno;
import br.com.candidatesfollowup.services.MotivoRetornoService;
import br.com.candidatesfollowup.utils.MotivoRetornoMocks;
import br.com.candidatesfollowup.utils.RealizarRequisicao;
import org.json.JSONArray;
import org.json.JSONObject;
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

import java.nio.charset.StandardCharsets;
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

    private static MotivoRetorno motivoRetorno;

    private static List<MotivoRetorno> motivosRetornoHabilitados, motivosRetornoDesabilitados;

    @Before
    public void setUp(){
        realizarRequisicao.setMockMvc(mockMvc);
        motivoRetorno = MotivoRetornoMocks.gerarMockMotivoRetorno();
        motivosRetornoHabilitados = Arrays.asList(
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(1),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(2),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(3));
        motivosRetornoDesabilitados = Arrays.asList(
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(0),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(4));
    }

    /*
    * Dado um Motivo de Retorno válido, o status http deve ser 200 OK ao inserir com sucesso.
    * */
    @Test
    public void deveRetornar200OkAoInserirMotivoDeRetorno() throws Exception{
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
    public void deveRetornarErro400AoInserirMotivoDeRetornoComDescricaoNula() throws Exception{
        String motivoDeRetorno = "{\"descricao\": null}";
        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /*
     * Dado um Motivo de Retorno com valor vazio, o status http deve ser 400 BAD REQUEST
     * ao tentar inserir um Motivo de Retorno vazio.
     * */
    @Test
    public void deveRetornarErro400AoInserirMotivoDeRetornoComDescricaoVazia() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"\"}";
        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /*
     * Dado um Motivo de Retorno com tamanho menor que 2 caracteres, o status http deve ser 400 BAD REQUEST
     * ao tentar inserir um Motivo de Retorno com tamanho menor que o permitido.
     * */
    @Test
    public void deveRetornarErro400AoInserirMotivoDeRetornoComDescricaoMenorQueOValido() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"a\"}";
        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /*
     * Dado um Motivo de Retorno com tamanho maior que 60 caracteres, o status http deve ser 400 BAD REQUEST
     * ao tentar inserir um Motivo de Retorno com tamanho maior que o permitido.
     * */
    @Test
    public void deveRetornarErro400AoInserirMotivoDeRetornoComDescricaoMaiorQueOValido() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";
        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /*
     * Dado um Motivo de Retorno válido, o status http deve ser 204 NO_CONTENT ao atualizar com sucesso.
     * */
    @Test
    public void deveRetornarNoContentAoAtualizarMotivoDeRetorno() throws Exception {
        when(motivoRetornoService.fromDTO(any())).thenReturn(motivoRetorno);

        String motivoDeRetorno = "{\"descricao\": \"Teste Update\"}";
        MockHttpServletResponse response = realizarRequisicao.Put(path + "1",motivoDeRetorno);
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado um código de Motivo de Retorno válido, o status http deve ser 204 NO_CONTENT ao desabilitar com sucesso.
     * */
    @Test
    public void deveRetornarNoContentAoDesabilitarMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path + "desabilitar/1", "");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado a chamada para desabilitar todos os motivos de retorno habilitados,
     * o status http deve ser 204 NO_CONTENT ao desabilitar todos com sucesso.
     * */
    @Test
    public void deveRetornarNoContentAoDesabilitarTodosMotivosDeRetornoHabilitados() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path+"desabilitar/", "");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado um codigo de Motivo de Retorno válido, o status http deve ser 204 NO_CONTENT ao habilitar com sucesso.
     * */
    @Test
    public void deveRetornarNoContentAoHabilitarMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path + "habilitar/2", "");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado a chamada para habilitar todos os motivos de retorno desabilitados,
     * o status http deve ser 204 NO_CONTENT ao habilitar todos com sucesso.
     * */
    @Test
    public void deveRetornarNoContentAoHabilitarTodosMotivoDeRetornoDesabilitados() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path + "habilitar/", "");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado um codigo de Motivo de Retorno válido, o status http deve ser 204 NO_CONTENT ao deletar com sucesso.
     * */
    @Test
    public void deveRetornarNoContentAoDeletarMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Delete(path+"2");
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

        Assert.assertEquals(response.getContentAsString(StandardCharsets.UTF_8), motivoRetorno.toString());
    }

    /*
    * Dada a chamada abaixo, deve retornar todos os motivos de retorno habilitados.
    * */
    @Test
    public void deveRetornarTodosMotivosDeRetornoHabilitados() throws Exception{
        when(motivoRetornoService.buscarMotivosDeRetornoHabilitados(0,5)).thenReturn(new PageImpl<>(motivosRetornoHabilitados));

        MockHttpServletResponse response = realizarRequisicao.GetPaginado(path+"", "0", "5");

        JSONArray motivosDeRetorno = new JSONArray(new JSONObject(response.getContentAsString(StandardCharsets.UTF_8)).get("content").toString());

        Assert.assertEquals(
                motivosDeRetorno.getJSONObject(1).get("descricao"),
                motivosRetornoHabilitados.get(1).getDescricao());
        Assert.assertTrue(motivosDeRetorno.getJSONObject(1).getBoolean(("isAtivo")));
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    /*
    * Dado uma descrição com os caracteres abaixo, deve ser listado os motivos de retorno contendo
    * os caracteres presentes na requisição.
    * */
    @Test
    public void deveRetornarMotivosDeRetornoPorDescricao() throws Exception{
        List<MotivoRetorno> motivosRetornoPaged = Arrays.asList(
                                                        MotivoRetornoMocks.gerarMockListMotivosRetorno().get(2),
                                                        MotivoRetornoMocks.gerarMockListMotivosRetorno().get(3));
        Page<MotivoRetorno> motivosRetorno = new PageImpl<>(motivosRetornoPaged);
        when(motivoRetornoService.buscarMotivoDeRetornoPorDescricao("mo",0,5))
                .thenReturn(motivosRetorno);

        MockHttpServletResponse response = realizarRequisicao.GetPaginadoByDescricao(path+"descricao", "mo",
                "0", "5");
        JSONArray motivosDeRetorno = new JSONArray(new JSONObject(response.getContentAsString(StandardCharsets.UTF_8)).get("content").toString());

        Assert.assertEquals(
                motivosDeRetorno.getJSONObject(0).get("descricao"),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(2).getDescricao());
        Assert.assertEquals(
                motivosDeRetorno.getJSONObject(1).get("descricao"),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(3).getDescricao());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    /*
     * Dado uma descrição vazia, deve ser chamado o método para listar todos os motivos
     * de retorno habilitados.
     * */
    @Test
    public void deveRetornarMotivosHabilitadosQuandoDescricaoForVazia() throws Exception{
        when(motivoRetornoService.buscarMotivosDeRetornoHabilitados(0,5)).thenReturn(new PageImpl<>(motivosRetornoHabilitados));

        MockHttpServletResponse response = realizarRequisicao.GetPaginadoByDescricao(path+"descricao", "",
                "0", "5");

        JSONArray motivosDeRetorno = new JSONArray(new JSONObject(response.getContentAsString(StandardCharsets.UTF_8)).get("content").toString());

        Assert.assertEquals(motivosDeRetorno.length(), 3L);
        Assert.assertEquals(motivosDeRetorno.getJSONObject(0).getString("descricao"), motivosRetornoHabilitados.get(0).getDescricao());
        Assert.assertTrue(motivosDeRetorno.getJSONObject(2).getBoolean("isAtivo"));
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
        JSONArray motivosDeRetorno = new JSONArray(new JSONObject(response.getContentAsString(StandardCharsets.UTF_8)).get("content").toString());

        Assert.assertEquals(
                motivosDeRetorno.getJSONObject(0).get("descricao"),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(0).getDescricao());
        Assert.assertFalse(motivosDeRetorno.getJSONObject(0).getBoolean("isAtivo"));
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    /*
    * Dada a requisição abaixo, o status http deve ser 204 NO_CONTENT ao deletar todos os motivos de retorno desabilitados.
    * */
    @Test
    public void deveRetornarNoContentAoDeletarTodosMotivosDeRetornoDesabilitados() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Delete(path+"desabilitados");

        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
}