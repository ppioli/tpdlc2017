package com.gaston.tpdlc2017.model;

/**
 * Created by ppioli on 07/05/17.
 */
public class PalabraXDocumento {
    private Palabra palabra;
    private Documento documento;
    private int count;
    private int totalCount;

    public PalabraXDocumento(Palabra palabra, Documento documento, int count, int totalCount) {
        this.palabra = palabra;
        this.documento = documento;
        this.count = count;
        this.totalCount = totalCount;
    }

    public void setPalabra(Palabra palabra) {
        this.palabra = palabra;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
