package com.tpi.pruebas_manejo.pruebas_manejo_service.utils;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Zona {
    private Coordenada noroeste;
    private Coordenada sureste;

    public Zona(Coordenada noroeste, Coordenada sureste) {
        this.noroeste = noroeste;
        this.sureste = sureste;
    }
}
