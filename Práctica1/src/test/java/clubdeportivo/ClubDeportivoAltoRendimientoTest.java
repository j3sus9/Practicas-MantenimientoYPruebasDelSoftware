/*
 * Jesús Repiso Rio
 * Alejandro Cueto Díaz
 */

package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClubDeportivoAltoRendimientoTest{

    @Test
    @DisplayName("Valores correctos del primer constructor no provocan excepción")
    void constructor_ParametrosCorrectos_NoLanzaExcepcion(){
        assertDoesNotThrow(()->new ClubDeportivoAltoRendimiento("Club",10,20));
        assertDoesNotThrow(()->new ClubDeportivoAltoRendimiento("Club",10,20,30));
    }

    @Test
    @DisplayName("Valores incorrectos del primer constructor lanzan excepción")
    void constructor_ParametrosIncorrectos_LanzaExcepcion(){
        assertThrows(ClubException.class, ()->new ClubDeportivoAltoRendimiento("Hola1", 0, 20));
        assertThrows(ClubException.class, ()->new ClubDeportivoAltoRendimiento("Hola2", 10, -4));
        assertThrows(ClubException.class, ()->new ClubDeportivoAltoRendimiento("Hola3", 20, 0, 20));
        assertThrows(ClubException.class, ()->new ClubDeportivoAltoRendimiento("Hola4", 30, 5, -10));
    }

    @Test
    @DisplayName("Con un string con una longitud menor que 5 no se puede añadir una actividad")
    void anyadirActividad_LongitudIncorrecta_LanzaExcepcion() throws ClubException{
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Elite", 10, 20.0);
        String[] datos = {"A1", "Natación", "10"};
        assertThrows(ClubException.class, ()->club.anyadirActividad(datos));
    }

    @Test
    @DisplayName("Si el formato de uno de los elementos que son números no es correcto, se lanza una excepción")
    void anyadirActividad_FormatoNumeroIncorrecto_LanzaExcepcion() throws ClubException{
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Elite", 10, 20.0);
        String[] datos = {"A2", "Tenis", "abc", "5", "70.0"};
        assertThrows(ClubException.class, ()->club.anyadirActividad(datos));
    }

    @Test
    @DisplayName("Si se introducen datos correctos no se lanza excepción")
    void anyadirActividad_DatosCorrectos_NoLanzaExcepcion() throws ClubException{
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Elite", 10, 20.0);
        String[] datos = {"A3", "Futbol", "12", "5", "50.0"};
        assertDoesNotThrow(()->club.anyadirActividad(datos));
        assertEquals(club.toString(), "Club Elite --> [ (A3 - Futbol - 50.0 euros - P:10 - M:5) ]");
    }

    @Test
    @DisplayName("La cantidad de ingresos que el club tiene es correcta")
    void ingresos_CalculoCorrecto_DevuelveValorEsperado() throws ClubException{
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Elite", 10, 20.0);
        String[] datos = {"A4", "Yoga", "8", "4", "100.0"};
        club.anyadirActividad(datos);
        double ingresosEsperados = club.ingresos();
        assert(ingresosEsperados > 0);
    }
}
