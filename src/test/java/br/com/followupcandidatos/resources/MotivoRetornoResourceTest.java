package br.com.followupcandidatos.resources;

import br.com.followupcandidatos.domain.MotivoRetorno;
import br.com.followupcandidatos.services.MotivoRetornoService;
import br.com.followupcandidatos.utils.MotivoRetornoMocks;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class MotivoRetornoResourceTest {

    @Autowired
    private MotivoRetornoResource motivoRetornoResource;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MotivoRetornoService motivoRetornoService;

    public MockHttpServletResponse realizarRequisicaoPost(String motivoRetornoDTO) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post("/motivo-retorno/")
                .accept(MediaType.APPLICATION_JSON)
                .content(motivoRetornoDTO)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
    }

    public MockHttpServletResponse realizarRequisicaoPut(String motivoRetornoDTO, String path) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .put("/motivo-retorno/"+path)
                .accept(MediaType.APPLICATION_JSON)
                .content(motivoRetornoDTO)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
    }

    public MockHttpServletResponse realizarRequisicaoGet(String path) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                        .get("/motivo-retorno/"+path)
                        .param("page","0")
                        .param("linesPerPage", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
    }

    public MockHttpServletResponse realizarRequisicaoGetComPaginacao(String path, String page, String linesPerPage) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                        .get("/motivo-retorno/"+path)
                        .param("page",page)
                        .param("linesPerPage", linesPerPage)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
    }

    @Test
    public void deveRetornarOkAoTestarPostMotivoDeRetorno() throws Exception{
        when(motivoRetornoService.salvarMotivoRetorno(Mockito.any())).thenReturn(MotivoRetornoMocks.gerarMockMotivoRetorno());

        String motivoDeRetorno = "{\"descricao\": \"Remuneração\"}";
        MockHttpServletResponse response = realizarRequisicaoPost(motivoDeRetorno);
        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void deveRetornarErro400AoTestarPostComDescricaoNula() throws Exception{
        String motivoDeRetorno = "{\"descricao\": null}";
        MockHttpServletResponse response = realizarRequisicaoPost(motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void deveRetornarErro400AoTestarPostComDescricaoVazia() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"\"}";
        MockHttpServletResponse response = realizarRequisicaoPost(motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void deveRetornarErro400AoTestarPostComDescricaoComTamanhoMenorQueOValido() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"a\"}";
        MockHttpServletResponse response = realizarRequisicaoPost(motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void deveRetornarErro400AoTestarPostComDescricaoComTamanhoMaiorQueOValido() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";
        MockHttpServletResponse response = realizarRequisicaoPost(motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoAtualizarMotivoDeRetorno() throws Exception {
        when(motivoRetornoService.fromDTO(any())).thenReturn(MotivoRetornoMocks.gerarMockMotivoRetorno());

        String motivoDeRetorno = "{\"descricao\": \"Teste Update\"}";
        MockHttpServletResponse response = realizarRequisicaoPut(motivoDeRetorno,"1");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoDesabilitarMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicaoPut("","desabilitar/1");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoDesabilitarTodosMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicaoPut("","desabilitar/");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoHabilitarMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicaoPut("","habilitar/2");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoHabilitarTodosMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicaoPut("","habilitar/");
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoDeletarMotivoDeRetorno() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/motivo-retorno/"+2)).andReturn().getResponse();

        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deveRetornarMotivoDeRetornoPorId() throws Exception{
        when(motivoRetornoService.buscarMotivoDeRetornoPorId(1)).thenReturn(MotivoRetornoMocks.gerarMockMotivoRetorno());

        MockHttpServletResponse response = realizarRequisicaoGet("1");
        Assert.assertEquals(response.getContentAsString(StandardCharsets.UTF_8), "{\"id\":1,\"descricao\":\"Remuneração\",\"isAtivo\":true}");
    }

    @Test
    public void deveRetornarTodosMotivoDeRetorno() throws Exception{
        Page<MotivoRetorno> motivosRetorno = new PageImpl<>(MotivoRetornoMocks.gerarMockListMotivosRetorno());
        when(motivoRetornoService.buscarMotivosDeRetorno(0,5)).thenReturn(motivosRetorno);

        MockHttpServletResponse response = realizarRequisicaoGet("");
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

        MockHttpServletResponse response = realizarRequisicaoGet("descricao/mo");
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

        MockHttpServletResponse response = realizarRequisicaoGet("/desabilitados");
        JSONArray motivosDeRetorno = new JSONArray(new JSONObject(response.getContentAsString(StandardCharsets.UTF_8)).get("content").toString());

        Assert.assertEquals(
                motivosDeRetorno.getJSONObject(0).get("descricao"),
                MotivoRetornoMocks.gerarMockListMotivosRetorno().get(0).getDescricao());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void deveRetornarNoContentAoDeletarTodosMotivosDeRetorno() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/motivo-retorno/disabled")).andReturn().getResponse();

        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

}