package org.mps.main;

import java.util.Arrays;

import org.mps.EvolutionaryAlgorithmException;
import org.mps.crossover.TwoPointCrossover;

public class main {
    public static void main(String[] args) {
        try {
            // Crear dos padres de prueba
            int[] parent1 = {1, 2, 3, 4, 5, 6, 7, 8};
            int[] parent2 = {9, 10, 11, 12, 13, 14, 15, 16};
            
            System.out.println("Padre 1: " + Arrays.toString(parent1));
            System.out.println("Padre 2: " + Arrays.toString(parent2));

            // Crear el operador de cruce de dos puntos
            TwoPointCrossover crossover = new TwoPointCrossover();

            // Realizar el cruce
            int[][] offspring = crossover.crossover(parent1, parent2);
            
            System.out.println("\nDescendientes después del cruce:");
            System.out.println("Hijo 1: " + Arrays.toString(offspring[0]));
            System.out.println("Hijo 2: " + Arrays.toString(offspring[1]));

            // Verificar que los descendientes tienen elementos de ambos padres
            System.out.println("\nAnálisis de los descendientes:");
            System.out.println("Longitud Hijo 1: " + offspring[0].length);
            System.out.println("Longitud Hijo 2: " + offspring[1].length);

            // Calcular y mostrar los promedios
            double parent1Avg = Arrays.stream(parent1).average().orElse(0);
            double parent2Avg = Arrays.stream(parent2).average().orElse(0);
            double offspring1Avg = Arrays.stream(offspring[0]).average().orElse(0);
            double offspring2Avg = Arrays.stream(offspring[1]).average().orElse(0);

            System.out.println("\nPromedios:");
            System.out.println("Promedio Padre 1: " + parent1Avg);
            System.out.println("Promedio Padre 2: " + parent2Avg);
            System.out.println("Promedio Hijo 1: " + offspring1Avg);
            System.out.println("Promedio Hijo 2: " + offspring2Avg);

        } catch (EvolutionaryAlgorithmException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}