package com.gaston.tpdlc2017.model;

import com.mysql.cj.jdbc.Blob;

import java.security.MessageDigest;
import java.util.Set;

/**
 * Created by ppioli on 07/05/17.
 */
public class Documento {


    private String name;
    private String hash;
    private Integer id;


    public Documento(Integer id, String name, String hash) {
        this.id = id;
        this.name = name;
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
