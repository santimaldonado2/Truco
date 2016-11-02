package com.company;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import com.company.utils.LinearRegression;

/**
 * Representa un Jugador para el juego del 7 y medio.
 *
 * @author Ing. Valerio Frittelli.
 * @version Mayo de 2011.
 */
public class Jugador {
    /**
     * La cantidad que dispone para apostar el jugador.
     */
    private LinkedList<Carta> cartas;

    public static final int ACEPTAR = 0;
    public static final int RECHAZAR = 1;
    public static final int SUBIR = 2;

    /**
     * El puntaje que posee el jugador a medida que recibe las cartas.
     */
    private float puntaje;

    /**
     * El nombre del jugador.
     */
    private String nombre;

    /**
     * La confianza que tiene la maquina para mentir, debe ser un numero entre 0
     * y 1.
     */
    private Double confianza_envido;
    private Double confianza_envido_envido;
    private double confianza_real_envido;
    private double confianza_falta_envido;

    private double confianza_truco;

    private double nivel_mentira_envido;
    private double nivel_mentira_truco;

    private int puntosEnvido;

    /**
     * La situacion del jugador. Cada jugador puede plantarse, continuar o
     * pasarse seg�n el momento del juego.
     */
    public LinkedList<Carta> getCartas() {
        return cartas;
    }

    public int getCantidadCartas() {
        return cartas.size();
    }

    /**
     * Constructor del jugador que no recibe par�metros.
     */
    public Jugador() {
        cartas = new LinkedList<Carta>();
        nombre = "No disponible";
    }

    /**
     * Constructor del jugador est�ndar.
     */
    public Jugador(String nom) {
        cartas = new LinkedList<Carta>();
        if (nom == null)
            nom = "No disponible";
        nombre = nom;
    }

    public Double getConfianza_envido() {
        return confianza_envido;
    }

    public void setConfianza_envido(Double confianza_envido) {
        this.confianza_envido = confianza_envido;
    }

    public Double getConfianza_envido_envido() {
        return confianza_envido_envido;
    }

    public void setConfianza_envido_envido(Double confianza_envido_envido) {
        this.confianza_envido_envido = confianza_envido_envido;
    }

    public double getConfianza_real_envido() {
        return confianza_real_envido;
    }

    public void setConfianza_real_envido(double confianza_real_envido) {
        this.confianza_real_envido = confianza_real_envido;
    }

    public double getConfianza_falta_envido() {
        return confianza_falta_envido;
    }

    public void setConfianza_falta_envido(double confianza_falta_envido) {
        this.confianza_falta_envido = confianza_falta_envido;
    }

    public double getNivel_mentira() {
        return nivel_mentira_envido;
    }

    public void setNivel_mentira(double nivel_mentira) {
        this.nivel_mentira_envido = nivel_mentira;
    }

    public double getNivel_mentira_truco() {
        return nivel_mentira_truco;
    }

    public void setNivel_mentira_truco(double nive_mentira_truco) {
        this.nivel_mentira_truco = nive_mentira_truco;
    }

    public double getConfianza_truco() {
        return confianza_truco;
    }

    public void setConfianza_truco(double confianza_truco) {
        this.confianza_truco = confianza_truco;
    }

    /**
     * M�todo accesor. Retorna el nombre del jugador.
     *
     * @return el nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * M�todo accesor. Define el nombre del jugador.
     *
     * @param n el nombre del jugador.
     */
    public void setNombre(String n) {
        nombre = n;
    }

    /**
     * M�todo accesor. Retorna el puntaje del jugador.
     *
     * @return el puntaje del jugador.
     */
    public float getPuntaje() {
        return puntaje;
    }

    /**
     * M�todo accesor. Define el puntaje del jugador.
     *
     * @param punt el puntaje del jugador.
     */
    public void setPuntaje(int punt) {
        puntaje = punt;
    }

    /**
     * Permite a los jugadores recibir una carta.
     *
     * @param c la carta que reciben.
     */
    public void recibir(Carta c) {
        cartas.addLast(c);
    }

    public int getPuntosEnvido() {
        return puntosEnvido;
    }

