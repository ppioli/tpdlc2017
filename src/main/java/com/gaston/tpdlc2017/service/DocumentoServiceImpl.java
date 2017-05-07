package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Documento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by ppioli on 07/05/17.
 */
@Service
public class DocumentoServiceImpl implements DocumentoService{
    private final Logger logger = LoggerFactory.getLogger(DocumentoServiceImpl.class);

    private DataSource dataSource;
    private HashingService hashingService;
    private MySQLUtils escapeUtils;

    @Autowired
    public void setEscapeUtils(MySQLUtils escapeUtils) {
        this.escapeUtils = escapeUtils;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setService(HashingService service) {
        this.hashingService = service;
    }

    @Override
    public boolean exists(Documento doc) {
        return false;
    }

    /**
     `id` int(8) NOT NULL AUTO_INCREMENT,
     `hash` varchar(250) NOT NULL,
     `name` varchar(20) NOT NULL,
     `blob` BLOB NOT NULL,
     PRIMARY KEY (`id`)
     */

    @Override
    public void create(Documento doc) {
        String sqlNew = "INSERT INTO documentos (hash, name) VALUES (?, ?)";
        String sqlUpdate = "INSERT INTO documentos (hash, name) VALUES (?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlNew);

            ps.setString(1, doc.getHash());
            ps.setString(2, doc.getName());
            ps.executeUpdate();
            ps.close();

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
