package com.company.utils;

public class GestorTruco {

    public static final int SIN_CANTAR = 0;
    public static final int TRUCO = 1;
    public static final int RE_TRUCO = 2;
    public static final int VALE_CUATRO = 3;

    // En que etapa se esta jugando
    private int estado;

    public GestorTruco() {
        estado = SIN_CANTAR;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public boolean jugandoTruco() {
        return estado != SIN_CANTAR;
    }

}