    public void setPuntosEnvido(int puntosEnvido) {
        this.puntosEnvido = puntosEnvido;
    }

    public Carta jugarCarta(int num) {
        Carta c = null;
        if (num < cartas.size()) {
            c = cartas.remove(num);
        } else {
            if (!cartas.isEmpty()) {
                c = cartas.removeFirst();
            }
        }
        return c;
    }

    public void soltarCartas() {
        cartas = new LinkedList<Carta>();
    }

    /**
     * M�todo que se utiliza para que otros jugadores definidos por el
     * programa jueguen autom�ticamente.
     *
     * @return la situacion del jugador.
     */

    private int getIndiceEnBarajaOrdenada(Carta c) {
        int valor = c.getValor();
        if (valor > 7) {
            valor = valor - 3;
        }
        int indice = valor + valor * c.getPalo();
        return indice;
    }

    public Carta JuegoAutomatico(Ronda ronda, Arbol arbol, boolean soyPrimero) {
        Carta cartaAJugar = null;
        float prob = 0.0f;
        if (soyPrimero) {

            for (Nodo hijo : arbol.getRaiz().getHijos().values()) {
                if (hijo.getProbabilidad() > prob) {
                    prob = hijo.getProbabilidad();
                    cartaAJugar = hijo.getMesa().getUltimaCartaJugadaMaquina();
                }
                /*
                 * modifico la logica, en el caso que tenga las mismas
                 * probabilidades en dos opciones, te juega la mas chota, onda
                 * para dejarte pasar en segunda y culiarte en tercera O si no
                 * hay ninguna carta jugada y ya perdiste, para que te juegue
                 * alguna porque sino no te juega nada
                 */
                else if ((hijo.getProbabilidad() == prob
                        && hijo.getMesa().getUltimaCartaJugadaMaquina().compareTo(cartaAJugar) <= 0)
                        || cartaAJugar == null) {
                    prob = hijo.getProbabilidad();
                    cartaAJugar = hijo.getMesa().getUltimaCartaJugadaMaquina();
                }
                System.out.println(hijo.getMesa().generarCodigoMesa() + ":" + hijo.getProbabilidad());
            }
        } else {
            Mesa mesa = arbol.getRaiz().getMesa();
            Carta cartaJugadaHumano = ronda.getUltimaCartaHumano();
            for (Carta carta : cartas) {
                Nodo hijo = arbol.getRaiz().getHijos()
                        .get(String.format("%s%s", mesa.generarCodigoMesa(), carta.generarCodigo()));
                Nodo nieto = hijo.getHijos().get(String.format("%s%s%s", mesa.generarCodigoMesa(),
                        carta.generarCodigo(), cartaJugadaHumano.generarCodigo()));

                if (nieto.getProbabilidad() > prob) {
                    prob = nieto.getProbabilidad();
                    cartaAJugar = carta;
                }
                /*
                 * modifico la logica, en el caso que tenga las mismas
                 * probabilidades en dos opciones, te juega la mas chota, onda
                 * para dejarte pasar en segunda y culiarte en tercera O si no
                 * hay ninguna carta jugada y ya perdiste, para que te juegue
                 * alguna porque sino no te juega nada
                 */
                else if (cartaAJugar == null
                        || (nieto.getProbabilidad() == prob && (carta.compareTo(cartaAJugar) <= 0))) {
                    prob = nieto.getProbabilidad();
                    cartaAJugar = carta;
                }

                System.out.println(nieto.getMesa().generarCodigoMesa() + ":" + nieto.getProbabilidad());

            }
        }
        cartas.remove(cartaAJugar);
        return cartaAJugar;
    }

    public int calcularPuntosCon3CartasDelMismoPalo() {

        int[] puntajesCarta = new int[3];
        int valor;
        for (int i = 0; i < 3; i++) {
            valor = cartas.get(i).getValor();
            if (valor > 7)
                valor = 0;

            puntajesCarta[i] = valor;
        }

        if (puntajesCarta[0] >= puntajesCarta[2] && puntajesCarta[1] >= puntajesCarta[2])
            return 20 + puntajesCarta[0] + puntajesCarta[1];
        else if (puntajesCarta[0] >= puntajesCarta[1] && puntajesCarta[2] >= puntajesCarta[1])
            return 20 + puntajesCarta[0] + puntajesCarta[2];
        else
            return 20 + puntajesCarta[1] + puntajesCarta[2];

    }

