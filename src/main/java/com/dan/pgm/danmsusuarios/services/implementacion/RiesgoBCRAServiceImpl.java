package com.dan.pgm.danmsusuarios.services.implementacion;

import com.dan.pgm.danmsusuarios.domain.Cliente;
import com.dan.pgm.danmsusuarios.services.RiesgoBCRAService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RiesgoBCRAServiceImpl implements RiesgoBCRAService {
    @Override
    public int verificarSituacionCrediticia(Cliente cli) {
        return (int) Math.floor(Math.random()*(2))+1;
    }
}
