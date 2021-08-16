package com.dan.pgm.danmsusuarios.service.impl;

import com.dan.pgm.danmsusuarios.database.ClienteRepository;
import com.dan.pgm.danmsusuarios.domain.Cliente;
import com.dan.pgm.danmsusuarios.domain.TipoUsuario;
import com.dan.pgm.danmsusuarios.domain.Usuario;
import com.dan.pgm.danmsusuarios.repository.ClienteRepositoryInMemory;
import com.dan.pgm.danmsusuarios.services.ClienteService;
import com.dan.pgm.danmsusuarios.services.RiesgoBCRAService;
import com.dan.pgm.danmsusuarios.services.implementacion.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteServiceImplUnitTest {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ClienteServiceImpl clienteServiceImpl;

    @MockBean
    ClienteRepositoryInMemory clienteRepo;


    @MockBean
    RiesgoBCRAService riesgoBCRAService;

    Cliente unCliente;
    Cliente unCliente2;
    List<Cliente> clientes  = new ArrayList<Cliente>();

    @Mock
    ArrayList<Integer> mockArrayList = new ArrayList<>();
/*
    @BeforeEach
    void SetUp() throws Exception {
         unCliente = new Cliente();
         unCliente.setId(1);
         unCliente.setRazonSocial("cli01");
         unCliente.setCuit("2099988871");
         unCliente.setMail("uncli@gmail.com");
         unCliente.setMaxCuentaCorriente(1000.0);
         //unCliente.setHabilitadoOnline(false);
         Usuario user1 = new Usuario();
         user1.setId(1);
         user1.setUser("cliente1");
         user1.setPassword("passcliente1");

         TipoUsuario tipouser1 = new TipoUsuario();
         tipouser1.setId(1);
         tipouser1.setTipo("CLIENTE");

         user1.setTipoUsuario(tipouser1);
         unCliente.setUser(user1);
         unCliente.setFechaBaja(null);
         unCliente.setObras(null);

         /////////////
        unCliente2 = new Cliente();
        unCliente2.setId(2);
        unCliente2.setRazonSocial("cli02");
        unCliente2.setCuit("2099988872");
        unCliente2.setMail("uncli2@gmail.com");
        unCliente2.setMaxCuentaCorriente(1000.0);

        Usuario user2 = new Usuario();
        user1.setId(2);
        user1.setUser("cliente2");
        user1.setPassword("passcliente2");

        TipoUsuario tipouser2 = new TipoUsuario();
        tipouser1.setId(2);
        tipouser1.setTipo("CLIENTE2");

        user2.setTipoUsuario(tipouser1);
        unCliente2.setUser(user2);
        unCliente2.setFechaBaja(Instant.now());
        unCliente2.setObras(null);

         // Lista
        clientes.add(unCliente);
        clientes.add(unCliente2);

    }

    @Test
    void crearClienteHabilitadoOnline () {
        when(riesgoBCRAService.verificarSituacionCrediticia(any(Cliente.class))).thenReturn(1);
        when(clienteRepo.save(any(Cliente.class))).thenReturn(unCliente);

        Cliente clienteResultado = clienteService.crearCliente(unCliente);
        assertThat(clienteResultado.getHabilitadoOnline().equals(true));
        verify(clienteRepo,times(1)).save(unCliente);
    }

    // TODO - fixear estos 2 test sobre borrar cliente
    @Test
    @Disabled
    void borrarClienteSinPedidos (){
        //Optional<Cliente> clienteResultado = given(clienteService.buscarPorId(any(Integer.class))).willReturn();
        when(clienteRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(unCliente));
        when(clienteService.buscarPorId(any(Integer.class))).thenReturn(Optional.ofNullable(unCliente));
        when(clienteService.verificarPedidosCliente(mockArrayList)).thenReturn(false);
        clienteService.borrarCliente(1);
        verify(clienteRepo,times(1)).delete(unCliente);
    }

    @Test
    @Disabled
    void borrarClienteConPedidos (){
        when(clienteService.buscarPorId(1)).thenReturn(unCliente);
        when(clienteService.verificarPedidosCliente(any(ArrayList.class))).thenReturn(true);
        assertThat(unCliente.getFechaBaja().equals(any(Instant.class)));
    }

    @Test
    void buscarTodos(){
        when(clienteRepo.findAll()).thenReturn((List<Cliente>) clientes);
        List<Cliente> listUnCliente = new ArrayList<Cliente>();
        listUnCliente.add(unCliente);
        assertArrayEquals(listUnCliente.toArray(), clienteService.buscarTodos().toArray());

    }
*/
}
