package com.dan.pgm.danmsusuarios.database;

import com.dan.pgm.danmsusuarios.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findAllByFechaBajaIsNull();

    List<Cliente> findAllByRazonSocial(String razonSocial);

    Optional<Cliente> findFirstByCuit(String cuil);

    Optional<Cliente> findById(Integer id);
}
