package com.dan.pgm.danmsusuarios.database;


import com.dan.pgm.danmsusuarios.domain.Empleado;
import org.springframework.data.repository.CrudRepository;

public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {
}
