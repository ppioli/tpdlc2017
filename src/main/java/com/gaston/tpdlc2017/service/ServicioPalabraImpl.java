package com.gaston.tpdlc2017.service;

import com.gaston.tpdlc2017.model.Palabra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ppioli on 07/05/17.
 */
@Service
public class ServicioPalabraImpl implements ServicioPalabra {
    private final Logger logger = LoggerFactory.getLogger(ServicioPalabraImpl.class);

    public static final String CREATE_OR_UPDATE= "INSERT INTO palabras (id, val, maxCount) VALUES ( ?, ?, ? ) "
        +"ON DUPLICATE KEY UPDATE maxCount=GREATEST(VALUES(maxCount), ?), docCount=docCount+1";

    public static final String SELECT = "SELECT * FROM palabras WHERE palabras.id = ?";

    private HashingService hashService;


    @Autowired
    public void setHashService(HashingService hashService) {
        this.hashService = hashService;
    }

    @Override
    public void createOrUpdate(String valor, int count, Connection conn) throws SQLException {

        byte[] hash = hashService.hash(valor);
        PreparedStatement ps = conn.prepareStatement(CREATE_OR_UPDATE);

        ps.setBytes(1, hash);
        ps.setString(2, valor);
        ps.setString(3, valor);
        ps.setString(4, valor);

        ps.executeUpdate();

    }

    @Override
    public List<Palabra> rank(String[] query, int totalDocuments, Connection conn) {
        List<Palabra> lista = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECT)){
            for (String palabra :query) {
                byte[] hash = hashService.hash(palabra);
                ps.setBytes(1 , hash);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    Palabra pal = new Palabra(hash, palabra,
                            rs.getInt("maxCount"),
                            rs.getInt("docCount"),
                            totalDocuments);
                    if(!pal.isStopWord()){
                        lista.add(pal);
                    }
                } else {
                    logger.info(String.format("Palabra: %s no encontrada", palabra));
                }
            }
            lista.sort( (palabraA, palabraB) -> {
                double scoreA = palabraA.getScore();
                double scoreB = palabraB.getScore();
                if(scoreA < scoreB) return 1;
                else if (scoreB < scoreA ) return -1;
                return 0; //should never happend
            });
            lista.forEach(pal -> logger.info(String.format("Palabra: %s  - Valor: %.2f( %d / %d )",
                    pal.getValor(), pal.getScore(),
                    pal.getDocCount(), pal.getTotalDoc())));
            return lista;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public PreparedStatement batchCreateOrUpdate(Set<Map.Entry<String, Integer>> entries, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(CREATE_OR_UPDATE);
        for (Map.Entry<String, Integer> entry : entries) {
            byte[] hash = hashService.hash(entry.getKey());
            ps.setBytes(1, hash);
            ps.setString(2, entry.getKey());
            ps.setInt(3, entry.getValue());
            ps.setInt(4, entry.getValue());
            ps.addBatch();
        }
        return ps;
    }

}
