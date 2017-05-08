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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

import com.gaston.tpdlc2017.model.Palabra;
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
    /*
    public static final String PREFIX = "SELECT documentos.id,"+
        " palabras.val, palabrasxdocumentos.frecuencia, palabras.docCount"+
        " FROM palabrasxdocumentos"+
        " INNER JOIN palabras ON palabras.id = palabrasxdocumentos.idPalabra"+
        " INNER JOIN documentos ON documentos.id = palabrasxdocumentos.idDocumento WHERE ";
    public static final String SUFFIX = " ORDER BY palabras.id, palabrasxdocumentos.frecuencia DESC";
    */
    public static final int M = 5;

    public static final String SQL = "SELECT documentos.id, documentos.name"+
            " FROM palabrasxdocumentos"+
            " INNER JOIN palabras ON palabras.id = palabrasxdocumentos.idPalabra"+
            " INNER JOIN documentos ON documentos.id = palabrasxdocumentos.idDocumento" +
            " WHERE palabras.id = ? "+
            " ORDER BY palabrasxdocumentos.frecuencia DESC" +
            " LIMIT "+M;

    private DataSource dataSource;
    private HashingService hashService;
    private DocumentoService documentoService;
    private ServicioPalabra palabraService;


    @Autowired
    public void setPalabraService(ServicioPalabra palabraService) {
        this.palabraService = palabraService;
    }

    @Autowired
    public void setDocumentoService(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Autowired
    public void setHashService(HashingService hashService) {
        this.hashService = hashService;
    }
    

    public List<Documento> search(String[] querySearch) {
        List<Documento> listaDocumentos = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL)) {
            int N =  documentoService.getCount(conn);
            List<Palabra> ranking = palabraService.rank(querySearch, N, conn);

            for( Palabra p : ranking){
                ps.setBytes(1, p.getId());
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    byte[] hash = rs.getBytes(1);
                    listaDocumentos.add( new Documento( hash,
                            rs.getString(2),
                            hashService.hashToFileName(hash))
                    );
                }
            }
            return listaDocumentos;
        } catch (SQLException ex) {
            Logger.getLogger(SearchService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

}
