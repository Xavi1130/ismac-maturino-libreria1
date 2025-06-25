package com.distribuida.controller;

import com.distribuida.model.Factura;
import com.distribuida.service.FacturaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacturaController.class)
public class FacturaControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaService facturaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAll() throws Exception {
        Factura factura = new Factura();
        factura.setIdFactura(1);
        factura.setTotal(200.50);

        Mockito.when(facturaService.findAll()).thenReturn(List.of(factura));

        mockMvc.perform(get("/api/facturas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].total").value(200.50));
    }

    @Test
    public void testFindOneExistente() throws Exception {
        Factura factura = new Factura();
        factura.setIdFactura(1);
        factura.setTotal(99.99);

        Mockito.when(facturaService.findOne(1)).thenReturn(factura);

        mockMvc.perform(get("/api/facturas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(99.99));
    }

    @Test
    public void testFindOneNoExistente() throws Exception {
        Mockito.when(facturaService.findOne(99)).thenReturn(null);

        mockMvc.perform(get("/api/facturas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSave() throws Exception {
        Factura factura = new Factura();
        factura.setIdFactura(0);
        factura.setTotal(150.00);

        Mockito.when(facturaService.save(any(Factura.class))).thenReturn(factura);

        mockMvc.perform(post("/api/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(150.00));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/facturas/1"))
                .andExpect(status().isNoContent());
    }
}
