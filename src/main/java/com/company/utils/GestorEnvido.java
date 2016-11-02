package com.company.utils;

import com.company.Jugador;
import com.company.ui.Graficador;

import javax.swing.JOptionPane;

/**
 * Created by Maldo on 1/11/2016.
 */
public class GestorEnvido {
    public static final int JUGADOR_HUMANO = 1;
    public static final int JUGADOR_MAQUINA = 2;

    public static final int SIN_CANTAR = 1;
    public static final int ESPERANDO_RESPUESTA = 2;
    public static final int FINALIZADO = 3;
    //atributo que indica si esta sin cantar, o ya cantado, aceptado o rechazado
    private int estado;

    public static final int ENVIDO = 1;
    public static final int ENVIDO_ENVIDO = 2;
    public static final int REAL_ENVIDO = 3;
    public static final int FALTA_ENVIDO = 4;
    //atributo que indica si es envido,envido-envido, envido-envido-realenvido, falta
    private int nivel;

    public static final int ACEPTAR = 0;
    public static final int RECHAZAR = 1;
    public static final int SUBIR = 2;

    private int jugadorCantador;

    private int jugadorGanador;

    private int puntosGanados;

    public GestorEnvido(Jugador jugadorHumano, Jugador jugadorMaquina, int jugadorMano, Graficador graficador) {
        this.jugadorHumano = jugadorHumano;
        this.jugadorMaquina = jugadorMaquina;
        this.jugadorMano = jugadorMano;
        this.graficador = graficador;
        this.estado = SIN_CANTAR;
    }

    private Jugador jugadorHumano, jugadorMaquina;
    private int jugadorMano;

    private Graficador graficador;

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

    public Jugador getJugadorHumano() {
        return jugadorHumano;
    }

    public void setJugadorHumano(Jugador jugadorHumano) {
        this.jugadorHumano = jugadorHumano;
    }

    public Jugador getJugadorMaquina() {
        return jugadorMaquina;
    }

    public void setJugadorMaquina(Jugador jugadorMaquina) {
        this.jugadorMaquina = jugadorMaquina;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }


    public int getPuntosNoQuerido() {
        switch (nivel) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 7;
            default:
                return 0;
        }
    }

    public int getPuntosGanado() {
        switch (nivel) {
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 7;
            case 4:
                return 10;
            default:
                return 0;
        }
    }
    public String getNombreNivel(){
        switch (nivel) {
            case 1:
                return "Envido";
            case 2:
                return "Envido-Envido";
            case 3:
                return "Real Envido";
            case 4:
                return "Falta Envido";
            default:
                return "Error";
        }
    }

    public void cantarEnvido(int jugadorCantador) {
        this.jugadorCantador = jugadorCantador;
        this.estado = ESPERANDO_RESPUESTA;
        this.nivel = ENVIDO;

        responderEnvido();


    }

    public void responderEnvido() {
        if (jugadorCantador == JUGADOR_HUMANO) {
            int respuesta = jugadorMaquina.responderEnvido(this.nivel);
            switch (respuesta) {
                case ACEPTAR:
                    this.aceptarEnvido(jugadorHumano.getPuntosEnvido(), jugadorMaquina.getPuntosEnvido(), jugadorMano);
                    break;
                case RECHAZAR:
                    this.rechazarEnvido();
                    break;
                case SUBIR:
                    this.subirEnvido(JUGADOR_MAQUINA);

            }
        }
        else{
            Object[] options;
            Opcion opcion;
            if (nivel < 4){
                 options = new Object[3];
                opcion = new Opcion("Subir",2);
                options[2] = opcion;
            }
            else
            {
                options = new Object[2];
            }
            opcion = new Opcion("Aceptar",0);
            options[0] = opcion;
            opcion = new Opcion("Rechazar",2);
            options[1] = opcion;



            int respuesta = JOptionPane.showOptionDialog(graficador.getV(),
                    "El otro jugador cantó "+ getNombreNivel(), "RESPONDER", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            switch (respuesta) {
                case ACEPTAR:
                    this.aceptarEnvido(jugadorHumano.getPuntosEnvido(), jugadorMaquina.getPuntosEnvido(), jugadorMano);
                    break;
                case RECHAZAR:
                    this.rechazarEnvido();
                    break;
                case SUBIR:
                    this.subirEnvido(JUGADOR_HUMANO);

            }



        }
    }


    public void subirEnvido(int jugadorCantador) {
        this.jugadorCantador = jugadorCantador;
        this.estado = ESPERANDO_RESPUESTA;
        this.nivel += 1;

        responderEnvido();
    }

    public void aceptarEnvido(int puntosHumano, int puntosMaquina, int mano) {
        if (puntosMaquina > puntosHumano)
            this.jugadorGanador = JUGADOR_MAQUINA;
        else if (puntosMaquina < puntosHumano)
            this.jugadorGanador = JUGADOR_HUMANO;
        else
            this.jugadorGanador = mano;

        this.puntosGanados = this.getPuntosGanado();
        this.estado = FINALIZADO;

        mensajeEnvidoFinalizado(true);


    }

    public void rechazarEnvido() {
        this.jugadorGanador = this.jugadorCantador;
        this.puntosGanados = this.getPuntosNoQuerido();
        this.estado = FINALIZADO;
        mensajeEnvidoFinalizado(false);
    }
    public void mensajeEnvidoFinalizado(boolean aceptado){
        String ganador= jugadorGanador== JUGADOR_HUMANO? "GANADOR HUMANO" : "GANADOR MAQUINA";
        Object[] options = {"OK"};

        if(jugadorGanador == JUGADOR_HUMANO){
            GestorPuntajes.sumarPuntosHumano(this.puntosGanados);
        }else{
            GestorPuntajes.sumarPuntosMaquina(this.puntosGanados);
        }

         if (aceptado){
             JOptionPane.showOptionDialog(graficador.getV(), ganador +
                             "\n" + "Gano : "+ this.puntosGanados + " puntos"
                             +"\n " + "Puntos Jugador Humano: " + jugadorHumano.getPuntosEnvido()
                             +"\n " + "Puntos Jugador Maquina: " + jugadorMaquina.getPuntosEnvido()
                     ,
                     "Envido Finalizado", JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
         }
         else{
             JOptionPane.showOptionDialog(graficador.getV(), ganador +
                             "\n" + "Gano : "+ this.puntosGanados + " puntos"
                             +"\n " + "ENVIDO NO QUERIDO "  ,
                     "Envido Finalizado", JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,options, options[0]);
         }


    }

}
