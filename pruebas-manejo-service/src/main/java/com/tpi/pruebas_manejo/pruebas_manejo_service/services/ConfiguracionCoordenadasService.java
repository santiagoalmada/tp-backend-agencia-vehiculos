package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.ConfiguracionCoordenadasDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

// En este Service se obtiene la configuración de las coordenadas de la agencia
// Consumiendo un servicio REST externo.

@Service
public class ConfiguracionCoordenadasService {
    @Autowired
    private RestTemplate restTemplate;

    private final String API_URL = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

    public ConfiguracionCoordenadasDTO obtenerConfiguracion() {
        try {
            // Realizamos la solicitud GET
            ConfiguracionCoordenadasDTO configuracion = restTemplate.getForObject(
                    API_URL, // URL del endpoint
                    ConfiguracionCoordenadasDTO.class // Tipo de respuesta esperada
            );

            // Verificar si la configuración es nula
            if (configuracion == null) {
                throw new RuntimeException("No se recibió ninguna configuración desde el servidor de la agencia.");
            }

            return configuracion;

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Manejar errores de cliente (4xx) o servidor (5xx)
            throw new RuntimeException("Error al obtener la configuración de la agencia: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            // Manejar cualquier otro tipo de excepción
            throw new RuntimeException("Error inesperado al intentar obtener la configuración de la agencia: " + e.getMessage(), e);
        }
    }

}
