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
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    @Before
    public void setUp() {
        realizarRequisicao.setMockMvc(mockMvc);
        tipoRetorno = TipoRetornoMocks.gerarMockTipoRetorno();
    }

    /*
    * Dado um tipo de retorno válido, o status http deve ser 200 OK ao inserir tipo de retorno com sucesso.
    * */
    @Test
    public void deveRetornar200OkAoInserirTipoDeRetorno() throws Exception {
        when(tipoRetornoService.salvarTipoDeRetorno(Mockito.any())).thenReturn(tipoRetorno);
        String tipoDeRetorno = "{\"descricao\": \"InMail\"}";

        MockHttpServletResponse response = realizarRequisicao.Post(path, tipoDeRetorno);

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

}
