package com.dan.pgm.danmsusuarios.services.implementacion;

import com.dan.pgm.danmsusuarios.database.EmpleadoRepository;
import com.dan.pgm.danmsusuarios.domain.Empleado;
import com.dan.pgm.danmsusuarios.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Override
    public Empleado crearEmpleado(Empleado e) {
        this.empleadoRepository.save(e);
        return e;
    }

    @Override
    public Empleado actualizarEmpleado(Empleado e, Integer idEmpleado) {
        try {
            if (empleadoRepository.findById(idEmpleado).isPresent())
                return empleadoRepository.save(e);
            else
                throw new RuntimeException("Para actualizar un empleado ingrese un id existente");
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean borrarEmpleado(Integer idEmpleado) {
        Empleado e = buscarPorId(idEmpleado);

        if(e != null){
            empleadoRepository.delete(e);
            return !empleadoRepository.findById(idEmpleado).isPresent();
        } else {
            return false;
        }
    }

    @Override
    public Empleado buscarPorId(Integer idEmpleado) {
        try{
            if (empleadoRepository.findById(idEmpleado).isPresent())
                return empleadoRepository.findById(idEmpleado).get();
            else
                throw new RuntimeException("No se halló el empleado " + idEmpleado);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Empleado buscarPorNombre(String nombre) {
        return empleadoRepository.findAllByNombre(nombre);
    }

    @Override
    public List<Empleado> buscarTodos() {
        try{
            return (List<Empleado>) empleadoRepository.findAll();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}