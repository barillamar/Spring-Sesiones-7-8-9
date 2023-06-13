package com.example.Sesiones_7_8_9.controllers;

import com.example.Sesiones_7_8_9.entities.Laptop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @DisplayName("Comprobar encontrar todas las laptops desde Spring Boot Rest")
    @Test
    void findAll() {
        ResponseEntity<Laptop[]> response = testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findOneById() {
        ResponseEntity<Laptop> response =
                testRestTemplate.getForEntity("/api/laptops/1", Laptop.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void create() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "marca": "Apple",
                    "modelo": "MacBook Mini",
                    "precio": 1499.9,
                    "ram": 8,
                    "almacenamiento": 395
                }
                """;

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<Laptop> response =
                testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);

        Laptop result = response.getBody();

        assertEquals(1L, result.getId());
        assertEquals("MacBook Mini", result.getModelo());
    }

    @Test
    void update() {
        Laptop laptop = new Laptop();
        laptop.setId(1L);
        laptop.setMarca("Apple");
        laptop.setModelo("Macbook Pro");
        laptop.setPrecio(1999.9);
        laptop.setRam(16);
        laptop.setAlmacenamiento(512);

        // Realizar una solicitud PUT para actualizar la laptop
        ResponseEntity<Void> response = testRestTemplate.exchange("/api/laptops", HttpMethod.PUT, new HttpEntity<>(laptop), Void.class);

        // Verificar el estado Http
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar que la laptop se haya actualizado correctamente
        ResponseEntity<Laptop> updatedResponse =
                testRestTemplate.getForEntity("/api/laptops/1", Laptop.class);

        Laptop updatedLaptop = updatedResponse.getBody();

        assertEquals("Macbook Pro", updatedLaptop.getModelo());
        assertEquals(16, updatedLaptop.getRam());

    }

    @Test
    void delete() {
        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/api/laptops/1", Laptop.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteAll() {
        Laptop laptop = new Laptop();
        laptop.setId(1L);
        laptop.setMarca("Apple");
        laptop.setModelo("Macbook Pro");
        laptop.setPrecio(1999.9);
        laptop.setRam(16);
        laptop.setAlmacenamiento(512);


        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.DELETE, new HttpEntity<>(laptop), Laptop.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
