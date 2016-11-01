package com.company;

/**
 * Created by Maldo on 26/10/2016.
 */
public class Mesa {


    private static final int GANA_JUGADOR_HUMANO = 0;
    private static final int EMPATE = 2;
    private static final int GANA_MAQUINA = 1;
    private static final int PRIMERA = 1;
    private static final int SEGUNDA = 2;
    private static final int TERCERA = 3;

    private Carta cartasHumano[];
    private Carta cartasMaquina[];

    public Mesa(){
        cartasHumano = new Carta[3];
        cartasMaquina = new Carta[3];
    }



    public Mesa(Carta[][] matrizCarta){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if(j == 0){
                    cartasHumano[i] = matrizCarta[i][j];
                }else {
                    cartasMaquina[i] = matrizCarta[i][j];
                }
            }
        }
    }

    public Mesa(Carta[] cartasHumano, Carta[] cartasMaquina) {
        this.cartasHumano = cartasHumano;
        this.cartasMaquina = cartasMaquina;
    }

    public Carta[] getCartasHumano() {
        return cartasHumano;
    }

    public void setCartasHumano(Carta[] cartasHumano) {
        this.cartasHumano = cartasHumano;
    }

    public Carta[] getCartasMaquina() {
        return cartasMaquina;
    }

    public void setCartasMaquina(Carta[] cartasMaquina) {
        this.cartasMaquina = cartasMaquina;
    }

    public boolean isFull() {
        return cartasHumano[2] != null;
    }

    public String generarCodigoMesa() {
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            if (cartasMaquina[i] != null) {
                buffer.append(cartasMaquina[i].generarCodigo());
            } else {
                break;
            }

            if (cartasHumano[i] != null) {
                buffer.append(cartasHumano[i].generarCodigo());
            } else {
                break;
            }
        }

        return buffer.toString();
    }

    public Mesa clonarMesa(Carta carta, boolean humano) {
        Mesa nuevaMesa = new Mesa();

        Carta[] nueva;
        if (humano) {
            nueva = this.cartasHumano.clone();
            for (int i = 0; i < 3; i++) {
                if (nueva[i] == null) {
                    nueva[i] = carta;
                    nuevaMesa.setCartasHumano(nueva);
                    nuevaMesa.setCartasMaquina(this.cartasMaquina.clone());
                    break;
                }
            }
        } else {
            nueva = this.cartasMaquina.clone();
            for (int i = 0; i < 3; i++) {
                if (nueva[i] == null) {
                    nueva[i] = carta;
                    nuevaMesa.setCartasHumano(this.cartasHumano.clone());
                    nuevaMesa.setCartasMaquina(nueva);
                    break;
                }
            }
        }
        return nuevaMesa;
    }

    @Override
    public String toString(){
        return this.generarCodigoMesa();
    }

    public int ganoMaquina(){
        return evaluarRonda();
    }

    public Carta getUltimaCartaJugadaMaquina(){
        for (int i = 2; i >= 0; i--) {
            if(cartasMaquina[i] != null){
                return cartasMaquina[i];
            }
        }
        return null;
    }


    private int evaluarRonda() {
            if (getResultadoMano(PRIMERA) == GANA_JUGADOR_HUMANO) {
                if (getResultadoMano(SEGUNDA) == GANA_JUGADOR_HUMANO || getResultadoMano(SEGUNDA) == EMPATE) {
                   return GANA_JUGADOR_HUMANO;
                }
            }
            else {
                if (getResultadoMano(PRIMERA) == EMPATE) {
                    if (getResultadoMano(SEGUNDA) != EMPATE) {
                        return getResultadoMano(SEGUNDA);
                    }
                } else {
                    if (getResultadoMano(SEGUNDA) == GANA_MAQUINA || getResultadoMano(SEGUNDA) == EMPATE) {
                        return GANA_MAQUINA;
                    }
                }
            }


            if (getResultadoMano(TERCERA) != EMPATE) {
                return getResultadoMano(TERCERA);
            }
            else{
                if(getResultadoMano(PRIMERA) != EMPATE){
                    return getResultadoMano(PRIMERA);
                }
                else{
                    return GANA_MAQUINA;
                }
            }

    }

    private int getResultadoMano(int mano)
    {
        if( cartasHumano[mano -1].compareTo(cartasMaquina[mano - 1]) > 0){
            return GANA_JUGADOR_HUMANO;
        }else if(cartasHumano[mano -1].compareTo(cartasMaquina[mano - 1]) == 0){
            return EMPATE;
        }else{
            return GANA_MAQUINA;
        }
    }
}
