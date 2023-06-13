package com.example.Sesiones_7_8_9.controllers;

import com.example.Sesiones_7_8_9.entities.Laptop;
import com.example.Sesiones_7_8_9.repositories.LaptopRepository;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {
    private final Logger log = LoggerFactory.getLogger(LaptopController.class);

    private LaptopRepository laptopRepository;

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }


    @ApiOperation("Buscar todas las laptops")
    @GetMapping("/api/laptops")
    public List<Laptop> findAll() {
        return laptopRepository.findAll();
    }

    @ApiOperation("Buscar una laptop por su id")
    @GetMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> findOneById(@PathVariable Long id) {

        Optional<Laptop> laptopOpt = laptopRepository.findById(id);

        if (laptopOpt.isPresent()) {
            return ResponseEntity.ok(laptopOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation("Crear una laptop")
    @PostMapping("/api/laptops")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop, @RequestHeader HttpHeaders headers) {
        System.out.println(headers.get("User-Agent"));

        if (laptop.getId() != null) {
            log.warn("trying to create a laptop who already exists");
            return ResponseEntity.badRequest().build();
        }

        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);
    }

    @ApiOperation("Actualizar una laptop")
    @PutMapping("/api/laptops")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop) {
        if (laptop.getId() == null) {
            log.warn("Trying to update a non existent laptop");
            return ResponseEntity.notFound().build();
        }

        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);
    }

    @ApiIgnore
    @DeleteMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id) {

        if (!laptopRepository.existsById(id)) {
            log.warn("Trying to delete a non existent laptop");
            return ResponseEntity.notFound().build();
        }

        laptopRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @ApiIgnore
    @DeleteMapping("/api/laptops")
    public ResponseEntity<Laptop> deleteAll() {
        log.info("Rest Request to delete all book");
        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
