package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Palabra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * Created by ppioli on 07/05/17.
 */
public interface ServicioPalabra {

    void createOrUpdate(String valor, int count, Connection conn) throws SQLException;

    PreparedStatement batchCreateOrUpdate(Set<Map.Entry<String, Integer>> valor, Connection conn) throws SQLException;
}
