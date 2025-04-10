package org.mps.main;

import java.util.Arrays;

import org.mps.EvolutionaryAlgorithmException;
import org.mps.selection.TournamentSelection;

public class main {
    public static void main(String[] args) {
        try {
            // Crear una población de prueba
            int[] population = {1, 5, 3, 8, 2, 7, 4, 6};
            System.out.println("Población original: " + Arrays.toString(population));

            // Crear el operador de selección por torneo con tamaño 3
            TournamentSelection tournament = new TournamentSelection(3);

            // Realizar la selección
            int[] selected = tournament.select(population);
            System.out.println("Población seleccionada: " + Arrays.toString(selected));

            // Calcular y mostrar el promedio de la población original y la seleccionada
            double originalAvg = Arrays.stream(population).average().orElse(0);
            double selectedAvg = Arrays.stream(selected).average().orElse(0);

            System.out.println("Promedio población original: " + originalAvg);
            System.out.println("Promedio población seleccionada: " + selectedAvg);

        } catch (EvolutionaryAlgorithmException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}