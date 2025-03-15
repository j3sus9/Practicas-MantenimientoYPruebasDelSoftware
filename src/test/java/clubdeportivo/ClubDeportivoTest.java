/*
 * Jesús Repiso Rio
 * Alejandro Cueto Díaz
 */

package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClubDeportivoTest {

    @Test
    @DisplayName("El club creado sin tamaño se inicializa con tamaño 10")
    void constructor_SoloNombre_ClubTamanyo10() throws ClubException{
        ClubDeportivo c1 = new ClubDeportivo("Elite");
        ClubDeportivo c2 = new ClubDeportivo("Elite", 10);
        assertEquals(c1.toString(), c2.toString());
    }

    @Test
    @DisplayName("El club debe tener un número positivo de integrantes")
    void constructor_NumeroCeroONegativo_LanzaExcepcion(){
        assertThrows(ClubException.class,() -> new ClubDeportivo("prueba", 0));
    }

    @Test 
    @DisplayName("El club se crea correctamente con un nombre y tamaño como parámetro")
    void constructor_NombreYTamano_CreaClub() throws ClubException{
        assertNotNull(new ClubDeportivo("prueba", 5));
    }

    @Test
    @DisplayName("Formato de número incorrecto al añadir actividad")
    void anyadirActividad_NumeroIncorrecto_LanzaExcepcion() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        String[] datos = {"1", "1", "1.5","1", "1"};
        assertThrows(ClubException.class,() -> club.anyadirActividad(datos));
    }

    @Test
    @DisplayName("Longiutd de datos menor a 5")
    void anyadirActividad_LongitudDatosMenor_LanzaExcepcion() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        String[] datos = {"1", "1", "1.5","1"};
        assertThrows(ClubException.class,() -> club.anyadirActividad(datos));
    }

    @Test
    @DisplayName("Añadir grupo nulo lanza excepción")
    void anyadirActividad_GrupoNulo_LanzaExcepcion() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        Grupo g = null;
        assertThrows(ClubException.class, () -> club.anyadirActividad(g));
    }

    @Test
    @DisplayName("Añadir una actividad correctamente")
    void anyadirActividad_ValoresCorrectos_AgregaActividad() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        club.anyadirActividad(new Grupo("1","prueba",4,4,10));
        assertEquals("prueba --> [ (1 - prueba - 10.0 euros - P:4 - M:4) ]", club.toString());
    }

    @Test
    @DisplayName("Añadir una actividad correctamente con String[]")
    void anyadirActividad_ConString_AgregaActividad() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        club.anyadirActividad(new String[]{"1", "prueba", "10", "5", "40.0"});
        assertEquals("prueba --> [ (1 - prueba - 40.0 euros - P:10 - M:5) ]", club.toString());
    }

    @Test
    @DisplayName("Añadir actividad existente actualiza plazas")
    void anyadirActividad_ActividadExistente_ActualizaPlazas() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        club.anyadirActividad(new Grupo("1", "prueba", 10, 5, 40.0));
        club.anyadirActividad(new Grupo("1", "prueba", 20, 5, 40.0));
        assertEquals("prueba --> [ (1 - prueba - 40.0 euros - P:20 - M:5) ]", club.toString());
    }

    @Test
    @DisplayName("Añadir grupos de mas lanza una excepcion")
    void anyadirActividad_GruposDeMas_LanzaExcepcion() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba", 1);
        club.anyadirActividad(new Grupo("1", "prueba", 10, 5, 40.0));
        assertThrows(ClubException.class, () -> club.anyadirActividad(new Grupo("2", "prueba", 20, 5, 40.0)));
    }

    @Test
    @DisplayName("No hay plazas suficientes para matricular")
    void matricular_NoPlazas_LanzaExcepcion() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        club.anyadirActividad(new Grupo("1","prueba",4,4,10));
        assertThrows(ClubException.class, () -> club.matricular("prueba", 5));
    }

    

    @Test
    @DisplayName("Matriculación correcta con plazas suficientes")
    void matricular_PlazasDisponibles_MatriculaExitosa() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        club.anyadirActividad(new Grupo("1", "prueba", 10, 5, 40.0));
        club.matricular("prueba", 3);
        assertEquals(2, club.plazasLibres("prueba"));
    }

    @Test
    @DisplayName("Matriculación con cero personas no modifica plazas")
    void matricular_CeroPersonas_NoCambio() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        club.anyadirActividad(new Grupo("1", "prueba", 10, 5, 40.0));
        club.matricular("prueba", 0);
        assertEquals(5, club.plazasLibres("prueba"));
    }

    @Test
    @DisplayName("Matricular personas en varios grupos correctamente")
    void matricular_RepartoEntreGrupos_Exitosa() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        club.anyadirActividad(new Grupo("1", "prueba",8, 4, 40.0));
        club.anyadirActividad(new Grupo("2", "prueba", 10, 2, 40.0));
        club.matricular("prueba", 8);
        assertEquals(4, club.plazasLibres("prueba"));
    }

    
    @Test
    @DisplayName("Matricular en actividad inexistente no cambia plazas")
    void matricular_ActividadInexistente_NoCambio() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        club.anyadirActividad(new Grupo("1", "prueba",8, 4, 40.0));
        club.matricular("prueba123", 0);
        assertEquals(0, club.plazasLibres("prueba123"));
    }

    @Test
    @DisplayName("No se encuentra un grupo con la actividad especificada")
    void matricular_ActividadNoCoincide_NoMatricula() throws ClubException {
        ClubDeportivo club = new ClubDeportivo("prueba");
        club.anyadirActividad(new Grupo("1", "futbol", 10, 5, 20.0));
        club.anyadirActividad(new Grupo("2", "baloncesto", 8, 4, 15.0));
        club.matricular("baloncesto", 1);
        assertEquals(5, club.plazasLibres("futbol"));
    }

    @Test
    @DisplayName("Revisar ingresos sin actividades")
    void ingresos_SinActividades_DevuelveCero() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        assertEquals(0.0, club.ingresos());
    }

    @Test
    @DisplayName("Revisar ingresos con una actividad")
    void ingresos_UnaActividad_CalculaCorrecto() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        club.anyadirActividad(new Grupo("1", "prueba", 10, 5, 40.0));
        assertEquals(200.0, club.ingresos());
    }

    @Test
    @DisplayName("Mostrar club sin grupos devuelve string vacío")
    void toString_SinGrupo_DevuelveCadenaVacia() throws ClubException{
        ClubDeportivo club = new ClubDeportivo("prueba");
        assertEquals("prueba --> [  ]", club.toString());
    }
}
