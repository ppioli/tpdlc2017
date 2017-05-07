package com.gaston.tpdlc2017.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Created by ppioli on 07/05/17.
 */
@Service
public class HashingService {

    private MessageDigest messageDigest;
    private Base64.Encoder encoder;

    @Autowired
    public void setMessageDigest(MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }
    @Autowired
    public void setEncoder(Base64.Encoder encoder) {
        this.encoder = encoder;
    }

    public String hash(byte[] bytes){
        try {
            byte[] hash = messageDigest.digest(bytes);
            byte[] encodedBytes = encoder.encodeToString(hash).getBytes("UTF8");
            return new String(encodedBytes, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
