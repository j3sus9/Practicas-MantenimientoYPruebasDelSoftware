package org.mps.ronqi2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mps.dispositivo.DispositivoSilver;

public class RonQI2SilverTest {

    private RonQI2Silver ronqi2silver;
    private DispositivoSilver dispositivosilver;

    @BeforeEach
    void setup(){
        ronqi2silver = new RonQI2Silver();
        dispositivosilver = mock(DispositivoSilver.class);
        ronqi2silver.anyadirDispositivo(dispositivosilver);
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
    @Test
    @DisplayName("Comprobar que el reconectar funciona como debe ser")
    void reconectar_whenNoEstaConectadoYSensoresConectados_ConectarloYDevolverTrue(){
        //Arrange
        when(dispositivosilver.estaConectado()).thenReturn(false);
        when(dispositivosilver.conectarSensorPresion()).thenReturn(true);
        when(dispositivosilver.conectarSensorSonido()).thenReturn(true);
        
        //Act
        boolean result = ronqi2silver.reconectar();

        //Assert
        verify(dispositivosilver).conectarSensorPresion();
        verify(dispositivosilver).conectarSensorSonido();
        assertEquals(true, result);
    }

    @Test
    @DisplayName("Comprobar que el reconectar funciona como debe ser cuando el dispositivo esta conectado")
    void reconectar_whenEstaConectado_DevolverFalse(){
        //Arrange
        when(dispositivosilver.estaConectado()).thenReturn(true);
        
        //Act
        boolean result = ronqi2silver.reconectar();

        //Assert
        assertEquals(false, result);
    }

    @Test
    @DisplayName("Comprobar que el reconectar funciona como debe ser cuando el sensor de presión no se inicializa")
    void reconectar_whenNoEstaConectadoYSensorPresion_DevolverFalse(){
        //Arrange
        when(dispositivosilver.estaConectado()).thenReturn(false);
        when(dispositivosilver.conectarSensorPresion()).thenReturn(false);
        when(dispositivosilver.conectarSensorSonido()).thenReturn(true);

        //Act
        boolean result = ronqi2silver.reconectar();

        //Assert
        verify(dispositivosilver).conectarSensorPresion();
        assertEquals(false, result);
    }

    @Test
    @DisplayName("Comprobar que el reconectar funciona como debe ser cuando el sensor de sonido no se inicializa")
    void reconectar_whenNoEstaConectadoYSensorSonido_DevolverFalse(){
        //Arrange
        when(dispositivosilver.estaConectado()).thenReturn(false);
        when(dispositivosilver.conectarSensorPresion()).thenReturn(true);
        when(dispositivosilver.conectarSensorSonido()).thenReturn(false);

        //Act
        boolean result = ronqi2silver.reconectar();

        //Assert
        verify(dispositivosilver).conectarSensorPresion();
        verify(dispositivosilver).conectarSensorSonido();
        assertEquals(false, result);
    }
    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     */
    
    @Test
    @DisplayName("Evaluar las ultimas 5 lecturas para ver si hay una apnea en proceso - HAY")
    void evaluarApneaSuenyo_ApneaEnProceso_DevuelveTrue(){
        //Arrange
        when(dispositivosilver.leerSensorPresion()).thenReturn(25f);
        when(dispositivosilver.leerSensorSonido()).thenReturn(35f);

        //Act
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();

        //Assert
        assertEquals(true, ronqi2silver.evaluarApneaSuenyo());
    }

    @Test
    @DisplayName("Evaluar las ultimas 5 lecturas para ver si hay una apnea en proceso pero no hay porque los sensores no superan sus umbrales")
    void evaluarApneaSuenyo_NoApneaEnProcesoSensoresMenores_DevuelveTrue(){
        //Arrange
        when(dispositivosilver.leerSensorPresion()).thenReturn(15f);
        when(dispositivosilver.leerSensorSonido()).thenReturn(25f);

        //Act
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();

        //Assert
        assertEquals(false, ronqi2silver.evaluarApneaSuenyo());
    }
    
    @Test
    @DisplayName("Evaluar las ultimas 5 lecturas para ver si hay una apnea en proceso pero no hay porque el sensor de sonido no supera su umbral")
    void evaluarApneaSuenyo_NoApneaEnProcesoSensorSonidoMenor_DevuelveTrue(){
        //Arrange
        when(dispositivosilver.leerSensorPresion()).thenReturn(25f);
        when(dispositivosilver.leerSensorSonido()).thenReturn(25f);

        //Act
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();

        //Assert
        assertEquals(false, ronqi2silver.evaluarApneaSuenyo());
    }
    
    @Test
    @DisplayName("Evaluar las ultimas 5 lecturas para ver si hay una apnea en proceso pero no hay porque el sensor de presión no supera su umbral")
    void evaluarApneaSuenyo_NoApneaEnProcesoSensorPresionMenor_DevuelveTrue(){
        //Arrange
        when(dispositivosilver.leerSensorPresion()).thenReturn(15f);
        when(dispositivosilver.leerSensorSonido()).thenReturn(35f);

        //Act
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();

        //Assert
        assertEquals(false, ronqi2silver.evaluarApneaSuenyo());
    }

    //TESTS obtenerNuevaLectura()
    @Test
    @DisplayName("Añadir nuevas lecturas sin superar el número máximo de lecturas")
    void obtenerNuevaLectura_TamañoNoSuperado_AñadeCorrectamente(){
        //Arrange
        when(dispositivosilver.leerSensorPresion()).thenReturn(10f);
        when(dispositivosilver.leerSensorSonido()).thenReturn(20f);

        //Act
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
    }

    @Test
    @DisplayName("Añadir nuevas lecturas superando el número máximo de lecturas")
    void obtenerNuevaLectura_TamañoSuperado_AñadeCorrectamente(){
        //Arrange
        when(dispositivosilver.leerSensorPresion()).thenReturn(10f);
        when(dispositivosilver.leerSensorSonido()).thenReturn(20f);

        //Act
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
        ronqi2silver.obtenerNuevaLectura();
    }

    //TESTS estaConectado()
    @Test
    @DisplayName("Cuando el dispositvo está conectado se devuelve true")
    void estaConectado_DispositivoConectado_DevuelveTrue(){
        //Arrange
        when(dispositivosilver.estaConectado()).thenReturn(true);

        //Act
        boolean res = ronqi2silver.estaConectado();

        //Assert
        assertEquals(true, res);
    }

    @Test
    @DisplayName("Cuando el dispositvo está conectado se devuelve false")
    void estaConectado_DispositivoDesconectado_DevuelveTrue(){
        //Arrange
        when(dispositivosilver.estaConectado()).thenReturn(false);

        //Act
        boolean res = ronqi2silver.estaConectado();

        //Assert
        assertEquals(false, res);
    }
    
    /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */
}
