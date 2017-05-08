package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Palabra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author gaston
 */
public interface ServicioPalabra {

    void createOrUpdate(String valor, int count, Connection conn) throws SQLException;

    List<Palabra> rank(String[] query, int totalDocuments, Connection conn);

    PreparedStatement batchCreateOrUpdate(Set<Map.Entry<String, Integer>> valor, Connection conn) throws SQLException;
}
