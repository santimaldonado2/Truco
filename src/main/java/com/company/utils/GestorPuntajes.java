package com.company.utils;

/**
 * Created by aherrera on 1/11/2016.
 */
public class GestorPuntajes {
    public static final int JUGADOR_HUMANO = 1;
    public static final int JUGADOR_MAQUINA = 2;

    private static int puntosHumano;
    private static int puntosMaquina;

    public static int getPuntosHumano() {
        return puntosHumano;
    }

    public static void setPuntosHumano(int puntos) {
        puntosHumano = puntos;
    }

    public static int getPuntosMaquina() {
        return puntosMaquina;
    }

    public static void setPuntosMaquina(int puntos) {
        puntosMaquina = puntos;
    }

    public static void sumarPuntosHumano(int puntos){
        puntosHumano += puntos;
    }

    public static void sumarPuntosMaquina(int puntos){
        puntosMaquina += puntos;
    }

    public static void init(){
        setPuntosHumano(0);
        setPuntosMaquina(0);
    }
}
