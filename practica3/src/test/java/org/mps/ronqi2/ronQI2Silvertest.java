package org.mps.ronqi2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.dispositivo.DispositivoSilver;

public class ronQI2Silvertest {

    private RonQI2Silver ronqi2silver;
    private DispositivoSilver dispositivosilver;

    @BeforeEach
    void setup(){
        ronqi2silver = new RonQI2Silver();
        dispositivosilver = mock(DispositivoSilver.class);
        ronqi2silver.disp = dispositivosilver;
    }

    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
     * el método inicializar de ronQI2 o sus subclases, 
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */
    
    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
     * se llama una sola vez al configurar de cada sensor.
     */

    @Test
    @DisplayName("Comprobar que al inicializar funciona como debe ser")
    void ronqi2silver_nuevoRonQI2Silver_devuelveVerdadero(){
        when(ronqi2silver.disp.conectarSensorPresion()).thenReturn(true);
        when(ronqi2silver.disp.configurarSensorPresion()).thenReturn(true);
        when(ronqi2silver.disp.conectarSensorSonido()).thenReturn(true);
        when(ronqi2silver.disp.configurarSensorSonido()).thenReturn(true);

        assertEquals(ronqi2silver.inicializar(), true);

        verify(ronqi2silver.disp, times(1)).configurarSensorPresion();
        verify(ronqi2silver.disp, times(1)).configurarSensorSonido();
    }

    @Test
    @DisplayName("Comprobar que al inicializar falla si falla ConfigurarSensorSonido")
    void ronqi2silver_nuevoRonQI2Silver_devuelveFalsoConfigurarSensorSonido(){
        when(ronqi2silver.disp.conectarSensorPresion()).thenReturn(true);
        when(ronqi2silver.disp.configurarSensorPresion()).thenReturn(true);
        when(ronqi2silver.disp.conectarSensorSonido()).thenReturn(true);
        when(ronqi2silver.disp.configurarSensorSonido()).thenReturn(false);

        assertEquals(ronqi2silver.inicializar(), false);
    
    }

    @Test
    @DisplayName("Comprobar que al inicializar falla si falla ConectarSensorSonido")
    void ronqi2silver_nuevoRonQI2Silver_devuelveFalsoConectarSensorSonido(){
        when(ronqi2silver.disp.conectarSensorPresion()).thenReturn(true);
        when(ronqi2silver.disp.configurarSensorPresion()).thenReturn(true);
        when(ronqi2silver.disp.conectarSensorSonido()).thenReturn(false);
        when(ronqi2silver.disp.configurarSensorSonido()).thenReturn(true);

        assertEquals(ronqi2silver.inicializar(), false);
    
    }

    @Test
    @DisplayName("Comprobar que al inicializar falla si falla ConfigurarSensorPresion")
    void ronqi2silver_nuevoRonQI2Silver_devuelveFalsoConfigurarSensorPresion(){
        when(ronqi2silver.disp.conectarSensorPresion()).thenReturn(true);
        when(ronqi2silver.disp.configurarSensorPresion()).thenReturn(false);
        when(ronqi2silver.disp.conectarSensorSonido()).thenReturn(true);
        when(ronqi2silver.disp.configurarSensorSonido()).thenReturn(true);

        assertEquals(ronqi2silver.inicializar(), false);
    
    }

    @Test
    @DisplayName("Comprobar que al inicializar falla si falla ConectarSensorPresion")
    void ronqi2silver_nuevoRonQI2Silver_devuelveFalsoConectarSensorPresion(){
        when(ronqi2silver.disp.conectarSensorPresion()).thenReturn(false);
        when(ronqi2silver.disp.configurarSensorPresion()).thenReturn(true);
        when(ronqi2silver.disp.conectarSensorSonido()).thenReturn(true);
        when(ronqi2silver.disp.configurarSensorSonido()).thenReturn(true);

        assertEquals(ronqi2silver.inicializar(), false);
    
    }

    

    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */
    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     * /
     
     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */
}
