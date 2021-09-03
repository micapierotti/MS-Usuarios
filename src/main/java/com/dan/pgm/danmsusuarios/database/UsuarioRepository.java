package com.dan.pgm.danmsusuarios.database;

import com.dan.pgm.danmsusuarios.domain.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.beans.factory.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
}
