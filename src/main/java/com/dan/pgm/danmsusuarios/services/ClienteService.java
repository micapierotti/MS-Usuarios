package com.dan.pgm.danmsusuarios.services;

import com.dan.pgm.danmsusuarios.domain.Cliente;
import com.dan.pgm.danmsusuarios.domain.Obra;
import com.dan.pgm.danmsusuarios.dtos.ClienteDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ClienteService {

    public Cliente crearCliente(ClienteDTO clienteDTO);
    public Cliente buscarPorId(Integer id);
    public boolean borrarCliente(Integer id);
    public Cliente buscarPorCuit(String cuit);
    public List<Cliente> buscarTodos();
    public List<Cliente> buscarTodosRazonSocial(String razonSocial);
    public Cliente actualizarCliente(Cliente cli, Integer id);
    public Boolean verificarPedidosCliente(ArrayList<Integer> idsDeObra);
}
