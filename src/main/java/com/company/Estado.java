package com.company;

import java.util.Enumeration;
import java.util.LinkedList;

import javax.swing.tree.TreeNode;

/**
 * Created by Maldo on 23/10/2016.
 */
public class Estado implements TreeNode {

    Ronda ronda;
    int[] baraja;
    int suma;
    LinkedList<Estado> children;
    LinkedList<Carta> cartasMano;

    public Estado(Ronda ronda, LinkedList<Carta> cartasMano) {
        this.ronda = ronda.clone();
        this.children = new LinkedList<Estado>();
        this.baraja = new int[] { 4, 4, 4, 2, 4, 4, 2, 4, 4, 1, 1, 1, 1 };
        this.cartasMano = cartasMano;
        for (Carta c : cartasMano) {
            this.baraja[c.getPuntaje()]--;
        }
        Carta cartaAux;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                cartaAux = this.ronda.getMesa()[i][j];
                if (cartaAux != null) {
                    this.baraja[cartaAux.getPuntaje()]--;
                }
            }
        }
    }

    public Estado(Ronda ronda, int[] baraja, LinkedList<Estado> children, LinkedList<Carta> cartasMano) {
        this.ronda = ronda;
        this.baraja = baraja;
        this.children = children;
        this.cartasMano = cartasMano;
    }

    @Override
    public Estado clone() {
        @SuppressWarnings("unchecked")
        Estado e = new Estado(this.ronda.clone(), this.baraja.clone(), (LinkedList<Estado>) this.children.clone(),
                (LinkedList<Carta>) this.cartasMano.clone());
        return e;
    }

    public int generarHijosSegundoTurno() {
        int suma = 0;
        for (Carta c : cartasMano) {
            Estado e = this.clone();
            e.ronda.jugarCarta(c);
            e.cartasMano.remove(c);
            e.ronda.evaluarMano(e.ronda.getMano());
            e.ronda.evaluarRonda();
            this.addChildren(e);
            e.ronda.avanzarMano();
            if (e.ronda.getEstado() != Ronda.FINALIZADA) {
                this.suma += e.generarHijosPrimerTurno();
            } else {
                if (e.ronda.getResultado() == Ronda.GANA_JUGADOR1) {
                    this.suma--;
                } else {
                    this.suma++;
                }
            }
        }
        return suma;
    }

    public int generarHijosPrimerTurno() {
        Estado estadoAux;
        Estado estadoHijo;
        Carta cartaAux;
        int suma = 0;
        for (int i = 0; i < this.baraja.length; i++) {
            if (this.baraja[i] > 0) {
                estadoAux = this.clone();
                cartaAux = new Carta(i);
                estadoAux.ronda.jugarCarta(cartaAux);
                estadoAux.baraja[i]--;
                for (Carta c : cartasMano) {
                    estadoHijo = estadoAux.clone();
                    estadoHijo.ronda.jugarCarta(c);
                    estadoHijo.cartasMano.remove(c);
                    estadoHijo.ronda.evaluarMano(estadoHijo.ronda.getMano());
                    estadoHijo.ronda.evaluarRonda();
                    this.addChildren(estadoHijo);
                    estadoHijo.ronda.avanzarMano();
                    if (estadoHijo.ronda.getEstado() != Ronda.FINALIZADA) {
                        this.suma += estadoHijo.generarHijosPrimerTurno();
                    } else {
                        if (estadoHijo.ronda.getResultado() == Ronda.GANA_JUGADOR1) {
                            this.suma++;
                        } else {
                            this.suma++;
                        }
                    }
                }
            }
        }
        return suma;
    }

    public int generarEspacioEstados() {
        int mano = this.ronda.getMano() - 1;
        int suma = 0;
        for (int i = this.ronda.getMano() - 1; i < 3; i++) {
            if (this.ronda.getMesa()[mano][0] == null) {
                suma += this.generarHijosPrimerTurno();
            } else {
                suma += this.generarHijosSegundoTurno();
            }

        }
        return suma;
    }
    // public void generarHijosConMano(){
    // Ronda rondaAux;
    // LinkedList<Carta> cartasNoJugadas;
    // for (Carta c: cartasMano) {
    // rondaAux = this.value.clone();
    // cartasNoJugadas = (LinkedList<Carta>) cartasMano.clone();
    // cartasNoJugadas.remove(c);
    // rondaAux.jugarCarta(c);
    // this.addChildren(new
    // Estado(rondaAux,this.baraja.clone(),cartasNoJugadas));
    // }
    // }
    // public void generarHijosConBaraja(){
    // Ronda rondaAux;
    // Carta[] barajaRestante;
    // for (int i = 0; i < this.baraja.length ; i++) {
    // if (this.baraja[i] != null){
    // rondaAux = this.value.clone();
    // barajaRestante = this.baraja.clone();
    // barajaRestante[i] = null;
    // rondaAux.evaluarMano();
    // this.addChildren(new Estado(rondaAux,barajaRestante,(LinkedList<Carta>)
    // this.cartasMano.clone()));
    // }
    // }
    // }
    //
    //
    //
    // public Ronda getValue() {
    // return value;
    // }
    //
    // public void setValue(Ronda value) {
    // this.value = value;
    // }

    public LinkedList<Estado> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<Estado> children) {
        this.children = children;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return 0;
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return children.size() == 0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Enumeration children() {
        return null;
    }

    public void addChildren(Estado e) {
        children.addLast(e);
    }
}
