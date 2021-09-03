package com.dan.pgm.danmsusuarios.services;

import com.dan.pgm.danmsusuarios.domain.Obra;
import com.dan.pgm.danmsusuarios.dtos.ObraDTO;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ObraService {
    public Obra crearObra(ObraDTO o);
    public Obra actualizarObra(ObraDTO o);
    public boolean borrarObra(Integer id);
    public Obra buscarObraPorId(Integer idObra);
    public List<Obra> buscarPorClienteOTipo(Integer idCliente, String tipoObra);


}
