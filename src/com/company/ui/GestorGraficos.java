package com.company.ui;

import com.company.Baraja;
import com.company.Carta;

import java.awt.*;

/**
 * Clase encargada de realizar los gr�ficos de las cartas en la consola 
 * de gr�ficos.
 * 
 * @author Ing. Valerio Frittelli continuado por Franco Ariel Salonia. 
 * @version Mayo de 2011.
 */
public class GestorGraficos
{
    
private GraphicsConsole v = GraphicsConsole.getInstance();
/**
     * dibuja una carta, comenzando en el punto de coordenadas (col, fil).
     * @param c la carta a dibujar.
     * @param col la coordenada de columna izquierda del rect�ngulo de la carta.
     * @param fil la coordenada de fila superior de la carta.
     */
    public void dibujar(Carta c , int col, int fil )
    {   
        if (c.getPalo()== Baraja.ESPADA) dibujarEspada(col, fil);
        if (c.getPalo()== Baraja.ORO)  dibujarOro (col, fil);
        if (c.getPalo()== Baraja.BASTO) dibujarBasto (col, fil);
        if (c.getPalo()== Baraja.COPA) dibujarCopa (col, fil);
        
       Font f = new Font( "TimesRoman", Font.BOLD, 20 );
       v.setFont( f );
       v.setColor( Color.black );
       
       if(c.getValor()>=10)
       {
       v.drawString( c.getValor()+"", col+6, fil+20 );
       v.drawString( c.getValor()+"", col+75, fil+144);
    }
       else
       {
       v.drawString( c.getValor()+"", col+6, fil+20 );
       v.drawString( c.getValor()+"",col+80, fil+144);
        }
 
    }
    
    /**
     * Dibuja una espada, comenzando en el punto de coordenadas (col, fil).
     * @param col la coordenada de columna izquierda del rect�ngulo del dibujo.
     * @param fil la coordenada de fila superior del rect�ngulo del dibujo.
     */
    public void dibujarEspada( int col, int fil )
    {
        v.setVisible(true);
        
        //fondo de la carta
        v.setColor (Color.white);
        v.fillRoundRect (col, fil, 100, 150, 21, 9);
        
        //bordes externos e internos de la carta
        v.setColor(Color.black);
        v.drawRoundRect (col, fil, 100, 150, 21, 9);
        v.drawRect (col+5, fil+5, 90, 140);
        
        //las lineas del palo
        v.setColor(Color.white);
        v.drawLine (col+23, fil+5, col+41, fil+5);
        v.setColor(Color.white);
        v.drawLine (col+59, fil+5, col+77, fil+5);
        v.setColor(Color.white);
        v.drawLine (col+23, fil+145, col+41, fil+145);
        v.setColor(Color.white);
        v.drawLine (col+59, fil+145, col+77, fil+145);
        
        //filo de la espada
        v.setColor (Color.blue);
        int ay[] = { col+50, col+45 , col+45, col+55, col+55 };
        int ax[] = { fil+7, fil+12, fil+100, fil+100, fil+12 };
        v.fillPolygon( ay, ax, 5 );
        v.setColor (Color.black);
        int ay1[] = { col+50, col+45 , col+45, col+55, col+55 };
        int ax1[] = { fil+7, fil+12, fil+100, fil+100, fil+12 };
        v.drawPolygon( ay1, ax1, 5 );
        v.setColor (Color.black);
        v.drawLine (col+50, fil+12, col+50, fil+100);
        
        //mango de la espada
        Color verdeoscuro = new Color (32, 164, 71);
        v.setColor(verdeoscuro);
        v.fillRect(col+28, fil+100, 44, 10);
        v.setColor(Color.black);
        v.drawRect(col+28, fil+100, 44, 10);
        v.setColor(Color.red);
        v.fillRect(col+45, fil+110, 10, 30);
        v.setColor(Color.black);
        v.drawRect(col+45, fil+110, 10, 30);
        
    
    }
    
    /**
     * Dibuja un oro, comenzando en el punto de coordenadas (col, fil).
     * @param col la coordenada de columna izquierda del rect�ngulo del dibujo.
     * @param fil la coordenada de fila superior del rect�ngulo del dibujo.
     */
    public void dibujarOro( int col, int fil )
    {
        v.setVisible(true);
        
        //fondo de la carta
        v.setColor (Color.white);
        v.fillRoundRect (col, fil, 100, 150, 21, 9);
        
        //bordes externos e internos de la carta
        v.setColor(Color.black);
        v.drawRoundRect (col, fil, 100, 150, 21, 9);
        v.drawRect (col+5, fil+5, 90, 140);
        
        //Circulo principal con su secundario
        v.setColor(Color.yellow);
        v.fillOval(col+13, fil+38, 75, 75);
        v.setColor(Color.black);
        v.drawOval(col+13, fil+38, 75, 75);
        v.drawOval(col+18, fil+43, 65, 65);
        
        //Circulo central
        v.setColor(Color.red);
        v.fillOval(col+45, fil+70, 10, 10);
        v.setColor(Color.black);
        v.drawOval(col+45, fil+70, 10, 10);
    
    }
    
