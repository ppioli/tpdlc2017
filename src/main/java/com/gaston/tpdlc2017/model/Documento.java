package com.gaston.tpdlc2017.model;

import java.util.Arrays;

/**
 * Created by ppioli on 07/05/17.
 */

public class Documento implements Comparable<Documento>{

    private String name;
    private byte[] id;
    private String path;
    private double score;

    public Documento(byte[] id, String name, String path, double score) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.score = score;
    }

    public Documento(byte[] id, String name, String path){
        this(id, name, path, 0);
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Documento documento = (Documento) o;

        return Arrays.equals(id, documento.id);
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void addScore(double score){
        this.score += score;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(id);
    }


    @Override
    public int compareTo(Documento o) {
        double scoreB = o.getScore();
        if(getScore() < scoreB) return 1;
        else if (getScore() > scoreB ) return -1;
        return 0; //should never happend
    }
}
