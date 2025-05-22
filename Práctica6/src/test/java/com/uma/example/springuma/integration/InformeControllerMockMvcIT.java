/*
 * Jesús Repiso Rio
 * Alejandro Cueto Díaz
 */

package com.uma.example.springuma.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.hasSize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.Informe;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;
import com.uma.example.springuma.model.RepositoryImagen;
import com.uma.example.springuma.model.RepositoryMedico;
import com.uma.example.springuma.model.RepositoryPaciente;
import com.uma.example.springuma.utils.ImageUtils;

public class InformeControllerMockMvcIT extends AbstractIntegration{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepositoryMedico medicoRepository;

    @Autowired
    private RepositoryPaciente pacienteRepository;

    @Autowired
    private RepositoryImagen imagenRepository;

    private Informe crearInforme() throws Exception{
        Medico medico = medicoRepository.save(new Medico(1L, "23567654J", "Jesus", "Radiología"));
        
        Paciente paciente = pacienteRepository.save(new Paciente(1L, "Juan", 34, "12:00", "34567654J", medico));
        
        byte[] imageBytes = Files.readAllBytes(Paths.get("src/test/resources/healthy.png"));
        byte[] compressedImage = ImageUtils.compressImage(imageBytes);
        Imagen imagen = imagenRepository.save(new Imagen(1L, compressedImage, paciente));

        Informe informe = new Informe(1L, null, "No hay cáncer observable", imagen);

        return informe;
    }

    @Test
    @DisplayName("Guardar un informe y ver que se ha guardado")
    public void saveInforme_NotExists_SavesInforme() throws Exception{
        Informe informe = crearInforme();

        // Guardar informe
        this.mockMvc.perform(post("/informe")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(informe)))
            .andExpect(status().isCreated())
            .andExpect(status().is2xxSuccessful());

        // Comprobar que se ha guardado
        this.mockMvc.perform(get("/informe/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value("1"))
            //.andExpect(jsonPath("$.prediccion").value("{status: Cancer,  score: 0.9531985833663051}"))
            .andExpect(jsonPath("$.contenido").value("No hay cáncer observable"));
            //.andExpect(jsonPath("$.imagen").value(imagen));
    }

    @Test
    @DisplayName("Eliminar un informe y ver que se ha eliminado")
    public void deleteInforme_Exists_DeletesInforme() throws Exception {
        Informe informe = crearInforme();

        // Guardar informe
        this.mockMvc.perform(post("/informe")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(informe)))
            .andExpect(status().isCreated())
            .andExpect(status().is2xxSuccessful());

        // Eliminar médico
        this.mockMvc.perform(delete("/informe/1"))
            .andExpect(status().isNoContent());

        // Comprobar que se ha eliminado
        this.mockMvc.perform(get("/informe/1"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Guardar un informe y obtenerlo por su Id")
    public void getInformeByInformeId_Exists_GetsInforme() throws Exception {
        Informe informe = crearInforme();

        // Guardar informe
        this.mockMvc.perform(post("/informe")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(informe)))
            .andExpect(status().isCreated())
            .andExpect(status().is2xxSuccessful());

        // Obtener el informe
        this.mockMvc.perform(get("/informe/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value("1"))
            //.andExpect(jsonPath("$.prediccion").value("{status: Cancer,  score: 0.9531985833663051}"))
            .andExpect(jsonPath("$.contenido").value("No hay cáncer observable"));
            //.andExpect(jsonPath("$.imagen").value(imagen));
    }
    
    @Test
    @DisplayName("Obtener todos los informes de una imagen")
    public void getInformesByImagen_ReturnsListOfInformes() throws Exception {
        Medico medico = medicoRepository.save(new Medico("23567654J", "Jesus", "Radiología"));
        
        Paciente paciente = pacienteRepository.save(new Paciente("Juan", 34, "12:00", "34567654J", medico));
        
        byte[] imageBytes = Files.readAllBytes(Paths.get("src/test/resources/healthy.png"));
        byte[] compressedImage = ImageUtils.compressImage(imageBytes);
        Imagen imagen = imagenRepository.save(new Imagen(compressedImage, paciente));

        Informe informe1 = new Informe("Buena", "No hay cáncer observable", imagen);
        Informe informe2 = new Informe("Razonablemente buena", "No hay cáncer observable", imagen);

        // Guardar informe1
        this.mockMvc.perform(post("/informe")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(informe1)))
            .andExpect(status().isCreated())
            .andExpect(status().is2xxSuccessful());
        
        // Guardar informe2
        this.mockMvc.perform(post("/informe")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(informe2)))
            .andExpect(status().isCreated())
            .andExpect(status().is2xxSuccessful());

        // Obtener los informes
        this.mockMvc.perform(get("/informe/imagen/{id}", imagen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$", hasSize(2)));
    }
    
}


