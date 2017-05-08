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
    private DataSource dataSource;
    private HashingService hashService;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setHashService(HashingService hashService) {
        this.hashService = hashService;
    }

    @Override
    public Palabra get(byte[] hash) {
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
                } catch (SQLException e) {
                }
            }
        }

    }

    @Override
    public Palabra createOrUpdate(String valor, int count) {

        byte[] hash = hashService.hash(valor);
        Palabra pal = this.exists(hash);
        if (pal != null) {
            //update
            if (pal.getCuentaMaxima() < count) {
                String updateTableSQL = "UPDATE palabras SET maxCount = ? WHERE id = ?";
                Connection conn = null;
                try {
                    conn = dataSource.getConnection();
                    PreparedStatement ps = conn.prepareStatement(updateTableSQL, Statement.RETURN_GENERATED_KEYS);
                    
                    ps.setInt(1, count);
                    ps.setInt(2, pal.getId());
                    ps.executeUpdate();
                    ps.close();
                    
                    //actualizar pal
                    pal.setCuentaMaxima(count);
                    return pal; //count es el max por ser la primera vez que se ingresa
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } else {
                return pal; 
            }
        } else {
        
        String sqlNew = "INSERT INTO palabras (maxCount, val, hash) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlNew, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, count);
            ps.setString(2, valor);
            ps.setBytes(3, hash);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int id = -1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            ps.close();
            return new Palabra(id, valor, count); //count es el max por ser la primera vez que se ingresa
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}

@Override
        public Palabra exists(byte[] hash) {
        String selectSQL = "SELECT * FROM palabras WHERE hash = ?";
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
                Palabra pal = new Palabra(rs.getInt("id"), rs.getString("val"), rs.getInt("maxCount"));
                return pal;
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
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
