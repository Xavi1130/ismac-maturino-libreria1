package com.distribuida.dao;

import com.distribuida.model.Cliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class ClienteRepositorioTestIntegracion {

    @Autowired
    private ClienteRepository clienteRepository;


    @Test
    public void findAll(){
        List<Cliente> clientes = clienteRepository.findAll();

        for(Cliente item : clientes){
            System.out.println(item.toString());
        }
    }


    @Test
    public void findOne(){
        Optional<Cliente> cliente = clienteRepository.findById(1);
        System.out.println(cliente.toString());
    }

    @Test
    public void save(){
        Cliente cliente = new Cliente(0, "1753848215", "Juan","Taipe","av.los conquistadores","0979332474","juanraipe@.com");
        clienteRepository.save(cliente);
    }

    @Test
    public void update(){
        Optional<Cliente> cliente = clienteRepository.findById(39);

        cliente.orElse(null).setCedula("5555554254");
        cliente.orElse(null).setNombre("Juanito");
        cliente.orElse(null).setApellido("Perez");
        cliente.orElse(null).setDireccion("Panama");
        cliente.orElse(null).setTelefono("0979332564");
        cliente.orElse(null).setCorreo("sacomrte@.com");

        clienteRepository.save(cliente.orElse(null));
    }
}
