package com.gaston.tpdlc2017.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Base64;

/**
 *
 * @author gaston
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

    public byte[] hash(byte[] bytes){
        return messageDigest.digest(bytes);
    }

    public byte[] hash(String string){
        return messageDigest.digest(string.getBytes());
    }

    public String encodedHash(byte[] bytes){
        return this.encoder.encodeToString(bytes);
    }

    public String encodedHash(String string){
        return this.encoder.encodeToString(hash(string.getBytes()));
    }

    public String hashToFileName(byte[] bytes){
        return encodedHash(bytes) + ".txt";
    }
}
