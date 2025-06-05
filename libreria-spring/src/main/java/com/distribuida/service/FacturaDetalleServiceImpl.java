package com.distribuida.service;

import com.distribuida.dao.AutorRepository;
import com.distribuida.dao.FacturaDetalleRepository;
import com.distribuida.dao.FacturaRepository;
import com.distribuida.dao.LibroRepository;
import com.distribuida.model.Autor;
import com.distribuida.model.Factura;
import com.distribuida.model.FacturaDetalle;
import com.distribuida.model.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaDetalleServiceImpl implements FacturaDetalleService {

    @Autowired
    private FacturaDetalleRepository facturaDetalleRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<FacturaDetalle> findAll() {
        return facturaDetalleRepository.findAll();
    }

    @Override
    public FacturaDetalle findOne(int id) {
        return facturaDetalleRepository.findById(id).orElse(null);
    }

    @Override
    public FacturaDetalle save(FacturaDetalle detalle, int idLibro, int idFactura, int idAutor) {
        Optional<Libro> libro = libroRepository.findById(idLibro);
        Optional<Factura> factura = facturaRepository.findById(idFactura);
        Optional<Autor> autor = autorRepository.findById(idAutor);

        if (libro.isPresent() && factura.isPresent() && autor.isPresent()) {
            detalle.setLibro(libro.get());
            detalle.setFactura(factura.get());
            detalle.setAutor(autor.get());
            return facturaDetalleRepository.save(detalle);
        }

        return null; // No se pudo guardar porque alguna FK no existe
    }

    @Override
    public FacturaDetalle update(int id, int idLibro, int idFactura, int idAutor, FacturaDetalle nuevoDetalle) {
        FacturaDetalle detalleExistente = findOne(id);
        Optional<Libro> libro = libroRepository.findById(idLibro);
        Optional<Factura> factura = facturaRepository.findById(idFactura);
        Optional<Autor> autor = autorRepository.findById(idAutor);

        if (detalleExistente == null) return null;

        detalleExistente.setCantidad(nuevoDetalle.getCantidad());
        detalleExistente.setSubtotal(nuevoDetalle.getSubtotal());
        detalleExistente.setLibro(libro.orElse(null));
        detalleExistente.setFactura(factura.orElse(null));
        detalleExistente.setAutor(autor.orElse(null));

        return facturaDetalleRepository.save(detalleExistente);
    }

    @Override
    public void delete(int id) {
        if (facturaDetalleRepository.existsById(id)) {
            facturaDetalleRepository.deleteById(id);
        }
    }
}

