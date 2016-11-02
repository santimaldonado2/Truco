package com.company.ui;

import com.company.Carta;
import com.company.Jugador;

import java.awt.*;
import java.util.LinkedList;

public class Graficador {

    private GraphicsConsole v = GraphicsConsole.getInstance();
    private GestorGraficos gestorGraficos = new GestorGraficos();

    private Color mantel = new Color(13, 143, 15);
    private Color marron = new Color(54, 36, 1);

    private Font titulo = new Font("Arial", Font.BOLD, 30);

    private Font subtitulo = new Font("TimesRoman", Font.BOLD, 20);

    private Font dato = new Font("Arial", Font.BOLD, 25);

    public Graficador() {
    }

    public void dibujarVentana() {
        v.setVisible(true);

        int anchoTotal = v.getWidth();
        int altoTotal = v.getHeight();
        System.out.println("ancho: " + anchoTotal + " - alto: " + altoTotal);

        //armado general
        v.setColor(mantel);
        v.fillRect(0, 0, anchoTotal, altoTotal);
        v.setColor(marron);
        v.fillRect((anchoTotal / 5) * 4, 0, 5, altoTotal);


        v.setFont(titulo);
        v.setColor(Color.black);
        v.drawString("Maquina", anchoTotal / 10, altoTotal / 20 * 3);
        v.drawString("Humano", anchoTotal / 10, altoTotal / 20 * 16);

    }

    public GraphicsConsole getV() {
        return v;
    }

    private void dibujarCarta(Carta carta, boolean humano, boolean oculta, int posicion) {
        int x = v.getWidth() / 5 + (posicion * 150);
        int y;
        if (!humano) {
            y = v.getHeight() / 20;
        } else {
            y = v.getHeight() / 20 * 14;
        }
        y += 10;
        x += 10;
        if (oculta) {
            gestorGraficos.dibujarOculta(carta, x, y);
        } else {
            gestorGraficos.dibujar(carta, x, y);
        }
    }

    public void dibujarCartasJugador(LinkedList<Carta> cartas, boolean humano) {
        int anchoTotal = v.getWidth();
        int altoTotal = v.getHeight();
        v.setColor(mantel);
        if (humano) {
            v.fillRoundRect(anchoTotal / 5, altoTotal / 20 * 14, anchoTotal / 10 * 3, altoTotal / 6, 10, 10);
            v.setColor(marron);
            v.drawRoundRect(anchoTotal / 5, altoTotal / 20 * 14, anchoTotal / 10 * 3, altoTotal / 6, 10, 10);
        } else {
            v.fillRoundRect(anchoTotal / 5, altoTotal / 20, anchoTotal / 10 * 3, altoTotal / 6, 10, 10);
            v.setColor(marron);
            v.drawRoundRect(anchoTotal / 5, altoTotal / 20, anchoTotal / 10 * 3, altoTotal / 6, 10, 10);
        }

        int i = 0;
        for (Carta carta : cartas) {
            dibujarCarta(carta, humano, !humano, i);
            i++;
        }
    }

    public void dibujarMesa(Carta[][] mesa) {
        int i = 0;
        boolean humano;
        for (Carta[] ronda : mesa) {
            humano = true;
            for (Carta carta : ronda) {
                if (carta != null) {
                    dibujarCartaEnMesa(carta, humano, i);
                }
                humano = false;
            }
            i++;
        }
    }

    private void dibujarCartaEnMesa(Carta carta, boolean humano, int posicion) {
        int x = v.getWidth() / 20 * 5 + (posicion * 150);
        int y;
        if (!humano) {
            y = v.getHeight() / 20 * 5;
        } else {
            y = v.getHeight() / 20 * 10;
        }
        y += 10;
        x += 10;
        gestorGraficos.dibujar(carta, x, y);
    }

    public void dibujarPuntajes(int puntosHumano, int puntosMaquina) {
        int anchoTotal = v.getWidth();
        int altoTotal = v.getHeight();

        v.setColor(mantel);
        v.fillRect(anchoTotal / 10 * 6, 0, anchoTotal / 10, altoTotal);

        v.setFont(dato);
        v.setColor(Color.black);
        v.drawString("Puntos: " + puntosMaquina, anchoTotal / 10 * 6, altoTotal / 20 * 3);
        v.drawString("Puntos: " + puntosHumano, anchoTotal / 10 * 6, altoTotal / 20 * 16);
    }
}

