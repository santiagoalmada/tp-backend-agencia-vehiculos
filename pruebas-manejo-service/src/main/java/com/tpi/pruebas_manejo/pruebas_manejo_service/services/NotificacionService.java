package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.NotificacionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

// Este servicio se encarga de enviar notificaciones al microservicio de Notificaciones
// Para poder hacerlo, necesita tener el Rol de Empleado, se debe obtener un token de acceso con dicho Rol.

@Service
public class NotificacionService {

    private final RestTemplate restTemplate;

    @Value("${notificaciones.service.url}") // URL del microservicio de Notificaciones
    private String notificacionesServiceUrl;

    // Datos de Keycloak para obtener el token de acceso
    @Value("${keycloak.token.url}") // URL para obtener el token de Keycloak
    private String keycloakTokenUrl;
    @Value("${keycloak.client.id}")
    private String clientId;
    @Value("${keycloak.client.secret}")
    private String clientSecret;
    @Value("${keycloak.username-empleado}") // Usuario Empleado (por ejemplo, g001-B)
    private String keycloakUsername;
    @Value("${keycloak.password-empleado}") // Password del Empleado
    private String keycloakPassword;


    public NotificacionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String obtenerToken() {
        // Definir el header del POST a Keycloak
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // Indica el formato de cuerpo que espera Keycloak

        // Definir el cuerpo del POST con los parametros de autenticación
        String body = "grant_type=password"
                + "&client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&username=" + keycloakUsername
                + "&password=" + keycloakPassword;

        // Crear el HTTPEntity que se enviara a keycloak, uniéndo el header y el cuerpo
        HttpEntity<String> tokenRequest = new HttpEntity<>(body, headers);

        try {
            // Realizar una petición POST al endpoint de Keycloak para obtener el token de acceso
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
                    keycloakTokenUrl,   // URL del endpoint
                    tokenRequest,       // Datos de la petición (body + headers)
                    Map.class           // Esperamos una respuesta JSON (representada como un Map)
            );

            // Verificar si la respuesta fue exitosa (HTTP 200 OK)
            if (tokenResponse.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Error al obtener el token de acceso keycloack para poder enviar la notificacion al microservicio de Notificaciones: " + tokenResponse.getStatusCode());
            }

            // Extraer el token de la respuesta JSON
            Map<String, Object> tokenData = tokenResponse.getBody();
            if (tokenData != null){
                String accessToken = (String) tokenData.get("access_token"); // Acceder al campo "access_token"
                if (accessToken != null) {
                    return accessToken;
                }
            }
            throw new RuntimeException("Token keycloack no encontrado");


        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al obtener el token de acceso para poder enviar la notificacion al microservicio de Notificaciones: " + e.getMessage(), e);
        }
    }


    public void enviarNotificacion(NotificacionDTO notificacionDTO) {
        // Definir la URL del endpoint en el microservicio de Notificaciones
        String url = notificacionesServiceUrl + "/nueva";

        try {
            // Imprimir la notificación a enviar
            System.out.println(notificacionDTO.toString());

            // Obtener el token JWT de Keycloak
            String token = obtenerToken();

            // Definir las cabeceras, incluyendo el token de autorización
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Crear el objeto HttpEntity con el cuerpo y las cabeceras
            HttpEntity<NotificacionDTO> request = new HttpEntity<>(notificacionDTO, headers);

            // Realizar el POST hacia el microservicio de Notificaciones
            ResponseEntity<Void> response = restTemplate.postForEntity(
                    url, // URL del endpoint
                    request, // Datos de la petición (notificacionDTO + cabecera (con token))
                    Void.class // Tipo de respuesta esperada
            );

            // Verificar si la respuesta fue exitosa (HTTP 200 OK)
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Error al enviar la notificación de tipo " + notificacionDTO.getTipo() + ": " + response.getStatusCode());
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Manejar errores de cliente (4xx) o servidor (5xx)
            throw new RuntimeException("Error al enviar la notificación de tipo " + notificacionDTO.getTipo() + ": " + e.getMessage(), e);
        } catch (Exception e) {
            // Manejar cualquier otro tipo de excepción
            throw new RuntimeException("Error inesperado al intentar enviar la notificación de tipo " + notificacionDTO.getTipo() + ": " + e.getMessage(), e);
        }
    }

}
