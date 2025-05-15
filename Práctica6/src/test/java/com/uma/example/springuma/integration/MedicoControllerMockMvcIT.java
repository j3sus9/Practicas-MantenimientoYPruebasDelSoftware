package com.uma.example.springuma.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Medico;

public class MedicoControllerMockMvcIT extends AbstractIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Guardar un médico y ver que se ha guardado")
    public void saveMedico_NotExists_SavesMedico() throws Exception {
        Medico medico = new Medico(1L, "23567654J", "Jesus", "Radiología");

        // Guardar médico
        this.mockMvc.perform(post("/medico")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isCreated())
            .andExpect(status().is2xxSuccessful());

        // Comprobar que se ha guardado
        this.mockMvc.perform(get("/medico/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.dni").value("23567654J"))
            .andExpect(jsonPath("$.nombre").value("Jesus"))
            .andExpect(jsonPath("$.especialidad").value("Radiología"));
    }

    @Test
    @DisplayName("Actualizar un médico y ver que se ha actualizado")
    public void updateMedico_Exists_UpdatesMedico() throws Exception {
        Medico medico = new Medico(1L, "23567654J", "Jesus", "Radiología");

        // Guardar médico
        this.mockMvc.perform(post("/medico")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isCreated());

        Medico medicoActualizado = new Medico(1L, "23567654N", "Manolo", "Osteopatía");

        // Actualizar médico
        this.mockMvc.perform(put("/medico")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(medicoActualizado)))
            .andExpect(status().is2xxSuccessful());

        // Comprobar que se ha actualizado
        this.mockMvc.perform(get("/medico/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.dni").value("23567654N"))
            .andExpect(jsonPath("$.nombre").value("Manolo"))
            .andExpect(jsonPath("$.especialidad").value("Osteopatía"));
    }

    @Test
    @DisplayName("Obtener un médico")
    public void getMedicoById_Exists_GetsMedico() throws Exception {
        Medico medico = new Medico(1L, "23567654J", "Jesus", "Radiología");

        // Guardar médico
        this.mockMvc.perform(post("/medico")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isCreated());

        // Obtener médico y comprobar valores
        this.mockMvc.perform(get("/medico/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.dni").value("23567654J"))
            .andExpect(jsonPath("$.nombre").value("Jesus"))
            .andExpect(jsonPath("$.especialidad").value("Radiología"));
    }

    @Test
    @DisplayName("Obtener un médico")
    public void getMedicoByDni_Exists_GetsMedico() throws Exception {
        Medico medico = new Medico(1L, "23567654J", "Jesus", "Radiología");

        // Guardar médico
        this.mockMvc.perform(post("/medico")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isCreated());

        // Obtener médico y comprobar valores
        this.mockMvc.perform(get("/medico/dni/23567654J"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.dni").value("23567654J"))
            .andExpect(jsonPath("$.nombre").value("Jesus"))
            .andExpect(jsonPath("$.especialidad").value("Radiología"));
    }

    @Test
    @DisplayName("Eliminar un médico y ver que se ha eliminado")
    public void deleteMedico_Exists_DeletesMedico() throws Exception {
        Medico medico = new Medico(1L, "23567654J", "Jesus", "Radiología");

        // Guardar médico
        this.mockMvc.perform(post("/medico")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isCreated());

        // Eliminar médico
        this.mockMvc.perform(delete("/medico/1"))
            .andExpect(status().isOk());

        // Comprobar que se ha eliminado
        this.mockMvc.perform(get("/medico/1"))
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType("application/json"));
    }
}