package com.distribuida.service;

import com.distribuida.model.FacturaDetalle;

import java.util.List;

public interface FacturaDetalleService {
    List<FacturaDetalle> findAll();
    FacturaDetalle findOne(int id);
    FacturaDetalle save(FacturaDetalle detalle, int idLibro, int idFactura, int idAutor);
    FacturaDetalle update(int id, int idLibro, int idFactura, int idAutor, FacturaDetalle detalle);
    void delete(int id);
}
