package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Documento;
import com.gaston.tpdlc2017.model.Palabra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ppioli on 07/05/17.
 */
@Service
public class DocumentoServiceImpl implements DocumentoService{
    private final Logger logger = LoggerFactory.getLogger(DocumentoServiceImpl.class);

    private DataSource dataSource;

    private HashingService hashingService;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setService(HashingService service) {
        this.hashingService = service;
    }

    @Override
    public boolean exists(byte[] hash) {
        String selectSQL = "SELECT count(*) FROM documentos WHERE hash = ?";
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(selectSQL);
            pstmt.setBytes(1, hash);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int numberOfRows = rs.getInt(1);
                logger.info("Number of rows "+ numberOfRows);
                return numberOfRows != 0;
            } else {
                return false;
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

    @Override
    public int create(Documento doc) {
        String sqlNew = "INSERT INTO documentos (hash, name) VALUES (?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlNew, Statement.RETURN_GENERATED_KEYS);

            ps.setBytes(1, doc.getHash());
            ps.setString(2, doc.getName());

            int result = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int id = -1;
            if(rs.next())
            {
                id = rs.getInt(1);
            }

            ps.close();
            return id;
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

    @Override
    public void indexDocument(Documento doc) {
        Map<Palabra, Integer> map = new HashMap<>();
    }

    @Override
    public Documento find(String hash) {
        String sqlNew = "SELECT FROM documentos (hash) WHERE (documentos.hash = ?)";
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlNew);

            ps.setString(1, hash);

            ResultSet re = ps.executeQuery();
            ps.close();
            return null;
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
