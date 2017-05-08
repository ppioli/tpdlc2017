package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Documento;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author gaston
 */
public interface DocumentoService {

    Documento find(byte[] id);
    void createOrUpdate(byte[] id, String name, Connection conn) throws SQLException;
    int getCount(Connection conn) throws SQLException;

}
