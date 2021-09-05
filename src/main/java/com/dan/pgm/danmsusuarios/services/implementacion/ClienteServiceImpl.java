package com.dan.pgm.danmsusuarios.services.implementacion;

import com.dan.pgm.danmsusuarios.database.ClienteRepository;
import com.dan.pgm.danmsusuarios.database.UsuarioRepository;
import com.dan.pgm.danmsusuarios.domain.Cliente;
import com.dan.pgm.danmsusuarios.domain.TipoUsuario;
import com.dan.pgm.danmsusuarios.domain.Usuario;
import com.dan.pgm.danmsusuarios.dtos.ClienteDTO;
import com.dan.pgm.danmsusuarios.repository.ClienteRepositoryInMemory;
import com.dan.pgm.danmsusuarios.services.ClienteService;
import com.dan.pgm.danmsusuarios.services.RiesgoBCRAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class ClienteServiceImpl implements ClienteService {

    private static final String GET_SI_EXISTEN_PEDIDOS = "/api/pedido/existen-pedidos";
    private static final String REST_API_URL = "http://localhost:9002";

    @Autowired
    CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    RiesgoBCRAService riesgoSrv;

    @Autowired
    ClienteRepositoryInMemory repo;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Cliente crearCliente(ClienteDTO clienteDTO) {
        Cliente clienteFinal = new Cliente();
        int riesgo = riesgoSrv.verificarSituacionCrediticia(clienteFinal);
        if(riesgo == 1 || riesgo == 2){
            clienteFinal.setHabilitadoOnline(true);
            System.out.println("cliente habilitado: " + clienteFinal.getHabilitadoOnline());
        } else {
            clienteFinal.setHabilitadoOnline(false);
            System.out.println("cliente inhabilitado: " + clienteFinal.getHabilitadoOnline());
        }

        // Crear el Usuario
        Usuario u = new Usuario();
        u.setUser(clienteDTO.getUsername());
        u.setTipoUsuario(TipoUsuario.CLIENTE);
        u.setPassword(clienteDTO.getPassword());
        // Guardar el Usuario
        usuarioRepository.save(u);
        // Setear el usuario al cliente y demás atributos
        clienteFinal.setUser(u);
        clienteFinal.setCuit(clienteDTO.getCuit());
        clienteFinal.setMail(clienteDTO.getMail());
        clienteFinal.setMaxCuentaCorriente(clienteDTO.getMaxCuentaCorriente());
        clienteFinal.setRazonSocial(clienteDTO.getRazonSocial());
        clienteFinal.setObras(clienteDTO.getObras());

        return clienteRepository.save(clienteFinal);
    }

    @Override
    public Cliente buscarPorId(Integer id){
        System.out.println("get por id service");
        Cliente cli = clienteRepository.findById(id).orElse(null);
        System.out.println("Cliente" + cli);
        if(cli!= null && cli.getFechaBaja() == null){
            return cli;
        }
        return null;
    }

    @Override
    public Cliente buscarPorCuit(String cuit) {
        Cliente c = clienteRepository.findFirstByCuit(cuit).orElse(null);
        return c;
    }

    @Override
    public List<Cliente> buscarTodos() {
        List<Cliente> sinFechaDeBaja = (List) clienteRepository.findAllByFechaBajaIsNull();
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
            System.out.println("ids de obras: " + idsDeObra.toString());
            if(idsDeObra.size() > 0) {
                System.out.println("Entro para ir a verificar pedidos");
                tienePedidos = this.verificarPedidosCliente(idsDeObra);
                System.out.println("tiene pedidos. "+ tienePedidos);
            } else {
                tienePedidos = false;
            }
            if(tienePedidos){
                System.out.println("tiene pedidos, así que se le asigna una fecha baja");
                cli.setFechaBaja(new Date());
                clienteRepository.save(cli);
                return true;
            } else {
                System.out.println("viene a borrar el cliente");
                clienteRepository.deleteById(id);
                if (clienteRepository.findById(id).isPresent()) {
                    System.out.println("no tuvo exito");
                    return false;
                } else {
                    System.out.println("tuvo exito");
                    return true;
                }
            }
        }
        System.out.println("el cliente no existia");
        return false;
    }
    public Boolean verificarPedidosCliente(ArrayList<Integer> idsDeObra){
        String url = REST_API_URL + GET_SI_EXISTEN_PEDIDOS;
        WebClient client = WebClient.create(url);
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");

        System.out.println("previo a pegarle al endpoint de ms pedidos");
        System.out.println("url: "+url);

        return circuitBreaker.run(() -> {
            Boolean response = client.post()
                    .uri(url)
                    .body(Mono.just(idsDeObra), List.class)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            System.out.println("response del endpoint: "+ response.toString());
            return response;
        }, throwable -> defaultVerificarPedidos());
    }

    private Boolean defaultVerificarPedidos() {
        return false;
    }

    @Override
    public Cliente actualizarCliente(Cliente cli, Integer id) {
        Cliente clientePorActualizar = buscarPorId(id);
        if(clientePorActualizar != null && clientePorActualizar.getId() == id) {
            return clienteRepository.save(cli);
        }
        return null;
    }

}