package com.dan.pgm.danmsusuarios.database;


import com.dan.pgm.danmsusuarios.domain.Obra;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ObraRepository extends CrudRepository<Obra, Integer> {

    @Query("select o from Obra o where o.tipo = ?1")
    List<Obra> findByTipo(String tipo);
}
