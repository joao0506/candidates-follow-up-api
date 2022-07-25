package br.com.followupcandidatos.resources;

import br.com.followupcandidatos.domain.MotivoRetorno;
import br.com.followupcandidatos.services.MotivoRetornoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

    public MotivoRetorno gerarMockMotivoRetorno(){
        return new MotivoRetorno(1, "Remuneração", true);
    }

    public MockHttpServletResponse realizarRequisicao(String motivoRetornoDTO) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/motivo-retorno/")
                .accept(MediaType.APPLICATION_JSON)
                .content(motivoRetornoDTO)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        return result.getResponse();
    }

    @Test
    public void deveRetornarOkAoTestarPostMotivoDeRetorno() throws Exception{
        Mockito.when(motivoRetornoService.salvarMotivoRetorno(Mockito.any())).thenReturn(gerarMockMotivoRetorno());

        String motivoDeRetorno = "{\"descricao\": \"Remuneração\"}";
        MockHttpServletResponse response = realizarRequisicao(motivoDeRetorno);
        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void deveRetornarErro400AoTestarPostComDescricaoNula() throws Exception{
        String motivoDeRetorno = "{\"descricao\": null}";
        MockHttpServletResponse response = realizarRequisicao(motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void deveRetornarErro400AoTestarPostComDescricaoVazia() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"\"}";
        MockHttpServletResponse response = realizarRequisicao(motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void deveRetornarErro400AoTestarPostComDescricaoComTamanhoMenorQueOValido() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"a\"}";
        MockHttpServletResponse response = realizarRequisicao(motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void deveRetornarErro400AoTestarPostComDescricaoComTamanhoMaiorQueOValido() throws Exception{
        String motivoDeRetorno = "{\"descricao\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";
        MockHttpServletResponse response = realizarRequisicao(motivoDeRetorno);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }


}
