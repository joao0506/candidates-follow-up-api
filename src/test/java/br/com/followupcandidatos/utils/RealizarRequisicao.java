package br.com.followupcandidatos.utils;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class RealizarRequisicao {

    private MockMvc mockMvc;

    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public MockHttpServletResponse Get(String path) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                        .get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
    }

    public MockHttpServletResponse GetPaginado(String path, String page, String linesPerPage) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                        .get(path)
                        .param("page", page)
                        .param("linesPerPage", linesPerPage)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
    }

    public MockHttpServletResponse Post(String path, String body) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                        .post(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
    }

    public MockHttpServletResponse Put(String path, String body) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                        .put(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
    }

    public MockHttpServletResponse Delete(String path) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .delete(path)).andReturn().getResponse();
    }

}