    public void calcularPuntosEnvido() {
        // la matriz de 2*4 tiene una columna por palo, y dos filas
        // en la primera guarda los puntos, y en la segunda la cantidad de
        // cartas con ese palo
        int[][] puntosPorPalo = new int[2][4];
        int palo;
        int valor;
        int puntaje = 0;

        for (Carta carta : cartas) {
            palo = carta.getPalo();
            valor = carta.getValor();
            if (valor > 7)
                puntosPorPalo[0][palo] += 0;
            else
                puntosPorPalo[0][palo] += valor;

            puntosPorPalo[1][palo] += 1;

            if (puntosPorPalo[1][palo] == 2)
                puntosPorPalo[0][palo] += 20;
            else if (puntosPorPalo[1][palo] == 3) {
                puntaje = this.calcularPuntosCon3CartasDelMismoPalo();
            }

        }

        if (puntaje == 0) {
            for (int i = 0; i < puntosPorPalo[0].length; i++) {
                if (puntosPorPalo[0][i] >= puntaje) {
                    puntaje = puntosPorPalo[0][i];
                }
            }
        }

        this.puntosEnvido = puntaje;
    }

    /**
     * Devuelve si true en caso de que la maquina se sienta confiable para
     * mentir y jugar el envido sin importar sus cartas, en caso de que no
     * supere la confianza devuelve falso. Todo es generado con un nivel de
     * confianza inicial y luego es calculado mediante un numero random y una
     * semilla tambien random.
     *
     * @return true si juega el envido, false en cualquier otro caso.
     */
    public boolean mentir(double nivel_mentira) {

        double seed = Math.random();
        Random random = new Random();
        random.setSeed((long) seed);

        return nivel_mentira < random.nextDouble();
    }

    /**
     * Determina si la maquina si juega el envido o no con los puntos que tiene.
     *
     * @param puntos puntos que tiene la maquina para jugar el envido.
     * @return true si juega el envido, false en cualquier otro caso.
     */
    public boolean jugarEnvidoEnBaseAPuntosRegresionLineal(double puntos) {
        double prob = LinearRegression.calcularProbabilidadGanarEnvido(puntos);
        System.out.println("probabilidad " + prob);

        return confianza_envido < prob;
    }

    public boolean jugarTrucoEnBaseAProbabiliadDeSiguienteMano(double prob) {

        return confianza_truco < prob;
    }

    /**
     * Determina si la maquina si juega el envido o no con los puntos que tiene.
     *
     *
     * @return true si juega el envido, false en cualquier otro caso.
     */
    public boolean jugarEnvidoEnBaseAPuntosRegresionLineal() {
        System.out.println("puntos envido: " + puntosEnvido);
        double prob = LinearRegression.calcularProbabilidadGanarEnvido(puntosEnvido);
        System.out.println("probabilidad " + prob);

        return confianza_envido < prob;
    }

    public boolean subirEnvido(int nivel) {
        double prob = LinearRegression.calcularProbabilidadGanarEnvido(puntosEnvido);
        switch (nivel) {
            case 1:
                return prob >= this.confianza_envido_envido || mentir(nivel_mentira_envido);
            case 2:
                return prob >= this.confianza_real_envido || mentir(nivel_mentira_envido);
            case 3:
                return prob >= this.confianza_falta_envido || mentir(nivel_mentira_envido);
            default:
                return false;
        }

    }

    public boolean aceptarEnvido(int nivel) {
        double prob = LinearRegression.calcularProbabilidadGanarEnvido(puntosEnvido);
        switch (nivel) {
            case 1:
                return prob >= this.confianza_envido;
            case 2:
                return prob >= this.confianza_envido_envido;
            case 3:
                return prob >= this.confianza_real_envido;
            case 4:
                return prob >= this.confianza_falta_envido;
            default:
                return false;
        }

    }

