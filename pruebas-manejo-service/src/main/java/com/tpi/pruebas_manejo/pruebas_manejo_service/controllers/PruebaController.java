package com.tpi.pruebas_manejo.pruebas_manejo_service.controllers;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.CrearPruebaRequestDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.FinalizarPruebaRequestDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Prueba;
import com.tpi.pruebas_manejo.pruebas_manejo_service.services.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pruebas")
public class PruebaController {
    @Autowired
    private PruebaService pruebaService;


    // Endpoint N1: Crear nueva prueba:
    // Se recibe (json):
    // - interesadoId: ID del interesado
    // - vehiculoId: ID del vehículo
    // - empleadoId: ID del empleado
    @PostMapping("/nueva")
    public ResponseEntity<String> crearPrueba(@RequestBody CrearPruebaRequestDTO request) {
        try {
            pruebaService.crearPrueba(request);
            return ResponseEntity.ok("Prueba creada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // Endpoint N2: Listar Pruebas en curso:
    @GetMapping("/en-curso")
    public List<Prueba> getPruebasEnCurso() {
        return pruebaService.getPruebasEnCurso();
    }



    // Endpoint N3: Finalizar prueba:
    // Se recibe (json):
    // - pruebaId: ID de la prueba a finalizar
    // - Comentarios
    @PutMapping("/finalizar")
    public ResponseEntity<String> finalizarPrueba(@RequestBody FinalizarPruebaRequestDTO request) {
        try {
            pruebaService.finalizarPrueba(request);
            return ResponseEntity.ok("Prueba finalizada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
