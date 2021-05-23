package com.dan.pgm.danmsusuarios.services.implementacion;

import com.dan.pgm.danmsusuarios.database.ClienteRepository;
import com.dan.pgm.danmsusuarios.database.UsuarioRepository;
import com.dan.pgm.danmsusuarios.domain.Cliente;
import com.dan.pgm.danmsusuarios.domain.TipoUsuario;
import com.dan.pgm.danmsusuarios.domain.Usuario;
import com.dan.pgm.danmsusuarios.repository.ClienteRepositoryInMemory;
import com.dan.pgm.danmsusuarios.services.ClienteService;
import com.dan.pgm.danmsusuarios.services.RiesgoBCRAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private static final String GET_SI_EXISTEN_PEDIDOS = "/api/pedido/existen-pedidos";
    private static final String REST_API_URL = "http://localhost:9002";

    @Autowired
    RiesgoBCRAService riesgoSrv;

    @Autowired
    ClienteRepositoryInMemory repo;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Cliente crearCliente(Cliente cli) {
        int riesgo = riesgoSrv.verificarSituacionCrediticia(cli);
        if(riesgo == 1 || riesgo == 2){
            cli.setHabilitadoOnline(true);
            System.out.println("cliente habilitado: " + cli.getHabilitadoOnline());
        } else {
            cli.setHabilitadoOnline(false);
            System.out.println("cliente habilitado: " + cli.getHabilitadoOnline());
        }

        // Crear el Usuario
        Usuario u = new Usuario();
        u.setUser(cli.getMail());
        u.setTipoUsuario(TipoUsuario.CLIENTE);
        u.setPassword("1234");
        // Guardar el Usuario
        usuarioRepository.save(u);
        // Setear el usuario al cliente
        cli.setUser(u);

        return clienteRepository.save(cli);
    }

    @Override
    public Cliente buscarPorId(Integer id){
        Cliente cli = clienteRepository.findById(id).get();
        if(cli.getFechaBaja() == null){
            return cli;
        }
        return null;
    }

    @Override
    public Cliente buscarPorCuit(String cuit) {
        Cliente c = clienteRepository.findAllByCuit(cuit);
        return c;
    }

    @Override
    public List<Cliente> buscarTodos() {
        List<Cliente> sinFechaDeBaja = (List) clienteRepository.findAllByFechaBaja();
        return sinFechaDeBaja;
    }

    @Override
    public List<Cliente>  buscarTodosRazonSocial( String razonSocial) {
        List<Cliente> clientes = clienteRepository.findAllByRazonSocial(razonSocial);
        return clientes;
    }


    public boolean borrarCliente(Integer id) {
        Cliente cli = buscarPorId(id);
        boolean tienePedidos;
        if( cli != null) {
            ArrayList<Integer> idsDeObra = new ArrayList<Integer>();
            cli.getObras().forEach((obra) -> idsDeObra.add(obra.getId()));
            tienePedidos = this.verificarPedidosCliente(idsDeObra);

            if(tienePedidos){
                cli.setFechaBaja(Instant.now());
            } else {
                clienteRepository.delete(cli);
                if (clienteRepository.findById(id).isPresent()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }
    public Boolean verificarPedidosCliente(ArrayList<Integer> idsDeObra){
        String url = REST_API_URL + GET_SI_EXISTEN_PEDIDOS;
        WebClient client = WebClient.create(url);

        return client.get()
                .uri(url, idsDeObra).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

    }



    @Override
    public Cliente actualizarCliente(Cliente cli, Integer id) {
        return clienteRepository.save(cli);
    }

}