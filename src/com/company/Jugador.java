package com.company;

import sun.reflect.generics.tree.Tree;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Representa un Jugador para el juego del 7 y medio.
 * 
 * @author Ing. Valerio Frittelli. 
 * @version Mayo de 2011.
 */
public class Jugador
{
    /**
     * La cantidad que dispone para apostar el jugador.
     */
    private LinkedList<Carta> cartas;

    /**
     * El puntaje que posee el jugador a medida que recibe las cartas.
     */
    private float puntaje;
    
    /**
     * El nombre del jugador.
     */
    private String nombre;
    
    /**
     * La situacion del jugador. Cada jugador puede plantarse, continuar o pasarse seg�n el momento del juego.
     */
    public LinkedList<Carta> getCartas(){
        return cartas;
    }
    
    
    /**
     * Constructor del jugador que no recibe par�metros.
     */
    public Jugador()
    {
        cartas = new LinkedList<Carta>();
        nombre = "No disponible";
    }
    
    /**
     * Constructor del jugador est�ndar.
     */
    public Jugador( String nom )
    {
        cartas = new LinkedList<Carta>();
        if( nom == null ) nom = "No disponible";
        nombre = nom;
    }

    /**
     * M�todo accesor. Retorna el nombre del jugador.
     * @return el nombre del jugador.
     */
    public String getNombre()
    {
        return nombre;
    }
    
    /**
     * M�todo accesor. Define el nombre del jugador.
     * @param n el nombre del jugador.
     */
    public void setNombre(String n)
    {
        nombre = n;
    }
    /**
     * M�todo accesor. Retorna el puntaje del jugador.
     * @return el puntaje del jugador.
     */
    public float getPuntaje()
    {
        return puntaje;
    }
    
    /**
     * M�todo accesor. Define el puntaje del jugador.
     * @param punt  el puntaje del jugador.
     */
    public void setPuntaje(int punt)
    {
        puntaje = punt;
    }
    
    /**
     * Permite a los jugadores recibir una carta.
     * @param c la carta que reciben.
     */
    public void recibir( Carta c )
    {
       cartas.addLast(c);
    }

    public Carta jugarCarta(int num){
        Carta c = null;
        if (num < cartas.size()){
            c = cartas.remove(num);
        }
        else{
            if(!cartas.isEmpty()){
                c = cartas.removeFirst();
            }
        }
        return c;
    }

    public void soltarCartas(){
        cartas = new LinkedList<Carta>();
    }
    
    /**
     * M�todo que se utiliza para que otros jugadores definidos por el programa jueguen autom�ticamente.
     * @return la situacion del jugador.
     */

    private int getIndiceEnBarajaOrdenada(Carta c){
        int valor = c.getValor();
        if (valor > 7){
            valor = valor-3;
        }
        int indice = valor + valor * c.getPalo();
        return indice;
    }

    public Carta JuegoAutomatico(Ronda ronda, Arbol arbol, boolean soyPrimero){
        Carta cartaAJugar = null;
        double prob = 0;
        if(soyPrimero){

           for(Nodo hijo : arbol.getRaiz().getHijos().values()){
               if(hijo.getProbabilidad() > prob){
                   prob = hijo.getProbabilidad();
                   cartaAJugar = hijo.getMesa().getUltimaCartaJugadaMaquina();
               }
               System.out.println(hijo.getMesa().generarCodigoMesa() + ":" + hijo.getProbabilidad());
           }
        }else {
            Mesa mesa = arbol.getRaiz().getMesa();
            Carta cartaJugadaHumano = ronda.getUltimaCartaHumano();
            for(Carta carta : cartas){
                Nodo hijo = arbol.getRaiz().getHijos().get(String.format("%s%s", mesa.generarCodigoMesa(), carta.generarCodigo()));
                Nodo nieto = hijo.getHijos().get(String.format("%s%s%s", mesa.generarCodigoMesa(), carta.generarCodigo(), cartaJugadaHumano.generarCodigo());

                if(nieto.getProbabilidad() > prob){
                    prob = nieto.getProbabilidad();
                    cartaAJugar = carta;
                }
                System.out.println(nieto.getMesa().generarCodigoMesa() + ":" + nieto.getProbabilidad());

            }
        }
        return cartaAJugar;
    }


}
