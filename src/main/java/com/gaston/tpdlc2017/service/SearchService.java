/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Documento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gaston
 */
@Service
public class SearchService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(SearchService.class);
    
    public static final String PREFIX = "SELECT palabrasxdocumentos.frecuencia,"+
    "documentos.id, documentos.name, palabras.maxCount"+ 
        " FROM palabrasxdocumentos"+
        " INNER JOIN palabras ON palabras.id = palabrasxdocumentos.idPalabra"+
        " INNER JOIN documentos ON documentos.id = palabrasxdocumentos.idDocumento WHERE ";
    public static final String SUFFIX = " ORDER BY palabras.maxCount";

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
    

    public List<Documento> search(String[] querySearch) {
        
      
        StringJoiner sj = new StringJoiner(" OR ", PREFIX, SUFFIX);
        for (String st: querySearch) {
            sj.add("palabras.id = ?");
            
        }
        String query = sj.toString();
        logger.info("La busqueda es: "+ query);

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            
            for (int i = 0; i < querySearch.length; i++) {
                
                ps.setBytes(i+1, hashService.hash(querySearch[i]));
                
            }
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) { 
                logger.info(rs.getString(3));
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(SearchService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

}
