package com.gaston.tpdlc2017.config;

import com.gaston.tpdlc2017.web.FileUploadController;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.nio.file.Path;  
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Configuration
@Import({DataBaseConfig.class})
@ComponentScan({ "com.gaston.tpdlc2017.service" })
public class SpringRootConfig {
    private final Logger logger = LoggerFactory.getLogger(SpringRootConfig.class);

   // public static final String docRepo = "/home/gaston/Documentos/uploads/"; //modificar
    public static final String docRepo = "/resources/uploads/"; //modificar

    @Bean
    public MessageDigest getMD5(){
        try {
            return MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Bean
    public Path baseUploadPath(){
        return Paths.get(docRepo);
    }

    @Bean
    public Base64.Encoder encoder(){
        return Base64.getUrlEncoder();
    }

}