package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Palabra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by ppioli on 07/05/17.
 */
public interface ServicioPalabra {
    void createOrUpdate(String valor, Connection conn) throws SQLException;
    PreparedStatement batchCreateOrUpdate(Iterable<String> valor, Connection conn) throws SQLException;
}
