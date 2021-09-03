package com.dan.pgm.danmsusuarios.database;


import com.dan.pgm.danmsusuarios.domain.Obra;
import com.dan.pgm.danmsusuarios.domain.TipoObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ObraRepository extends JpaRepository<Obra, Integer> {

    List<Obra> findByTipo(TipoObra tipo);
}
