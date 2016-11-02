package com.company.utils;

import com.company.Jugador;
import com.company.Arbol;
import com.company.Ronda;
import com.company.ui.Graficador;

import javax.swing.*;

public class GestorTruco {

    public static final int JUGADOR_HUMANO = 1;
    public static final int JUGADOR_MAQUINA = 2;

    public static final int ACEPTAR = 0;
    public static final int RECHAZAR = 1;


    public static final int SIN_CANTAR = 0;
    public static final int JUGANDO = 3;
    public static final int ESPERANDO_RESPUESTA = 1;
    public static final int FINALIZADO = 2;

    public static final int TRUCO = 1;
    public static final int RE_TRUCO = 2;
    public static final int VALE_CUATRO = 3;

    private int jugadorCantador;

    private int jugadorGanador;

    private int puntosGanados;
    private Graficador graficador;

    private Jugador jugadorHumano, jugadorMaquina;
    private int jugadorMano;

    // En que etapa se esta jugando
    private int estado;

    public GestorTruco() {
        estado = SIN_CANTAR;
    }

    public GestorTruco(Graficador graficador, Jugador jugadorHumano, Jugador jugadorMaquina, int jugadorMano) {
        this.graficador = graficador;
        this.jugadorHumano = jugadorHumano;
        this.jugadorMaquina = jugadorMaquina;
        this.jugadorMano = jugadorMano;
        this.estado = SIN_CANTAR;
        this.puntosGanados = 1;
    }

    public int getEstado() {
        return estado;
    }

    public int getJugadorCantador() {
        return jugadorCantador;
    }

    public void setJugadorCantador(int jugadorCantador) {
        this.jugadorCantador = jugadorCantador;
    }

    public int getJugadorGanador() {
        return jugadorGanador;
    }

    public void setJugadorGanador(int jugadorGanador) {
        this.jugadorGanador = jugadorGanador;
    }

    public int getPuntosGanados() {
        return puntosGanados;
    }

    public void setPuntosGanados(int puntosGanados) {
        this.puntosGanados = puntosGanados;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }



    public boolean trucoSinCantar() {
        return estado == SIN_CANTAR;
    }

    public void cantarTruco(int jugadorCantador,Arbol arbol,Ronda ronda, boolean pcYaJugo) {
        this.jugadorCantador = jugadorCantador;
        this.estado = ESPERANDO_RESPUESTA;

        responderTruco(arbol,ronda,pcYaJugo);
    }

    public void aceptarTruco() {
        this.estado = JUGANDO;
        this.puntosGanados = 2;
        if (this.jugadorCantador == JUGADOR_HUMANO) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(graficador.getV(),
                    "El otro jugador respondió QUIERO", "RESPONDER", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        }
    }

    public void rechazarTruco(Ronda ronda) {
        this.estado = FINALIZADO;
        this.puntosGanados = 1;
        this.jugadorGanador = this.jugadorCantador;
        ronda.setEstado(Ronda.FINALIZADA);
        ronda.setResultado(jugadorCantador);
        if (this.jugadorCantador == JUGADOR_HUMANO) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(graficador.getV(),
                    "El otro jugador respondió NO QUIERO, usted ha ganado", "RESPONDER", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        }
    }

    public void responderTruco(Arbol arbol,Ronda ronda,boolean pcYaJugo) {
        if (jugadorCantador == JUGADOR_HUMANO) {
            if( jugadorMaquina.responderTruco(arbol,ronda,pcYaJugo))
                  this.aceptarTruco();
            else
                  this.rechazarTruco(ronda);

        } else {
            Object[] options = new Object[2];
            Opcion opcion;
            opcion = new Opcion("Aceptar", 0);
            options[0] = opcion;
            opcion = new Opcion("Rechazar", 2);
            options[1] = opcion;


            int respuesta = JOptionPane.showOptionDialog(graficador.getV(),
                    "El otro jugador cantó TRUCO", "RESPONDER", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            switch (respuesta) {
                case ACEPTAR:
                    this.aceptarTruco();
                    break;
                case RECHAZAR:
                    this.rechazarTruco(ronda);
                    break;
            }


        }
    }

}
