package com.dan.pgm.danmsusuarios.database;

import com.dan.pgm.danmsusuarios.domain.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
}
