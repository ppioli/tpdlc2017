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
 *
 * @author gaston
 */
@Service
public class IndexadorDocumentos {

    private final Logger logger = LoggerFactory.getLogger(IndexadorDocumentos.class);

    private static final String INSERT = "INSERT INTO palabrasxdocumentos (idPalabra, idDocumento, frecuencia) VALUES (?, ?, ?)";

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

        logger.info(String.format("Analisis completado. %d palabras distintas encontradas.", freqMap.size()));

        return insertIntoDB(freqMap, name, data, hash);
    }

    private String insertIntoDB(HashMap<String, Integer> freqMap, String fileName, byte[] fileData, byte[] fileHash) {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT))
        {
            conn.setAutoCommit(false);

            logger.info(String.format("Creando batch de palabras"));
            //create batch palabras
            PreparedStatement palabrasBatch = servicioPalabra.batchCreateOrUpdate(freqMap.entrySet(), conn);
            logger.info(String.format("Creando batch de palabrasxdocumentos"));
            //create batch palabrasxdocument
            for (Map.Entry<String, Integer> entry : freqMap.entrySet()){
                ps.setBytes(1, hashingService.hash(entry.getKey()));
                ps.setBytes(2, fileHash);
                ps.setInt(3, entry.getValue());
                ps.addBatch();
            }

            //crate the document
            logger.info(String.format("Insertando documento"));
            servicioDocumento.createOrUpdate(fileHash, fileName, conn);
            //insert all the words
            logger.info(String.format("Insertando palabras"));
            palabrasBatch.executeBatch();
            //insert all the wordxdocument
            logger.info(String.format("Insertando palabrasxdocumento"));
            ps.executeBatch();
            // write file
            logger.info(String.format("Copiando el archivo"));
            Path filePath = fileRepo.resolve( hashingService.hashToFileName(fileHash));
            logger.info( "Writing file to "+ filePath);
            Files.write(filePath, fileData);
            logger.info(String.format("Consolidando cambios"));
            conn.commit();
            ps.close();
            logger.info(String.format("Todo joya, sin errores."));
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

/**
 * PreparedStm stm = ...
 * stn.execute() stn.addBatch() --> stm.batexecuteBatch()
 *
 *
 *
 */

