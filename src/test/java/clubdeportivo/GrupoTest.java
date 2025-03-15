/*
 * Jesús Repiso Rio
 * Alejandro Cueto Díaz
 */

package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GrupoTest {

    @Test
    @DisplayName("Si los valores del constructor son correctos, se inicializa correctamente el grupo")
    void constructor_ParametrosCorrectos_GrupoInicializado() throws ClubException{
        String codigo = "G01";
        String actividad = "A1";
        int nplazas = 10;
        int matriculados = 2;
        double tarifa = 10.0;
        Grupo g = new Grupo(codigo, actividad, nplazas, matriculados, tarifa);

        assertEquals(codigo, g.getCodigo());
        assertEquals(actividad, g.getActividad());
        assertEquals(nplazas, g.getPlazas());
        assertEquals(matriculados, g.getMatriculados());
        assertEquals(tarifa, g.getTarifa());
    }

    @Test
    @DisplayName("Valores incorrectos del constructor hacen que se lance una excepción")
    void constructor_ParametrosIncorrectos_LanzaExcepcion(){
        assertThrows(ClubException.class, ()->new Grupo("G01", "A1", 0, 2, 10.0));
        assertThrows(ClubException.class, ()->new Grupo("G01", "A1", 10, -3, 10.0));
        assertThrows(ClubException.class, ()->new Grupo("G01", "A1", 10, 2, 0.0));
    }

    @Test
    @DisplayName("Si los matriculados son más que el  número de plazas se lanza una excepción")
    void constructor_MatriculadosMayorQuePlazas_LanzaExcepcion(){
        assertThrows(ClubException.class, ()->new Grupo("G01", "A1", 1, 2, 10.0));
    }

    @Test
    @DisplayName("Comprobar que las plazas libres se calculan bien")
    void plazasLibres_SinCalcular_Calculadas() throws ClubException{
        Grupo grupo = new Grupo("G01", "A1", 10, 4, 10.0);
        assertEquals(6, grupo.plazasLibres());
    }

    @Test
    @DisplayName("Si el numero de plazas es menor o igual que 0 o menos que el numero de matriculados se lanza una excepción")
    void actualizarPlazas_ValorIncorrecto_LanzaExcepcion() throws  ClubException{
        Grupo grupo = new Grupo("G01", "A1", 10, 4, 10.0);
        assertThrows(ClubException.class, ()->grupo.actualizarPlazas(3));
        assertThrows(ClubException.class, ()->grupo.actualizarPlazas(0));
    }

    @Test
    @DisplayName("Si el numero de plazas a matricular es menor o igual que 0 o hay menos plazas libres que éstas, se lanza una excepcion")
    void matricular_ValorIncorrecto_LanzaExcepcion() throws ClubException{
        Grupo grupo = new Grupo("G01", "A1", 10, 4, 10.0);
        assertThrows(ClubException.class, ()->grupo.matricular(8));
        assertThrows(ClubException.class, ()->grupo.matricular(0));
    }

    @Test
    @DisplayName("Comprobar que el string devuelto es correcto")
    void toString_Grupo_FormatoCorrecto() throws ClubException{
        Grupo grupo = new Grupo("G01", "A1", 10, 4, 10.0);
        assertEquals(grupo.toString(), "(G01 - A1 - 10.0 euros - P:10 - M:4)");
    }

    @Test
    @DisplayName("Comprobar el correcto funcionamiento del método equals")
    void equals_ObjetosIguales_DevuelveTrue() throws ClubException{
        Grupo grupo = new Grupo("G01", "A1", 10, 4, 10.0);
        Grupo newGrupo = new Grupo("G01", "A1", 20, 10, 30.0);
        assertEquals(true, grupo.equals(newGrupo));
    }

    @Test
    @DisplayName("Comprobar el funcionamiento del método equals cuando son dos objetos de tipo diferente")
    void equals_DistintoTipo_DevuelveFalse() throws ClubException{
        Grupo grupo = new Grupo("G01", "A1", 10, 4, 10.0);
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Elite", 10, 20.0);
        assertEquals(false, grupo.equals(club));
    }

    @Test
    @DisplayName("Comprobar el funcionamiento del método equals cuando son dos objetos grupo con código diferente")
    void equals_DistintoCodigo_DevuelveFalse() throws ClubException{
        Grupo grupo = new Grupo("G01", "A1", 10, 4, 10.0);
        Grupo newGrupo = new Grupo("G02", "A1", 20, 10, 30.0);
        assertEquals(false, grupo.equals(newGrupo));
    }

    @Test
    @DisplayName("Comprobar el funcionamiento del método equals cuando son dos objetos grupo con código diferente")
    void equals_DistintaActividad_DevuelveFalse() throws ClubException{
        Grupo grupo = new Grupo("G01", "A1", 10, 4, 10.0);
        Grupo newGrupo = new Grupo("G01", "A2", 20, 10, 30.0);
        assertEquals(false, grupo.equals(newGrupo));
    }

    @Test
    @DisplayName("Comprobar el correcto funcionamiento del método hashCode")
    void hashCode_Grupo_GeneradoCorrectamente() throws ClubException{
        Grupo grupo = new Grupo("G01", "A1", 10, 4, 10.0);
        assertEquals(71832, grupo.hashCode());
    }
}
