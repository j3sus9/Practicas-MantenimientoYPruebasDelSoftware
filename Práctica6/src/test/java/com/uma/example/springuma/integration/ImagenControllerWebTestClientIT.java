/*
 * Jesús Repiso Rio
 * Alejandro Cueto Díaz
 */

package com.uma.example.springuma.integration;

import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import jakarta.annotation.PostConstruct;
import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.Paciente;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImagenControllerWebTestClientIT {

    @LocalServerPort
    private int port;

    private WebTestClient client;

    @PostConstruct
    public void init() {
        client = WebTestClient.bindToServer()
            .baseUrl("http://localhost:" + port)
            .build();
    }

    @Test
    @DisplayName("Sube una imagen para un paciente correctamente")
    void uploadImage_shouldSucceed_whenValidData() throws Exception {
    

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Filemon");
        paciente.setEdad(30);
        paciente.setDni("12345678B");

        client.post()
            .uri("/paciente")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(paciente))
            .exchange()
            .expectStatus().isCreated();

        ClassPathResource imagen = new ClassPathResource("healthy.png");
        File archivoSubida = imagen.getFile();

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(archivoSubida));
        builder.part("paciente", paciente);

        client.post()
            .uri("/imagen")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.response").value(resp -> {
                assertTrue(resp.toString().contains("file uploaded successfully"));
            });
    }

    @Test
    @DisplayName("Realiza una predicción sobre una imagen existente")
    void predictImage_shouldReturnStatusAndScore_whenValidImageId() throws Exception {
    
       //Creacion paciente e imagen
        Paciente paciente = new Paciente();
        paciente.setId(1L); 
        paciente.setNombre("Ofelia");
        paciente.setEdad(50);
        paciente.setDni("12345678D");

        client.post()
            .uri("/paciente")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(paciente))
            .exchange()
            .expectStatus().isCreated();

        ClassPathResource imagen = new ClassPathResource("healthy.png");
        File archivoSubida = imagen.getFile();

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("image", new FileSystemResource(archivoSubida));
            builder.part("paciente", paciente);
                    
        client.post()
            .uri("/imagen")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .returnResult()
            .getResponseBody();

        //Obtener la imagen asociada al paciente
        List<Imagen> imagenes = client.get()
            .uri("/imagen/paciente/1")
            .exchange()
            .expectStatus().isOk()
            .returnResult(Imagen.class)
            .getResponseBody()
            .collectList()
            .block();

        assertNotNull(imagenes);
        assertFalse(imagenes.isEmpty());

        Long imagenId = imagenes.get(0).getId();

        // Realizar la predicción
        client.get()
            .uri("/imagen/predict/" + imagenId)
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .consumeWith(result -> {
                String json = result.getResponseBody();
                assertNotNull(json);
                assertTrue(json.contains("Cancer") || json.contains("Not cancer"));
                assertTrue(json.contains("score"));
            });
    }
    
}
