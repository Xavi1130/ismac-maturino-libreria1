package com.distribuida.controller;

import com.distribuida.model.Autor;
import com.distribuida.model.Categoria;
import com.distribuida.model.Libro;
import com.distribuida.service.LibroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LibroControllerTestUnitaria {

    @InjectMocks
    private LibroController libroController;

    @Mock
    private LibroService libroService;

    private Libro libro;
    private Categoria categoria;
    private Autor autor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        categoria = new Categoria();
        categoria.setIdCategoria(1);

        autor = new Autor();
        autor.setIdAutor(1);

        libro = new Libro();
        libro.setCategoria(categoria);
        libro.setAutor(autor);
    }

    @Test
    public void testFindAll() {
        when(libroService.findAll()).thenReturn(List.of(libro));
        ResponseEntity<List<Libro>> response = libroController.findAll();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(libroService, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente() {
        when(libroService.findOne(1)).thenReturn(libro);
        ResponseEntity<Libro> response = libroController.findOne(1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getCategoria().getIdCategoria());
        assertEquals(1, response.getBody().getAutor().getIdAutor());
    }

    @Test
    public void testFindOneNoExistente() {
        when(libroService.findOne(2)).thenReturn(null);
        ResponseEntity<Libro> response = libroController.findOne(2);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testSave() {
        when(libroService.save(any(Libro.class))).thenReturn(libro);
        ResponseEntity<Libro> response = libroController.save(libro);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getAutor().getIdAutor());
    }

    @Test
    public void testUpdateExistente() {
        when(libroService.update(eq(1), eq(1), eq(1), any(Libro.class))).thenReturn(libro);
        ResponseEntity<Libro> response = libroController.update(1, libro);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testUpdateNoExistente() {
        when(libroService.update(eq(2), eq(1), eq(1), any(Libro.class))).thenReturn(null);
        ResponseEntity<Libro> response = libroController.update(2, libro);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        doNothing().when(libroService).delete(1);
        ResponseEntity<Void> response = libroController.delete(1);
        assertEquals(204, response.getStatusCodeValue());
        verify(libroService, times(1)).delete(1);
    }
}


