package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Documento;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ppioli on 07/05/17.
 */
public interface DocumentoService {

    Documento find(byte[] id);
    void createOrUpdate(byte[] id, String name, Connection conn) throws SQLException;
    int getCount(Connection conn) throws SQLException;

}
