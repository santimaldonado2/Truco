package com.company;

import com.company.ui.GestorGraficos;
import com.company.ui.Graficador;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // write your code here

        Jugador jugadorHumano = new Jugador("Jugador Humano");
        Jugador jugadorMaquina = new Jugador("Jugador Maquina");
//        String opcion;
        int op = 0;
        Scanner s = new Scanner(System.in);
//        System.out.println("Ingrese su nombre");
//        String nombre =  s.nextLine();
//        String nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre:");
//        jugadorHumano.setNombre(nombre);
        int jugadorMano = 1;
        do {
            Baraja baraja = new Baraja(Baraja.REDUCIDA, true);
            Ronda ronda = new Ronda();
            Graficador graficador = new Graficador();
            graficador.dibujarVentana();
            System.out.println("dibujada");
            ronda.init(jugadorMano);


            for (int i = 0; i < 3; i++) {
                Carta cartaHumano = baraja.pedir();
                jugadorHumano.recibir(cartaHumano);
//                graficador.dibujarCarta(cartaHumano, true, i);

                Carta cartaMaquina = baraja.pedir();
                jugadorMaquina.recibir(cartaMaquina);
//                graficador.dibujarCarta(cartaMaquina, false, i);
            }
            jugadorHumano.calcularPuntosEnvido();
            jugadorMaquina.calcularPuntosEnvido();
            graficador.dibujarCartasJugador(jugadorHumano.getCartas(), true);
            graficador.dibujarCartasJugador(jugadorMaquina.getCartas(), false);

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
                        if(ronda.getMano() == 1)
                            System.out.println("SUS PUNTOS DEL ENVIDO SON: " + jugadorHumano.getPuntosEnvido());
//                        System.out.println("Ingrese la carta que quiere jugar");
//                        int num_carta = Integer.parseInt(s.nextLine());

                        int num_carta = JOptionPane.showOptionDialog(graficador.getV(),
                                "Es su turno. ¿Qué carta desea tirar?",
                                "Su turno",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                        System.out.println("num_carta: " + num_carta);
                        ronda.jugarCarta(jugadorHumano.jugarCarta(num_carta));
                        System.out.println(ronda);
                    } else {
                        System.out.println("TURNO " + jugadorMaquina.getNombre());
                        int index = 0;
                        for (Carta c : jugadorMaquina.getCartas()) {
                            index++;
                            System.out.println("[" + index + "]" + c);
                        }
                        if(ronda.getMano() == 1)
                            System.out.println("SUS PUNTOS DEL ENVIDO SON: " + jugadorMaquina.getPuntosEnvido());
                        ronda.jugarCarta(jugadorMaquina.JuegoAutomatico(ronda, arbol, ronda.pcEsPrimeraEnMano()));
//                        ronda.jugarCarta(j2.jugarCarta(0));
                        System.out.println(ronda);
                        Thread.sleep(2000);
                    }
                    graficador.dibujarCartasJugador(jugadorHumano.getCartas(), true);
                    graficador.dibujarCartasJugador(jugadorMaquina.getCartas(), false);
                    graficador.dibujarMesa(ronda.getMesa());
                }
                //TRUNCO EL ARBOL, PRIMERO AL ESTADO CON MI CARTA JUGADA
                arbol.MoverAHijo(ronda.getCartaJugada(ronda.getMano(), 2));
                //Y DESPUES AL ESTADO CON AMBAS CARTAS JUGADAS
                arbol.MoverAHijo(ronda.getCartaJugada(ronda.getMano(), 1));
                ronda.avanzarMano();
            }
            String resultado = "";
            switch (ronda.getResultado()) {
                case Ronda.GANA_JUGADOR1:
                    resultado = "GANADOR " + jugadorHumano.getNombre();
//                    System.out.println("GANADOR " + jugadorHumano.getNombre());
                    break;
                case Ronda.GANA_JUGADOR2:
                    resultado = "GANADOR " + jugadorMaquina.getNombre();
//                    System.out.println("GANADOR " + jugadorMaquina.getNombre());
                    break;
                default:
                    resultado = "ALGO ANDA MAL";
//                    System.out.println("ALGO ANDA MAL");
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
                            + "\nPuntos Maquina: "+ jugadorMaquina.getPuntosEnvido()
                            + "\nPuntos Humano: "+ jugadorHumano.getPuntosEnvido()+"\n¿Desea volver a jugar?",
                    "Ronda Finalizada",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, null, null
            );
//            System.out.println("Desea Jugar de nuevo? (Ingrese S o s para jugar de nuevo, cualquier otra cosa para finalizar");
//            opcion = s.nextLine();

        }
        while (op != 1);
//        while(opcion != "S" || opcion != "s");
    }

}
