package com.company;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
	// write your code here

        Jugador j1 = new Jugador( " ");
        Jugador j2 = new Jugador("MACHO ARGENTINO");
        String opcion;
        Scanner s = new Scanner(System.in);
        System.out.println("Ingrese su nombre");
        String nombre =  s.nextLine();
        int jugadorMano = 1;
        do{
            Baraja b = new Baraja(Baraja.REDUCIDA, true);
            Ronda r = new Ronda();
            r.init(jugadorMano);

            j1.setNombre(nombre);

            for (int i = 0; i < 3 ; i++) {
                j1.recibir(b.pedir());
                j2.recibir(b.pedir());
            }

            while(r.getEstado() == Ronda.ACTIVA){

                while(r.getEstadoMano() == Ronda.EN_JUEGO)
                {
                    if (r.getJugadorTurno() == 1){
                        System.out.println("Su turno");
                        System.out.println("Estas son sus cartas");
                        int index = 0;
                        for (Carta c: j1.getCartas()) {
                            index++;
                            System.out.println("["+ index + "]" + c);
                        }
                        System.out.println("Ingrese la carta que quiere jugar");
                        int num_carta = Integer.parseInt(s.nextLine());
                        num_carta--;
                        r.jugarCarta(j1.jugarCarta(num_carta));
                        System.out.println(r);

                    }
                    else
                    {
                        System.out.println("TURNO "+j2.getNombre());
                        int index = 0;
                        for (Carta c: j2.getCartas()) {
                            index++;
                            System.out.println("["+ index + "]" + c);
                        }
                        r.jugarCarta(j2.JuegoAutomatico(r));
//                        r.jugarCarta(j2.jugarCarta(0));
                        System.out.println(r);
                        //Thread.sleep(2000);
                    }
                }
                r.avanzarMano();

            }
            switch (r.getResultado()){
                case Ronda.GANA_JUGADOR1:
                    System.out.println("GANADOR " + j1.getNombre());
                    break;
                case Ronda.GANA_JUGADOR2:
                    System.out.println("GANADOR " + j2.getNombre());
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
            j1.soltarCartas();
            j2.soltarCartas();
            System.out.println(r.printResultados());
            System.out.println("Desea Jugar de nuevo? (Ingrese S o s para jugar de nuevo, cualquier otra cosa para finalizar");
            opcion = s.nextLine();

        }
        while(opcion != "S" && opcion != "s");
    }
}
