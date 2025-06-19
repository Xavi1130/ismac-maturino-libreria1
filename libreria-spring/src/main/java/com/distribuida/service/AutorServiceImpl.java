package com.distribuida.service;

import com.distribuida.dao.AutorRepository;
import com.distribuida.model.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Aquí se gestiona la lógica de negocio de Autor

@Service
public  class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

//    @Override
//    public Autor findById(int id) {
//        return autorRepository.findById(id).orElse(null);
//    }

    @Override
    public Autor findOne(int id) {
        Optional<Autor> autor = autorRepository.findById(id);
        return autor.orElse(null);
    }

    @Override
    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }

    @Override
    public Autor update(int id, Autor autor){
        Optional<Autor> autorExistente = autorRepository.findById(id);
        autorExistente.orElse(null).setNombre(autor.getNombre());
        autorExistente.orElse(null).setApellido(autor.getApellido());
        autorExistente.orElse(null).setPais(autor.getPais());
        autorExistente.orElse(null).setDireccion(autor.getDireccion());
        autorExistente.orElse(null).setTelefono(autor.getTelefono());
        autorExistente.orElse(null).setCorreo(autor.getCorreo());

        return autorRepository.save(autorExistente.orElse(null));

    }

    @Override
    public void delete(int id) {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id);
        }
    }
}


