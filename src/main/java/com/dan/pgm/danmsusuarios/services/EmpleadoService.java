package com.dan.pgm.danmsusuarios.services;

import com.dan.pgm.danmsusuarios.domain.Empleado;
import com.dan.pgm.danmsusuarios.dtos.EmpleadoDTO;

import java.util.List;

public interface EmpleadoService {

    Empleado crearEmpleado(EmpleadoDTO emp);
    Empleado actualizarEmpleado(Empleado emp, Integer id);
    boolean borrarEmpleado(Integer id);
    Empleado buscarPorId(Integer id);
    Empleado buscarPorNombre(String nombre);
    List<Empleado> buscarTodos();
}
