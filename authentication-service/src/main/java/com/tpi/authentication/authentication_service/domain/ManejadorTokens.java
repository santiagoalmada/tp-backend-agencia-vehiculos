package com.tpi.authentication.authentication_service.domain;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.*;
import org.jose4j.jwt.consumer.*;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ManejadorTokens {

    private final String nombre;
    private final String algoritmo;
    private final String secreto;

    private final long segundosValidez;
    private final HmacKey hmacKey;

    public ManejadorTokens(String nombreLlave, String algoritmo, String secreto, long validezSegundos) {
        this.nombre = nombreLlave;
        this.algoritmo = algoritmo;
        this.secreto = secreto;
        this.segundosValidez = validezSegundos;
        this.hmacKey = new HmacKey(this.secreto.getBytes(StandardCharsets.UTF_8));
    }

    public String crearToken(String usuario) throws JoseException {

        // Momento en el que se genera el token
        var issuedAt = NumericDate.now();
        // Fecha de expiración del token, se calcula como issuedAt + segundosValidez
        var expiresAt = NumericDate.fromSeconds(issuedAt.getValue());
        expiresAt.addSeconds(segundosValidez);

        // Creación de los claims. Son datos que va a contener el token
        JwtClaims claims = new JwtClaims();
        claims.setSubject(usuario);
        claims.setIssuedAt(issuedAt);
        claims.setExpirationTime(expiresAt);

        // Se crea el token con una firma
        JsonWebSignature jws = new JsonWebSignature();
        // La firma se realiza a partir de una llave y aplicando un cierto algoritmo
        jws.setKeyIdHeaderValue(nombre); // El nombre de la llave
        jws.setKey(hmacKey); // La llave secreta
        jws.setAlgorithmHeaderValue(algoritmo); // El algoritmo a utilizar
        // Hay que setear los claims creados anteriormente al token
        jws.setPayload(claims.toJson());

        // Se serializa el token en su forma compacta para poder transmitirlo
        return jws.getCompactSerialization();
    }

    // LO PODEMOS VALIDAR ACA O EN CADA SERVICIO QUE NECESITE VALIDAR EL TOKEN
    public Map<String, Object> validarToken(String token) throws InvalidJwtException {
        var jwtConsumer = new JwtConsumerBuilder() // Se crea un consumidor de tokens
                .setRequireExpirationTime() // requiere que el token tenga una fecha de expiración
                .setRequireSubject() // requiere que el token tenga un sujeto
                .setVerificationKey(hmacKey) // La llave secreta, es la misma hmacKey para verificar la firma
                .setJwsAlgorithmConstraints( // restricciones para permitir solo el algoritmo configurado
                        AlgorithmConstraints.ConstraintType.PERMIT, algoritmo
                )
                .build();

        // verifica la validez del token y devuelve los claims
        JwtClaims claims = jwtConsumer.processToClaims(token);

        // Se devuelven los claims como un mapa
        return claims.getClaimsMap();
    }
}
