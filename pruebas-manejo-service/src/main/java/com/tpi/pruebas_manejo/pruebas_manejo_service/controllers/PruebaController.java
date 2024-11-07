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
@RequestMapping("/pruebas")
public class PruebaController {
    @Autowired
    private PruebaService pruebaService;
    // Endpoint N1: Crear nueva prueba:

    // Metodo: POST
    // Path: /pruebas/nueva

    // Se recibe:
    // DTO con los datos de la prueba a crear:
    // - interesadoId: ID del interesado (cliente)
    // - vehiculoId: ID del vehículo
    // - empleadoId: ID del empleado que realiza la prueba

    // Validaciones:
    //  - El interesado no debe tener la licencia vencida.
    //  - El interesado no debe estar restringido para realizar pruebas.
    //  - El vehículo no debe estar siendo probado en ese mismo momento.

    // Pasos:
    // - Crear una nueva prueba con los datos recibidos (DTO).
    // - Asignar el Vehículo y el Interesado a la prueba.
    // - Realizar las validaciones de antes.
    // - Guardar la prueba en la base de datos.

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
    // Metodo: GET
    // Path: /pruebas/en-curso
    @GetMapping("/en-curso")
    public List<Prueba> getPruebasEnCurso() {
        return pruebaService.getPruebasEnCurso();
    }

    // Endpoint N3: Finalizar prueba:
    // Metodo: POST
    // Path: /pruebas/finalizar
    // Se recibe:
    // - pruebaId: ID de la prueba a finalizar
    // - Comentarios

    @PostMapping("/finalizar")
    public ResponseEntity<String> finalizarPrueba(@RequestBody FinalizarPruebaRequestDTO request) {
        try {
            pruebaService.finalizarPrueba(request);
            return ResponseEntity.ok("Prueba finalizada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
