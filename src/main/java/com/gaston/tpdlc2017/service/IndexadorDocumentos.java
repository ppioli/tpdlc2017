package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Documento;
import com.gaston.tpdlc2017.model.Palabra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import javax.sql.DataSource;

/**
 * Created by ppioli on 07/05/17.
 */
@Service
public class IndexadorDocumentos {

    private final Logger logger = LoggerFactory.getLogger(IndexadorDocumentos.class);

    private Path fileRepo;
    private HashingService hashingService;
    private ServicioPalabra servicioPalabra;
    private DocumentoService servicioDocumento;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setDocRepo(Path fileRepo) {
        this.fileRepo = fileRepo;
    }

    @Autowired
    public void setServicioPalabra(ServicioPalabra servicioPalabra) {
        this.servicioPalabra = servicioPalabra;
    }

    @Autowired
    public void setServicioDocumento(DocumentoService servicioDocumento) {
        this.servicioDocumento = servicioDocumento;
    }

    @Autowired
    public void setHashingService(HashingService hashingService) {
        this.hashingService = hashingService;
    }

    public String indexar(byte[] data, String name, byte[] hash)  {

        HashMap<String, Integer> freqMap = new HashMap<>();
        ByteArrayInputStream reader = new ByteArrayInputStream(data);
        DataInputStream input = new DataInputStream(reader);
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String line = null;
        try {
            line = br.readLine();
            while (line != null) {
                String[] palabrasDeLaLinea = line.split("[^a-zA-Záéíóúñ0-9]");
                for (String palabra : palabrasDeLaLinea) {                             //por cada palabra de la linea
                    if (palabra.length() > 1 && !palabra.matches(".*[0-9].*")) {      //elimino letras, espacios y numeros
                        palabra = palabra.toLowerCase();                                //elimino mayusculas
                        if (freqMap.containsKey(palabra)) {
                            freqMap.put(palabra, freqMap.get(palabra) + 1);
                        } else {
                            freqMap.put(palabra, 1);
                        }
                    }
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            return e.getMessage();
        }
        for (Map.Entry entry : freqMap.entrySet()) {
            logger.info(entry.getKey() + " -> " + entry.getValue());
        }
        return insertIntoDB(freqMap, name, data, hash);
    }

    private String insertIntoDB(HashMap<String, Integer> freqMap, String name, byte[] data, byte[] docHash) {

        String sqlNew = "INSERT INTO palabrasxdocumentos (idPalabra, idDocumento, frecuencia) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlNew))
        {
            conn.setAutoCommit(false);
            Set<String> palabras = freqMap.keySet();

            PreparedStatement palabrasBatch = servicioPalabra.batchCreateOrUpdate(palabras, conn);


            for (Map.Entry<String, Integer> entry : freqMap.entrySet()){
                ps.setBytes(1, hashingService.hash(entry.getKey()));
                ps.setBytes(2, docHash);
                ps.setInt(3, entry.getValue());
                ps.addBatch();
            }

            //crate the document
            servicioDocumento.createOrUpdate(docHash, name, conn);
            //insert all the words
            palabrasBatch.executeBatch();
            //insert all the wordxdocument
            ps.executeBatch();
            // write file
            Path filePath = fileRepo.resolve( hashingService.hashToFileName(docHash));
            Files.write(filePath, data);
            conn.commit();
            ps.close();
            return null;
        } catch (SQLException e){
            e.printStackTrace();
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}