    /**
     * Dibuja un basto, comenzando en el punto de coordenadas (col, fil).
     * @param col la coordenada de columna izquierda del rect�ngulo del dibujo.
     * @param fil la coordenada de fila superior del rect�ngulo del dibujo.
     */
    public void dibujarBasto( int col, int fil )
    {
        v.setVisible(true);
        
        //fondo de la carta
        v.setColor (Color.white);
        v.fillRoundRect (col, fil, 100, 150, 21, 9);
        
        //bordes externos e internos de la carta
        v.setColor(Color.black);
        v.drawRoundRect (col, fil, 100, 150, 21, 9);
        v.drawRect (col+5, fil+5, 90, 140);
        
        //lineas de palo
        v.setColor(Color.white);
        v.drawLine (col+23, fil+5, col+35, fil+5);
        v.drawLine (col+47, fil+5, col+59, fil+5);
        v.drawLine (col+71, fil+5, col+83, fil+5);
        v.drawLine (col+23, fil+145, col+35, fil+145);
        v.drawLine (col+47, fil+145, col+59, fil+145);
        v.drawLine (col+71, fil+145, col+83, fil+145);
              
        //Extremos del basto
        Color verdeoscuro1 = new Color (32, 164, 71);
        Color puntas = new Color (102, 176, 28);
        v.setColor(puntas);
        v.fillOval(col+30, fil+10, 40, 40);
        v.fillOval(col+40, fil+110, 20, 20);
        v.setColor(Color.black);
        v.drawOval(col+30, fil+10, 40, 40);
        v.drawOval(col+40, fil+110, 20, 20);
        
        //Tronco del basto
        v.setColor(verdeoscuro1);
        int by[] = { col+30, col+40 , col+60, col+70};
        int bx[] = { fil+30, fil+120, fil+120, fil+30};
        v.fillPolygon( by, bx, 4 );
        v.setColor (Color.black);
        int by1[] = { col+30, col+40 , col+60, col+70};
        int bx1[] = { fil+30, fil+120, fil+120, fil+30};
        v.drawPolygon( by1, bx1, 4 );

        //Eliminamos las lineas del medio
        v.setColor(verdeoscuro1);
        v.drawLine(col+30, fil+30, col+70, fil+30);
        v.drawLine(col+40, fil+120, col+60, fil+120);
    }
    
    /**
     * Dibuja una copa, comenzando en el punto de coordenadas (col, fil).
     * @param col la coordenada de columna izquierda del rect�ngulo del dibujo.
     * @param fil la coordenada de fila superior del rect�ngulo del dibujo.
     */
    public void dibujarCopa( int col, int fil )
    {
        v.setVisible(true);
        
        //fondo de la carta
        v.setColor (Color.white);
        v.fillRoundRect (col, fil, 100, 150, 21, 9);
        
        //bordes externos e internos de la carta
        v.setColor(Color.black);
        v.drawRoundRect (col, fil, 100, 150, 21, 9);
        v.drawRect (col+5, fil+5, 90, 140);
        
        //Lineas del palo
        v.setColor(Color.white);
        v.drawLine(col+35, fil+5, col+65, fil+5);
        v.drawLine(col+35, fil+145, col+65, fil+145);
        
        //Base de la copa
        Color copero = new Color (58, 139, 31);
        v.setColor(copero);
        int cy[] = { col+50, col+30 , col+60};
        int cx[] = { fil+70, fil+130, fil+130};
        v.fillPolygon( cy, cx, 3 );
        v.setColor(Color.black);
        int cy1[] = { col+50, col+30 , col+60};
        int cx1[] = { fil+70, fil+130, fil+130};
        v.drawPolygon( cy1, cx1, 3 );
        v.setColor(copero);
        v.fillRect(col+20, fil+120, 50, 10);
        v.setColor(Color.black);
        v.drawRect(col+20, fil+120, 50, 10);
        v.setColor(copero);
   
        //Cuerpo de la copa
        v.setColor(copero);
        v.fillOval(col+28, fil, 40, 90);
        v.setColor(Color.black);
        v.drawOval(col+28, fil, 40, 90);
        v.setColor(Color.white);
        v.clearRect(col+28, fil+1, 45, 40);
        v.setColor(Color.black);
        v.drawLine (col+28, fil+40, col+68, fil+40);
        
       //interior de la copa
       v.setColor(Color.red);
       v.fillOval(col+28, fil+30, 40, 20);
       v.setColor(Color.black);
       v.drawOval(col+28, fil+30, 40, 20);
    }
}
