package com.dan.pgm.danmsusuarios.services.implementacion;

import com.dan.pgm.danmsusuarios.database.ClienteRepository;
import com.dan.pgm.danmsusuarios.database.ObraRepository;
import com.dan.pgm.danmsusuarios.database.UsuarioRepository;
import com.dan.pgm.danmsusuarios.domain.Cliente;
import com.dan.pgm.danmsusuarios.domain.Obra;
import com.dan.pgm.danmsusuarios.repository.ClienteRepositoryInMemory;
import com.dan.pgm.danmsusuarios.services.ObraService;
import com.dan.pgm.danmsusuarios.services.RiesgoBCRAService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ObraServiceImpl implements ObraService {

    @Autowired
    RiesgoBCRAService riesgoSrv;

    @Autowired
    ObraRepository obraRepository;

    @Autowired
    ClienteServiceImpl clienteServiceImpl;

    public Obra crearObra(Obra obra){
        Cliente c = clienteServiceImpl.buscarPorId(obra.getCliente().getId());
        if(c != null){
            return obraRepository.save(obra);
        } else {
            return null;
        }
    }

    public Obra actualizarObra(Obra o){
        return obraRepository.save(o);
    }

    public boolean borrarObra(Integer idObra){
        Obra o = this.buscarObraPorId(idObra);
        if(o != null){
            obraRepository.delete(o);
            if(this.buscarObraPorId(idObra) != null){
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public Obra buscarObraPorId(Integer idObra){
        try{
            if (obraRepository.findById(idObra).isPresent()) {
                return obraRepository.findById(idObra).get();
            } else {
                throw new RuntimeException("No se halló la obra con id: " + idObra);
            }
        } catch ( Exception exception){
            System.out.println(exception.getMessage());
            return null;
        }
    }

    public List<Obra> buscarPorClienteOTipo(Integer idCliente, String tipoObra){
        List<Obra> resultado = new ArrayList<Obra>();

        if(idCliente != null && tipoObra != null){
            Cliente c = clienteServiceImpl.buscarPorId(idCliente);
            if(c != null){
                resultado.addAll(c.getObras().stream().filter( obra ->
                    obra.getTipo().toString().equals(tipoObra) ).collect(Collectors.toList()));
            }
            return resultado;
        }
        else if(idCliente != null && tipoObra == null){
           Cliente c = clienteServiceImpl.buscarPorId(idCliente);
           if(c != null){
               resultado.addAll(c.getObras());

           }
           return resultado;
        }
        else if(idCliente == null && tipoObra != null){
            return obraRepository.findByTipo(tipoObra);
        }

        return (List) obraRepository.findAll();

    }



}
