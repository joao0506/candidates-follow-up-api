package br.com.candidatesfollowup.resources;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import br.com.candidatesfollowup.services.CandidatoRetornoService;
import br.com.candidatesfollowup.utils.CandidatoRetornoMocks;
import br.com.candidatesfollowup.utils.RealizarRequisicao;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CandidatoRetornoResource.class)
@ContextConfiguration(classes= {CandidatoRetornoResource.class})
@AutoConfigureMockMvc
public class CandidatoRetornoResourceTest {

    @Autowired
    private CandidatoRetornoResource candidatoRetornoResource;

    @MockBean
    private CandidatoRetornoService candidatoRetornoService;

    @Autowired
    private MockMvc mockMvc;

    private RealizarRequisicao realizarRequisicao = new RealizarRequisicao();
    private static String path = "/candidato-retorno/";
    private static CandidatoRetorno candidatoRetorno;
    private static List<CandidatoRetorno> candidatosRetornoHabilitados, candidatosRetornoDesabilitados;

    @Before
    public void setUp(){
        realizarRequisicao.setMockMvc(mockMvc);
        candidatoRetorno = CandidatoRetornoMocks.gerarMockCandidatoRetorno();
        candidatosRetornoHabilitados = CandidatoRetornoMocks.gerarMockListCandidatoRetornoHabilitados();
        candidatosRetornoDesabilitados = CandidatoRetornoMocks.gerarMockListCandidatoRetornoDesabilitados();
    }

