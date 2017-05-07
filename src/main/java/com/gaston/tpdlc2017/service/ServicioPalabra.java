package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Palabra;

/**
 * Created by ppioli on 07/05/17.
 */
public interface ServicioPalabra {

    void insertar(Palabra customer);
    Palabra buscarPalabra(int custId);
}
