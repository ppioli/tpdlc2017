package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Documento;

/**
 * Created by ppioli on 07/05/17.
 */
public interface DocumentoService {
    boolean exists(Documento doc);
    void create(Documento doc);
}
