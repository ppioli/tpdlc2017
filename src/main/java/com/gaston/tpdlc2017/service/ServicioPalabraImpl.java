package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Palabra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ppioli on 07/05/17.
 */
@Service
public class ServicioPalabraImpl implements ServicioPalabra {
    private final Logger logger = LoggerFactory.getLogger(ServicioPalabraImpl.class);

    public static final String CREATE_OR_UPDATE= "INSERT INTO palabras (id, val) VALUES ( ?, ? ) ON DUPLICATE KEY UPDATE id=id";

    private HashingService hashService;

    @Autowired
    public void setHashService(HashingService hashService) {
        this.hashService = hashService;
    }

    @Override
    public void createOrUpdate(String valor, Connection conn) throws SQLException {

        byte[] hash = hashService.hash(valor);
        PreparedStatement ps = conn.prepareStatement(CREATE_OR_UPDATE);

        ps.setBytes(1, hash);
        ps.setString(2, valor);

        ps.executeUpdate();

    }

    @Override
    public PreparedStatement batchCreateOrUpdate(Iterable<String> it, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(CREATE_OR_UPDATE);
        for (String st : it) {
            byte[] hash = hashService.hash(st);
            ps.setBytes(1, hash);
            ps.setString(2, st);
            ps.addBatch();

        }
        return ps;
    }

}
