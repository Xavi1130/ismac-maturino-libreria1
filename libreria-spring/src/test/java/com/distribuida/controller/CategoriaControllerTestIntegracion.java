package com.distribuida.controller;

import com.distribuida.model.Categoria;
import com.distribuida.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAll() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setDescripcion("Libros de tecnología");

        Mockito.when(categoriaService.findAll()).thenReturn(List.of(categoria));

        mockMvc.perform(get("/categoria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descripcion").value("Libros de tecnología"));
    }

    @Test
    public void testFindOneExistente() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setDescripcion("Historia");

        Mockito.when(categoriaService.findOne(1)).thenReturn(categoria);

        mockMvc.perform(get("/categoria/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Historia"));
    }

    @Test
    public void testFindOneNoExistente() throws Exception {
        Mockito.when(categoriaService.findOne(2)).thenReturn(null);

        mockMvc.perform(get("/categoria/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSave() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setDescripcion("Ciencia");

        Mockito.when(categoriaService.save(any(Categoria.class))).thenReturn(categoria);

        mockMvc.perform(post("/categoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Ciencia"));
    }

    @Test
    public void testUpdateExistente() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setDescripcion("Arte");

        Mockito.when(categoriaService.update(Mockito.eq(1), any(Categoria.class))).thenReturn(categoria);

        mockMvc.perform(put("/categoria/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Arte"));
    }

    @Test
    public void testUpdateNoExistente() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setDescripcion("No existe");

        Mockito.when(categoriaService.update(Mockito.eq(99), any(Categoria.class))).thenReturn(null);

        mockMvc.perform(put("/categoria/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/categoria/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(categoriaService, Mockito.times(1)).delete(1);
    }
}
