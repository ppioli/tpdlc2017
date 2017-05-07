package com.gaston.tpdlc2017.config;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Configuration
@Import({DataBaseConfig.class})
@ComponentScan({ "com.gaston.tpdlc2017.service" })
public class SpringRootConfig {

    @Bean
    public MessageDigest getMD5(){
        try {
            return MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public Base64.Encoder encoder(){
        return Base64.getUrlEncoder();
    }

}