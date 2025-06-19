package com.distribuida.controller;

import com.distribuida.model.Autor;
import com.distribuida.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    // GET /autores
    @GetMapping
    public ResponseEntity<List<Autor>> findAll() {
        List<Autor> autores = autorService.findAll();
        return ResponseEntity.ok(autores);
    }

    // GET /autores/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Autor> findOne(@PathVariable int id) {
        Autor autor = autorService.findOne(id);
        if (autor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(autor);
    }

    // POST /autores
    @PostMapping
    public ResponseEntity<Autor> save(@RequestBody Autor autor) {
        Autor nuevoAutor = autorService.save(autor);
        return ResponseEntity.ok(nuevoAutor);
    }

    // PUT /autores/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Autor> update(@PathVariable int id,
                                        @RequestBody Autor autor) {
        Autor autorActualizado = autorService.update(id, autor);
        if (autorActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(autorActualizado);
    }

    // DELETE /autores/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        autorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

