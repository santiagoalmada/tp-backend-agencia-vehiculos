package com.tpi.authentication.authentication_service.config;

import com.tpi.authentication.authentication_service.domain.ManejadorTokens;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ManejadorTokens crearManejadorTokens(
            @Value("llave-jijeada") String nombreLlave,
            @Value("HS256") String algoritmo,
            @Value("LLAVE_LA_MAS_SECRETA_DE_TODAS") String secreto,
            @Value("300") long duracionSegundos) {

        return new ManejadorTokens(nombreLlave, algoritmo, secreto, duracionSegundos);
    }
}
