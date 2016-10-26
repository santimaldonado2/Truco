package com.company;

import sun.reflect.generics.tree.Tree;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.LinkedList;

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
    public Carta JuegoAutomatico(Ronda r){
        //Estado.suma = 0;
        Estado estadoActual = new Estado(r,this.cartas);
        System.out.println("CANTIDAD DE ESTADOSSSSSSSan");
        System.out.println(estadoActual.generarEspacioEstados());
        int index = 0;
        for (Estado e: estadoActual.getChildren()) {
            index++;
            System.out.println("Chances estado "+ index);
            System.out.println(e.suma);
        }
        //System.out.println(Estado.suma);
        return this.jugarCarta(0);
    }
    
    
}
