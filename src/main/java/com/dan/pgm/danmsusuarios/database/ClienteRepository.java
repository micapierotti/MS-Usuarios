package com.dan.pgm.danmsusuarios.database;

import com.dan.pgm.danmsusuarios.domain.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

    //@Query("select * from Pedido p where p.estado = ?1")
    @Query("select * from Cliente c where c.fecheBaja is null")
    List<Cliente> findAllByFechaBaja();

    @Query("select * from Cliente c where c.fecheBaja is null AND c.razonSocial = ?1 ")
    List<Cliente> findAllByRazonSocial(String razonSocial);

    @Query("select * from Cliente c where c.fecheBaja is null AND c.cuil = ?1 ")
    Cliente findAllByCuit(String cuil);
}