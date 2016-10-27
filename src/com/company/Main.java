package com.company;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
	// write your code here

        Jugador jugadorHumano = new Jugador( " ");
        Jugador jugadorMaquina = new Jugador("Jugador Maquina");
        String opcion;
        Scanner s = new Scanner(System.in);
        System.out.println("Ingrese su nombre");
        String nombre =  s.nextLine();
        int jugadorMano = 1;
        do{
            Baraja baraja = new Baraja(Baraja.REDUCIDA, true);
            Ronda ronda = new Ronda();
            ronda.init(jugadorMano);

            jugadorHumano.setNombre(nombre);

            for (int i = 0; i < 3 ; i++) {
                jugadorHumano.recibir(baraja.pedir());
                jugadorMaquina.recibir(baraja.pedir());
            }

            System.out.println("generando arbol chabon");
            int hola = 1;
            for (Carta c: jugadorMaquina.getCartas()) {
                hola++;
                System.out.println("["+ hola + "]" + c);
            }

            List<Carta> nuevaBaraja = baraja.getMazo();
            nuevaBaraja.addAll(jugadorHumano.getCartas());

            Arbol arbol = new Arbol(new Nodo(jugadorMaquina.getCartas(), new Mesa(), nuevaBaraja, false));
            arbol.generarArbol();
            System.out.println("\n termine el arbol chabon");

            while(ronda.getEstado() == Ronda.ACTIVA){

                while(ronda.getEstadoMano() == Ronda.EN_JUEGO)
                {
                    if (ronda.getJugadorTurno() == 1){
                        System.out.println("Su turno");
                        System.out.println("Estas son sus cartas");
                        int index = 0;
                        for (Carta c: jugadorHumano.getCartas()) {
                            index++;
                            System.out.println("["+ index + "]" + c);
                        }
                        System.out.println("Ingrese la carta que quiere jugar");
                        int num_carta = Integer.parseInt(s.nextLine());
                        num_carta--;
                        ronda.jugarCarta(jugadorHumano.jugarCarta(num_carta));
                        System.out.println(ronda);

                    }
                    else
                    {
                        System.out.println("TURNO "+jugadorMaquina.getNombre());
                        int index = 0;
                        for (Carta c: jugadorMaquina.getCartas()) {
                            index++;
                            System.out.println("["+ index + "]" + c);
                        }
                        ronda.jugarCarta(jugadorMaquina.JuegoAutomatico(ronda, arbol, false));
//                        ronda.jugarCarta(j2.jugarCarta(0));
                        System.out.println(ronda);
                        //Thread.sleep(2000);
                    }
                }
                ronda.avanzarMano();

            }
            switch (ronda.getResultado()){
                case Ronda.GANA_JUGADOR1:
                    System.out.println("GANADOR " + jugadorHumano.getNombre());
                    break;
                case Ronda.GANA_JUGADOR2:
                    System.out.println("GANADOR " + jugadorMaquina.getNombre());
                    break;
                default:
                    System.out.println("ALGO ANDA MAL");
                    break;
            }
            if (jugadorMano == 1){
                jugadorMano =2;
            }
            else
                jugadorMano = 1;
            jugadorHumano.soltarCartas();
            jugadorMaquina.soltarCartas();
            System.out.println(ronda.printResultados(jugadorHumano, jugadorMaquina));
            System.out.println("Desea Jugar de nuevo? (Ingrese S o s para jugar de nuevo, cualquier otra cosa para finalizar");
            opcion = s.nextLine();

        }
        while(opcion != "S" && opcion != "s");
    }
}
