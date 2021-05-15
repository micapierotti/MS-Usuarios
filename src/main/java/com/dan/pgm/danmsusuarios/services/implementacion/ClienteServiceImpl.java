package com.dan.pgm.danmsusuarios.services.implementacion;

import com.dan.pgm.danmsusuarios.domain.Cliente;
import com.dan.pgm.danmsusuarios.domain.Obra;
import com.dan.pgm.danmsusuarios.repository.ClienteRepository;
import com.dan.pgm.danmsusuarios.services.ClienteService;
import com.dan.pgm.danmsusuarios.services.RiesgoBCRAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import sun.jvm.hotspot.tools.soql.SOQL;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private static final String GET_SI_EXISTEN_PEDIDOS = "/api/pedido/existenPedidos";
    private static final String REST_API_URL = "http://localhost:8081";

    @Autowired
    RiesgoBCRAService riesgoSrv;

    @Autowired
    ClienteRepository repo;

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
        return this.repo.save(cli);

    }

    @Override
    public Optional<Cliente> buscarPorId(Integer id){
        Optional<Cliente> cli = this.repo.findById(id);
        if(cli.get().getFechaBaja() == null){
            return cli;
        }
        return null;
    }

    // TODO -  ver el ms pedidos
    public void borrarCliente(Integer id) {
        Optional<Cliente> cli = this.buscarPorId(id);

        boolean tienePedidos;
        ArrayList<Integer> idsDeObra = new ArrayList<Integer>();
        cli.get().getObras().forEach((obra) -> idsDeObra.add(obra.getId()));

        tienePedidos = this.verificarPedidosCliente(idsDeObra);
        if(tienePedidos){
            cli.get().setFechaBaja(Instant.now());
        }else{
            this.repo.delete(cli.get());
        }
    }
    public Boolean verificarPedidosCliente(ArrayList<Integer> idsDeObra){
        /* String url = REST_API_URL + GET_SI_EXISTEN_PEDIDOS;
        WebClient client = WebClient.create(url);

        return client.get()
                .uri(url, idsDeObra).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();*/
        return false;
    }

    @Override
    public Optional<Cliente> buscarPorCuit(String cuit) {
        //TODO implementacion de busqueda por cuit con bdd implementada
        return Optional.empty();
    }

    @Override
    public List<Cliente> buscarTodos() {
        List<Cliente> clientes = (List) this.repo.findAll();

        List<Cliente> sinFechaDeBaja = new ArrayList<Cliente>();

        clientes.forEach(c -> {
            if( Objects.isNull(c.getFechaBaja())){
                sinFechaDeBaja.add(c);
            }else{
                System.out.println(c.getFechaBaja());
            }
        } );

        System.out.println(sinFechaDeBaja.size() + " " + sinFechaDeBaja.toString());
        return sinFechaDeBaja;
    }

    @Override
    public Optional<Cliente> actualizarCliente(Cliente cli, Integer id) {
        //TODO implementacion de actualizar cliente con bdd implementada
        return Optional.empty();
    }

}