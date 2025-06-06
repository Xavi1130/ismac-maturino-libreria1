package com.distribuida.service;

import com.distribuida.dao.LibroRepository;
import com.distribuida.dao.CategoriaRepository;
import com.distribuida.dao.AutorRepository;
import com.distribuida.model.Libro;
import com.distribuida.model.Categoria;
import com.distribuida.model.Autor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibroServicioTestUnitaria {

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    private Libro libro;
    private Categoria categoria;
    private Autor autor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        categoria = new Categoria(1, "Ciencia Ficción", "Libros de ciencia ficción");
        autor = new Autor();

        libro = new Libro(
                1, "Fundación", "Minotauro", 300, "1ra", "Español",
                new Date(), "Una obra maestra", "Dura", "9788497594257", 5,
                "portada.jpg", "Tapa dura", 25.50, categoria, autor
        );
    }

    @Test
    public void testFindAll() {
        when(libroRepository.findAll()).thenReturn(Arrays.asList(libro));
        List<Libro> libros = libroService.findAll();
        assertNotNull(libros);
        assertEquals(1, libros.size());
        verify(libroRepository, times(1)).findAll();
    }

    @Test
    public void testFindOne() {
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        Libro resultado = libroService.findOne(1);
        assertNotNull(resultado);
        assertEquals("Fundación", resultado.getTitulo());
        verify(libroRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        when(libroRepository.save(libro)).thenReturn(libro);
        Libro guardado = libroService.save(libro);
        assertNotNull(guardado);
        assertEquals("Fundación", guardado.getTitulo());
        verify(libroRepository, times(1)).save(libro);
    }

    @Test
    public void testUpdate() {
        Libro libroActualizado = new Libro(
                1, "Fundación y Tierra", "Minotauro", 350, "2da", "Español",
                new Date(), "Secuela directa", "Blanda", "9788497594264", 7,
                "portada2.jpg", "Tapa blanda", 30.00, categoria, autor
        );

        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        when(libroRepository.save(any(Libro.class))).thenReturn(libroActualizado);
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));

        Libro actualizado = libroService.update(1, 1, 1, libroActualizado);

        assertNotNull(actualizado);
        assertEquals("Fundación y Tierra", actualizado.getTitulo());
        assertEquals(30.00, actualizado.getPrecio());
        verify(libroRepository, times(1)).save(any(Libro.class));
    }

    @Test
    public void testDelete() {
        when(libroRepository.existsById(1)).thenReturn(false);
        libroService.delete(1);
        verify(libroRepository, times(0)).deleteById(1);
    }
}
