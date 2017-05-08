package com.gaston.tpdlc2017.model;

/**
 * Created by ppioli on 07/05/17.
 */
public class Palabra {
    public static final double MIN = 0.1;
    private byte[] id;
    private String valor;
    private int cuentaMaxima;
    private int docCount; // cantidad de documentos en los que aparece la palabra
    private int totalDoc; // cantidad de documentos en la base de datos

    public Palabra(byte[] id, String valor,
                   int cuenta_maxima,
                   int docCount,
                   int totalDoc)
    {
        this.id = id;
        this.valor = valor;
        this.cuentaMaxima = cuenta_maxima;
        this.docCount = docCount;
        this.totalDoc = totalDoc;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getCuentaMaxima() {
        return cuentaMaxima;
    }

    public void setCuentaMaxima(int cuenta_maxima) {
        this.cuentaMaxima = cuenta_maxima;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public boolean isStopWord() {
        return getScore() < MIN;
    }

    public double getScore(){
        return Math.log10( totalDoc / docCount);
    }
}
