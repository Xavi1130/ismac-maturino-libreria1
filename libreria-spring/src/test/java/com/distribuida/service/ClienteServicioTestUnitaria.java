package com.distribuida.service;

import com.distribuida.dao.ClienteRepository;
import com.distribuida.model.Cliente;
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
public class ClienteServicioTestUnitaria {


    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;


    private Cliente cliente;

    @BeforeEach
    public void setUp(){
        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setCedula("1753848215");
        cliente.setNombre("perro");
        cliente.setApellido("loco");
        cliente.setDireccion("los huevos");
        cliente.setTelefono("0979332474");
        cliente.setCorreo("perroloco@gmail.com");
    }

    @Test
    public void testFindAll(){
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        List<Cliente> clientes = clienteService.findAll();
        assertNotNull(clientes);
        assertEquals(1,clientes.size());
        verify(clienteRepository, times(1)).findAll();

    }

    @Test
    public void testFindOneExistente(){
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        Cliente cliente = clienteService.findOne(1);
        assertNotNull(cliente);
        assertEquals("perro", cliente.getNombre());
    }


    @Test
    public void testFindOneNoExistente(){
        when(clienteRepository.findById(2)).thenReturn(Optional.empty());
        Cliente cliente = clienteService.findOne(2);
        assertNull(cliente);

    }

    @Test
    public void testSave(){
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        Cliente cliente1 = clienteService.save(cliente);
        assertNotNull(cliente1);
        assertEquals("perro", cliente1.getNombre());
    }

    @Test
    public void TestUpdate(){
        Cliente clienteActualizado = new Cliente();

        clienteActualizado.setCedula("1735482851");
        clienteActualizado.setNombre("perro2");
        clienteActualizado.setApellido("loco2");
        clienteActualizado.setDireccion("loshuevos2");
        clienteActualizado.setTelefono("0997334274");
        clienteActualizado.setCorreo("locoperro@gmail.com");

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any())).thenReturn(clienteActualizado);
        Cliente clienteResultado = clienteService.update(1, clienteActualizado);
        assertNotNull(clienteResultado);
        assertEquals("perro2", clienteResultado.getNombre());
        verify(clienteRepository, times(1)).save(cliente);

    }

    @Test
    public void testUpdateNoExistente(){
        Cliente clienteNuevo = new Cliente();
        when(clienteRepository.findById(2)).thenReturn(Optional.empty());
        Cliente clienteResultado = clienteService.update(2, clienteNuevo);
        assertNull(clienteResultado);
        verify(clienteRepository, never()).save(any());//never() == times(0)

    }

    @Test
    public void testDeleteExistente(){
        when(clienteRepository.existsById(1)).thenReturn(true);
        clienteService.delete(1);
        verify(clienteRepository).deleteById(1);
    }

    @Test
    public void testDeleteNoExistente(){
        when(clienteRepository.existsById(2)).thenReturn(false);
        clienteService.delete(2);
        verify(clienteRepository, never()).deleteById(anyInt());
    }

}
