package com.company;

/**
 * Created by Maldo on 23/10/2016.
 */
public class Ronda {
    private Carta[][] mesa;
    private int[] resultados;
    private int mano;
    private int jugadorMano;
    private int jugadorTurno;
    private int resultado;

    public int[] getResultados() {
        return resultados;
    }


    public Ronda(Carta[][] mesa, int[] resultados, int mano, int jugadorMano, int jugadorTurno, int resultado, int estadoMano, int estado) {
        this.mesa = mesa;
        this.resultados = resultados;
        this.mano = mano;
        this.jugadorMano = jugadorMano;
        this.jugadorTurno = jugadorTurno;
        this.resultado = resultado;
        this.estadoMano = estadoMano;
        this.estado = estado;
    }

    public String printResultados() {
        StringBuilder sb = new StringBuilder();
        sb.append("ganador 1ra: ");
        sb.append(this.getResultadoMano(PRIMERA) + "\n");
        sb.append("ganador 2ra: ");
        sb.append(this.getResultadoMano(SEGUNDA) + "\n");
        sb.append("ganador 3ra: ");
        sb.append(this.getResultadoMano(TERCERA) + "\n");
        return sb.toString();
    }

    public static final int GANA_JUGADOR1 = 1;
    public static final int EMPATE = 0;
    public static final int GANA_JUGADOR2 = 2;

    private int estadoMano;
    public static final int EN_JUEGO = 1;
    public static final int COMPLETA = 2;

    private int estado;
    public static final int ACTIVA = 1;
    public static final int FINALIZADA = 2;

    public Carta[][] getMesa() {
        return mesa;
    }

    public int getEstadoMano() {
        return estadoMano;
    }

    public void setEstadoMano(int estadoMano) {
        this.estadoMano = estadoMano;
    }

    public void setMesa(Carta[][] mesa) {
        this.mesa = mesa;
    }

    public int getMano() {
        return mano;
    }

    public void setMano(int mano) {
        this.mano = mano;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getJugadorMano() {
        return jugadorMano;
    }

    public void setJugadorMano(int jugadorMano) {
        this.jugadorMano = jugadorMano;
    }

    public int getJugadorTurno() {
        return jugadorTurno;
    }

    public void setJugadorTurno(int jugadorTurno) {
        this.jugadorTurno = jugadorTurno;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public void cambiarEstadoMano()
    {
        if (this.getCartaJugada(mano,1) != null && this.getCartaJugada(mano,2) != null)
        {
            this.estadoMano = FINALIZADA;
        }
        else{
            this.estadoMano = EN_JUEGO;
        }
    }

    public void avanzarMano()
    {
        this.evaluarMano(this.mano);
        this.evaluarRonda();
        this.mano ++;
        this.estadoMano = EN_JUEGO;
    }

    public static final int PRIMERA = 1;
    public static final int SEGUNDA = 2;
    public static final int TERCERA = 3;
    
    private Carta getCartaJugada(int mano, int jugador){
        return this.mesa[mano - 1][jugador -1];
    }
    
    private void setCartaJugada(int mano, int jugador,Carta c) {
        this.mesa[mano - 1][jugador - 1] = c;
    }
    

    public void evaluarMano(int mano) {
        if (this.getCartaJugada(mano,1).compareTo(this.getCartaJugada(mano,2)) > 0) {
            this.resultados[mano-1] = GANA_JUGADOR1;
            this.jugadorTurno = GANA_JUGADOR1;
        } else {
            if ((this.getCartaJugada(mano,1).compareTo(this.getCartaJugada(mano,2)) == 0)) {
                this.resultados[mano-1] = EMPATE;
            } else {
                this.resultados[mano-1] = GANA_JUGADOR2;
                this.jugadorTurno = GANA_JUGADOR2;
            }

        }
    }

    public void jugarCarta(Carta c)
    {
        this.setCartaJugada(this.mano,jugadorTurno,c);
        if(this.jugadorTurno == GANA_JUGADOR1){
            this.jugadorTurno = GANA_JUGADOR2;
        }
        else {
            this.jugadorTurno = GANA_JUGADOR1;
        }
        cambiarEstadoMano();
    }
    
    public int getResultadoMano(int mano)
    {
        return this.resultados[mano -1];
    }
    public void evaluarRonda() {
        if (mano == SEGUNDA) {
            if (getResultadoMano(PRIMERA) == GANA_JUGADOR1) {
                if (getResultadoMano(SEGUNDA) == GANA_JUGADOR1 || getResultadoMano(SEGUNDA) == EMPATE) {
                    this.resultado = GANA_JUGADOR1;
                    this.estado = FINALIZADA;
                }
            }
            else {
                if (getResultadoMano(PRIMERA) == EMPATE) {
                    if (getResultadoMano(SEGUNDA) != EMPATE) {
                        this.resultado = getResultadoMano(SEGUNDA);
                        this.estado = FINALIZADA;
                    }
                } else {
                    if (getResultadoMano(SEGUNDA) == GANA_JUGADOR2 || getResultadoMano(SEGUNDA) == EMPATE) {
                        this.resultado = GANA_JUGADOR2;
                        this.estado = FINALIZADA;
                    }
                }
            }


        }
        if (mano == TERCERA) {
            if (getResultadoMano(TERCERA) != EMPATE) {
                this.resultado = getResultadoMano(TERCERA);
            }
            else{
                if(getResultadoMano(PRIMERA) != EMPATE){
                    this.resultado = getResultadoMano(PRIMERA);
                }
                else{
                    this.resultado = this.jugadorMano;
                }
            }
            this.estado = FINALIZADA;

        }
    }

    public Ronda() {
    }

    public void init(int jugMano)
    {
        this.mesa = new Carta[3][2];
        this.jugadorMano = jugMano;
        this.estado = ACTIVA;
        this.mano = PRIMERA;
        this.jugadorTurno = jugMano;
        this.estadoMano = EN_JUEGO;
        this.resultados = new int[3];
    }
    public String toString(){
        StringBuilder  sb = new StringBuilder();
        sb.append("Jugador 1\n");
        sb.append("----------");
        for (int i = 1; i <4 ; i++) {
            if (this.getCartaJugada(i,1) == null)
            {
                sb.append("VACIO\n");
            }
            else {
                sb.append(this.getCartaJugada(i,1)+"\n");
            }
        }
        sb.append("+++++++++++++++\n");
        sb.append("Jugador 2\n");
        sb.append("----------\n");
        for (int i = 1; i <4 ; i++) {
            if (this.getCartaJugada(i,2) == null)
            {
                sb.append("VACIO\n");
            }
            else {
                sb.append(this.getCartaJugada(i,2)+"\n");
            }
        }

        return sb.toString();
    }

    public Ronda clone(){
        Carta[][] mesa = new Carta[3][2];
        for (int i = 0; i <mesa.length ; i++) {
            mesa[i] = this.mesa[i].clone();
        }
        Ronda clon = new Ronda(mesa,
                                this.resultados.clone(),this.mano,this.jugadorMano,this.jugadorTurno,this.resultado,this.estadoMano,this.estado);
        return clon;
    }

    public Carta getUltimaCartaHumano(){
        for (int i = 2; i >= 0; i--) {
            if(mesa[i][0]  != null){
                return mesa[i][0];
            }
        }
        return null;
    }
}
