package com.distribuida.controller;


import com.distribuida.model.Cliente;
import com.distribuida.service.ClienteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;


//import static java.lang.reflect.Array.get;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SuppressWarnings("removal")
@WebMvcTest(ClienteController.class)

public class ClienteControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testfindAll() throws Exception {
        Cliente cliente = new Cliente(1 , "1753848215", "Perro", "Taipe", "av, los perros", "0979332474", "xavicet64gmail.com");

        Mockito.when(clienteService.findAll()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Perro"));
    }


    @Test
    public void testSave() throws Exception {
        Cliente cliente = new Cliente(0 , "1753848215", "Perro", "Taipe", "av, los perros", "0979332474", "xavicet64gmail.com");

        Mockito.when(clienteService.save(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Perro"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/clientes/1")).andExpect(status().isNoContent());
    }


}
