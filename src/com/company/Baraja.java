package com.company;

import java.util.Random;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Clase que representa la Baraja Espa�ola para la implementaci�n de 
 * un juego de cartas. En esa baraja, los palos son espada, basto, oro
 * y copa. La baraja completa tiene 12 cartas de cada palo. El 1 de cada 
 * palo se conoce como "as". Las tres �ltimas cartas de cada palo en la
 * baraja completa, se conocen como "figuras" o "cartas negras", y 
 * son respectivamente el 10 ("la sota"), el 11 ("el caballo") y el 12
 * ("el rey").
 * 
 * La clase provee constantes p�blicas de tipo num�rico (int) para 
 * representar en forma simple a los cuatro palos. Esas constantes p�blicas 
 * son:
 *     public static final int ESPADA = 0;
 *     public static final int BASTO  = 1;
 *     public static final int ORO    = 2;
 *     public static final int COPA   = 3;
 * 
 * En algunos juegos de cartas, se usa toda la baraja completa, y en otros 
 * se usa la baraja reducida, en la cual se eliminan previamente los ochos 
 * y los nueves. La clase cuenta con dos constantes p�blicas que representan
 * a la baraja completa y a la reducida. Uno de los constructores de la clase
 * puede tomar un par�metro para configurar la baraja como completa o reducida.
 * 
 * @author Ing. Valerio Frittelli. 
 * @version Mayo de 2011.
 */

public class Baraja
{
    // Constantes p�blicas para representar los palos 
    /**
     * Constante para representar el palo de Espada.
     */
    public static final int ESPADA = 0;
    
    /**
     * Constante para representar el palo de Basto.
     */
    public static final int BASTO  = 1;
    
    /**
     * Constante para representar el palo de Oro.
     */
    public static final int ORO    = 2;
    
    /**
     * Constante para representar el palo de Copa.
     */
    public static final int COPA   = 3;
    
    // Constantes p�blicas para indicar si se usan todas las cartas
    /**
     * Constante para representar indicar baraja completa.
     */
    public static final int COMPLETA = 0;  // juegan todas
    
    /**
     * Constante para indicar baraja reducida.
     */
    public static final int REDUCIDA = 1;  // fuera los ochos y los nueves

    public LinkedList<Carta> getMazo() {
        return mazo;
    }

    // el mazo de cartas...
    private LinkedList < Carta > mazo;
    
    // completa, reducida...
    private int tipo;
    
    /**
     * Crea una baraja, con un mazo inicial completo y ordenado:
     * primero todas las espadas del 1 al 12, luego los bastos, 
     * los oros, y finalmente las copas.
     */
    public Baraja()
    {
        this( COMPLETA, false );
    }
    
    /**
     * Crea una baraja completa. La misma quedar� ordenada a menos 
     * que el par�metro "mezcle" sea true, en cuyo caso el mazo ser� 
     * mezclado en forma aleatoria.
     * @param mezcle true: el mazo se crea y se mezcla - false: el mazo se crea y queda ordenado.
     */
    public Baraja( boolean mezcle )
    {
        this( COMPLETA, mezcle );
    }
    
    /**
     * Crea una baraja completa. La misma quedar� ordenada a menos 
     * que el par�metro "mezcle" sea true, en cuyo caso el mazo ser� 
     * mezclado en forma aleatoria. El primer par�metro permite seleccionar
     * entre crear una baraja completa (t = Baraja.COMPLETA) o reducida
     * (t = Baraja.REDUCIDA).
     * @param t si vale 0, la baraja se crea completa. Si vale 1, se crea reducida.
     * @param mezcle true: el mazo se crea y se mezcla - false: el mazo se crea y queda ordenado.
     */
    public Baraja( int t, boolean mezcle )
    {
        if( t != COMPLETA && t != REDUCIDA ) t = COMPLETA;
        tipo = t;

        init();
        if( mezcle ) mezclar();
    }

    
    /**
     * Junta nuevamente todas las cartas en el mazo, dej�ndolo desordenado.
     */
    public void juntar()
    {
        init();
        mezclar();
    }
    
    /**
     * Junta nuevamente todas las cartas en el mazo. El mazo quedar� ordenado
     * si el par�metro "ordene" es true. De lo contrario, quedar� desordenado.
     * @param ordene true: el mazo se junta y se ordena - false: se junta y queda desordenado.
     */
    public void juntar( boolean ordene )
    {
        init();
        if( ! ordene ) mezclar();
    }
    
    /**
     * Mezcla el mazo. La mezcla se hace con las cartas que en ese
     * momento queden en el mazo, sin garant�a que el mazo est�
     * completo (por haber usado ya algunas cartas).
     */
    public void mezclar()
    {
        Collections.shuffle( mazo, new Random( System.currentTimeMillis() ) );
    }
    
    /**
     * Retira una carta del mazo, y la retorna (a menos que el mazo est� vac�o, 
     * en cuyo caso retornar� null). La carta retirada es la que en ese momento 
     * est� "encima" del mazo (o sea, "la de arriba"...) La carta es retirada del 
     * mazo: esto quiere decir que una nueva invocaci�n al m�todo pedir(), no 
     * podr� retornar nunca una carta que ya haya sido devuelta (a menos que el 
     * objeto se vuelva a crear, o se invoque al m�todo juntar()).
     * @return la primera carta de arriba del mazo.
     */
    public Carta pedir()
    {
        return ( mazo.isEmpty() )? null : mazo.removeFirst(); 
    }
    
    /**
     * Retira una carta del mazo, y la retorna (a menos que el mazo est� vac�o, 
     * en cuyo caso retornar� null). La carta retirada depende del valor del 
     * par�metro "arriba": si este es true, la carta retornada es la que en ese 
     * momento est� "encima" del mazo (o sea, "la de arriba"...) Si el par�metro
     * es false, la carta retornada ser� la de "abajo" del mazo. La carta es 
     * retirada del mazo: esto quiere decir que una nueva invocaci�n al m�todo 
     * pedir(), no podr� retornar nunca una carta que ya haya sido devuelta (a 
     * menos que el objeto baraja se vuelva a crear, o se invoque al m�todo juntar()).
     * @return la primera (o la �ltima) carta de del mazo.
     * @param arriba true: retorna la carta de arriba - false: retorna la de abajo.
     */
    public Carta pedir( boolean arriba )
    {
        if( mazo.isEmpty() ) return null;
        
        if( ! arriba ) return mazo.removeLast();
        return mazo.removeFirst(); 
    }
    
    @Override
    public String toString()
    {
        return mazo.toString();   
    }
    
    private void init()
    {
        mazo = new LinkedList < Carta > ();
                
        for( int palo = 0; palo <= 3; palo++ )
        {
            for( int valor = 1; valor <= 12; valor++ ) 
            {
                if( tipo == REDUCIDA && ( valor == 8 || valor == 9 || valor ==10) ) continue;
                Carta c = new Carta( valor, palo );
                mazo.addLast( c );
            }
        }
    }
}
