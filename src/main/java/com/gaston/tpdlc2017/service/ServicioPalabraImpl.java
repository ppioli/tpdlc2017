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
import java.util.Map;
import java.util.Set;

/**
 * Created by ppioli on 07/05/17.
 */
@Service
public class ServicioPalabraImpl implements ServicioPalabra {
    private final Logger logger = LoggerFactory.getLogger(ServicioPalabraImpl.class);

    public static final String CREATE_OR_UPDATE= "INSERT INTO palabras (id, val, maxCount) VALUES ( ?, ?, ? ) "
        +"ON DUPLICATE KEY UPDATE maxCount=GREATEST(VALUES(maxCount), ?)";

    private HashingService hashService;

    @Autowired
    public void setHashService(HashingService hashService) {
        this.hashService = hashService;
    }

    @Override
    public void createOrUpdate(String valor, int count, Connection conn) throws SQLException {

        byte[] hash = hashService.hash(valor);
        PreparedStatement ps = conn.prepareStatement(CREATE_OR_UPDATE);

        ps.setBytes(1, hash);
        ps.setString(2, valor);
        ps.setString(3, valor);
        ps.setString(4, valor);

        ps.executeUpdate();

    }

    @Override
    public PreparedStatement batchCreateOrUpdate(Set<Map.Entry<String, Integer>> entries, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(CREATE_OR_UPDATE);
        for (Map.Entry<String, Integer> entry : entries) {
            byte[] hash = hashService.hash(entry.getKey());
            ps.setBytes(1, hash);
            ps.setString(2, entry.getKey());
            ps.setInt(3, entry.getValue());
            ps.setInt(4, entry.getValue());
            ps.addBatch();
        }
        return ps;
    }

}
