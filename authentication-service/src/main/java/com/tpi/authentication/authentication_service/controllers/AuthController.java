package com.tpi.authentication.authentication_service.controllers;

import com.tpi.authentication.authentication_service.domain.ManejadorTokens;
import com.tpi.authentication.authentication_service.controllers.response.TokenDTO;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.base64url.Base64;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    // Prefijo para el header de autorización
    private static final String PREFIJO_BASIC = "Basic ";
    private final ManejadorTokens manejadorTokens;

    @Autowired
    public AuthController(ManejadorTokens mt) {
        manejadorTokens = mt;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> autenticar(@RequestHeader Map<String, String> headers) {
        // Extraer el header de autorización para validar el usuario
        var headerAutenticacion = headers.get("authorization");

        // Revisar que el header esté presente
        if (headerAutenticacion == null || !headerAutenticacion.startsWith(PREFIJO_BASIC)) {
            log.warn("Autenticación fallida");
            throw new IllegalArgumentException("Header de autorización inválido");
        }

        // Extraer las credenciales
        // Eliminar el prefijo Basic
        var credencialesB64 = headerAutenticacion.replace(PREFIJO_BASIC, "");
        // Decodifica las credenciales y las separa en usuario y password
        var credenciales = new String(Base64.decode(credencialesB64), StandardCharsets.UTF_8).split(":", 2);

        // Tiene que haber dos componentes usuario y password
        if (credenciales.length != 2) {
            log.warn("Se encontraron {} componentes en el header de autenticación.", credenciales.length);
            throw new IllegalArgumentException("Header de autorización inválido");
        }

        var usuario = credenciales[0];
        var password = credenciales[1];

        log.info("Autenticando al usuario: {}", usuario);

        // CAMBIAR POR NUESTRA IMPLEMENTACION EN BUSQUEDA DE USUARIO Y PASSWORD
        if (usuario.equals("usuario123") && password.equals("password123")) {
            try {
                return ResponseEntity.ok(new TokenDTO(manejadorTokens.crearToken(usuario)));
            } catch (JoseException ex) {
                log.error("Problemas generando el token", ex);
                return ResponseEntity.internalServerError().build();
            }
        }

        log.warn("Autenticación no exitosa para el usuario: {}", usuario);
        throw new IllegalArgumentException("Usuario/Password incorrectos!");
    }

}
