package com.company;


import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Nodo {

    Map<String, Nodo> hijos;
    private List<Carta> cartasEnMano;
    private Mesa mesa;
    private List<Carta> cartasEnBaraja;
    private Integer jugados;
    private Integer ganados;
    private boolean generadoConMano;
    private Integer cantHijos = 0;
    private Integer cantHojas = 0;



    public Nodo(List<Carta> cartasEnMano, Mesa mesa, List<Carta> cartasEnBaraja, boolean generadoConMano) {
        this.cartasEnMano = cartasEnMano;
        this.mesa = mesa;
        this.cartasEnBaraja = cartasEnBaraja;
        this.generadoConMano = generadoConMano;
        this.hijos = new HashMap<>();
    }

    public Integer getGanados() {
        return ganados;
    }

    public Map<String, Nodo> getHijos() {
        return hijos;
    }

    public void setHijos(Map<String, Nodo> hijos) {
        this.hijos = hijos;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public List<Carta> getCartasEnBaraja() {
        return cartasEnBaraja;
    }

    public void setCartasEnBaraja(List<Carta> cartasEnBaraja) {
        this.cartasEnBaraja = cartasEnBaraja;
    }

    public Integer getJugados() {
        return jugados;
    }

    public void setJugados(Integer jugados) {
        this.jugados = jugados;
    }

    public void setGanados(Integer ganados) {
        this.ganados = ganados;
    }

    public boolean isHoja(){
        return hijos.isEmpty();
    }

    public List<Carta> getCartasEnMano() {
        return cartasEnMano;
    }

    public void setCartasEnMano(List<Carta> cartasEnMano) {
        this.cartasEnMano = cartasEnMano;
    }

    public boolean isGeneradoConMano() {
        return generadoConMano;
    }

    public void setGeneradoConMano(boolean generadoConMano) {
        this.generadoConMano = generadoConMano;
    }

    public Integer getCantHijos() {
        return cantHijos;
    }

    public void setCantHijos(Integer cantHijos) {
        this.cantHijos = cantHijos;
    }

    public Integer getCantHojas() {
        return cantHojas;
    }

    public void setCantHojas(Integer cantHojas) {
        this.cantHojas = cantHojas;
    }

    public void evaluarResultado(){
        this.jugados = 1;
        this.ganados = mesa.ganoMaquina();
    }

    public double getProbabilidad(){
        return ganados / jugados;
    }

    public void generarHijosConMano(){
        for(Carta carta : cartasEnMano) {

            List<Carta> cartasRestantesEnMano = new ArrayList<>();
            cartasRestantesEnMano.addAll(cartasEnMano);
            cartasRestantesEnMano.remove(carta);

            Nodo hijo = new Nodo(cartasRestantesEnMano, mesa.clonarMesa(carta, false), cartasEnBaraja, true);
            hijos.put(hijo.getMesa().generarCodigoMesa(), hijo);

            cantHijos++;

            if(!hijo.getMesa().isFull()){
                hijo.generarHijosConBaraja();
                cantHijos+= hijo.getCantHijos();
                cantHojas+=hijo.getCantHojas();
                this.jugados+=hijo.getJugados();
                this.ganados+=hijo.getGanados();
            }else {
                cantHojas++;
                hijo.evaluarResultado();
                //aca va otra cosa
            }
        }
    }

    public void generarHijosConBaraja(){
        for(Carta carta : cartasEnBaraja) {

            List<Carta> barajaRestante = new ArrayList<>();
            barajaRestante.addAll(cartasEnBaraja);
            barajaRestante.remove(carta);

            Nodo hijo = new Nodo(cartasEnMano, mesa.clonarMesa(carta, true), barajaRestante, false);
            hijos.put(hijo.getMesa().generarCodigoMesa(), hijo);
            cantHijos++;

            if(!hijo.getMesa().isFull()){
                hijo.generarHijosConMano();
                cantHijos+= hijo.getCantHijos();
                cantHojas+=hijo.getCantHojas();
                this.jugados+=hijo.getJugados();
                this.ganados+=hijo.getGanados();
            }else {
                cantHojas++;
                hijo.evaluarResultado();
                //aca va otra cosa
            }
        }
    }

}
