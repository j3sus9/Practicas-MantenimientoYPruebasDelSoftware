package org.mps.mutation;

import org.mps.EvolutionaryAlgorithmException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
public class GaussianMutationTest {


    private GaussianMutation gaussianmutation;
    

    @BeforeEach
    void setup(){
        gaussianmutation = new GaussianMutation();
    }

    @Test
    @DisplayName("Cuando al constructor de GaussianMutation se le pasa el parametro individual con valor null, devuelve EvolutionaryAlgorithmException")
    void mutate_individualNulo_devuelveEvolutionaryAlgorithmException(){
        assertThrows(EvolutionaryAlgorithmException.class,() -> gaussianmutation.mutate(null));
    }

    @Test
    @DisplayName("Cuando al constructor de GaussianMutation se le pasa el parametro individual con valor vacio, devuelve EvolutionaryAlgorithmException")
    void mutate_individualVacio_devuelveEvolutionaryAlgorithmException(){
        int[] individual = new int[0];
        assertThrows(EvolutionaryAlgorithmException.class,() -> gaussianmutation.mutate(individual));
    }

    @Test
    @DisplayName("Cuando GaussianMutation usa el metodo mutate con parametro correcto, devuelve el parametro mutado")
    void mutate_individualCorrecto_mutacionProducida() throws EvolutionaryAlgorithmException{
        gaussianmutation = new GaussianMutation(5, 2);
        int[] individual = new int[]{1,2,3,4,5};
        assertFalse(gaussianmutation.mutate(individual) == (individual));
    }

    @Test
    @DisplayName("Cuando GaussianMutation con atributos iguales a 0, usa el metodo mutate con parametro correcto, devuelve el parametro mutado")
    void mutate_individualCorrecto_noMutacionProducida() throws EvolutionaryAlgorithmException{
        int[] individual = new int[]{1,2};
        assertArrayEquals(gaussianmutation.mutate(individual),individual);
    }
}