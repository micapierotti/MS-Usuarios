package com.dan.pgm.danmsusuarios.services.implementacion;

import com.dan.pgm.danmsusuarios.domain.Cliente;
import com.dan.pgm.danmsusuarios.domain.Obra;
import com.dan.pgm.danmsusuarios.repository.ClienteRepository;
import com.dan.pgm.danmsusuarios.services.ClienteService;
import com.dan.pgm.danmsusuarios.services.RiesgoBCRAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    RiesgoBCRAService riesgoSrv;

    @Autowired
    ClienteRepository repo;

    @Override
    public Cliente crearCliente(Cliente cli) {
        int riesgo = riesgoSrv.verificarSituacionCrediticia(cli);
        if(riesgo == 1 || riesgo == 2){
            cli.setHabilitadoOnline(true);
        } else {
            cli.setHabilitadoOnline(false);
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

    // TODO -  CONSULTAR FECHABAJA?
    public void borrarCliente(Integer id) {
        Optional<Cliente> cli = this.buscarPorId(id);
        cli.get().setFechaBaja(Instant.now());
    }

    @Override
    public Optional<Cliente> buscarPorCuit(String cuit) {
        //TODO implementacion de busqueda por cuit con bdd implementada
        return Optional.empty();
    }

    @Override
    public Iterable<Cliente> buscarTodos() {
        return this.repo.findAll();
    }

    @Override
    public Optional<Cliente> actualizarCliente(Cliente cli, Integer id) {
        //TODO implementacion de actualizar cliente con bdd implementada
        return Optional.empty();
    }

}