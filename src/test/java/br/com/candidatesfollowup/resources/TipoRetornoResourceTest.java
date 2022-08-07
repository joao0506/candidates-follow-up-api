package br.com.candidatesfollowup.resources;

import br.com.candidatesfollowup.domain.TipoRetorno;
import br.com.candidatesfollowup.services.TipoRetornoService;
import br.com.candidatesfollowup.utils.RealizarRequisicao;
import br.com.candidatesfollowup.utils.TipoRetornoMocks;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(TipoRetornoResource.class)
@ContextConfiguration(classes= {TipoRetornoResource.class})
@AutoConfigureMockMvc
public class TipoRetornoResourceTest {

    @Autowired
    private TipoRetornoResource tipoRetornoResource;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TipoRetornoService tipoRetornoService;

    private RealizarRequisicao realizarRequisicao = new RealizarRequisicao();

    private static String path = "/tipo-retorno/";

    private static TipoRetorno tipoRetorno;

    private static List<TipoRetorno> tipoRetornoHabilitados, tipoRetornoDesabilitados;

    @Before
    public void setUp() {
        realizarRequisicao.setMockMvc(mockMvc);
        tipoRetorno = TipoRetornoMocks.gerarMockTipoRetorno();
        tipoRetornoHabilitados = TipoRetornoMocks.gerarMockTipoRetornoListHabilitados();
        tipoRetornoDesabilitados = TipoRetornoMocks.gerarMockTipoRetornoListDesabilitados();
    }

