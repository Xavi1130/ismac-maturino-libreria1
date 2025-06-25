package com.distribuida.controller;

import com.distribuida.model.Autor;
import com.distribuida.model.Categoria;
import com.distribuida.model.Libro;
import com.distribuida.service.LibroService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibroController.class)
public class LibroControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibroService libroService;

    @Autowired
    private ObjectMapper objectMapper;

    private Libro crearLibroMock() {
        Libro libro = new Libro();
        libro.setTitulo("Java Básico");
        libro.setPrecio(25.5);

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        libro.setCategoria(categoria);

        Autor autor = new Autor();
        autor.setIdAutor(1);
        libro.setAutor(autor);

        return libro;
    }

    @Test
    public void testFindAll() throws Exception {
        Libro libro = crearLibroMock();

        Mockito.when(libroService.findAll()).thenReturn(List.of(libro));

        mockMvc.perform(get("/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Java Básico"))
                .andExpect(jsonPath("$[0].precio").value(25.5))
                .andExpect(jsonPath("$[0].categoria.idCategoria").value(1))
                .andExpect(jsonPath("$[0].autor.idAutor").value(1));
    }

    @Test
    public void testFindOneExistente() throws Exception {
        Libro libro = crearLibroMock();

        Mockito.when(libroService.findOne(1)).thenReturn(libro);

        mockMvc.perform(get("/libros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Java Básico"))
                .andExpect(jsonPath("$.precio").value(25.5))
                .andExpect(jsonPath("$.categoria.idCategoria").value(1))
                .andExpect(jsonPath("$.autor.idAutor").value(1));
    }

    @Test
    public void testFindOneNoExistente() throws Exception {
        Mockito.when(libroService.findOne(99)).thenReturn(null);

        mockMvc.perform(get("/libros/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSave() throws Exception {
        Libro libroRequest = crearLibroMock();
        Libro libroResponse = crearLibroMock();

        Mockito.when(libroService.save(any(Libro.class))).thenReturn(libroResponse);

        mockMvc.perform(post("/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libroRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Java Básico"))
                .andExpect(jsonPath("$.precio").value(25.5))
                .andExpect(jsonPath("$.categoria.idCategoria").value(1))
                .andExpect(jsonPath("$.autor.idAutor").value(1));
    }

    @Test
    public void testUpdateExistente() throws Exception {
        Libro libroRequest = crearLibroMock();
        Libro libroResponse = crearLibroMock();

        Mockito.when(libroService.update(eq(1), eq(1), eq(1), any(Libro.class)))
                .thenReturn(libroResponse);

        mockMvc.perform(put("/libros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libroRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Java Básico"))
                .andExpect(jsonPath("$.precio").value(25.5))
                .andExpect(jsonPath("$.categoria.idCategoria").value(1))
                .andExpect(jsonPath("$.autor.idAutor").value(1));
    }

    @Test
    public void testUpdateNoExistente() throws Exception {
        Libro libroRequest = crearLibroMock();

        Mockito.when(libroService.update(eq(99), eq(1), eq(1), any(Libro.class)))
                .thenReturn(null);

        mockMvc.perform(put("/libros/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libroRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/libros/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(libroService, Mockito.times(1)).delete(1);
    }
}