    /*
    * Dado um retorno de candidato válido,
    * deve retornar 201 CREATED ao inserir com sucesso o retorno para o candidato.
    * */
    @Test
    public void deveRetornar201CreatedAoInserirORetornoDoCandidato() throws Exception {
        when(candidatoRetornoService.salvarCandidatoRetorno(any())).thenReturn(candidatoRetorno);
        String candidatoRetornoRequest = "{    \"nomeCandidato\": \"José da Silva\",    \"dataRetorno\": \"2022-08-08\",   " +
                " \"motivoRetorno\": [        {            \"id\": 1,            \"descricao\": \"Remuneração\",            \"isAtivo\": true        }    ],   " +
                " \"tipoRetorno\": {        \"id\": 2,        \"descricao\": \"InMail\",        \"isAtivo\": true    }}";

        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Post(path, candidatoRetornoRequest);
        Assert.assertEquals(HttpStatus.CREATED.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado um retorno de candidato válido com tamanho do nome do candidato igual ao mínimo permitido (2 caracteres),
     * deve retornar 201 CREATED ao inserir com sucesso o retorno para o candidato.
     * */
    @Test
    public void deveRetornar201CreatedAoInserirORetornoDoCandidatoComNomeMinimoPermitido() throws Exception {
        when(candidatoRetornoService.salvarCandidatoRetorno(any())).thenReturn(candidatoRetorno);
        String candidatoRetornoRequest = "{    \"nomeCandidato\": \"aa\",    \"dataRetorno\": \"2022-08-08\",   " +
                " \"motivoRetorno\": [        {            \"id\": 1,            \"descricao\": \"Remuneração\",            \"isAtivo\": true        }    ],   " +
                " \"tipoRetorno\": {        \"id\": 2,        \"descricao\": \"InMail\",        \"isAtivo\": true    }}";

        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Post(path, candidatoRetornoRequest);
        Assert.assertEquals(HttpStatus.CREATED.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado um retorno de candidato válido com tamanho do nome do candidato igual ao máximo permitido (80 caracteres),
     * deve retornar 201 CREATED ao inserir com sucesso o retorno para o candidato.
     * */
    @Test
    public void deveRetornar201CreatedAoInserirORetornoDoCandidatoComNomeMaximoPermitido() throws Exception {
        when(candidatoRetornoService.salvarCandidatoRetorno(any())).thenReturn(candidatoRetorno);
        String candidatoRetornoRequest = "{    \"nomeCandidato\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",    \"dataRetorno\": \"2022-08-08\",   " +
                " \"motivoRetorno\": [        {            \"id\": 1,            \"descricao\": \"Remuneração\",            \"isAtivo\": true        }    ],   " +
                " \"tipoRetorno\": {        \"id\": 2,        \"descricao\": \"InMail\",        \"isAtivo\": true    }}";

        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Post(path, candidatoRetornoRequest);
        Assert.assertEquals(HttpStatus.CREATED.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado um retorno de candidato inválido com tamanho do nome do candidato menor que o permitido (2 caracteres),
     * deve retornar 400 BAD REQUEST ao inserir o retorno para o candidato.
     * */
    @Test
    public void deveRetornar400BadRequestAoInserirORetornoDoCandidatoComNomeMenorQueOPermitido() throws Exception {
        when(candidatoRetornoService.salvarCandidatoRetorno(any())).thenReturn(candidatoRetorno);
        String candidatoRetornoRequest = "{    \"nomeCandidato\": \"a\",    \"dataRetorno\": \"2022-08-08\",   " +
                " \"motivoRetorno\": [        {            \"id\": 1,            \"descricao\": \"Remuneração\",            \"isAtivo\": true        }    ],   " +
                " \"tipoRetorno\": {        \"id\": 2,        \"descricao\": \"InMail\",        \"isAtivo\": true    }}";

        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Post(path, candidatoRetornoRequest);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado um retorno de candidato inválido com tamanho do nome do candidato maior que o permitido (80 caracteres),
     * deve retornar 400 BAD REQUEST ao inserir o retorno para o candidato.
     * */
    @Test
    public void deveRetornar400BadRequestAoInserirORetornoDoCandidatoComNomeMaiorQueOPermitido() throws Exception {
        when(candidatoRetornoService.salvarCandidatoRetorno(any())).thenReturn(candidatoRetorno);
        String candidatoRetornoRequest = "{    \"nomeCandidato\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",    \"dataRetorno\": \"2022-08-08\",   " +
                " \"motivoRetorno\": [        {            \"id\": 1,            \"descricao\": \"Remuneração\",            \"isAtivo\": true        }    ],   " +
                " \"tipoRetorno\": {        \"id\": 2,        \"descricao\": \"InMail\",        \"isAtivo\": true    }}";

        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Post(path, candidatoRetornoRequest);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado um retorno de candidato inválido com nome do candidato vazio,
     * deve retornar 400 BAD REQUEST ao inserir o retorno para o candidato.
     * */
    @Test
    public void deveRetornar400BadRequestAoInserirORetornoDoCandidatoComNomeVazio() throws Exception {
        String candidatoRetornoRequest = "{    \"nomeCandidato\": \"\",    \"dataRetorno\": \"2022-08-08\",   " +
                " \"tipoRetorno\": {        \"id\": 2,        \"descricao\": \"InMail\",        \"isAtivo\": true    }}";

        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Post(path, candidatoRetornoRequest);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado um retorno de candidato inválido com data de retorno vazia,
     * deve retornar 400 BAD REQUEST ao inserir o retorno para o candidato.
     * */
    @Test
    public void deveRetornar400BadRequestAoInserirORetornoDoCandidatoComDataVazia() throws Exception {
        String candidatoRetornoRequest = "{    \"nomeCandidato\": \"José da Silva\",    \"dataRetorno\": \"\",   " +
                " \"tipoRetorno\": {        \"id\": 2,        \"descricao\": \"InMail\",        \"isAtivo\": true    }}";

        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Post(path, candidatoRetornoRequest);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado um retorno de candidato inválido sem um tipo de retorno informado,
     * deve retornar 400 BAD REQUEST ao inserir o retorno para o candidato.
     * */
    @Test
    public void deveRetornar400BadRequestAoInserirORetornoDoCandidatoSemTipoDeRetorno() throws Exception {
        String candidatoRetornoRequest = "{    \"nomeCandidato\": \"José da Silva\",    \"dataRetorno\": \"2022-08-08\"}";

        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Post(path, candidatoRetornoRequest);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado um retorno de candidato válido,
     * deve retornar 204 NO CONTENT ao atualizar com sucesso o retorno para o candidato.
     * */
    @Test
    public void deveRetornar204NoContentAoAtualizarORetornoDoCandidato() throws Exception {
        when(candidatoRetornoService.salvarCandidatoRetorno(any())).thenReturn(candidatoRetorno);
        when(candidatoRetornoService.fromDTO(any())).thenReturn(candidatoRetorno);

        String candidatoRetornoRequest = "{    \"nomeCandidato\": \"José da Silva\",    \"dataRetorno\": \"2022-08-08\",   " +
                " \"motivoRetorno\": [        {            \"id\": 1,            \"descricao\": \"Remuneração\",            \"isAtivo\": true        }    ],   " +
                " \"tipoRetorno\": {        \"id\": 2,        \"descricao\": \"InMail\",        \"isAtivo\": true    }}";

        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Put(path+"/1", candidatoRetornoRequest);
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado a requisição abaixo,
     * deve retornar 204 NO CONTENT ao desabilitar com sucesso todos os retornos de candidato Habilitados.
     * */
    @Test
    public void deveRetornar204NoContentAoDesabilitarTodosOsRetornosDeCandidatoHabilitados() throws Exception {
        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Put(path+"desabilitar","");

        verify(candidatoRetornoService, times(1)).desabilitarTodosRetornosCandidatosHabilitados();
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado um código de retorno de candidato válido,
     * deve retornar 204 NO CONTENT ao desabilitar com sucesso o retorno do candidato.
     * */
    @Test
    public void deveRetornar204NoContentAoDesabilitarORetornoDoCandidato() throws Exception {
        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Put(path+"desabilitar/1", "");

        verify(candidatoRetornoService, times(1)).desabilitarRetornoCandidato(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado a requisição abaixo,
     * deve retornar 204 NO CONTENT ao habilitar com sucesso todos os retornos de candidato desabilitados.
     * */
    @Test
    public void deveRetornar204NoContentAoHabilitarTodosOsRetornosDeCandidatoDesabilitados() throws Exception {
        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Put(path+"habilitar","");

        verify(candidatoRetornoService, times(1)).habilitarTodosRetornosCandidatosDesabilitados();
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado um código de retorno de candidato válido,
     * deve retornar 204 NO CONTENT ao habilitar com sucesso o retorno do candidato.
     * */
    @Test
    public void deveRetornar204NoContentAoHabilitarORetornoDoCandidato() throws Exception {
        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Put(path+"habilitar/1", "");

        verify(candidatoRetornoService, times(1)).habilitarRetornoCandidato(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado um código de retorno de candidato válido,
     * deve retornar 204 NO CONTENT ao marcar com sucesso o retorno do candidato como contatado.
     * */
    @Test
    public void deveRetornar204NoContentAoContatarORetornoDoCandidato() throws Exception {
        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Put(path+"contatado/1", "");

        verify(candidatoRetornoService, times(1)).marcarRetornoCandidatoComoContatado(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado um código de retorno de candidato válido,
     * deve retornar 204 NO CONTENT ao marcar com sucesso o retorno do candidato como não contatado.
     * */
    @Test
    public void deveRetornar204NoContentAoNaoContatarORetornoDoCandidato() throws Exception {
        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Put(path+"descontatado/1", "");

        verify(candidatoRetornoService, times(1)).marcarRetornoCandidatoComoNaoContatado(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado um código de retorno de candidato válido,
     * deve retornar 204 NO CONTENT ao deletar com sucesso o retorno do candidato.
     * */
    @Test
    public void deveRetornar204NoContentAoDeletarORetornoDoCandidato() throws Exception {
        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Delete(path+"/1");

        verify(candidatoRetornoService, times(1)).deletarRetornoCandidato(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado a requisição abaixo,
     * deve retornar 204 NO CONTENT ao deletar com sucesso todos os retornos de candidato desabilitados.
     * */
    @Test
    public void deveRetornar204NoContentAoDeletarTodosOsRetornosDoCandidatoDesabilitados() throws Exception {
        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Delete(path+"/desabilitados");

        verify(candidatoRetornoService, times(1)).deletarRetornoCandidatoDesabilitados();
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), candidatoRetornoResponse.getStatus());
    }

    /*
     * Dado a requisição abaixo,
     * deve retornar 200 OK ao listar com sucesso todos os retornos de candidato habilitados.
     * */
    @Test
    public void deveRetornar200OkAoListarTodosOsRetornosDoCandidatoHabilitados() throws Exception {
        when(candidatoRetornoService.buscarTodosRetornoCandidatoHabilitados(0, 5)).thenReturn(new PageImpl<>(candidatosRetornoHabilitados));
        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.GetPaginado(path, "0", "5");

        JSONArray response = CandidatoRetornoMocks.convertMockHttpServletResponseJson(candidatoRetornoResponse);

        verify(candidatoRetornoService, times(1)).buscarTodosRetornoCandidatoHabilitados(0,5);
        Assert.assertEquals(candidatosRetornoHabilitados.size(), response.length());
        Assert.assertTrue(response.getJSONObject(0).getBoolean("isAtivo"));
    }

    /*
     * Dado a requisição abaixo,
     * deve retornar 200 OK ao listar com sucesso todos os retornos de candidato desabilitados.
     * */
    @Test
    public void deveRetornar200OkAoListarTodosOsRetornosDoCandidatoDesabilitados() throws Exception {
        when(candidatoRetornoService.buscarTodosRetornoCandidatoDesabilitados(0, 5)).thenReturn(new PageImpl<>(candidatosRetornoDesabilitados));
        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.GetPaginado(path+"/desabilitados", "0", "5");

        JSONArray response = CandidatoRetornoMocks.convertMockHttpServletResponseJson(candidatoRetornoResponse);

        verify(candidatoRetornoService, times(1)).buscarTodosRetornoCandidatoDesabilitados(0,5);
        Assert.assertEquals(candidatosRetornoDesabilitados.size(), response.length());
        Assert.assertFalse(response.getJSONObject(0).getBoolean("isAtivo"));
    }

    /*
     * Dado um id válido,
     * deve retornar 200 OK ao listar com sucesso o retorno de candidado do id informado.
     * */
    @Test
    public void deveRetornar200OkAoListarPorId() throws Exception {
        Integer id = 2;
        when(candidatoRetornoService.buscarCandidatoRetornoPorId(id)).thenReturn(candidatosRetornoHabilitados.get(1));
        MockHttpServletResponse candidatoRetornoResponse = realizarRequisicao.Get(path+"/"+id);

        JSONObject response = CandidatoRetornoMocks.convertMockHttpServletResponseJsonObject(candidatoRetornoResponse);

        verify(candidatoRetornoService, times(1)).buscarCandidatoRetornoPorId(id);
        Assert.assertEquals(id, response.get("id"));
        Assert.assertTrue(response.getBoolean("isAtivo"));
    }

}