    /*
    * Dado um Tipo de Retorno válido, o status http deve ser 201 CREATED ao inserir tipo de retorno com sucesso.
    * */
    @Test
    public void deveRetornar201CreatedAoInserirTipoDeRetorno() throws Exception {
        when(tipoRetornoService.salvarTipoDeRetorno(Mockito.any())).thenReturn(tipoRetorno);
        String tipoDeRetorno = "{\"descricao\": \"InMail\"}";

        MockHttpServletResponse response = realizarRequisicao.Post(path, tipoDeRetorno);

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    /*
     * Dado um Tipo de Retorno com 2 caracteres,
     * deve retornar 201 CREATED ao inserir com sucesso o tipo de retorno com tamanho mínimo permitido de caracteres.
     * */
    @Test
    public void deveRetornar201CreatedAoInserirTipoDeRetornoComDescricaoIgualAoMinimoPermitido() throws Exception {
        when(tipoRetornoService.salvarTipoDeRetorno(Mockito.any())).thenReturn(tipoRetorno);
        String tipoDeRetorno = "{\"descricao\": \"In\"}";

        MockHttpServletResponse response = realizarRequisicao.Post(path, tipoDeRetorno);

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    /*
     * Dado um Tipo de Retorno com 60 caracteres,
     * deve retornar 201 CREATED ao inserir com sucesso o tipo de retorno com o tamanho máximo permitido de caracteres.
     * */
    @Test
    public void deveRetornar201CreatedAoInserirTipoDeRetornoComDescricaoIgualAoMaximoPermitido() throws Exception {
        when(tipoRetornoService.salvarTipoDeRetorno(Mockito.any())).thenReturn(tipoRetorno);
        String tipoDeRetorno = "{\"descricao\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";

        MockHttpServletResponse response = realizarRequisicao.Post(path, tipoDeRetorno);

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    /*
     * Dado um Tipo de Retorno com descrição nula, o status http deve ser 400 BAD REQUEST ao inserir tipo de retorno
     * com descrição null.
     * */
    @Test
    public void deveRetornar400BadRequestAoInserirTipoDeRetornoComDescricaoNull() throws Exception {
        String tipoDeRetorno = "{\"descricao\": null}";

        MockHttpServletResponse response = realizarRequisicao.Post(path, tipoDeRetorno);

        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /*
     * Dado um Tipo de Retorno com descrição vazia, o status http deve ser 400 BAD REQUEST ao inserir tipo de retorno
     * com descrição vazia.
     * */
    @Test
    public void deveRetornar400BadRequestAoInserirTipoDeRetornoComDescricaoVazia() throws Exception {
        String tipoDeRetorno = "{\"descricao\": \"\"}";

        MockHttpServletResponse response = realizarRequisicao.Post(path, tipoDeRetorno);

        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /*
    * Dado um Tipo de Retorno com 1 caracter,
    * deve retornar 400 BAD REQUEST por não permitir descrição menor que o permitido de 2 caracteres.
    * */
    @Test
    public void deveRetornar400BadRequestAoInserirTipoDeRetornoComDescricaoMenorQueOPermitido() throws Exception {
        String tipoDeRetorno = "{\"descricao\": \"a\"}";

        MockHttpServletResponse response = realizarRequisicao.Post(path, tipoDeRetorno);

        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /*
     * Dado um Tipo de Retorno com 61 caracteres,
     * deve retornar 400 BAD REQUEST por não permitir descrição maior que 60 caracteres.
     * */
    @Test
    public void deveRetornar400BadRequestAoInserirTipoDeRetornoComDescricaoMaiorQueOPermitido() throws Exception {
        String tipoDeRetorno = "{\"descricao\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";

        MockHttpServletResponse response = realizarRequisicao.Post(path, tipoDeRetorno);

        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /*
    * Dado um tipo de retorno válido,
    * deve retornar 204 NO CONTENT ao atualizar um tipo de retorno com sucesso.
    */
    @Test
    public void deveRetornar204NoContentAoAtualizarUmTipoDeRetorno() throws Exception {
        tipoRetorno.setDescricao("LinkedIn");
        when(tipoRetornoService.fromDTO(Mockito.any())).thenReturn(tipoRetorno);

        String tipoDeRetorno = "{\"descricao\": \"LinkedIn\"}";

        MockHttpServletResponse response = realizarRequisicao.Put(path+"1", tipoDeRetorno);

        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
    * Dado o código de um tipo de retorno,
    * deve retornar 204 NO CONTENT ao desabilitar o tipo de retorno com sucesso.
    * */
    @Test
    public void deveRetornar204NoContentAoDesabilitarTipoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path + "desabilitar/1", "");

        Mockito.verify(tipoRetornoService, times(1)).desabilitarTipoDeRetorno(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado a requisição abaixo,
     * deve retornar 204 NO CONTENT ao desabilitar com sucesso todos os tipos de retorno habilitados;
     * */
    @Test
    public void deveRetornar204NoContentAoDesabilitarTodosOsTiposDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path + "desabilitar", "");

        Mockito.verify(tipoRetornoService, times(1)).desabilitarTodosTipoDeRetorno();
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado o código de um tipo de retorno,
     * deve retornar 204 NO CONTENT ao habilitar o tipo de retorno com sucesso.
     * */
    @Test
    public void deveRetornar204NoContentAoHabilitarTipoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path + "habilitar/1", "");

        Mockito.verify(tipoRetornoService, times(1)).habilitarTipoDeRetorno(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado a requisição abaixo,
     * deve retornar 204 NO CONTENT ao habilitar com sucesso todos os tipos de retorno desabilitados;
     * */
    @Test
    public void deveRetornar204NoContentAoHabilitarTodosOsTiposDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Put(path + "habilitar", "");

        Mockito.verify(tipoRetornoService, times(1)).habilitarTodosTipoDeRetorno();
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado o código de um tipo de retorno,
     * deve retornar 204 NO CONTENT ao deletar o tipo de retorno com sucesso.
     * */
    @Test
    public void deveRetornar204NoContentAoDeletarTipoDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Delete(path + "/1");

        Mockito.verify(tipoRetornoService, times(1)).deletarTipoDeRetorno(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*
     * Dado a requisição abaixo,
     * deve retornar 204 NO CONTENT ao deletar com sucesso todos os tipos de retorno desabilitados;
     * */
    @Test
    public void deveRetornar204NoContentAoDeletarTodosOsTiposDeRetorno() throws Exception {
        MockHttpServletResponse response = realizarRequisicao.Delete(path + "desabilitados");

        Mockito.verify(tipoRetornoService, times(1)).deletarTiposDeRetornoDesabilitados();
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }


    /*
    * Dado o código de um tipo de retorno,
    * deve retornar 200 OK com o tipo de retorno correspondente ao código informado (mock).
    * */
    @Test
    public void deveRetornarTipoDeRetornoPorId() throws Exception {
        when(tipoRetornoService.buscarTipoDeRetornoPorId(1)).thenReturn(tipoRetorno);

        MockHttpServletResponse response = realizarRequisicao.Get(path+"1");

        TipoRetorno tipoRetornoResponse = TipoRetornoMocks.convertMockHttpServletResponseToTipoRetorno(response);

        Assert.assertEquals(tipoRetorno.getId(), tipoRetornoResponse.getId());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    /*
     * Dado a requisição abaixo,
     * deve retornar 200 OK com a lista de Tipo de Retorno habilitados
     * */
    @Test
    public void deveRetornarTodosTiposDeRetornoHabilitados() throws Exception {
        when(tipoRetornoService.buscarTodosTiposDeRetornoHabilitados(0, 5)).thenReturn(new PageImpl<>(tipoRetornoHabilitados));

        MockHttpServletResponse response = realizarRequisicao.GetPaginado(path, "0", "5");

        List<TipoRetorno> tipoRetornoList = TipoRetornoMocks.convertMockHttpServletResponseToListTipoRetorno(response);

        Assert.assertEquals(tipoRetornoHabilitados.get(0).getDescricao(), tipoRetornoList.get(0).getDescricao());
        Assert.assertTrue(tipoRetornoList.get(1).getIsAtivo());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    /*
     * Dado a requisição abaixo,
     * deve retornar 200 OK com a lista de Tipo de Retorno que contém os caracteres
     * na descrição, mesmo que estejam desabilitados.
     * */
    @Test
    public void deveRetornarTodosOsMotivosDeRetornoContendoOsCaracteresNaDescricao() throws Exception {
        List<TipoRetorno> listTiposRetornoByDescricao = Arrays.asList(tipoRetornoHabilitados.get(0), tipoRetornoDesabilitados.get(0));
        when(tipoRetornoService.buscarTipoDeRetornoPorDescricao("mai", 0, 5)).thenReturn(new PageImpl<>(listTiposRetornoByDescricao));

        MockHttpServletResponse response = realizarRequisicao.GetPaginadoByDescricao(path+"/descricao","mai", "0", "5");
        List<TipoRetorno> tipoRetornoList = TipoRetornoMocks.convertMockHttpServletResponseToListTipoRetorno(response);

        Assert.assertEquals(listTiposRetornoByDescricao.get(0).getDescricao(), tipoRetornoList.get(0).getDescricao());
        Assert.assertFalse(tipoRetornoList.get(1).getIsAtivo());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    /*
     * Dado a requisição quando a descrição é vazia,
     * deve retornar 200 OK com a lista de Tipo de Retorno Habilitados.
     * */
    @Test
    public void deveRetornarTodosOsMotivosDeRetornoHabilitadosQuandoADescricaoEhVazia() throws Exception {
        when(tipoRetornoService.buscarTodosTiposDeRetornoHabilitados(0, 5)).thenReturn(new PageImpl<>(tipoRetornoHabilitados));

        MockHttpServletResponse response = realizarRequisicao.GetPaginadoByDescricao(path+"/descricao","", "0", "5");
        List<TipoRetorno> tipoRetornoList = TipoRetornoMocks.convertMockHttpServletResponseToListTipoRetorno(response);

        Assert.assertEquals(tipoRetornoHabilitados.get(0).getDescricao(), tipoRetornoList.get(0).getDescricao());
        Assert.assertTrue(tipoRetornoList.get(1).getIsAtivo());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    /*
    * Dado a requisição abaixo,
    * deve retornar 200 OK com a lista de tipos de retorno desabilitados
    * */
    @Test
    public void deveRetornarListaDeTiposDeRetornoDesabilitados() throws Exception {
        when(tipoRetornoService.buscarTodosTiposDeRetornoDesabilitados(0, 5)).thenReturn(new PageImpl<>(tipoRetornoDesabilitados));

        MockHttpServletResponse response = realizarRequisicao.GetPaginado(path+"/desabilitados","0", "5");
        List<TipoRetorno> tipoRetornoList = TipoRetornoMocks.convertMockHttpServletResponseToListTipoRetorno(response);

        Assert.assertEquals(tipoRetornoDesabilitados.get(0).getDescricao(), tipoRetornoList.get(0).getDescricao());
        Assert.assertFalse(tipoRetornoList.get(0).getIsAtivo());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
