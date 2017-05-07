package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Documento;
import com.mysql.cj.api.mysqla.result.Resultset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

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
    public boolean exists(String hash) {

        String sqlNew = "SELECT FROM documentos (hash) WHERE (documentos.hash = ?)";
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlNew);

            ps.setString(1, hash);

            ResultSet rs = ps.executeQuery();

            ps.close();
            return rs.getFetchSize() == 1;
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

    @Override
    public Documento find(String hash) {
        String sqlNew = "SELECT FROM documentos (hash) WHERE (documentos.hash = ?)";
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlNew);

            ps.setString(1, hash);

            ResultSet re = ps.executeQuery();
            if(re.getFetchSize() == 1 ){
                re.next();

                Documento doc = new Documento(
                        re.getInt("id"),
                        re.getString("name"),
                        re.getString("hash")
                );
                return doc;
            }
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
        return null;
    }
}
