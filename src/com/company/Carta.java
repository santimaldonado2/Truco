package com.company;
/**
 * Una clase para representar una carta de la baraja espa�ola, que 
 * pueda ser usada para implementar el juego del 7 y medio (u otro 
 * que sirva a esta baraja). El palo de cada carta se representa con
 * un valor num�rico entre 0 y 3 mediante constantes declaradas en la
 * clase Baraja. Esas constantes p�blicas son:
 *     public static final int ESPADA = 0;
 *     public static final int BASTO  = 1;
 *     public static final int ORO    = 2;
 *     public static final int COPA   = 3;
 * 
 * @author Ing. Valerio Frittelli.
 * @version Mayo de 2011. 
 */
public class Carta implements Comparable
{
    // el n�mero que corresponde a la carta
    private int valor;
    
    // el palo que corresponde a la carta (constantes clase Baraja...)
    private int palo;
    
    /**
     * Inicializa una carta con valor = 1 (el as...) y palo = ESPADA (...de espadas)
     */
    public Carta()
    {
        valor = 1;
        palo = Baraja.ESPADA;
    }
    
    /**
     * Inicializa una carta con valores tomados como par�metro. Si el par�metro v es incorrecto,
     * entonces el valor de la carta quedar� en 1 (el as...) Y si el par�metro p es incorrecto, 
     * entonces el palo quedar� valiendo ESPADA (...de espada).
     * @param v el valor a asignar para la carta creada.
     * @param p el palo a asignar para la carta creada.
     */
    public Carta( int v, int p )
    {
        if( v < 1 || v > 12 ) v = 1;
        valor = v;
        
        if( p != Baraja.ESPADA && p != Baraja.BASTO && p != Baraja.ORO && p != Baraja.COPA ) p = Baraja.ESPADA;
        palo = p;
        
        //GestorGraficos.dibujar(c, 120, 300);
        
    }
    
    /**
     * Retorna el valor de la carta.
     * @return el valor de la carta.
     */
    public int getValor()
    {
        return valor;
    }
    
    /**
     * Retorna el palo de la carta (que corresponde a una de las constantes de palo declaradas
     * la clase Baraja).
     * @return el palo de la carta.
     */
    public int getPalo()
    {
        return palo;
    }
    
    /**
     * Cambia el valor de la carta. Si nuevo valor es incorrecto, se dejar� sin cambios el anterior. 
     * @param v el nuevo valor de la carta.
     */
    public void setValor( int v )
    {
        if( v < 1 || v > 12 ) return;
        valor = v;
    }
    
    /**
     * Cambia el palo de la carta. Si nuevo palo es incorrecto, se dejar� sin cambios el anterior. 
     * @param p el nuevo palo de la carta.
     */
    public void setPalo( int p )
    {
        if( p != Baraja.ESPADA && p != Baraja.BASTO && p != Baraja.ORO && p != Baraja.COPA ) return;
        palo = p;
    }

    public int getPuntaje()
    {
        int valor = this.getValor();
        int palo  = this.getPalo();
        int puntaje = -1;
        switch (valor){
            case 1: switch(palo){
                case Baraja.ESPADA: puntaje = 12;
                                    break;
                case Baraja.BASTO: puntaje = 11;
                    break;
                case Baraja.ORO: puntaje = 6;
                    break;
                case Baraja.COPA: puntaje = 6;
                    break;
            }
            break;
            case 2: puntaje = 7;
                    break;
            case 3: puntaje = 8;
                break;
            case 4: puntaje = 0;
                break;
            case 5: puntaje = 1;
                break;
            case 6: puntaje = 2;
                break;
            case 7: switch(palo){
                case Baraja.ESPADA: puntaje = 10;
                    break;
                case Baraja.BASTO: puntaje = 3;
                    break;
                case Baraja.ORO: puntaje = 9;
                    break;
                case Baraja.COPA: puntaje = 3;
                    break;
            }
            break;
            case 11: puntaje = 4;
                break;
            case 12: puntaje = 5;
                break;

        };
        return puntaje;
    }
    public Carta(int puntaje){
        switch (puntaje){
            case 0: valor =4;
                break;
            case 1: valor =5;
                break;
            case 2: valor =6;
                break;
            case 3: valor =7;
                palo = Baraja.BASTO;
                break;
            case 4: valor =11;
                break;
            case 5: valor =12;
                break;
            case 6: valor =1;
                palo = Baraja.COPA;
                break;
            case 7: valor =2;
                break;
            case 8: valor =3;
                break;
            case 9: valor =7;
                palo = Baraja.ORO;
                break;
            case 10: valor =7;
                palo = Baraja.ESPADA;
                break;
            case 11: valor =1;
                palo = Baraja.BASTO;
                break;
            case 12: valor =1;
                palo = Baraja.ESPADA;
                break;
        }
    }
    @Override
    public String toString()
    { 
        String p = "Espada";
        if( palo == Baraja.BASTO ) p = "Basto";
        if( palo == Baraja.ORO ) p = "Oro";
        if( palo == Baraja.COPA ) p = "Copa";
        
        return "{" + valor + " de " + p + "}";
    }

    @Override
    public int compareTo(Object o) {
        Carta c = (Carta) o;
        return this.getPuntaje() - c.getPuntaje();
    }
}
