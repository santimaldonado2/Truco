package com.company;
import javax.swing.*;
/**
 * @author Franco Ariel Salonia.
 * @version junio de 2011.
 */
public class Inicio
{
    /**
     * Le da la bienvenida al usuario y le brinda la posibilidad de elegir si quiere consultar las reglas del juego o si quiere
     * comenzar autom�ticamente con la partida.
     */
    public void Presentacion()
    {
        int a = JOptionPane.showConfirmDialog(null, "�Bienvenido al juego del 7 y medio!\n�El juego m�s criollo de la Rep�blica Argentina!\n�Desea revisar las instrucciones del juego antes de comenzar?", "�Bienvenido!", JOptionPane.YES_NO_OPTION);
        if (a == JOptionPane.YES_OPTION) instrucciones();
        else
        {
            JOptionPane.showMessageDialog(null, "�Qu� disfrute del juego!", "�Bienvenido!", JOptionPane.PLAIN_MESSAGE);
    }
    }
    
    /**
     * Le informa al jugador humano cu�les son las reglas del juego.
     */
    public void instrucciones()
    {
       JOptionPane.showMessageDialog (null, "El objetivo principal del juego es acumular 7 puntos y medio\nrecibiendo cartas y tratando de no pasarse de los mismos.", "Instrucciones del juego", JOptionPane.INFORMATION_MESSAGE);
       JOptionPane.showMessageDialog (null, "Cada jugador recibe una carta. Si la misma es menor o igual\na 7, las mismas suman la cantidad de puntos deacuerdo al n�mero.\nSi superan, suman medio punto. (Tener en cuenta la ausencia de 8 y 9).", "Instrucciones del juego", JOptionPane.INFORMATION_MESSAGE);
       JOptionPane.showMessageDialog (null, "El jugador puede tomar la decisi�n de plantarse cuando lo desee.\nEl mismo ganar� si SUPERA los puntos que\nacumul� la banca en su juego.", "Instrucciones del juego", JOptionPane.INFORMATION_MESSAGE);
       JOptionPane.showMessageDialog (null, "Tener en cuenta que cada jugador juega �NICAMENTE contra la\nbanca. Los jugadores no compiten entre ellos.", "Instrucciones del juego", JOptionPane.INFORMATION_MESSAGE);
       JOptionPane.showMessageDialog (null, "�Mucha suerte y qu� te diviertas!", "Instrucciones del juego", JOptionPane.INFORMATION_MESSAGE);
    }
}