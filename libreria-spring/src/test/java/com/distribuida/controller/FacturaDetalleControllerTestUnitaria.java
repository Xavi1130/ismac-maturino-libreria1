package com.distribuida.controller;

import com.distribuida.model.FacturaDetalle;
import com.distribuida.service.FacturaDetalleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FacturaDetalleControllerTestUnitaria {

    @InjectMocks
    private FacturaDetalleController facturaDetalleController;

    @Mock
    private FacturaDetalleService facturaDetalleService;

    private FacturaDetalle detalle;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        detalle = new FacturaDetalle();
        detalle.setIdFacturaDetalle(1);
        detalle.setCantidad(3);
        // puedes añadir más campos si es necesario (por ejemplo, setLibro(), setFactura(), etc.)
    }

    @Test
    public void testFindAll() {
        when(facturaDetalleService.findAll()).thenReturn(List.of(detalle));
        ResponseEntity<List<FacturaDetalle>> respuesta = facturaDetalleController.findAll();
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(1, respuesta.getBody().size());
        verify(facturaDetalleService, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente() {
        when(facturaDetalleService.findOne(1)).thenReturn(detalle);
        ResponseEntity<FacturaDetalle> respuesta = facturaDetalleController.findOne(1);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(3, respuesta.getBody().getCantidad());
    }

    @Test
    public void testFindOneNoExistente() {
        when(facturaDetalleService.findOne(2)).thenReturn(null);
        ResponseEntity<FacturaDetalle> respuesta = facturaDetalleController.findOne(2);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    public void testSave() {
        when(facturaDetalleService.save(any(FacturaDetalle.class))).thenReturn(detalle);
        ResponseEntity<FacturaDetalle> respuesta = facturaDetalleController.save(detalle);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(3, respuesta.getBody().getCantidad());
    }

    @Test
    public void testUpdateExistente() {
        when(facturaDetalleService.update(eq(1), any(FacturaDetalle.class))).thenReturn(detalle);
        ResponseEntity<FacturaDetalle> respuesta = facturaDetalleController.update(1, detalle);
        assertEquals(200, respuesta.getStatusCodeValue());
    }

    @Test
    public void testUpdateNoExistente() {
        when(facturaDetalleService.update(eq(2), any(FacturaDetalle.class))).thenReturn(null);
        ResponseEntity<FacturaDetalle> respuesta = facturaDetalleController.update(2, detalle);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        doNothing().when(facturaDetalleService).delete(1);
        ResponseEntity<Void> respuesta = facturaDetalleController.delete(1);
        assertEquals(204, respuesta.getStatusCodeValue());
        verify(facturaDetalleService, times(1)).delete(1);
    }

    @Test
    public void testFindByFacturaId() {
        when(facturaDetalleService.findById(1)).thenReturn(detalle);
        ResponseEntity<List<FacturaDetalle>> respuesta = facturaDetalleController.findByFacturaId(1);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(1, respuesta.getBody().size());
    }
}
