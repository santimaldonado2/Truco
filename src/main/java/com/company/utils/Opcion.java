package com.company.utils;

/**
 * Created by Maldo on 1/11/2016.
 */
public class Opcion {
     private String texto;
     private int numero;

    public Opcion(String texto, int numero) {
        this.texto = texto;
        this.numero = numero;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString(){
        return this.texto;
    }
}
