package com.dan.pgm.danmsusuarios.database;

import com.dan.pgm.danmsusuarios.domain.Empleado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {

    @Query("select * from Empleado e where e.user = ?1 ")
    Empleado findAllByNombre(String user);
}
