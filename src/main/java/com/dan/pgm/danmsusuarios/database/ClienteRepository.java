package com.dan.pgm.danmsusuarios.database;

import com.dan.pgm.danmsusuarios.domain.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

    //@Query("select * from Pedido p where p.estado = ?1")
    @Query(value = "select c from Cliente c where c.fecheBaja is null", nativeQuery = true)
    List<Cliente> findAllByFechaBaja();

    @Query(value = "select c from Cliente c where c.fecheBaja is null AND c.razonSocial = ?1 ", nativeQuery = true)
    List<Cliente> findAllByRazonSocial(String razonSocial);

    @Query(value = "select c from Cliente c where c.fecheBaja is null AND c.cuil = ?1 ", nativeQuery = true)
    Cliente findAllByCuit(String cuil);
}
