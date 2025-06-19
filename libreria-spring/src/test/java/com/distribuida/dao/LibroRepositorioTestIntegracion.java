package com.distribuida.dao;

import com.distribuida.model.Autor;
import com.distribuida.model.Categoria;
import com.distribuida.model.Libro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // usa tu BD real
class LibroRepositorioTestIntegracion {

    @Autowired LibroRepository libroRepository;
    @Autowired CategoriaRepository categoriaRepository;
    @Autowired AutorRepository autorRepository;

    @Test
    @DisplayName("Debe devolver todos los libros")
    void findAll_ok() {
        List<Libro> libros = libroRepository.findAll();
        assertThat(libros).isNotEmpty();
    }

    @Test
    @DisplayName("Debe encontrar un libro existente por ID")
    void findOne_ok() {
        Libro libro = libroRepository.findById(1).orElse(null);
        assertThat(libro).isNotNull();
    }

    @Test
    @Rollback(false)          // solo este test persiste
    @DisplayName("Debe guardar un nuevo libro con sus relaciones")
    void save_ok() {
        Categoria categoria = categoriaRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException("Categoria no existe"));
        Autor autor = autorRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException("Autor no existe"));

        Libro libro = new Libro();
        libro.setTitulo("Nuevo Libro");
        libro.setEditorial("Mi Editorial");
        libro.setNumpaginas(200);
        libro.setEdicion("1ª");
        libro.setIdioma("Español");
        libro.setFechapublicacion(java.sql.Date.valueOf(LocalDate.now()));
        libro.setDescripcion("Descripción");
        libro.setTipopasta("Dura");
        libro.setIsbn("1234567890");
        libro.setNumejemplares(10);
        libro.setPortada("portada.jpg");
        libro.setPresentacion("Presentación");
        libro.setPrecio(250.0);
        libro.setCategoria(categoria);
        libro.setAutor(autor);

        Libro guardado = libroRepository.saveAndFlush(libro);
        assertThat(guardado.getIdlibro()).isPositive();
    }

    @Test
    @Rollback(false)
    @DisplayName("Debe actualizar un libro existente")
    void update_ok() {
        Libro libro = libroRepository.findById(1).orElseThrow();
        libro.setPrecio(999.9);
        libroRepository.saveAndFlush(libro);

        Libro actualizado = libroRepository.findById(1).orElseThrow();
        assertThat(actualizado.getPrecio()).isEqualTo(999.9);
    }

    @Test
    @Rollback(false)
    @DisplayName("Debe eliminar un libro por ID")
    void delete_ok() {
        int id = 5;
        if (libroRepository.existsById(id)) {
            libroRepository.deleteById(id);
        }
        assertThat(libroRepository.existsById(id)).isFalse();
    }
}

