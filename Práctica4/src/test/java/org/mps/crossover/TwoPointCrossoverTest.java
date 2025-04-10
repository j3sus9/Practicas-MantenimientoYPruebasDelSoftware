/*
 * Jesús Repiso Rio
 * Alejandro Cueto Díaz
 */

package org.mps.crossover;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;

public class TwoPointCrossoverTest {

    @Test
    @DisplayName("Test para probar el constructor")
    void TwoPointCrossover_WellInitiated_NotNUll(){
        TwoPointCrossover twoPointCrossover = new TwoPointCrossover();
        assertNotNull(twoPointCrossover);
    }

    @Nested 
    @DisplayName("Clase para probar el método crossover")
    class crossoverTests {
        private TwoPointCrossover twoPointCrossover;
        int[] parent1;
        int[] parent2;

        @BeforeEach
        void setUp(){
            twoPointCrossover = new TwoPointCrossover();
        }

        @Test
        @DisplayName("Valores correctos --> hijos creados")
        void crossover_CorrectValues_ChildrenCreated() throws EvolutionaryAlgorithmException {
            parent1 = new int[]{2,3,4};
            parent2 = new int[]{5,6,7};

            int[][] offspring = twoPointCrossover.crossover(parent1, parent2);

            // Verificar que se crean dos hijos
            assertNotNull(offspring);
            assertEquals(2, offspring.length);
            
            // Verificar que los hijos tienen la misma longitud que los padres
            assertEquals(parent1.length, offspring[0].length);
            assertEquals(parent2.length, offspring[1].length);
            
            // Verificar que los hijos contienen valores de ambos padres
            boolean hasParent1Values = false;
            boolean hasParent2Values = false;
            
            for (int i = 0; i < offspring[0].length; i++) {
                if (offspring[0][i] == parent1[i] || offspring[0][i] == parent2[i]) {
                    hasParent1Values = true;
                }
                if (offspring[1][i] == parent1[i] || offspring[1][i] == parent2[i]) {
                    hasParent2Values = true;
                }
            }
            
            assertTrue(hasParent1Values);
            assertTrue(hasParent2Values);
        }

        @Test
        @DisplayName("El parent1 es null --> Se devuelve una excepción")
        void crossover_Parent1Null_ThrowsException(){
            parent2 = new int[]{5,6,7};

            assertThrows(EvolutionaryAlgorithmException.class, () -> twoPointCrossover.crossover(parent1, parent2));
        }

        @Test
        @DisplayName("El parent2 es null --> Se devuelve una excepción")
        void crossover_Parent2Null_ThrowsException(){
            parent1 = new int[]{5,6,7};

            assertThrows(EvolutionaryAlgorithmException.class, () -> twoPointCrossover.crossover(parent1, parent2));
        }

        @Test
        @DisplayName("El parent1 tiene longitud menor o igual que 1 --> Se devuelve una excepción")
        void crossover_Parent1LengthLowerThan0_ThrowsException(){
            parent1 = new int[1];
            parent2 = new int[4];

            assertThrows(EvolutionaryAlgorithmException.class, () -> twoPointCrossover.crossover(parent1, parent2));
        }

        @Test
        @DisplayName("El parent1 tiene longitud mayor que el parent2 --> Se devuelve una excepción")
        void crossover_Parent1LengthBiggerThanParentLength_ThrowsException(){
            parent1 = new int[8];
            parent2 = new int[4];

            assertThrows(EvolutionaryAlgorithmException.class, () -> twoPointCrossover.crossover(parent1, parent2));
        }
    }

}
