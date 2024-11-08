package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.ConfiguracionCoordenadasDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// En este Service se obtiene la configuración de las coordenadas de la agencia
// Consumiendo un servicio REST externo.

@Service
public class ConfiguracionCoordenadasService {
    @Autowired
    private RestTemplate restTemplate;

    private final String API_URL = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

    public ConfiguracionCoordenadasDTO obtenerConfiguracion() {
        return restTemplate.exchange(
                API_URL, // URL
                HttpMethod.GET, // Metodo
                null, // Body (no hay por ser GET)
                ConfiguracionCoordenadasDTO.class // Tipo de dato de la respuesta
        ).getBody(); // Obtenemos el cuerpo de la respuesta
    }

}
