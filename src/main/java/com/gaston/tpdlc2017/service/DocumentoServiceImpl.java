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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author gaston
 */
@Service
public class DocumentoServiceImpl implements DocumentoService{
    private final Logger logger = LoggerFactory.getLogger(DocumentoServiceImpl.class);

    private DataSource dataSource;
    private HashingService hashingService;

    private static final String CREATE_OR_UPDATE = "INSERT INTO documentos (id, name) VALUES ( ?, ? )";
    private static final String SELECT =  "SELECT * FROM documentos WHERE id = ?";
    private static final String COUNT =  "SELECT count(*) FROM documentos";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setService(HashingService service) {
        this.hashingService = service;
    }

    @Override
    public Documento find(byte[] id) {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SELECT)) {
            pstmt.setBytes(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] bytes = rs.getBytes("id");
                String name = rs.getString("name");
                return new Documento(bytes, name, hashingService.hashToFileName(bytes));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createOrUpdate(byte[] id, String name, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(CREATE_OR_UPDATE);
        ps.setBytes(1, id);
        ps.setString(2, name);
        ps.executeUpdate();
    }

    @Override
    public int getCount(Connection conn) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement(COUNT)){
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            rs.next();
            return rs.getInt(1);
        }
    }
}
