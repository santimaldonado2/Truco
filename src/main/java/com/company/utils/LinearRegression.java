package com.company.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class LinearRegression {

    private static final SimpleRegression simple = new SimpleRegression();

    /**
     * Para que no pueda ser construido el objeto y mandarse mocos.
     */
    private LinearRegression() {

    }

    /**
     * Carga los datos historicos para poder calcular la regresión lineal de la
     * maquina jugando al envido.
     */
    public static void cargarDatos() {

        try {
            for(String line : Files.readAllLines(Paths.get("EnvidoJugadas.txt"))) {
                String[] data = line.split("\\s");
                simple.addData(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Agrega datos a la DB para que luego los calculos sean con mejor
     * probabilidades y vaya aprendiendo.
     * 
     * @param puntos
     *            puntos de la maquina para jugar el envido
     * @param ganado
     *            true si gano la maquina, false cualquier otro caso.
     */
    public static void agregarDatos(int puntos, boolean ganado) {
        try {

            Writer envidoEstadisticas = new BufferedWriter(new FileWriter("EnvidoJugadas.txt", true));
            envidoEstadisticas.append(String.format("\n%d %d", puntos, ganado ? 1 : 0));
            envidoEstadisticas.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve la probabilidad que tiene la maquina de ganar el envido
     * 
     * @param puntos
     *            puntos que tiene la maquina para jugar el envido.
     * @return probabilidad de ganar el envido
     */
    public static double calcularProbabilidadGanarEnvido(double puntos) {
        return simple.predict(puntos);
    }
}
