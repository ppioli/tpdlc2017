package com.gaston.tpdlc2017.model;

/**
 * Created by ppioli on 07/05/17.
 */
public class Palabra {
    private int id;
    private String valor;
    private int cuentaMaxima;

    public Palabra(int id, String valor, int cuenta_maxima) {
        this.id = id;
        this.valor = valor;
        this.cuentaMaxima = cuenta_maxima;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
