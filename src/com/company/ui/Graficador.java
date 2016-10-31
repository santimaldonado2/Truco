package com.company.ui;

import com.company.Carta;
import com.company.Jugador;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by aherrera on 30/10/2016.
 */
public class Graficador {

    /**
     * Permite utilizar la consola gr�fica en la clase.
     */
    GraphicsConsole v = GraphicsConsole.getInstance();
    GestorGraficos gestorGraficos = new GestorGraficos();

    /**
     * Define el color mantel.
     */
    Color mantel = new Color (13, 143, 15);
    Color marron = new Color (54, 36, 1);

    /**
     * La fuente utilizada en los t�tulos. As� como el nombre de los jugadores y la banca.
     */
    Font titulo = new Font ("Arial", Font.BOLD, 30);

    /**
     * La fuente utilizada para los subtitulos. As� como la palabra pozo y puntaje.
     */
    Font subtitulo = new Font ("TimesRoman", Font.BOLD, 20);

    /**
     * La fuente utilizada para los datos. As� como el puntaje y el pozo.
     */
    Font dato = new Font ("Arial", Font.BOLD, 25);

    /**
     * Constructor nulo.
     */
    public Graficador()
    {}

    /**
     * Define el fondo general del juego.
     */
    public void dibujarVentana()
    {
        v.setVisible( true );

        int anchoTotal = v.getWidth();
        int altoTotal = v.getHeight();
        System.out.println("ancho: " + anchoTotal + " - alto: " + altoTotal);

        //armado general
        v.setColor(mantel);
        v.fillRect(0, 0, anchoTotal, altoTotal);
        v.setColor(marron);
        v.fillRect((anchoTotal/5)*4, 0, 5, altoTotal);

        //cuadrados particulares
        v.drawRoundRect(anchoTotal/5, altoTotal/20, anchoTotal/5*2, altoTotal/6, 10, 10);
        v.drawRoundRect(anchoTotal/5, altoTotal/20*14, anchoTotal/5*2, altoTotal/6, 10, 10);

        //atributos de los jugadores
//        v.setFont(subtitulo);
//        v.setColor(Color.black);
//        v.drawString("Puntaje", 422, 300);
//        v.drawString("Puntaje", 930, 300);
//        v.drawString("Pozo", 430, 400);
//        v.drawString("Pozo", 938, 400);
//
//        //titulos de los jugadores
//
        v.setFont(titulo);
        v.setColor(Color.black);
        v.drawString("Maquina", anchoTotal/10, altoTotal/20*3);
        v.drawString("Humano", anchoTotal/10, altoTotal/20*16);
//
//        //la banca...
//        v.setFont(titulo);
//        v.setColor(Color.black);
//        v.drawString("BANCA", 450, 30);
//        v.drawRect(300, 50, 400, 160);
//        v.setFont(subtitulo);
//        v.setColor(Color.black);
//        v.drawString("Puntaje", 722, 70);
//
//        //dibujar el mazo
//        v.setColor(Color.orange);
//        v.fillRoundRect (170, 25, 100, 150, 21, 9);
//        v.setColor(Color.black);
//        v.drawRoundRect (170, 25, 100, 150, 21, 9);
//        Color celeste = new Color (15, 227, 222);
//        v.setColor(celeste);
//        v.fillRect (175, 30, 90, 50);
//        v.fillRect (175, 120, 90, 50);
//        v.setColor(Color.white);
//        v.fillRect (175, 75, 90, 50);
//        v.setColor(Color.black);
//        v.drawRect (175, 30, 90, 140);

    }

    public GraphicsConsole getV() {
        return v;
    }

    public void dibujarCarta(Carta carta, boolean humano, int posicion){
        int x = v.getWidth()/5+(posicion * 150);
        int y;
        if(!humano){
            y = v.getHeight()/20;
        }else{
            y = v.getHeight()/20*14;
        }
        y += 10;
        x += 10;
        gestorGraficos.dibujar(carta, x, y);
    }

    public void dibujarCartasJugador(LinkedList<Carta> cartas, boolean humano){
        int anchoTotal = v.getWidth();
        int altoTotal = v.getHeight();
        v.setColor(mantel);
        if(humano){
            v.fillRoundRect(anchoTotal/5, altoTotal/20*14, anchoTotal/5*2, altoTotal/6, 10, 10);
            v.setColor(marron);
            v.drawRoundRect(anchoTotal/5, altoTotal/20*14, anchoTotal/5*2, altoTotal/6, 10, 10);
        }else{
            v.fillRoundRect(anchoTotal/5, altoTotal/20, anchoTotal/5*2, altoTotal/6, 10, 10);
            v.setColor(marron);
            v.drawRoundRect(anchoTotal/5, altoTotal/20, anchoTotal/5*2, altoTotal/6, 10, 10);
        }

        int i = 0;
        for (Carta carta: cartas) {
            dibujarCarta(carta, humano, i);
            i++;
        }
    }

    public void dibujarMesa(Carta[][] mesa) {
        int i = 0;
        boolean humano;
        for (Carta[] ronda: mesa) {
            humano = true;
            for (Carta carta: ronda) {
                if(carta != null){
                    dibujarCartaEnMesa(carta, humano, i);
                }
                humano = false;
            }
            i++;
        }
    }

    private void dibujarCartaEnMesa(Carta carta, boolean humano, int posicion){
        int x = v.getWidth()/10*3+(posicion * 150);
        int y;
        if(!humano){
            y = v.getHeight()/20*5;
        }else{
            y = v.getHeight()/20*10;
        }
        y += 10;
        x += 10;
        gestorGraficos.dibujar(carta, x, y);
    }


    /**
     * Dibuja los puntajes de los jugadores a medida que reciben las cartas.
     * @param ja el jugador humano.
     * @param jb el jugador manejado por la computadora.
     */
//    public void puntajes(Jugador ja, Jugador jb)
//    {
//
//        v.setVisible( true );
//
//        //borra el puntaje anterior
//        v.setColor(mantel);
//        v.fillRect(435, 330, 40, 20);
//        v.fillRect(937, 330, 40, 20);
//
//        //define el puntaje y el pozo de cada jugador
//        v.setFont(dato);
//        v.setColor(Color.black);
//        v.drawString(ja.getPuntaje()+"", 435, 350);
//        v.drawString(jb.getPuntaje()+"", 937, 350);
//        v.drawString("XXX", 938, 450);
//
//    }

    /**
     * Escribe el nombre del jugador humano.
     * @param ja el jugador humano.
     */
//    public void escribirnombre(Jugador ja)
//    {
//        v.setVisible(true);
//
//        v.setFont(titulo);
//        v.setColor(Color.black);
//        v.drawString(ja.getNombre(), 80, 290);
//    }

    /**
     * Dibuja el puntaje de la banca a medida que recibe las cartas.
     * @param b el jugador banca.
     */
//    public void puntajebanca(float b)
//    {
//        v.setVisible(true);
//
//        v.setColor(mantel);
//        v.fillRect(735, 100, 40, 20);
//        v.setFont(dato);
//        v.setColor(Color.black);
//        v.drawString(b+"", 735, 120);
//    }

    /**
     * Dibuja el pozo del jugador humano.
     * @param ja el jugador humano.
     */
//    public void dibujarpozo(Jugador ja)
//    {
//        v.setColor(mantel);
//        v.fillRect(433, 430, 60, 20);
//
//        v.setFont(dato);
//        v.setColor(Color.black);
//        v.drawString(ja.getPozo()+"", 433, 450);
//
//    }
}

