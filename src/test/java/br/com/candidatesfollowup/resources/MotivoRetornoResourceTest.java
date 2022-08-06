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
import org.springframework.context.annotation.ComponentScan;
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

    @Before
    public void setUp(){
        realizarRequisicao.setMockMvc(mockMvc);
        motivoRetorno = MotivoRetornoMocks.gerarMockMotivoRetorno();
    }

    @Test
    public void deveRetornarOkAoTestarPostMotivoDeRetorno() throws Exception{
        when(motivoRetornoService.salvarMotivoRetorno(Mockito.any())).thenReturn(motivoRetorno);
        String motivoDeRetorno = "{\"descricao\": \"Remuneração\"}";

        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void deveRetornarErro400AoTestarPostComDescricaoNula() throws Exception{
        String motivoDeRetorno = "{\"descricao\": null}";
        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void deveRetornarErro400AoTestarPostComDescricaoVazia() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"\"}";
        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void deveRetornarErro400AoTestarPostComDescricaoComTamanhoMenorQueOValido() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"a\"}";
        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void deveRetornarErro400AoTestarPostComDescricaoComTamanhoMaiorQueOValido() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";
        MockHttpServletResponse response = realizarRequisicao.Post(path, motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoAtualizarMotivoDeRetorno() throws Exception {
        when(motivoRetornoService.fromDTO(any())).thenReturn(motivoRetorno);

        String motivoDeRetorno = "{\"descricao\": \"Teste Update\"}";
        MockHttpServletResponse response = realizarRequisicao.Put(path + "1",motivoDeRetorno);
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoDesabilitarMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path + "desabilitar/1", "");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoDesabilitarTodosMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path+"desabilitar/", "");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoHabilitarMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path + "habilitar/2", "");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoHabilitarTodosMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path + "habilitar/", "");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoDeletarMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Delete(path+"2");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deveRetornarMotivoDeRetornoPorId() throws Exception{
        when(motivoRetornoService.buscarMotivoDeRetornoPorId(1)).thenReturn(motivoRetorno);

        MockHttpServletResponse response = realizarRequisicao.Get(path+"1");

        Assert.assertEquals(response.getContentAsString(StandardCharsets.UTF_8), motivoRetorno.toString());
    }

    @Test
    public void deveRetornarTodosMotivoDeRetornoHabilitados() throws Exception{
        Page<MotivoRetorno> motivosRetorno = new PageImpl<>(MotivoRetornoMocks.gerarMockListMotivosRetorno());
        when(motivoRetornoService.buscarMotivosDeRetornoHabilitados(0,5)).thenReturn(motivosRetorno);

        MockHttpServletResponse response = realizarRequisicao.GetPaginado(path+"", "0", "5");

        JSONArray motivosDeRetorno = new JSONArray(new JSONObject(response.getContentAsString(StandardCharsets.UTF_8)).get("content").toString());
        Assert.assertEquals(
                motivosDeRetorno.getJSONObject(1).get("descricao"),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(1).getDescricao());

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

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

    @Test
    public void deveRetornarMotivosDeRetornoDesabilitados() throws Exception{
        List<MotivoRetorno> motivosRetornoPaged = Arrays.asList(MotivoRetornoMocks.gerarMockListMotivosRetorno().get(0));
        Page<MotivoRetorno> motivosRetorno = new PageImpl<>(motivosRetornoPaged);
        when(motivoRetornoService.buscarMotivosDeRetornoDesabilitados(0,5))
                .thenReturn(motivosRetorno);

        MockHttpServletResponse response = realizarRequisicao.GetPaginado(path+"/desabilitados", "0", "5");
        JSONArray motivosDeRetorno = new JSONArray(new JSONObject(response.getContentAsString(StandardCharsets.UTF_8)).get("content").toString());

        Assert.assertEquals(
                motivosDeRetorno.getJSONObject(0).get("descricao"),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(0).getDescricao());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoDeletarTodosMotivosDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Delete(path+"desabilitados");

        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
}