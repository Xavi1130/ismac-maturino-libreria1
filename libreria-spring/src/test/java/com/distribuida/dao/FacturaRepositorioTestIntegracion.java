package com.distribuida.dao;


import com.distribuida.model.Cliente;
import com.distribuida.model.Factura;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)

public class FacturaRepositorioTestIntegracion{
    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void findAll(){
        List<Factura>facturas = facturaRepository.findAll();
        assertNotNull(facturas);
        assertTrue(facturas.size() > 0);
        for(Factura item : facturas){
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne(){
        Optional<Factura> facturaExistente = facturaRepository.findById(1);
        assertNotNull(facturaExistente.isPresent());
        assertEquals("FAC-0100",facturaExistente.orElse(null).getNumFactura());
        assertEquals(200,facturaExistente.orElse(null).getTotalNeto(),"la factura deberia existir");

        System.out.println(facturaExistente.toString());
    }

    @Test
    public void save(){
        Optional<Cliente> cliente = clienteRepository.findById(1);
        Factura factura = new Factura(0,9.99,14.00,10.11,cliente.orElse(null),new Date(),"FAC-0099");
        facturaRepository.save(factura);

    }

    @Test
    public void  update(){
        Optional<Factura> factura = facturaRepository.findById(80);
        Optional<Cliente> cliente = clienteRepository.findById(2);
        factura.orElse(null).setNumFactura("FAC-00100");
        factura.orElse(null).setFecha(new Date());
        factura.orElse(null).setTotalNeto(200.00);
        factura.orElse(null).setIva(14.00);
        factura.orElse(null).setTotal(230.00);
        factura.orElse(null).setCliente(cliente.orElse(null));

        facturaRepository.save(factura.orElse(null));
    }

    @Test
    public void delete(){
        if(facturaRepository.existsById(90)){
            facturaRepository.deleteById(90);
        }
    }
}



/*@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)

public class FacturaRepositorioTestIntegracion {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void findAll(){
        List<Factura> facturas = facturaRepository.findAll();

        assertNotNull(facturas);

        for (Factura item: facturas){
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne(){
        Optional<Factura> factura = facturaRepository.findById(1);

        assertTrue(factura.isPresent());

        System.out.println(factura.toString());
    }

    @Test
    public void save(){
        Factura factura = new Factura();
        Optional<Cliente> cliente = clienteRepository.findById(1);

        assertTrue(cliente.isPresent());

        factura.setIdFactura(0);
        factura.setNumFactura("FAC-0099");
        factura.setFecha(new Date());
        factura.setTotalNeto(100.00);
        factura.setIva(15.00);
        factura.setTotal(115.00);
        factura.setCliente(cliente.orElse(null));

        Factura facturaGuardada = facturaRepository.save(factura);
        assertEquals(115.00, facturaGuardada.getTotal());

    }


    @Test
    public void update(){
        Optional<Factura> facturaExistente = facturaRepository.findById(89);

        Optional<Cliente> cliente = clienteRepository.findById(2);

        assertTrue(cliente.isPresent());


        facturaExistente.orElse(null).setNumFactura("FAC-0100");
        facturaExistente.orElse(null).setFecha(new Date());
        facturaExistente.orElse(null).setTotalNeto(200.00);
        facturaExistente.orElse(null).setIva(30.00);
        facturaExistente.orElse(null).setTotal(230.00);
        facturaExistente.orElse(null).setCliente(cliente.orElse(null));

        Factura facturaActualizada = facturaRepository.save(facturaExistente.orElse(null));

        assertEquals(230.00, facturaActualizada.getTotal());

    }

    @Test
    public void delete(){
        if (facturaRepository.existsById(86)){

            facturaRepository.deleteById(86);
            Optional<Factura> facturaEliminada = facturaRepository.findById(8);
            assertFalse(facturaEliminada.isPresent());
        }
    }

}*/


