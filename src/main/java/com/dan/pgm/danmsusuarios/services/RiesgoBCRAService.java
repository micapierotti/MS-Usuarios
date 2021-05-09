package com.dan.pgm.danmsusuarios.services;

import com.dan.pgm.danmsusuarios.domain.Cliente;

public interface RiesgoBCRAService {

    public int verificarSituacionCrediticia(Cliente cli);
}
