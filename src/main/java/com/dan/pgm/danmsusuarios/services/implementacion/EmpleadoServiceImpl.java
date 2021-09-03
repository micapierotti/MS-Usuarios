package com.dan.pgm.danmsusuarios.services.implementacion;

import com.dan.pgm.danmsusuarios.database.EmpleadoRepository;
import com.dan.pgm.danmsusuarios.database.UsuarioRepository;
import com.dan.pgm.danmsusuarios.domain.Empleado;
import com.dan.pgm.danmsusuarios.domain.TipoUsuario;
import com.dan.pgm.danmsusuarios.domain.Usuario;
import com.dan.pgm.danmsusuarios.dtos.EmpleadoDTO;
import com.dan.pgm.danmsusuarios.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Empleado crearEmpleado(EmpleadoDTO e) {
        Empleado empl = new Empleado();
        empl.setNombre(e.getNombre());
        empl.setMail(e.getMail());

        Usuario u = new Usuario();
        u.setPassword(e.getPassword());
        u.setTipoUsuario(TipoUsuario.EMPLEADO);
        u.setUser(e.getUser());
        usuarioRepository.save(u);

        empl.setUser(u);
        return empleadoRepository.save(empl);
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

        Empleado empl = empleadoRepository.findFirstByNombre(nombre).orElse(null);
        return empl;
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