package com.company;


import java.util.List;

public class Arbol {

    private Nodo raiz;

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    public void generarArbol(){
        raiz.generarHijosConMano();
        System.out.println("nodos " + raiz.getCantHijos());
        System.out.println("hojas " + raiz.getCantHojas());
    }

    public void MoverAHijo(Carta cartaJugada){
        String codigo = raiz.getMesa().generarCodigoMesa();
        codigo += cartaJugada.generarCodigo();
        raiz = raiz.hijos.get(codigo);
    }
}
