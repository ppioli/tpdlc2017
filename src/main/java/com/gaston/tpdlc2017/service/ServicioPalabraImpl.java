package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Palabra;
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

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insertar(Palabra palabra) {
        String sql = "INSERT INTO PALABRAS " +
                "(id, valor, cuenta_max) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, palabra.getId());
            ps.setString(2, palabra.getValor());
            ps.setInt(3, palabra.getCuentaMaxima());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
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
    public Palabra buscarPalabra(int id) {
        String sql = "SELECT * FROM palabras WHERE id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            Palabra palabra = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                palabra = new Palabra(
                        rs.getInt("id"),
                        rs.getString("valor"),
                        rs.getInt("cuenta_max")
                );
            }
            rs.close();
            ps.close();
            return palabra;
        } catch (SQLException e) {
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
