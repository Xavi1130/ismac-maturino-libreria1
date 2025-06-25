package com.distribuida.controller;

import com.distribuida.model.FacturaDetalle;
import com.distribuida.service.FacturaDetalleService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacturaDetalleController.class)
public class FacturaDetalleControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaDetalleService facturaDetalleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAll() throws Exception {
        FacturaDetalle detalle = new FacturaDetalle();
        detalle.setIdFacturaDetalle(1);
        detalle.setCantidad(2);

        Mockito.when(facturaDetalleService.findAll()).thenReturn(List.of(detalle));

        mockMvc.perform(get("/api/factura-detalles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cantidad").value(2));
    }

    @Test
    public void testSave() throws Exception {
        FacturaDetalle detalle = new FacturaDetalle();
        detalle.setCantidad(5);

        Mockito.when(facturaDetalleService.save(any(FacturaDetalle.class))).thenReturn(detalle);

        mockMvc.perform(post("/api/factura-detalles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(5));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/factura-detalles/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindOneExistente() throws Exception {
        FacturaDetalle detalle = new FacturaDetalle();
        detalle.setIdFacturaDetalle(1);
        detalle.setCantidad(3);

        Mockito.when(facturaDetalleService.findOne(1)).thenReturn(detalle);

        mockMvc.perform(get("/api/factura-detalles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(3));
    }

    @Test
    public void testFindOneNoExistente() throws Exception {
        Mockito.when(facturaDetalleService.findOne(999)).thenReturn(null);

        mockMvc.perform(get("/api/factura-detalles/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateExistente() throws Exception {
        FacturaDetalle detalle = new FacturaDetalle();
        detalle.setIdFacturaDetalle(1);
        detalle.setCantidad(7);

        Mockito.when(facturaDetalleService.update(Mockito.eq(1), any(FacturaDetalle.class))).thenReturn(detalle);

        mockMvc.perform(put("/api/factura-detalles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(7));
    }

    @Test
    public void testUpdateNoExistente() throws Exception {
        FacturaDetalle detalle = new FacturaDetalle();
        detalle.setCantidad(7);

        Mockito.when(facturaDetalleService.update(Mockito.eq(999), any(FacturaDetalle.class))).thenReturn(null);

        mockMvc.perform(put("/api/factura-detalles/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalle)))
                .andExpect(status().isNotFound());
    }
}

