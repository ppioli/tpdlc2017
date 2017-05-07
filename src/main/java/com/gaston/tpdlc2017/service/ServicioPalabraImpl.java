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

/**
 * Created by ppioli on 07/05/17.
 */
@Service
public class ServicioPalabraImpl implements ServicioPalabra{

    private final Logger logger = LoggerFactory.getLogger(ServicioPalabraImpl.class);
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Palabra create(Palabra palabra) {
        return null;
    }

    @Override
    public Palabra get(byte[] hash){
        String selectSQL = "SELECT id, val, maxCount FROM palabras WHERE hash = ?";
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(selectSQL);
            pstmt.setBytes(1, hash);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Palabra(rs.getInt("id"),
                        rs.getString("val"),
                        rs.getInt("maxCount"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }

    }
}
