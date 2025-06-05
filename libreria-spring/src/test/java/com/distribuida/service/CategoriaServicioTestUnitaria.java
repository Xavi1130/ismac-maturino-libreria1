package com.distribuida.service;

import com.distribuida.dao.CategoriaRepository;
import com.distribuida.model.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaServicioTestUnitaria {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    private Categoria categoria;

    @BeforeEach
    public void setUp() {
        categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setCategoria("Novela");
        categoria.setDescripcion("Literatura narrativa extensa.");
    }

    @Test
    public void testFindAll() {
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));
        List<Categoria> categorias = categoriaService.findAll();
        assertNotNull(categorias);
        assertEquals(1, categorias.size());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        Categoria resultado = categoriaService.findOne(1);
        assertNotNull(resultado);
        assertEquals("Novela", resultado.getCategoria());
    }

    @Test
    public void testFindOneNoExistente() {
        when(categoriaRepository.findById(2)).thenReturn(Optional.empty());
        Categoria resultado = categoriaService.findOne(2);
        assertNull(resultado);
    }

    @Test
    public void testSave() {
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        Categoria resultado = categoriaService.save(categoria);
        assertNotNull(resultado);
        assertEquals("Novela", resultado.getCategoria());
    }

    @Test
    public void testUpdateExistente() {
        Categoria categoriaActualizada = new Categoria();
        categoriaActualizada.setCategoria("Poesía");
        categoriaActualizada.setDescripcion("Obras líricas en verso.");

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any())).thenReturn(categoriaActualizada);

        Categoria resultado = categoriaService.update(1, categoriaActualizada);
        assertNotNull(resultado);
        assertEquals("Poesía", resultado.getCategoria());
        verify(categoriaRepository, times(1)).save(categoria);
    }

    @Test
    public void testUpdateNoExistente() {
        Categoria nuevaCategoria = new Categoria();
        when(categoriaRepository.findById(2)).thenReturn(Optional.empty());

        Categoria resultado = categoriaService.update(2, nuevaCategoria);
        assertNull(resultado);
        verify(categoriaRepository, never()).save(any());
    }

    @Test
    public void testDeleteExistente() {
        when(categoriaRepository.existsById(1)).thenReturn(true);
        categoriaService.delete(1);
        verify(categoriaRepository).deleteById(1);
    }

    @Test
    public void testDeleteNoExistente() {
        when(categoriaRepository.existsById(2)).thenReturn(false);
        categoriaService.delete(2);
        verify(categoriaRepository, never()).deleteById(anyInt());
    }
}

