/*
 * Jesús Repiso Rio
 * Alejandro Cueto Díaz
 */

package org.mps.selection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;

public class TournamentSelectionTest {
    
    @Nested
    @DisplayName("Clase de prueba para el constructor")
    class TournamentSelectionTests {

        private Random random;
        private int size;
        private TournamentSelection tournament;

        @Test 
        @DisplayName("Contsructor correcto")
        void TournamentSelection_SizeCorrect_SizeInitiated(){
            size = 4;
            assertDoesNotThrow(() -> new TournamentSelection(size));
        }

        @Test 
        @DisplayName("Contsructor incorrecto")
        void TournamentSelection_SizeIncorrect_SizeNotInitiated(){
            size = 0;
            assertThrows(EvolutionaryAlgorithmException.class, () -> new TournamentSelection(size));
        }
    }

    @Nested
    @DisplayName("Clase de prueba para el método select")
    class selectTests {

        private int[] population;
        private int size;
        private TournamentSelection tournament;

        @Test 
        @DisplayName("Valores correctos implican selección del mejor")
        void select_CorrectValues_BestSelected() throws EvolutionaryAlgorithmException{
            population = new int[]{2,4,5,6};
            size = 3;
            tournament = new TournamentSelection(size);

           int[] selected = tournament.select(population);
           boolean esta = false;

           for(int i = 0; i < selected.length; i++){
                for(int j = 0; j < population.length; j++){
                    if(selected[i] == population[j]){
                        esta = true;
                        break;
                    }
                }
                if(esta == false){
                    break;
                }
           }

           assertTrue(esta);
        }

        @Test 
        @DisplayName("La población es nula --> se devuelve una excepción")
        void select_PopulationNull_ThrowsException() throws EvolutionaryAlgorithmException{
            size = 3;
            tournament = new TournamentSelection(size);

           assertThrows(EvolutionaryAlgorithmException.class, () -> tournament.select(population));
        }

        @Test 
        @DisplayName("La población es menor que 0 --> se devuelve una excepción")
        void select_PopulationLowerThan0_ThrowsException() throws EvolutionaryAlgorithmException{
            population = new int[0];
            size = 3;
            tournament = new TournamentSelection(size);

           assertThrows(EvolutionaryAlgorithmException.class, () -> tournament.select(population));
        }

        @Test 
        @DisplayName("La población es menor que las rondas del torneo --> se devuelve una excepción")
        void select_PopulationLowerThanTournamentSize_ThrowsException() throws EvolutionaryAlgorithmException{
            population = new int[2];
            size = 4;
            tournament = new TournamentSelection(size);

           assertThrows(EvolutionaryAlgorithmException.class, () -> tournament.select(population));
        }
    }
}
