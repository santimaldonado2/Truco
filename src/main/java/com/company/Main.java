package com.company;

import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.Line;
import javax.swing.JOptionPane;

import com.company.ui.Graficador;
import com.company.utils.GestorEnvido;
import com.company.utils.GestorPuntajes;
import com.company.utils.LinearRegression;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // write your code here

        Jugador jugadorHumano = new Jugador("Jugador Humano");
        Jugador jugadorMaquina = new Jugador("Jugador Maquina");

        // Aca seteamos la confianza de la maquina para jugar.
        jugadorMaquina.setConfianza_envido(0.655);
        jugadorMaquina.setConfianza_envido_envido(0.715);
        jugadorMaquina.setConfianza_real_envido(0.765);
        jugadorMaquina.setConfianza_falta_envido(0.825);

        jugadorMaquina.setNivel_mentira(0.735);

        GestorPuntajes.init();

        int op;
        int jugadorMano = 1;
        do {
            // Aca cargamos todas las probabilidades para el calculo de la
            // regresion
            LinearRegression.cargarDatos();
            Baraja baraja = new Baraja(Baraja.REDUCIDA, true);
            Ronda ronda = new Ronda();
            Graficador graficador = new Graficador();
            GestorEnvido gestorEnvido = new GestorEnvido(jugadorHumano, jugadorMaquina, jugadorMano, graficador);
            graficador.dibujarVentana();
            System.out.println("dibujada");
            ronda.init(jugadorMano);

            for (int i = 0; i < 3; i++) {
                Carta cartaHumano = baraja.pedir();
                jugadorHumano.recibir(cartaHumano);

                Carta cartaMaquina = baraja.pedir();
                jugadorMaquina.recibir(cartaMaquina);
            }
            jugadorMaquina.calcularPuntosEnvido();
            jugadorHumano.calcularPuntosEnvido();

            graficador.dibujarCartasJugador(jugadorHumano.getCartas(), true);
            graficador.dibujarCartasJugador(jugadorMaquina.getCartas(), false);
            graficador.dibujarPuntajes(GestorPuntajes.getPuntosHumano(), GestorPuntajes.getPuntosMaquina());

            List<Carta> nuevaBaraja = baraja.getMazo();
            nuevaBaraja.addAll(jugadorHumano.getCartas());

            Arbol arbol = new Arbol(new Nodo(jugadorMaquina.getCartas(), new Mesa(), nuevaBaraja, false));
            arbol.generarArbol();

            while (ronda.getEstado() == Ronda.ACTIVA) {

                while (ronda.getEstadoMano() == Ronda.EN_JUEGO) {
                    if (ronda.getJugadorTurno() == 1) {
                        System.out.println("Su turno " + jugadorHumano.getNombre());
                        System.out.println("Estas son sus cartas");
                        int index = 0;
                        int cantidadCartasEnMano = jugadorHumano.getCantidadCartas();
                        Object[] options = new Object[cantidadCartasEnMano];
                        for (Carta c : jugadorHumano.getCartas()) {
                            options[index] = c.toString();
                            index++;
                            System.out.println("[" + index + "]" + c);
                        }
                        if (ronda.getMano() == 1) {
                            if (gestorEnvido.getEstado() == GestorEnvido.SIN_CANTAR) {
                                int cantaEnvido = JOptionPane.showOptionDialog(graficador.getV(),
                                        "¿Desea cantar Envido?",
                                        "Cantar Envido", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                                if (cantaEnvido == JOptionPane.YES_OPTION) {
                                    gestorEnvido.cantarEnvido(GestorEnvido.JUGADOR_HUMANO);
                                }
                            }
                        }

                        graficador.dibujarPuntajes(GestorPuntajes.getPuntosHumano(), GestorPuntajes.getPuntosMaquina());

                        int num_carta = JOptionPane.showOptionDialog(graficador.getV(),
                                "Es su turno. ¿Qué carta desea tirar?", "Su turno", JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        System.out.println("num_carta: " + num_carta);
                        ronda.jugarCarta(jugadorHumano.jugarCarta(num_carta));
                        System.out.println(ronda);
                        graficador.dibujarPuntajes(GestorPuntajes.getPuntosHumano(), GestorPuntajes.getPuntosMaquina());
                    } else {
                        // Calculo el puntaje del envido en esta mano
                        // Aca voy a decidir si canto envido o no
                        if (ronda.getMano() == Ronda.PRIMERA) {
                            if ((gestorEnvido.getEstado() == GestorEnvido.SIN_CANTAR) && (jugadorMaquina.jugarEnvido() || jugadorMaquina.jugarEnvidoEnBaseAPuntosRegresionLineal())) {
                                gestorEnvido.cantarEnvido(GestorEnvido.JUGADOR_MAQUINA);
                            }
                            System.out.println("La maquina juega el envido");
                        }
                        graficador.dibujarPuntajes(GestorPuntajes.getPuntosHumano(), GestorPuntajes.getPuntosMaquina());
                        System.out.println("TURNO " + jugadorMaquina.getNombre());
                        int index = 0;
                        for (Carta c : jugadorMaquina.getCartas()) {
                            index++;
                            System.out.println("[" + index + "]" + c);
                        }
                        ronda.jugarCarta(jugadorMaquina.JuegoAutomatico(ronda, arbol, ronda.pcEsPrimeraEnMano()));
                        System.out.println(ronda);
                        graficador.dibujarPuntajes(GestorPuntajes.getPuntosHumano(), GestorPuntajes.getPuntosMaquina());
                        Thread.sleep(2000);
                    }
                    int puntosEnvidoMaquina = jugadorMaquina.getPuntosEnvido();
                    int puntosEnvidoHumano = jugadorHumano.getPuntosEnvido();
                    boolean maquinaGanoEnvido = (puntosEnvidoMaquina > puntosEnvidoHumano) || ((puntosEnvidoMaquina == puntosEnvidoHumano) && (jugadorMano == 2));
                    LinearRegression.agregarDatos(puntosEnvidoMaquina, maquinaGanoEnvido);

                    graficador.dibujarCartasJugador(jugadorHumano.getCartas(), true);
                    graficador.dibujarCartasJugador(jugadorMaquina.getCartas(), false);
                    graficador.dibujarMesa(ronda.getMesa());
                }
                // TRUNCO EL ARBOL, PRIMERO AL ESTADO CON MI CARTA JUGADA
                arbol.MoverAHijo(ronda.getCartaJugada(ronda.getMano(), 2));
                // Y DESPUES AL ESTADO CON AMBAS CARTAS JUGADAS
                arbol.MoverAHijo(ronda.getCartaJugada(ronda.getMano(), 1));
                ronda.avanzarMano();
            }
            String resultado;
            switch (ronda.getResultado()) {
                case Ronda.GANA_JUGADOR1:
                    resultado = "GANADOR " + jugadorHumano.getNombre();
                    break;
                case Ronda.GANA_JUGADOR2:
                    resultado = "GANADOR " + jugadorMaquina.getNombre();
                    break;
                default:
                    resultado = "ALGO ANDA MAL";
                    break;
            }
            if (jugadorMano == 1) {
                jugadorMano = 2;
            } else
                jugadorMano = 1;
            jugadorHumano.soltarCartas();
            jugadorMaquina.soltarCartas();
            System.out.println(ronda.printResultados(jugadorHumano, jugadorMaquina));
            op = JOptionPane.showOptionDialog(graficador.getV(),
                    resultado + "\n" + ronda.printResultados(jugadorHumano, jugadorMaquina)
                            + "\n¿Desea volver a jugar?",
                    "Ronda Finalizada", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        } while (op != 1);
    }

}
