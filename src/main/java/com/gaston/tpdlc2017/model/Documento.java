package com.gaston.tpdlc2017.model;

/**
 * Created by ppioli on 07/05/17.
 */

public class Documento {

    private String name;
    private byte[] id;

    public Documento(byte[] id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

}
