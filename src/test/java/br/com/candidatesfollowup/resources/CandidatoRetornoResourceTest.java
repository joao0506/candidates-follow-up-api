package br.com.candidatesfollowup.resources;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import br.com.candidatesfollowup.services.CandidatoRetornoService;
import br.com.candidatesfollowup.utils.CandidatoRetornoMocks;
import br.com.candidatesfollowup.utils.RealizarRequisicao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    @Before
    public void setUp(){
        realizarRequisicao.setMockMvc(mockMvc);
        candidatoRetorno = CandidatoRetornoMocks.gerarMockCandidatoRetorno();
    }

    /*
    * Dado um retorno de candidato válido,
    * deve retornar 201 CREATED ao inserir com sucesso o retorno para o candidato.
    * */
    @Test
    public void deveRetornar201CreatedAoInserirORetornoDoCandidato() throws Exception {
        when(candidatoRetornoService.salvarRetornoCandidato(any())).thenReturn(candidatoRetorno);
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
        when(candidatoRetornoService.salvarRetornoCandidato(any())).thenReturn(candidatoRetorno);
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
        when(candidatoRetornoService.salvarRetornoCandidato(any())).thenReturn(candidatoRetorno);
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
        when(candidatoRetornoService.salvarRetornoCandidato(any())).thenReturn(candidatoRetorno);
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
        when(candidatoRetornoService.salvarRetornoCandidato(any())).thenReturn(candidatoRetorno);
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
}
