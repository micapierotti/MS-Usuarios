package com.dan.pgm.danmsusuarios.services;

import com.dan.pgm.danmsusuarios.domain.Cliente;
import com.dan.pgm.danmsusuarios.domain.Obra;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ClienteService {

    public Cliente crearCliente(Cliente cli);
    public Optional<Cliente> buscarPorId(Integer id);
    public void borrarCliente(Integer id);
    public Optional<Cliente> buscarPorCuit(String cuit);
    public List<Cliente> buscarTodos();
    public Optional<Cliente> actualizarCliente(Cliente cli, Integer id);
    public Boolean verificarPedidosCliente(ArrayList<Integer> idsDeObra);
}
