package com.tpi.pruebas_manejo.pruebas_manejo_service.controllers;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.PosicionDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.services.PosicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/pruebas/posicion")
public class PosicionController {
    @Autowired
    private PosicionService PosicionService;
    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarPosicion(@RequestBody PosicionDTO request) {
        try {
            PosicionService.actualizarPosicion(request);
            return ResponseEntity.ok("Posicion recibida.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
