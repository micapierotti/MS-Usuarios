package com.dan.pgm.danmsusuarios.database;


import com.dan.pgm.danmsusuarios.domain.Obra;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ObraRepository extends CrudRepository<Obra, Integer> {

    @Query("select * from Obra o where o.tipo = ?1")
    List<Obra> findByTipo(String tipo);
}