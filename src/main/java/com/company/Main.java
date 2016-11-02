package com.company;

import java.util.List;

import javax.swing.JOptionPane;

import com.company.ui.Graficador;
import com.company.utils.GestorEnvido;
import com.company.utils.GestorPuntajes;
import com.company.utils.GestorTruco;
import com.company.utils.LinearRegression;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // write your code here

        Jugador jugadorHumano = new Jugador("Jugador Humano");
        Jugador jugadorMaquina = new Jugador("Jugador Maquina");

        // Aca seteamos la confianza de la maquina para jugar el envido.
        jugadorMaquina.setConfianza_envido(0.655);
        jugadorMaquina.setConfianza_envido_envido(0.715);
        jugadorMaquina.setConfianza_real_envido(0.765);
        jugadorMaquina.setConfianza_falta_envido(0.825);

        // Seteamos el nivel de mentira del envido
        jugadorMaquina.setNivel_mentira(0.735);

        // Seteamos el nivel de mentira del envido
        jugadorMaquina.setNivel_mentira_truco(0.735);

        // seteamos el nivel de confianza para jugar al truco
        jugadorMaquina.setConfianza_truco(0.632);

        // seteamos si queremos ver las probabilidades de la maquina o no
        boolean mostrarProbabilidadesMaquina = false;

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
            GestorTruco gestorTruco = new GestorTruco(graficador,jugadorHumano,jugadorMaquina,jugadorMano);
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
            if (mostrarProbabilidadesMaquina){ graficador.dibujarProbabilidades(jugadorMaquina.getProbabilidades(ronda, arbol, true)); }
            while (ronda.getEstado() == Ronda.ACTIVA && gestorTruco.getEstado() != GestorTruco.FINALIZADO ) {

                while (ronda.getEstadoMano() == Ronda.EN_JUEGO && gestorTruco.getEstado() != GestorTruco.FINALIZADO) {

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
                                        "�Desea cantar Envido?", "Cantar Envido", JOptionPane.YES_NO_OPTION,
                                        JOptionPane.INFORMATION_MESSAGE, null, null, null);
                                if (cantaEnvido == JOptionPane.YES_OPTION) {
                                    gestorEnvido.cantarEnvido(GestorEnvido.JUGADOR_HUMANO);
                                }
                            }
                        }
                        else {
                            if (gestorTruco.trucoSinCantar()) {

                                int truco = JOptionPane.showOptionDialog(graficador.getV(), "\n�Desea cantar truco ?",
                                        "Truco", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null,
                                        null);

                                if (truco == JOptionPane.YES_OPTION) {
                                    gestorTruco.cantarTruco(GestorTruco.JUGADOR_HUMANO,arbol,ronda,ronda.pcJugoEnEstaMano());
                                }

                            }
                        }
                        if (gestorTruco.getEstado() == GestorTruco.FINALIZADO) continue;
                        graficador.dibujarPuntajes(GestorPuntajes.getPuntosHumano(), GestorPuntajes.getPuntosMaquina());

                        int num_carta = JOptionPane.showOptionDialog(graficador.getV(),
                                "Es su turno. �Qu� carta desea tirar?", "Su turno", JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        System.out.println("num_carta: " + num_carta);

                        ronda.jugarCarta(jugadorHumano.jugarCarta(num_carta));
                        System.out.println(ronda);

                        graficador.dibujarPuntajes(GestorPuntajes.getPuntosHumano(), GestorPuntajes.getPuntosMaquina());
                        if (mostrarProbabilidadesMaquina){ graficador.dibujarProbabilidades(jugadorMaquina.getProbabilidades(ronda, arbol, ronda.pcEsPrimeraEnMano())); }
                    } else {
                        if (mostrarProbabilidadesMaquina){ graficador.dibujarProbabilidades(jugadorMaquina.getProbabilidades(ronda, arbol, ronda.pcEsPrimeraEnMano())); }
                        // Calculo el puntaje del envido en esta mano
                        // Aca voy a decidir si canto envido o no
                        if (ronda.getMano() == Ronda.PRIMERA) {
                            if ((gestorEnvido.getEstado() == GestorEnvido.SIN_CANTAR)
                                    && (jugadorMaquina.mentir(jugadorMaquina.getNivel_mentira())
                                            || jugadorMaquina.jugarEnvidoEnBaseAPuntosRegresionLineal())) {
                                gestorEnvido.cantarEnvido(GestorEnvido.JUGADOR_MAQUINA);
                            }
                            System.out.println("La maquina juega el envido");
                        }
                        else{
                            if(gestorTruco.trucoSinCantar()){
                                if(jugadorMaquina.mentir(jugadorMaquina.getNivel_mentira_truco()) || jugadorMaquina.cantarTrucoEnBaseAProbabilidad(arbol,ronda,ronda.pcEsPrimeraEnMano())){
                                    gestorTruco.cantarTruco(GestorTruco.JUGADOR_MAQUINA,arbol,ronda,ronda.pcEsPrimeraEnMano());
                                }
                            }

                        }
                        if (gestorTruco.getEstado() == GestorTruco.FINALIZADO) continue;

                        // Magia del truco - primera version

                        // Si es la segunda ronda o la tercera y todav�a no se
                        // canto truco lo sigue, checkea si miente o no, despues
                        // en base a la probabilidad de la siguiente mano y si
                        // gano la primera juega al truco. Se podr�a mejorar
//                      if (((ronda.getMano() == Ronda.SEGUNDA || ronda.getMano() == Ronda.TERCERA)
//                                && !gestorTruco.jugandoTruco())
//                                && (jugadorMaquina.mentir(jugadorMaquina.getNivel_mentira_truco()) || (jugadorMaquina
//                                        .jugarTrucoEnBaseAProbabiliadDeSiguienteMano(arbol.getRaiz().getProbabilidad()))
//                                        && ronda.getResultado() == Ronda.GANA_JUGADOR2)) {
//
//                            gestorTruco.setEstado(GestorTruco.TRUCO);
//                            System.out.println("Maquina canta truco");
//
//                            System.out.println("Probabilidad de ganar la mano o el partido? " + arbol.getRaiz());
//
//                        }

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
                    boolean maquinaGanoEnvido = (puntosEnvidoMaquina > puntosEnvidoHumano)
                            || ((puntosEnvidoMaquina == puntosEnvidoHumano) && (jugadorMano == 2));
                    LinearRegression.agregarDatos(puntosEnvidoMaquina, maquinaGanoEnvido);

                    graficador.dibujarCartasJugador(jugadorHumano.getCartas(), true);
                    graficador.dibujarCartasJugador(jugadorMaquina.getCartas(), false);
                    graficador.dibujarMesa(ronda.getMesa());
                }
                if(gestorTruco.getEstado()!= GestorTruco.FINALIZADO){
                // TRUNCO EL ARBOL, PRIMERO AL ESTADO CON MI CARTA JUGADA
                    arbol.MoverAHijo(ronda.getCartaJugada(ronda.getMano(), 2));
                    // Y DESPUES AL ESTADO CON AMBAS CARTAS JUGADAS
                    arbol.MoverAHijo(ronda.getCartaJugada(ronda.getMano(), 1));
                    ronda.avanzarMano();
                }
            }
            String resultado;
            switch (ronda.getResultado()) {
            case Ronda.GANA_JUGADOR1:
                resultado = "GANADOR " + jugadorHumano.getNombre();
                GestorPuntajes.sumarPuntosHumano(gestorTruco.getPuntosGanados());
                break;
            case Ronda.GANA_JUGADOR2:
                resultado = "GANADOR " + jugadorMaquina.getNombre();
                GestorPuntajes.sumarPuntosMaquina(gestorTruco.getPuntosGanados());
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
                            + "\n�Desea volver a jugar?",
                    "Ronda Finalizada", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        } while (op != 1);
    }

}
