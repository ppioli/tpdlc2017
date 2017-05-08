package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Documento;
import com.gaston.tpdlc2017.model.Palabra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ppioli on 07/05/17.
 */
@Service
public class IndexadorDocumentos {
    private final Logger logger = LoggerFactory.getLogger(IndexadorDocumentos.class);

    private Path fileRepo;
    private HashingService hashingService;
    private ServicioPalabra servicioPalabra;


    @Autowired
    public void setDocRepo(Path fileRepo) {
        this.fileRepo = fileRepo;
    }

    @Autowired
    public void setServicioPalabra(ServicioPalabra servicioPalabra) {
        this.servicioPalabra = servicioPalabra;
    }

    @Autowired
    public void setHashingService(HashingService hashingService) {
        this.hashingService = hashingService;
    }

    public boolean indexar(Documento doc) throws IOException {
        Map<String, Integer> freqMap = new HashMap<>();
        Path filePath = fileRepo.resolve(hashingService.hashToFileName(doc.getHash()));
        final FileReader fr = new FileReader(filePath.toFile());
        final BufferedReader br = new BufferedReader(fr);
        do {
            String linea = br.readLine();
            if (linea == null) {
                break;
            }
            String[] palabrasDeLaLinea = linea.split("[^a-zA-Záéíóúñ0-9]");
            for (String palabra : palabrasDeLaLinea) {                             //por cada palabra de la linea
                if (palabra.length() > 1 && !palabra.matches(".*[0-9].*")) {      //elimino letras, espacios y numeros
                    palabra = palabra.toLowerCase();                                //elimino mayusculas
                    if(freqMap.containsKey(palabra)){
                        freqMap.put(palabra, freqMap.get(palabra)+1);
                    } else {
                        freqMap.put(palabra, 1);
                    }
                }
            }
        } while (true);
        for(Map.Entry entry : freqMap.entrySet()){
            logger.info(entry.getKey() + " -> " + entry.getValue());
        }
        return true;
    }
}