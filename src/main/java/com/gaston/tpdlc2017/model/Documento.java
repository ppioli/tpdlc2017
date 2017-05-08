package com.gaston.tpdlc2017.model;

/**
 * Created by ppioli on 07/05/17.
 */

public class Documento {

    private String name;
    private byte[] hash;
    private Integer id;

    public Documento(Integer id, String name, byte[] hash) {
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

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
