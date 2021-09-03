package com.dan.pgm.danmsusuarios.services.implementacion;

import com.dan.pgm.danmsusuarios.database.ClienteRepository;
import com.dan.pgm.danmsusuarios.database.ObraRepository;
import com.dan.pgm.danmsusuarios.database.UsuarioRepository;
import com.dan.pgm.danmsusuarios.domain.Cliente;
import com.dan.pgm.danmsusuarios.domain.Obra;
import com.dan.pgm.danmsusuarios.domain.TipoObra;
import com.dan.pgm.danmsusuarios.dtos.ObraDTO;
import com.dan.pgm.danmsusuarios.repository.ClienteRepositoryInMemory;
import com.dan.pgm.danmsusuarios.services.ObraService;
import com.dan.pgm.danmsusuarios.services.RiesgoBCRAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ObraServiceImpl implements ObraService {

    @Autowired
    RiesgoBCRAService riesgoSrv;

    @Autowired
    ObraRepository obraRepository;

    @Autowired
    ClienteServiceImpl clienteServiceImpl;

    public Obra crearObra(ObraDTO obra){
        Cliente c = clienteServiceImpl.buscarPorId(obra.getClienteId());
        if(c != null){
            Obra o = new Obra(obra.getId(), obra.getDescripcion(), obra.getLatitud(), obra.getLongitud(),
                    obra.getDireccion(), obra.getSuperficie(), TipoObra.valueOf(obra.getTipo()), c);
            return obraRepository.save(o);
        } else {
            return null;
        }
    }

    @Override
    public Obra actualizarObra(ObraDTO obra){
        Cliente c = clienteServiceImpl.buscarPorId(obra.getClienteId());
        if(c != null){
            Obra o = new Obra(obra.getId(), obra.getDescripcion(), obra.getLatitud(), obra.getLongitud(),
                    obra.getDireccion(), obra.getSuperficie(), TipoObra.valueOf(obra.getTipo()), c);
            return obraRepository.save(o);
        } else {
            return null;
        }
    }

    public boolean borrarObra(Integer idObra){
        Obra o = obraRepository.findById(idObra).orElse(null);
        if(o != null){
            o.setCliente(null);
            obraRepository.delete(o);
            if(obraRepository.findById(idObra).orElse(null) != null){
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
            return (List) obraRepository.findByTipo(TipoObra.valueOf(tipoObra));
        }

        return (List) obraRepository.findAll();

    }



}
