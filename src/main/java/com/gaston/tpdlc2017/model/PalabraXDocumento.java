package com.gaston.tpdlc2017.model;

/**
 * Created by ppioli on 07/05/17.
 */
public class PalabraXDocumento {
    private Palabra palabra;
    private Documento documento;
    private int count;

    public Palabra getPalabra() {
        return palabra;
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
