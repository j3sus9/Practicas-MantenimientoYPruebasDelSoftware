package com.uma.example.springuma.integration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;

import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;

public class PacienteControllerMockMvcIT extends AbstractIntegration{
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Guardar un paciente y ver que se ha guardado")
    public void savePaciente_NotExists_SavesPaciente() throws Exception {

        Medico medico = new Medico(1L, "12345678A", "Mortadelo", "Radiología");

        mockMvc.perform(post("/medico")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isCreated());

        Paciente paciente = new Paciente("Filemon", 30, "2025-05-20", "12345678B", medico);

        // Guardar paciente
         mockMvc.perform(post("/paciente")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isCreated());

        // Comprobar que se ha guardado
         mockMvc.perform(get("/paciente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Filemon"))
                .andExpect(jsonPath("$.edad").value(30))
                .andExpect(jsonPath("$.dni").value("12345678B"))
                .andExpect(jsonPath("$.medico.nombre").value("Mortadelo"));
    }

    @Test
    @DisplayName("Asociar paciente a médico, editar paciente y cambiar médico")
    public void associateAndUpdatePaciente_ChangesMedico() throws Exception {

        //Crear médicos y pacientes

        Medico medico1 = new Medico(1L, "12345678A", "Mortadelo", "Radiología");
        mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(medico1)))
        .andExpect(status().isCreated());

        Medico medico2 = new Medico(2L, "12345678C", "Bacterio", "Cardiología");
        mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(medico2)))
        .andExpect(status().isCreated());

        Paciente paciente = new Paciente("Filemon", 30, "2025-05-20", "12345678B", medico1);
        mockMvc.perform(post("/paciente")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(paciente)))
        .andExpect(status().isCreated());

        //Comprobar que el paciente está asociado al médico 1

        mockMvc.perform(get("/paciente/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.medico.id").value(1))
        .andExpect(jsonPath("$.medico.nombre").value("Mortadelo"));

        // Cambiar datos del paciente y asignar medico2

        paciente.setId(1L);
        paciente.setNombre("Filemon II");
        paciente.setMedico(medico2);

        // Actualizar paciente

         mockMvc.perform(put("/paciente")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(paciente)))
        .andExpect(status().isNoContent());

        //Comprobar que el paciente está asociado al médico 2 y que se ha actualizado el nombre

         mockMvc.perform(get("/paciente/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nombre").value("Filemon II"))
        .andExpect(jsonPath("$.medico.id").value(2))
        .andExpect(jsonPath("$.medico.nombre").value("Bacterio"));
        }   

}
