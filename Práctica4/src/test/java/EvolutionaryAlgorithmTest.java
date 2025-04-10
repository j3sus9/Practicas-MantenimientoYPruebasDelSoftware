import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithm;
import org.mps.EvolutionaryAlgorithmException;
import org.mps.crossover.CrossoverOperator;
import org.mps.crossover.TwoPointCrossover;
import org.mps.mutation.GaussianMutation;
import org.mps.mutation.MutationOperator;
import org.mps.selection.SelectionOperator;
import org.mps.selection.TournamentSelection;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class EvolutionaryAlgorithmTest {

    private SelectionOperator seleccion;
    private MutationOperator mutacion;
    private CrossoverOperator cruce;
    EvolutionaryAlgorithm evolution;
    
    @BeforeEach
    void setup() throws EvolutionaryAlgorithmException{
        seleccion = new TournamentSelection(1);
        mutacion = new GaussianMutation();
        cruce = new TwoPointCrossover();
        evolution = new EvolutionaryAlgorithm(seleccion, mutacion, cruce);
    }

    @Test
    @DisplayName("Cuando al constructor de EvolutionaryAlgorithm se le pasa el parametro SelectionOperator con valor null, devuelve EvolutionaryAlgorithmException")
    void EvolutionaryAlgorithm_parametrosSeleccionNulo_EvolutionaryAlgortihmException(){
        assertThrows(EvolutionaryAlgorithmException.class, () ->  new EvolutionaryAlgorithm(null, mutacion, cruce));
    }  

    @Test
    @DisplayName("Cuando al constructor de EvolutionaryAlgorithm se le pasa el parametro MutationOperator con valor null, devuelve EvolutionaryAlgorithmException")
    void EvolutionaryAlgorithm_parametrosMutacionNulo_EvolutionaryAlgortihmException(){
        assertThrows(EvolutionaryAlgorithmException.class, () ->  new EvolutionaryAlgorithm(seleccion, null, cruce));
    } 

    @Test
    @DisplayName("Cuando al constructor de EvolutionaryAlgorithm se le pasa el parametro CrossoverOperator con valor null, devuelve EvolutionaryAlgorithmException")
    void EvolutionaryAlgorithm_parametrosCruceNulo_EvolutionaryAlgortihmException(){
        assertThrows(EvolutionaryAlgorithmException.class, () ->  new EvolutionaryAlgorithm(seleccion, mutacion, null));
    } 


    @Test
    @DisplayName("Cuando EvolutionaryAlgorithm usa el metodo optimize con parametro nulo, devuelve EvolutionaryAlgorithmException")
    void optimize_parametrosPopulationNulo_EvolutionaryAlgorithmException(){
        assertThrows(EvolutionaryAlgorithmException.class, () ->  evolution.optimize(null));
    }

    @Test
    @DisplayName("Cuando EvolutionaryAlgorithm usa el metodo optimize con parametro vacio, devuelve EvolutionaryAlgorithmException")
    void optimize_parametrosPopulationVacio_EvolutionaryAlgorithmException(){
        int[][] population = new int[0][0];
        assertThrows(EvolutionaryAlgorithmException.class, () ->  evolution.optimize(population));
    }

    @Test
    @DisplayName("Cuando EvolutionaryAlgorithm usa el metodo optimize con parametro impar, devuelve EvolutionaryAlgorithmException")
    void optimize_parametrosPopulationImpar_EvolutionaryAlgorithmException(){   
        int[][] population = new int[1][1];
        assertThrows(EvolutionaryAlgorithmException.class, () ->  evolution.optimize(population));
    }

    @Test
    @DisplayName("Cuando EvolutionaryAlgorithm usa el metodo optimize con parametro correcto, devuelve el parametro optimizado")
    void optimize_parametrosPopulationCorrecto_optimizaPopulation() throws EvolutionaryAlgorithmException{
        int[][] population = new int[][]{{1,2},{3,4}};
        assertArrayEquals(population, evolution.optimize(population));       
    }

     @Test
     @DisplayName("Este test es solo para comprobar el correcto funcionamiento de EvolutionaryAlgorithmException")
     void EvolutionaryAlgorithmException_recibeError_devuelveMensajeCorrecto() {
        EvolutionaryAlgorithmException exception = new EvolutionaryAlgorithmException();
        assertNull(exception.getMessage(), "El mensaje debe ser nulo por defecto");
    }

}