    public int responderEnvido(int nivel) {
        if (subirEnvido(nivel)) {
            System.out.println("Subido");
            System.out.println("Prob Reg:" + LinearRegression.calcularProbabilidadGanarEnvido(puntosEnvido));
            System.out.println("puntos : " + puntosEnvido);
            return SUBIR;
        } else if (aceptarEnvido(nivel)) {
            System.out.println("ACEPTADO");
            System.out.println("Prob Reg:" + LinearRegression.calcularProbabilidadGanarEnvido(puntosEnvido));
            System.out.println("puntos : " + puntosEnvido);
            return ACEPTAR;
        } else {
            System.out.println("RECHAZADO");
            System.out.println("Prob Reg:" + LinearRegression.calcularProbabilidadGanarEnvido(puntosEnvido));
            System.out.println("puntos : " + puntosEnvido);
            return RECHAZAR;
        }

    }

    private double getProbabildadHijos(Arbol arbol){
        double prob = 0.0;
        for (Nodo hijo : arbol.getRaiz().getHijos().values()) {
            if (hijo.getProbabilidad() >= prob) {
                prob = hijo.getProbabilidad();

            }
        }
        return prob;
    }

    private double getProbabilidadNietos(Ronda ronda,Arbol arbol){
        Mesa mesa = arbol.getRaiz().getMesa();
        double prob = 0.0;
        Carta cartaJugadaHumano = ronda.getUltimaCartaHumano();
        for (Carta carta : cartas) {
            Nodo hijo = arbol.getRaiz().getHijos()
                    .get(String.format("%s%s", mesa.generarCodigoMesa(), carta.generarCodigo()));
            Nodo nieto = hijo.getHijos().get(String.format("%s%s%s", mesa.generarCodigoMesa(),
                    carta.generarCodigo(), cartaJugadaHumano.generarCodigo()));

            if (nieto.getProbabilidad() > prob) {
                prob = nieto.getProbabilidad();
            }
        }

        return prob;
    }

    public boolean cantarTrucoEnBaseAProbabilidad(Arbol arbol,Ronda ronda,boolean soyPrimero) {
        double prob = 0.0;
        if(soyPrimero){
            prob = this.getProbabildadHijos(arbol);
        }
        else{
            prob = this.getProbabilidadNietos(ronda,arbol);
        }

        if (this.confianza_truco < prob)
            return true;
        else
            return false;
    }

    public boolean  responderTruco(Arbol arbol,Ronda ronda,boolean pcYaJugo){
        double prob = 0.0;
        Carta cartaJugada = ronda.getCartaJugada(ronda.getMano(),ronda.GANA_JUGADOR2);
        if(pcYaJugo){
            prob = arbol.getProbabilidadHijo(cartaJugada);
        }
        else {
            prob = this.getProbabildadHijos(arbol);
        }
        if (this.confianza_truco < prob)
            return true;
        else
            return false;
    }





    public Map<Carta, Float> getProbabilidades(Ronda ronda, Arbol arbol, boolean soyPrimero) {
        Map<Carta, Float> probabilidades = new HashMap<>();
        float probCarta;
        Carta carta;
        if (soyPrimero) {
            for (Nodo hijo : arbol.getRaiz().getHijos().values()) {
                probCarta = hijo.getProbabilidad();
                carta = hijo.getMesa().getUltimaCartaJugadaMaquina();
                probabilidades.put(carta, probCarta);
            }
        } else {
            Mesa mesa = arbol.getRaiz().getMesa();
            Carta cartaJugadaHumano = ronda.getUltimaCartaHumano();
            for (Carta c : cartas) {
                Nodo hijo = arbol.getRaiz().getHijos()
                        .get(String.format("%s%s", mesa.generarCodigoMesa(), c.generarCodigo()));
                Nodo nieto = hijo.getHijos().get(String.format("%s%s%s", mesa.generarCodigoMesa(), c.generarCodigo(), cartaJugadaHumano.generarCodigo()));
                probCarta = nieto.getProbabilidad();
                carta = c;
                probabilidades.put(carta, probCarta);
            }
        }
        return probabilidades;
    }

}
