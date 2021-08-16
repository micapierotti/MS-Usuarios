package com.dan.pgm.danmsusuarios.services;

import com.dan.pgm.danmsusuarios.domain.Obra;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ObraService {
    public Obra crearObra(Obra o);
    public Obra actualizarObra(Obra o);
    public boolean borrarObra(Integer id);
    public Obra buscarObraPorId(Integer idObra);
    public List<Obra> buscarPorClienteOTipo(Integer idCliente, String tipoObra);


}
